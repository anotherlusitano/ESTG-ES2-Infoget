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
}
