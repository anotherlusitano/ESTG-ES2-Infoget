<?php

namespace App\Http\Controllers;

use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\DB;
use Illuminate\Http\Request;

class CursoDisciplinaController extends Controller
{
    public function index()
    {
        $user = Auth::user();
        if ($user->role !== 1) {
            return redirect()->intended(route('dashboard'));
        }

        $cursos = DB::table('cursos')->get(); 
        $disciplinas = DB::table('disciplinas')->get(); 
        return view('admin.curso_disciplina', compact('cursos', 'disciplinas'));
    }

    public function associarDisciplinaCurso(Request $request)
    {
        try {
            DB::beginTransaction();
        
            $idcurso = $request->input('idcurso');
            $iddisciplina = $request->input('iddisciplina');
        
            if (!$idcurso || !$iddisciplina === null) {
                return redirect()->back()->with('message', json_encode(['success' => false, 'message' => 'Parâmetros inválidos.']));
            }
        
            $existeDisciplinaCurso = DB::table('disciplina_curso')
                ->where('iddisciplina', $iddisciplina)
                ->where('idcurso', $idcurso)
                ->first();
        
            if ($existeDisciplinaCurso) {
                return redirect()->back()->with('message', json_encode(['success' => false, 'message' => 'A disciplina já está associada ao curso.']));
            }

            DB::table('disciplina_curso')->insert([
                'iddisciplina' => $iddisciplina,
                'idcurso' => $idcurso,
            ]);

            DB::commit();

            return redirect()->back()->with('message', json_encode(['success' => true, 'message' => 'Disciplina associada com sucesso.']));
        } catch (\Exception $e) {
            DB::rollBack();
            return redirect()->back()->with('message', json_encode(['success' => false, 'message' => 'Erro interno no servidor.', 'error' => $e->getMessage()]));
        }        
    }
}

