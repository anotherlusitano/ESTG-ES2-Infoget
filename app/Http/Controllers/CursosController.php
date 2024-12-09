<?php

namespace App\Http\Controllers;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Auth;
use Illuminate\Http\Request;

class CursosController extends Controller
{
    public function cursos()
    {
        $userId = Auth::id();

        $courses = DB::table('cursos')
                     ->join('aluno_curso', 'cursos.id', '=', 'aluno_curso.idcurso')
                     ->where('aluno_curso.idaluno', $userId)
                     ->select('cursos.id', 'cursos.nome')
                     ->get();

        $coursesWithStudents = DB::table('cursos')
                                    ->leftJoin('aluno_curso', 'cursos.id', '=', 'aluno_curso.idcurso')
                                    ->leftJoin('users as alunos', 'aluno_curso.idaluno', '=', 'alunos.id')
                                    ->leftJoin('disciplina_curso', 'cursos.id', '=', 'disciplina_curso.idcurso')
                                    ->leftJoin('disciplinas', 'disciplina_curso.iddisciplina', '=', 'disciplinas.id')
                                    ->leftJoin('users as coordenadores', 'cursos.idcoordenador', '=', 'coordenadores.id')
                                    ->where('disciplinas.idprofessor', '=', $userId)
                                    ->select(
                                        'cursos.id as idcurso',
                                        'cursos.nome as nome_curso',
                                        'coordenadores.name as nome_coordenador',
                                        'alunos.id as idaluno',
                                        'alunos.name as nome_aluno'
                                    )
                                    ->distinct()
                                    ->get()
                                    ->groupBy('idcurso')
                                    ->map(function ($course) {
                                        return [
                                            'idcurso' => $course[0]->idcurso,
                                            'nome_curso' => $course[0]->nome_curso,
                                            'nome_coordenador' => $course[0]->nome_coordenador,
                                            'estudados' => $course->map(function ($aluno) {
                                                return [
                                                    'idaluno' => $aluno->idaluno,
                                                    'nome_aluno' => $aluno->nome_aluno,
                                                ];
                                            })->filter()->values()
                                        ];
                                    })->values(); 

        return response()->json([
            'courses' => $courses,
            'coursesWithStudents' => $coursesWithStudents,
        ]);
    }

    public function index()
    {
        $user = Auth::user();
        if ($user->role !== 1) {
            return redirect()->intended(route('dashboard'));
        }

        $users = DB::table('users')->where('role', '=', 2)->get(); 
        return view('admin.curso', compact('users'));
    }

    public function criarCurso(Request $request)
    {
        try {
            DB::beginTransaction();
        
            $nome = $request->input('nome');
            $idcoordenador = $request->input('idcoordenador');
        
            if (!$nome === null || !$idcoordenador === null) {
                return redirect()->back()->with('message', json_encode(['success' => false, 'message' => 'Parâmetros inválidos.']));
            }
        
            $existeCurso = DB::table('cursos')
                ->where('nome', $nome)
                ->first();
        
            if ($existeCurso) {
                return redirect()->back()->with('message', json_encode(['success' => false, 'message' => 'O curso já está registrado.']));
            }

            $existeCoordenador = DB::table('cursos')
                ->where('idcoordenador', $idcoordenador)
                ->first();
        
            if ($existeCoordenador) {
                return redirect()->back()->with('message', json_encode(['success' => false, 'message' => 'O coordenador já é coordenador de um curso.']));
            }
        
            $idcurso = DB::table('cursos')->insertGetId([
                'nome' => $nome,
                'idcoordenador' => $idcoordenador,
            ]);

            DB::commit();

            return redirect()->back()->with('message', json_encode(['success' => true, 'message' => 'Curso registrado com sucesso.']));
        } catch (\Exception $e) {
            DB::rollBack();
            return redirect()->back()->with('message', json_encode(['success' => false, 'message' => 'Erro interno no servidor.', 'error' => $e->getMessage()]));
        }        
    }
}
