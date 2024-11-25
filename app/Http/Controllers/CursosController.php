<?php

namespace App\Http\Controllers;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Auth;

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
    
    // Exibir o formulário de criação
    public function create()
    {
        return view('curso');
    }
    // Salvar os dados do curso no banco
    /*
    public function store(Request $request)
    {
        $request->validate([
            'nome' => 'required|string|max:255',
            'codigo' => 'required|string|max:10|unique:cursos,codigo',
            'duracao' => 'required|integer|min:1|max:10', // Duração em anos
            'descricao' => 'nullable|string|max:1000',
        ]);
        Curso::create($request->all());
        return redirect()->route('cursos.create')->with('success', 'Curso adicionado com sucesso!');
    }*/
}
