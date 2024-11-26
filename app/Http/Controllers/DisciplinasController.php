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


        return response()->json([
            'disciplinas' => $disciplinas,
        ]);
    }
}
