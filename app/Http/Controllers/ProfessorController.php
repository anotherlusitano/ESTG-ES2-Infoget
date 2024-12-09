<?php

namespace App\Http\Controllers;

use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\DB;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Str;

class ProfessorController extends Controller
{
    public function index()
    {
        $user = Auth::user();
        if ($user->role !== 1) {
            return redirect()->intended(route('dashboard'));
        }
        return view('admin.professor');
    }

    public function criarProfessor(Request $request)
    {
        try {
            DB::beginTransaction();
        
            $nome = $request->input('nome');
            $email = $request->input('email');
            $password = $request->input('password');
        
            if (!$nome || !$email || $password === null) {
                return redirect()->back()->with('message', json_encode(['success' => false, 'message' => 'Parâmetros inválidos.']));
            }
        
            $existeProfessor = DB::table('users')
                ->where('email', $email)
                ->first();
        
            if ($existeProfessor) {
                return redirect()->back()->with('message', json_encode(['success' => false, 'message' => 'O professor já está registrado.']));
            }
        
            DB::table('users')->insert([
                'name' => $nome,
                'email' => $email,
                'email_verified_at' => now(),
                'role' => 2,
                'password' => Hash::make($password)
            ]);
        
            DB::commit();
            return redirect()->back()->with('message', json_encode(['success' => true, 'message' => 'Professor registrado com sucesso.']));
        } catch (\Exception $e) {
            DB::rollBack();
            return redirect()->back()->with('message', json_encode(['success' => false, 'message' => 'Erro interno no servidor.', 'error' => $e->getMessage()]));
        }        
    }
}
