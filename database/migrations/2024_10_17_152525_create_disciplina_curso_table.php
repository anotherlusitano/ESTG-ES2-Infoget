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
        Schema::create('disciplina_curso', function (Blueprint $table) {
            $table->unsignedBigInteger('iddisciplina');
			$table->foreign('iddisciplina')->references('id')->on('disciplinas');
			$table->unsignedBigInteger('idcurso');
			$table->foreign('idcurso')->references('id')->on('cursos');
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('disciplina_curso');
    }
};
