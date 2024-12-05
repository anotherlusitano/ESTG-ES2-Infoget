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

Route::post('/dashboard/generate', [OllamaController::class, 'generate'])->name('dashboard.generate');
Route::get('/dashboard/cursos', [CursosController::class, 'cursos'])->name('dashboard.cursos');
Route::get('/dashboard/disciplinas', [DisciplinasController::class, 'disciplinas'])->name('dashboard.disciplinas');
Route::post('/disciplinas/submit-grade', [DisciplinasController::class, 'submitGrade']);
Route::get('/dashboard/estudados', [CursosController::class, 'cursos'])->name('dashboard.estudados');
Route::get('/dashboard/dados-curriculares', [DisciplinasController::class, 'disciplinas_com_notas'])->name('dashboard.disciplinas_com_notas');

Route::middleware('auth')->group(function () {
    Route::get('/profile', [ProfileController::class, 'edit'])->name('profile.edit');
    Route::patch('/profile', [ProfileController::class, 'update'])->name('profile.update');
    Route::delete('/profile', [ProfileController::class, 'destroy'])->name('profile.destroy');
});

Route::get('/admin', function () {
    return view('admin.area');
});

Route::get('/admin/aluno', [AlunoController::class, 'index'])->name('admin.aluno.index');
Route::post('/admin/aluno/criarAluno', [AlunoController::class, 'criarAluno']);

Route::get('/admin/professor', function () {
    return view('admin.professor');
})->name('admin.professor');
Route::post('/admin/professor/criarProfessor', [ProfessorController::class, 'criarProfessor']);

Route::get('/admin/secretaria', function () {
    return view('admin.secretaria');
});

Route::get('/admin/curso', [CursosController::class, 'index'])->name('admin.curso.index');
Route::post('/admin/curso/criarCurso', [CursosController::class, 'criarCurso']);

Route::get('/admin/menudisciplina', function () {
    return view('admin.menudisciplina');
});

Route::get('/admin/disciplina', function () {
    return view('admin.disciplina');
})->name('admin.disciplina.index');
Route::post('/admin/disciplina/criarDisciplina', [DisciplinasController::class, 'criarDisciplina']);

Route::get('/admin/curso_disciplina', [CursoDisciplinaController::class, 'index'])->name('admin.curso_disciplina.index');
Route::post('/admin/curso_disciplina/associarDisciplinaCurso', [CursoDisciplinaController::class, 'associarDisciplinaCurso']);

Route::get('/admin/professor_disciplina', [ProfessorDisciplinaController::class, 'index'])->name('admin.professor_disciplina.index');
Route::post('/admin/professor_disciplina/associarProfessorDisciplina', [ProfessorDisciplinaController::class, 'associarProfessorDisciplina']);


require __DIR__.'/auth.php';
