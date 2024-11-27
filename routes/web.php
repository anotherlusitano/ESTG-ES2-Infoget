<?php

use App\Http\Controllers\CursosController;
use App\Http\Controllers\ProfileController;
use Illuminate\Foundation\Application;
use Illuminate\Support\Facades\Route;
use Inertia\Inertia;

use App\Http\Controllers\OllamaController;
use App\Http\Controllers\DisciplinaController;

Route::get('/cursos/adicionar', [CursosController::class, 'create'])->name('cursos.create');
Route::post('/cursos', [CursosController::class, 'store'])->name('cursos.store');

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
Route::get('/dashboard/estudados', [CursosController::class, 'cursos'])->name('dashboard.estudados');

Route::middleware('auth')->group(function () {
    Route::get('/profile', [ProfileController::class, 'edit'])->name('profile.edit');
    Route::patch('/profile', [ProfileController::class, 'update'])->name('profile.update');
    Route::delete('/profile', [ProfileController::class, 'destroy'])->name('profile.destroy');
});

Route::get('/admin', function () {
    return view('admin.area');
});
require __DIR__.'/auth.php';
