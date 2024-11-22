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

        return response()->json([
            'courses' => $courses,
        ]);
    }
    
    // Exibir o formulário de criação
    public function create()
    {
        return view('curso');
    }
    // Salvar os dados do curso no banco
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
    }
}
