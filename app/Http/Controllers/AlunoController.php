<?php

namespace App\Http\Controllers;

use Illuminate\Support\Facades\DB;

class AlunoController extends Controller
{
    public function index()
    {
        $cursos = DB::table('cursos')->get(); 
        return view('admin.aluno', compact('cursos'));
    }
}

