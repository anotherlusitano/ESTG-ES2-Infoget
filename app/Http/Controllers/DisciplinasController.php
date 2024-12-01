<?php

namespace App\Http\Controllers;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Auth;


class DisciplinasController extends Controller
{
    public function disciplinas()
    {
        $userId = Auth::id();

        $disciplinas = DB::table('disciplinas')
                     ->where('idprofessor', $userId)
                     ->select('nome', 'carga_horaria')
                     ->get();

        $disciplinesWithStudents = DB::table('disciplinas')
                                        ->leftJoin('disciplina_curso', 'disciplinas.id', '=', 'disciplina_curso.iddisciplina')
                                        ->leftJoin('cursos', 'disciplina_curso.idcurso', '=', 'cursos.id')
                                        ->leftJoin('aluno_curso', 'cursos.id', '=', 'aluno_curso.idcurso')
                                        ->leftJoin('users as alunos', 'aluno_curso.idaluno', '=', 'alunos.id')
                                        ->leftJoin('notas', function ($join) {
                                            $join->on('notas.idaluno', '=', 'alunos.id')
                                                ->on('notas.iddisciplina', '=', 'disciplinas.id');
                                        })
                                        ->where('disciplinas.idprofessor', '=', $userId)
                                        ->select(
                                            'disciplinas.id as iddisciplina',
                                            'disciplinas.nome as nome_disciplina',
                                            'disciplinas.carga_horaria',
                                            'alunos.id as idaluno',
                                            'alunos.name as nome_aluno',
                                            'notas.nota as nota_aluno'
                                        )
                                        ->distinct()
                                        ->get()
                                        ->groupBy('iddisciplina')
                                        ->map(function ($disciplina) {
                                            return [
                                                'iddisciplina' => $disciplina[0]->iddisciplina,
                                                'nome_disciplina' => $disciplina[0]->nome_disciplina,
                                                'carga_horaria' => $disciplina[0]->carga_horaria,
                                                'alunos' => $disciplina->map(function ($aluno) {
                                                    return [
                                                        'idaluno' => $aluno->idaluno,
                                                        'nome_aluno' => $aluno->nome_aluno,
                                                        'nota' => $aluno->nota_aluno,
                                                    ];
                                                })->filter()->values()
                                            ];
                                        })->values();
        
        return response()->json([
            'disciplinas' => $disciplinas,
            'disciplinesWithStudents' => $disciplinesWithStudents,
        ]);
    }

    public function submitGrade(Request $request)
    {
        try {
            DB::beginTransaction();
        
            $idaluno = $request->input('idaluno');
            $iddisciplina = $request->input('iddisciplina');
            $nota = $request->input('nota');
        
            if (!$idaluno || !$iddisciplina || $nota === null) {
                return response()->json(['success' => false, 'message' => 'Parâmetros inválidos.'], 400);
            }
        
            $existingGrade = DB::table('notas')
                ->where('idaluno', $idaluno)
                ->where('iddisciplina', $iddisciplina)
                ->first();
        
            if ($existingGrade) {
                return response()->json(['success' => false, 'message' => 'O aluno já possui uma nota registrada nesta disciplina.'], 400);
            }
        
            DB::table('notas')->insert([
                'idaluno' => $idaluno,
                'iddisciplina' => $iddisciplina,
                'nota' => $nota
            ]);
        
            DB::commit();
            return response()->json(['success' => true, 'message' => 'Nota registrada com sucesso.']);
        } catch (\Exception $e) {
            DB::rollBack();
            return response()->json(['success' => false, 'message' => 'Erro interno no servidor.', 'error' => $e->getMessage()], 500);
        }        
    }
}