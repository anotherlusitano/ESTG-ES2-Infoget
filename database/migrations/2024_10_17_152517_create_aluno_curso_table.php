<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('aluno_curso', function (Blueprint $table) {
            $table->unsignedBigInteger('idaluno');
			$table->foreign('idaluno')->references('id')->on('users');
			$table->unsignedBigInteger('idcurso');
			$table->foreign('idcurso')->references('id')->on('cursos');
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('aluno_curso');
    }
};
