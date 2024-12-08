<?php

namespace Tests\Feature;

use Illuminate\Foundation\Testing\RefreshDatabase;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;
use Tests\TestCase;
use App\Models\User;

class AlunoControllerTest extends TestCase
{
    use RefreshDatabase;

    public function test_adicionar_aluno_com_sucesso()
    {
        // Simula um curso no banco de dados
        $curso = DB::table('cursos')->insertGetId([
            'nome' => 'Curso Teste',
        ]);

        // Envia a requisição para criar aluno
        $response = $this->post('/admin/aluno/criarAluno', [
            'nome' => 'José Antunes',
            'email' => 'jose.antunes@example.com',
            'password' => 'senhaSegura123',
            'curso' => $curso,
        ]);

        // Verifica se o redirecionamento ocorreu
        $response->assertRedirect();

        // Verifica se a mensagem de sucesso está na sessão
        $response->assertSessionHas('message', json_encode(['success' => true, 'message' => 'Aluno registrado com sucesso.']));

        // Verifica se o aluno foi adicionado ao banco de dados
        $this->assertDatabaseHas('users', [
            'name' => 'José Antunes',
            'email' => 'jose.antunes@example.com',
            'role' => 3, // Role do aluno
        ]);

        $this->assertDatabaseHas('aluno_curso', [
            'idcurso' => $curso,
        ]);
    }

    public function test_adicionar_aluno_falha_com_email_duplicado()
    {
        // Cria um aluno existente no banco
        $alunoExistente = User::factory()->create([
            'name' => 'Aluno Existente',
            'email' => 'aluno.existente@example.com',
            'password' => Hash::make('senha123'),
        ]);

        // Simula um curso no banco de dados
        $curso = DB::table('cursos')->insertGetId([
            'nome' => 'Curso Teste',
        ]);

        // Envia a requisição para criar aluno com email duplicado
        $response = $this->post('/admin/aluno/criarAluno', [
            'nome' => 'Novo Aluno',
            'email' => 'aluno.existente@example.com', // Email duplicado
            'password' => 'novaSenha123',
            'curso' => $curso,
        ]);

        // Verifica se a mensagem de erro foi retornada
        $response->assertRedirect();
        $response->assertSessionHas('message', json_encode(['success' => false, 'message' => 'O aluno já está registrado.']));

        // Verifica que o aluno duplicado não foi criado
        $this->assertDatabaseCount('users', 1);
    }

    public function test_adicionar_aluno_falha_com_dados_invalidos()
    {
        // Simula um curso no banco de dados
        $curso = DB::table('cursos')->insertGetId([
            'nome' => 'Curso Teste',
        ]);

        // Envia a requisição com dados inválidos
        $response = $this->post('/admin/aluno/criarAluno', [
            'nome' => '', // Nome vazio
            'email' => 'email_invalido', // Email inválido
            'password' => '', // Senha vazia
            'curso' => $curso,
        ]);

        // Verifica se a mensagem de erro foi retornada
        $response->assertRedirect();
        $response->assertSessionHas('message', json_encode(['success' => false, 'message' => 'Parâmetros inválidos.']));

        // Verifica que nenhum aluno foi criado
        $this->assertDatabaseCount('users', 0);
    }

    public function test_adicionar_aluno_falha_com_curso_inexistente()
    {
        // Envia a requisição com um ID de curso inexistente
        $response = $this->post('/admin/aluno/criarAluno', [
            'nome' => 'José Antunes',
            'email' => 'jose.antunes@example.com',
            'password' => 'senhaSegura123',
            'curso' => 9999, // ID inexistente
        ]);

        // Verifica se a mensagem de erro foi retornada
        $response->assertRedirect();
        $response->assertSessionHas('message', json_encode(['success' => false, 'message' => 'Erro interno no servidor.']));
    }
}
