<?php

namespace Tests\Feature;

use Illuminate\Foundation\Testing\RefreshDatabase;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\DB;
use Tests\TestCase;
use App\Models\User;

class DisciplinasControllerTest extends TestCase
{
    use RefreshDatabase;

    public function test_ver_disciplinas_lecionadas_com_sucesso()
    {
        // Criar um professor (usuário autenticado)
        $professor = User::factory()->create([
            'name' => 'Professor Teste',
            'email' => 'professor@example.com',
            'password' => bcrypt('senha123'),
            'role' => 2
        ]);

        // Simula a autenticação do professor
        $this->actingAs($professor);

        // Adiciona disciplinas para o professor
        DB::table('disciplinas')->insert([
            ['nome' => 'Engenharia de Software', 'carga_horaria' => 40, 'idprofessor' => $professor->id],
            ['nome' => 'Matemática Discreta', 'carga_horaria' => 60, 'idprofessor' => $professor->id],
        ]);

        // Faz uma requisição GET para a rota de disciplinas
        $response = $this->get('/dashboard/disciplinas');

        // Verifica se a resposta é 200 (sucesso)
        $response->assertStatus(200);

        // Verifica se as disciplinas retornadas estão corretas
        $response->assertJson([
            'disciplinas' => [
                ['nome' => 'Engenharia de Software', 'carga_horaria' => 40],
                ['nome' => 'Matemática Discreta', 'carga_horaria' => 60],
            ],
        ]);
    }

    public function test_ver_disciplinas_quando_nao_existem_disciplinas()
    {
        // Criar um professor (usuário autenticado)
        $professor = User::factory()->create([
            'name' => 'Professor Teste',
            'email' => 'professor@example.com',
            'password' => bcrypt('senha123'),
            'role' => 2
        ]);

        // Simula a autenticação do professor
        $this->actingAs($professor);

        // Faz uma requisição GET para a rota de disciplinas
        $response = $this->get('/dashboard/disciplinas');

        // Verifica se a resposta é 200 (sucesso)
        $response->assertStatus(200);

        // Verifica que a resposta contém um array vazio
        $response->assertJson([
            'disciplinas' => [],
        ]);
    }

    public function test_ver_disciplinas_quando_nao_autenticado()
    {
        // Faz uma requisição GET para a rota de disciplinas sem autenticação
        $response = $this->get('/dashboard/disciplinas');

        // Verifica que a resposta é 302 (redirecionamento para login)
        $response->assertStatus(302);

        // Verifica se redireciona para a rota de login
        $response->assertRedirect('/login');
    }
}
