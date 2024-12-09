<?php

namespace App\Http\Controllers;

use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\DB;
use Illuminate\Http\Request;

class ProfessorDisciplinaController extends Controller
{
    public function index()
    {
        $user = Auth::user();
        if ($user->role !== 1) {
            return redirect()->intended(route('dashboard'));
        }

        $users = DB::table('users')->where('role', '=', '2')->get(); 
        $disciplinas = DB::table('disciplinas')->get(); 
        return view('admin.professor_disciplina', compact('users', 'disciplinas'));
    }

    public function associarProfessorDisciplina(Request $request)
    {
        try {
            DB::beginTransaction();
        
            $idprofessor = $request->input('idprofessor');
            $iddisciplina = $request->input('iddisciplina');
        
            if (!$idprofessor || !$iddisciplina === null) {
                return redirect()->back()->with('message', json_encode(['success' => false, 'message' => 'Parâmetros inválidos.']));
            }
        
            $existeProfessorDisciplina = DB::table('disciplinas')
                ->where('id', $iddisciplina)
                ->where('idprofessor', $idprofessor)
                ->first();
        
            if ($existeProfessorDisciplina) {
                return redirect()->back()->with('message', json_encode(['success' => false, 'message' => 'O professor já está associado à disciplina.']));
            }

            DB::table('disciplinas')
                ->where('id', '=', $iddisciplina)
                ->update([
                    'idprofessor' => $idprofessor,
                ]);

            DB::commit();

            return redirect()->back()->with('message', json_encode(['success' => true, 'message' => 'Professor associado com sucesso.']));
        } catch (\Exception $e) {
            DB::rollBack();
            return redirect()->back()->with('message', json_encode(['success' => false, 'message' => 'Erro interno no servidor.', 'error' => $e->getMessage()]));
        }        
    }
}

