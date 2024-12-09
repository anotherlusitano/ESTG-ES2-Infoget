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
use App\Http\Controllers\CursoDisciplinaController;
use App\Http\Controllers\ProfessorDisciplinaController;
use App\Http\Controllers\AdminController;
use App\Http\Controllers\SecretariaController;
use App\Http\Controllers\MenuDisciplinaController;
use App\Http\Controllers\DashboardController;
use Illuminate\Support\Facades\Auth;

Route::middleware(['auth', 'verified'])->get('/api/user', function () {
    $user = Auth::user();
    return response()->json([
        'role' => $user->role,
    ]);
});

Route::get('/', function () {
    return Inertia::render('Dashboard', [
        'canLogin' => Route::has('login'),
        'laravelVersion' => Application::VERSION,
        'phpVersion' => PHP_VERSION,
    ]);
})->middleware(['guest', 'verified'])->name('login');

Route::get('/welcome', function () {
    return Inertia::render('Welcome');
})->middleware(['auth', 'verified'])->name('welcome');

Route::get('/dashboard', function () {
    return Inertia::render('Dashboard');
})->middleware(['auth', 'verified'])->name('dashboard');

Route::post('/dashboard/generate', [OllamaController::class, 'generate'])->middleware(['auth', 'verified'])->name('dashboard.generate');
Route::get('/dashboard/cursos', [CursosController::class, 'cursos'])->middleware(['auth', 'verified'])->name('dashboard.cursos');
Route::get('/dashboard/disciplinas', [DisciplinasController::class, 'disciplinas'])->middleware(['auth', 'verified'])->name('dashboard.disciplinas');
Route::post('/disciplinas/submit-grade', [DisciplinasController::class, 'submitGrade'])->middleware(['auth', 'verified']);
Route::get('/dashboard/estudados', [CursosController::class, 'cursos'])->middleware(['auth', 'verified'])->name('dashboard.estudados');
Route::get('/dashboard/dados-curriculares', [DisciplinasController::class, 'disciplinas_com_notas'])->middleware(['auth', 'verified'])->name('dashboard.disciplinas_com_notas');

Route::middleware('auth')->group(function () {
    Route::get('/profile', [ProfileController::class, 'edit'])->name('profile.edit');
    Route::patch('/profile', [ProfileController::class, 'update'])->name('profile.update');
    Route::delete('/profile', [ProfileController::class, 'destroy'])->name('profile.destroy');
});

Route::get('/admin', [AdminController::class, 'index'])->middleware(['auth', 'verified'])->name('admin.area.index');

Route::get('/admin/aluno', [AlunoController::class, 'index'])->middleware(['auth', 'verified'])->name('admin.aluno.index');
Route::post('/admin/aluno/criarAluno', [AlunoController::class, 'criarAluno'])->middleware(['auth', 'verified']);

Route::get('/admin/professor', [ProfessorController::class, 'index'])->middleware(['auth', 'verified'])->name('admin.professor.index');
Route::post('/admin/professor/criarProfessor', [ProfessorController::class, 'criarProfessor'])->middleware(['auth', 'verified']);

Route::get('/admin/secretaria', [SecretariaController::class, 'index'])->middleware(['auth', 'verified'])->name('admin.secretaria.index');

Route::get('/admin/curso', [CursosController::class, 'index'])->middleware(['auth', 'verified'])->name('admin.curso.index');
Route::post('/admin/curso/criarCurso', [CursosController::class, 'criarCurso'])->middleware(['auth', 'verified']);

Route::get('/admin/disciplina', [DisciplinasController::class, 'index'])->middleware(['auth', 'verified'])->name('admin.disciplina.index');
Route::post('/admin/disciplina/criarDisciplina', [DisciplinasController::class, 'criarDisciplina'])->middleware(['auth', 'verified']);

Route::get('/admin/menudisciplina', [MenuDisciplinaController::class, 'index'])->middleware(['auth', 'verified'])->name('admin.menudisciplina.index');

Route::get('/admin/curso_disciplina', [CursoDisciplinaController::class, 'index'])->middleware(['auth', 'verified'])->name('admin.curso_disciplina.index');
Route::post('/admin/curso_disciplina/associarDisciplinaCurso', [CursoDisciplinaController::class, 'associarDisciplinaCurso'])->middleware(['auth', 'verified']);

Route::get('/admin/professor_disciplina', [ProfessorDisciplinaController::class, 'index'])->middleware(['auth', 'verified'])->name('admin.professor_disciplina.index');
Route::post('/admin/professor_disciplina/associarProfessorDisciplina', [ProfessorDisciplinaController::class, 'associarProfessorDisciplina'])->middleware(['auth', 'verified']);


require __DIR__.'/auth.php';
