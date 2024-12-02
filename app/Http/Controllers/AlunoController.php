<?php

namespace App\Http\Controllers;

use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\DB;
use Illuminate\Http\Request;

class AlunoController extends Controller
{
    public function index()
    {
        $cursos = DB::table('cursos')->get(); 
        return view('admin.aluno', compact('cursos'));
    }

    public function criarAluno(Request $request)
    {
        try {
            DB::beginTransaction();
        
            $nome = $request->input('nome');
            $email = $request->input('email');
            $password = $request->input('password');
            $idcurso = $request->input('curso');
        
            if (!$nome || !$email || $password === null) {
                return redirect()->back()->with('message', json_encode(['success' => false, 'message' => 'Parâmetros inválidos.']));
            }
        
            $existeAluno = DB::table('users')
                ->where('email', $email)
                ->first();
        
            if ($existeAluno) {
                return redirect()->back()->with('message', json_encode(['success' => false, 'message' => 'O aluno já está registrado.']));
            }
        
            $idaluno = DB::table('users')->insertGetId([
                'name' => $nome,
                'email' => $email,
                'role' => 3,
                'password' => Hash::make($password)
            ]);

            DB::table('aluno_curso')->insert([
                'idaluno' => $idaluno,
                'idcurso' => (int)$idcurso,
            ]);

            DB::commit();

            return redirect()->back()->with('message', json_encode(['success' => true, 'message' => 'Aluno registrado com sucesso.']));
        } catch (\Exception $e) {
            DB::rollBack();
            return redirect()->back()->with('message', json_encode(['success' => false, 'message' => 'Erro interno no servidor.', 'error' => $e->getMessage()]));
        }        
    }
}

