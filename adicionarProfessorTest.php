<?php

namespace Tests\Feature;

use Illuminate\Foundation\Testing\RefreshDatabase;
use Illuminate\Support\Facades\DB;
use Tests\TestCase;
use Illuminate\Support\Facades\Hash;

class ProfessorControllerTest extends TestCase
{
    use RefreshDatabase;

    /** @test */
    public function pode_criar_um_professor_com_dados_validos()
    {
        // Dados para criar o professor
        $data = [
            'nome' => 'Professor Teste',
            'email' => 'professor@teste.com',
            'password' => 'senha123',
        ];

        // Envia requisição para criar o professor
        $response = $this->post('/professor/criar', $data);

        // Verifica se a resposta redireciona corretamente com sucesso
        $response->assertStatus(302); // Redireciona após o sucesso
        $response->assertSessionHas('message', json_encode(['success' => true, 'message' => 'Professor registrado com sucesso.']));

        // Confirma que o professor foi criado no banco de dados
        $this->assertDatabaseHas('users', [
            'name' => $data['nome'],
            'email' => $data['email'],
            'role' => 2, // Role de professor
        ]);
    }

    /** @test */
    public function nao_pode_criar_professor_com_email_duplicado()
    {
        // Insere um professor com o mesmo e-mail no banco de dados
        DB::table('users')->insert([
            'name' => 'Professor Existente',
            'email' => 'professor@teste.com',
            'role' => 2,
            'password' => Hash::make('senha123'),
        ]);

        // Dados para tentar criar outro professor com o mesmo e-mail
        $data = [
            'nome' => 'Outro Professor',
            'email' => 'professor@teste.com',
            'password' => 'senha123',
        ];

        // Envia requisição para criar o professor
        $response = $this->post('/professor/criar', $data);

        // Verifica se a resposta indica falha devido ao e-mail duplicado
        $response->assertStatus(302); // Redireciona após a tentativa
        $response->assertSessionHas('message', json_encode(['success' => false, 'message' => 'O professor já está registrado.']));

        // Confirma que o banco de dados não tem duplicatas
        $this->assertEquals(1, DB::table('users')->where('email', $data['email'])->count());
    }

    /** @test */
    public function nao_pode_criar_professor_com_dados_invalidos()
    {
        // Dados inválidos para criação
        $data = [
            'nome' => '',              // Nome ausente
            'email' => 'emailinvalido', // E-mail inválido
            'password' => '',           // Senha ausente
        ];

        // Envia requisição para criar o professor
        $response = $this->post('/professor/criar', $data);

        // Verifica se a resposta indica falha devido aos dados inválidos
        $response->assertStatus(302); // Redireciona após a tentativa
        $response->assertSessionHas('message', json_encode(['success' => false, 'message' => 'Parâmetros inválidos.']));

        // Confirma que o banco de dados não registou nenhum professor
        $this->assertDatabaseMissing('users', ['email' => $data['email']]);
    }
}
