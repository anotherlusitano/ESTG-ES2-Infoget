<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;
use Illuminate\Support\Facades\Log;
use Cloudstudio\Ollama\Facades\Ollama;

class OllamaController extends Controller
{
    public function index()
    {
        return view('ollama');
    }

    public function generate(Request $request)
    {
        $prompt = $request->input('prompt');
        $model = 'llama3.2';
        $responseText = '';

        // Faz uma requisição para a API do Ollama
        $response = Ollama::agent("You are a chatbot...")
                    ->prompt($prompt)
                    ->model(config('ollama-laravel.model'))
                    ->options(['temperature' => floatval(config('ollama-laravel.temprature'))])
                    ->stream(false)
                    ->ask();

        $responseText = $response['response'];
        return view('ollama', ['response' => $responseText]);
    }
}
