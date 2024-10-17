<?php

namespace App\Http\Controllers;

abstract class Controller
{
	/*public function ValidarRole(Request $request)
	{
		$request->validate([
			'name' => 'required|string',
			'email' => 'required|string|email|unique:users',
			'password' => 'required|string',
			'role' => 'required|in:1,2,3',
		]);

		User::create([
			'name' => $request['name'],
			'email' => $request['email'],
			'password' => Hash::make($request['password']),
			'role' => $request['role'],
		]);

		return redirect()->back()->with('sucess', 'User criado com sucesso!');
	}
	
	public function ValidarCoordenadorCurso(Request $request)
	{
		$request->validate([
			'nome' => 'required|string',
			'idcoordenador' => [
				'required',
				function ($attribute, $value, $fail) {
					$user = User::find($value);
					if (!$user || $user->role != 2) {
						$fail('O user tem que ser professor(role 2) para ser associado a um coordenador.');
					}
				},
			],
		]);

		Cursos::create([
			'nome' => $request->nome,
			'idcoordenador' => $request->idcoordenador,
		]);

		return redirect()->back()->with('success', 'Curso inserido com sucesso!');
	}
	
	public function ValidarAlunoCurso(Request $request)
	{
		$request->validate([
			'idaluno' => [
				'required',
				function ($attribute, $value, $fail) {
					$user = User::find($value);
					if (!$user || $user->role != 3) {
						$fail('O user tem que ser aluno(role 3) para ser associado a um curso.');
					}
				},
			],
			'idcurso' => 'required|exists:cursos,id',
		]);

		aluno_curso::create([
			'idaluno' => $request->idaluno,
			'idcurso' => $request->idcurso,
		]);

		return redirect()->back()->with('success', 'Aluno associado ao curso com sucesso!');
	}
	
	public function ValidarProfessorDisciplina(Request $request)
	{
		$request->validate([
			'nome' => 'required|string',
			'carga_horaria' => 'nullable|integer',
			'idprofessor' => [
				'nullable',
				function ($attribute, $value, $fail) {
					$user = User::find($value);
					if (!$user || $user->role != 2) {
						$fail('O user tem que ser professor(role 2) para ser associado a uma disciplina.');
					}
				},
			],
		]);

		Disciplinas::create([
			'nome' => $request->nome,
			'carga_horaria' => $request->carga_horaria,
			'idprofessor' => $request->idprofessor,
		]);

		return redirect()->back()->with('success', 'Disciplina inserida com sucesso!');
	}
	
    public function ValidarAlunoNota(Request $request)
	{
		$request->validate([
			'idaluno' => [
				'required',
				function ($attribute, $value, $fail) {
					$user = User::find($value);
					if (!$user || $user->role != 3) {
						$fail('O user tem que ser aluno(role 3) para ser associado a uma nota.');
					}
				},
			],
			'iddisciplina' => 'required|exists:disciplinas,id',
			'nota' => 'nullable|integer|min:0|max:20',
		]);

		Nota::create([
			'idaluno' => $request->idaluno,
			'iddisciplina' => $request->iddisciplina,
			'nota' => $request->nota,
		]);

		return redirect()->back()->with('success', 'Nota inserida com sucesso!');
	}*/
}
