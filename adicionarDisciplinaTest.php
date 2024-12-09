<?php

namespace Tests\Feature;

use Illuminate\Foundation\Testing\RefreshDatabase;
use Illuminate\Support\Facades\DB;
use Tests\TestCase;

class DisciplinaControllerTest extends TestCase
{
    use RefreshDatabase;

    /** @test */
    public function it_adds_a_new_disciplina_successfully()
    {
        // Configura os dados da disciplina
        $payload = [
            'nome' => 'Matemática Discreta',
            'carga_horaria' => 40,
        ];

        // Faz a requisição para criar a disciplina
        $response = $this->post('/admin/disciplina/criar', $payload);

        // Verifica se o redirecionamento foi feito com sucesso
        $response->assertRedirect();

        // Verifica se a disciplina foi criada no banco de dados
        $this->assertDatabaseHas('disciplinas', [
            'nome' => 'Matemática Discreta',
            'carga_horaria' => 40,
        ]);

        // Verifica se a mensagem de sucesso está presente na sessão
        $response->assertSessionHas('message', json_encode([
            'success' => true,
            'message' => 'Disciplina registada com sucesso.',
        ]));
    }

    /** @test */
    public function it_prevents_duplicate_disciplina_names()
    {
        // Cria uma disciplina existente no banco de dados
        DB::table('disciplinas')->insert([
            'nome' => 'Álgebra Linear',
            'carga_horaria' => 30,
        ]);

        // Tenta criar uma disciplina com o mesmo nome
        $payload = [
            'nome' => 'Álgebra Linear',
            'carga_horaria' => 45,
        ];

        $response = $this->post('/admin/disciplina/criar', $payload);

        // Verifica se o redirecionamento foi feito com erro
        $response->assertRedirect();

        // Verifica se a disciplina duplicada não foi adicionada
        $this->assertDatabaseMissing('disciplinas', [
            'nome' => 'Álgebra Linear',
            'carga_horaria' => 45,
        ]);

        // Verifica se a mensagem de erro está presente na sessão
        $response->assertSessionHas('message', json_encode([
            'success' => false,
            'message' => 'A disciplina já está registada.',
        ]));
    }

    /** @test */
    public function it_returns_error_for_invalid_input()
    {
        // Tenta criar uma disciplina com dados inválidos
        $payload = [
            'nome' => null, // Nome é obrigatório
        ];

        $response = $this->post('/admin/disciplina/criar', $payload);

        // Verifica se o redirecionamento foi feito com erro
        $response->assertRedirect();

        // Verifica se nenhuma disciplina foi adicionada
        $this->assertDatabaseCount('disciplinas', 0);

        // Verifica se a mensagem de erro está presente na sessão
        $response->assertSessionHas('message', json_encode([
            'success' => false,
            'message' => 'Parâmetros inválidos.',
        ]));
    }
}

//É necessário que a rota POST /admin/disciplina/criar esteja configurada no arquivo routes/web.php.

