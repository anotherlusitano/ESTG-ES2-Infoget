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
        Schema::create('notas', function (Blueprint $table) {
            $table->unsignedBigInteger('idaluno');
			$table->foreign('idaluno')->references('id')->on('users');
			$table->unsignedBigInteger('iddisciplina');
			$table->foreign('iddisciplina')->references('id')->on('disciplinas');
			$table->unsignedInteger('nota', 2)->nullable();
        });
    }
	
    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('notas');
    }
};
