<?php

namespace Tests\Feature;

use Illuminate\Foundation\Testing\RefreshDatabase;
use Illuminate\Support\Facades\DB;
use Tests\TestCase;

class CursosControllerTest extends TestCase
{
    use RefreshDatabase;

    /** @test */
    public function pode_criar_um_curso_com_dados_validos()
    {
        $coordenador = DB::table('users')->insertGetId([
            'name' => 'Coordenador Teste',
            'email' => 'coordenador@teste.com',
            'role' => 2,
            'password' => bcrypt('senha123'),
        ]);

        // Dados para criar o curso
        $data = [
            'nome' => 'Curso Teste',
            'idcoordenador' => $coordenador,
        ];

        // Envia requisição para criar o curso
        $response = $this->post('/admin/curso/criarCurso', $data);

        // Verifica se a resposta redireciona corretamente com sucesso
        $response->assertStatus(302); // Redireciona após sucesso
        $response->assertSessionHas('message', json_encode(['success' => true, 'message' => 'Curso registrado com sucesso.']));

        // Confirma que o curso foi criado no banco de dados
        $this->assertDatabaseHas('cursos', [
            'nome' => $data['nome'],
            'idcoordenador' => $data['idcoordenador'],
        ]);
    }

    /** @test */
    public function nao_pode_criar_curso_com_nome_duplicado()
    {
        // Insere um coordenador e curso no banco de dados
        $coordenador = DB::table('users')->insertGetId([
            'name' => 'Coordenador Teste',
            'email' => 'coordenador@teste.com',
            'role' => 2,
            'password' => bcrypt('senha123'),
        ]);

        DB::table('cursos')->insert([
            'nome' => 'Curso Existente',
            'idcoordenador' => $coordenador,
        ]);

        // Dados para tentar criar um curso com o mesmo nome
        $data = [
            'nome' => 'Curso Existente',
            'idcoordenador' => $coordenador,
        ];

        // Envia requisição para criar o curso
        $response = $this->post('/admin/curso/criarCurso', $data);

        // Verifica se a resposta indica falha devido ao nome duplicado
        $response->assertStatus(302); // Redireciona após tentativa
        $response->assertSessionHas('message', json_encode(['success' => false, 'message' => 'O curso já está registrado.']));

        // Confirma que não há duplicados no banco de dados
        $this->assertEquals(1, DB::table('cursos')->where('nome', $data['nome'])->count());
    }

    /** @test */
    public function nao_pode_criar_curso_com_coordenador_duplicado()
    {
        // Insere um coordenador e curso no banco
        $coordenador = DB::table('users')->insertGetId([
            'name' => 'Coordenador Teste',
            'email' => 'coordenador@teste.com',
            'role' => 2,
            'password' => bcrypt('senha123'),
        ]);

        DB::table('cursos')->insert([
            'nome' => 'Curso Existente',
            'idcoordenador' => $coordenador,
        ]);

        // Dados para tentar criar outro curso com o mesmo coordenador
        $data = [
            'nome' => 'Novo Curso',
            'idcoordenador' => $coordenador,
        ];

        // Envia requisição para criar o curso
        $response = $this->post('/admin/curso/criarCurso', $data);

        // Verifica se a resposta indica falha devido ao coordenador duplicado
        $response->assertStatus(302); // Redireciona após tentativa
        $response->assertSessionHas('message', json_encode(['success' => false, 'message' => 'O coordenador já é coordenador de um curso.']));

        // Confirma que o banco de dados não contém o novo curso
        $this->assertDatabaseMissing('cursos', ['nome' => $data['nome']]);
    }

    /** @test */
    public function nao_pode_criar_curso_com_dados_invalidos()
    {
        // Dados inválidos para criação do curso
        $data = [
            'nome' => '',             // Nome ausente
            'idcoordenador' => null,  // Coordenador não selecionado
        ];

        // Envia requisição para criar o curso
        $response = $this->post('/admin/curso/criarCurso', $data);

        // Verifica se a resposta indica falha devido aos dados inválidos
        $response->assertStatus(302); // Redireciona após tentativa
        $response->assertSessionHas('message', json_encode(['success' => false, 'message' => 'Parâmetros inválidos.']));

        // Confirma que nenhum curso foi criado no banco de dados
        $this->assertDatabaseMissing('cursos', ['nome' => $data['nome']]);
    }
}
