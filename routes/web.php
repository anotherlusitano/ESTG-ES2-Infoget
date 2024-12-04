<?php

use App\Http\Controllers\AlunoController;
use App\Http\Controllers\CursosController;
use App\Http\Controllers\DisciplinasController;
use App\Http\Controllers\ProfileController;
use Illuminate\Foundation\Application;
use Illuminate\Support\Facades\Route;
use Inertia\Inertia;
use App\Http\Controllers\OllamaController;
use App\Http\Controllers\ProfessorController;






Route::get('/admin', function () {
    return view('admin.area');
});

Route::get('/admin/professor', function () {
    return view('admin.professor');
})->name('admin.professor');

Route::get('/admin/aluno', [AlunoController::class, 'index'])->name('admin.aluno.index');
Route::post('/admin/aluno/criarAluno', [AlunoController::class, 'criarAluno']);

Route::post('/admin/professor/criarProfessor', [ProfessorController::class, 'criarProfessor']);

require __DIR__.'/auth.php';

?>
