<?php

namespace Tests\Feature;

use Illuminate\Foundation\Testing\RefreshDatabase;
use Illuminate\Support\Facades\DB;
use Tests\TestCase;
use App\Models\User;

class DisciplinasControllerTest extends TestCase
{
    use RefreshDatabase;

    public function setUp(): void
    {
        parent::setUp();

        // Cria dados necessários para os testes
        $this->professor = User::factory()->create(); // Professor logado
        $this->aluno = User::factory()->create();     // Aluno vinculado ao curso

        $this->disciplina = DB::table('disciplinas')->insertGetId([
            'nome' => 'Matemática Discreta',
            'carga_horaria' => 40,
            'idprofessor' => $this->professor->id,
        ]);

        $this->curso = DB::table('cursos')->insertGetId([
            'nome' => 'Curso de Engenharia Informática',
        ]);

        // Associa a disciplina ao curso
        DB::table('disciplina_curso')->insert([
            'iddisciplina' => $this->disciplina,
            'idcurso' => $this->curso,
        ]);

        // Associa o aluno ao curso
        DB::table('aluno_curso')->insert([
            'idcurso' => $this->curso,
            'idaluno' => $this->aluno->id,
        ]);
    }

    /** @test */
    public function professor_pode_submeter_uma_nota_para_um_aluno()
    {
        // Simula o login do professor
        $this->actingAs($this->professor);

        // Envia a requisição para registar a nota
        $response = $this->postJson('/disciplinas/submit-grade', [
            'idaluno' => $this->aluno->id,
            'iddisciplina' => $this->disciplina,
            'nota' => 18,
        ]);

        // Verifica se a resposta foi bem sucedida
        $response->assertStatus(200);
        $response->assertJson([
            'success' => true,
            'message' => 'Nota registada com sucesso.',
        ]);

        // Verifica se a nota foi registada no banco de dados
        $this->assertDatabaseHas('notas', [
            'idaluno' => $this->aluno->id,
            'iddisciplina' => $this->disciplina,
            'nota' => 18,
        ]);
    }

    /** @test */
    public function nao_pode_registar_uma_nota_para_um_aluno_ja_avaliado()
    {
        // Simula o login do professor
        $this->actingAs($this->professor);

        // Insere a nota previamente no banco
        DB::table('notas')->insert([
            'idaluno' => $this->aluno->id,
            'iddisciplina' => $this->disciplina,
            'nota' => 16,
        ]);

        // Envia a requisição para tentar registar a nota novamente
        $response = $this->postJson('/disciplinas/submit-grade', [
            'idaluno' => $this->aluno->id,
            'iddisciplina' => $this->disciplina,
            'nota' => 18,
        ]);

        // Verifica se a resposta indica erro
        $response->assertStatus(400);
        $response->assertJson([
            'success' => false,
            'message' => 'O aluno já possui uma nota registada nesta disciplina.',
        ]);

        // Verifica que a nota original permanece no banco de dados
        $this->assertDatabaseHas('notas', [
            'idaluno' => $this->aluno->id,
            'iddisciplina' => $this->disciplina,
            'nota' => 16,
        ]);
    }

    /** @test */
    public function nao_pode_submeter_nota_com_dados_invalidos()
    {
        // Simula o login do professor
        $this->actingAs($this->professor);

        // Envia requisição com dados faltando
        $response = $this->postJson('/disciplinas/submit-grade', [
            'idaluno' => null, // ID do aluno inválido
            'iddisciplina' => $this->disciplina,
            'nota' => null,   // Nota ausente
        ]);

        // Verifica se a resposta indica erro de parâmetros
        $response->assertStatus(400);
        $response->assertJson([
            'success' => false,
            'message' => 'Parâmetros inválidos.',
        ]);
    }

    /** @test */
    public function nao_pode_submeter_nota_se_nao_estiver_autenticado()
    {
        // Envia requisição sem autenticação
        $response = $this->postJson('/disciplinas/submit-grade', [
            'idaluno' => $this->aluno->id,
            'iddisciplina' => $this->disciplina,
            'nota' => 15,
        ]);

        // Verifica se a resposta indica erro de autenticação
        $response->assertStatus(401);
    }
}
