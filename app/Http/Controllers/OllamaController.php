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

		$response = Ollama::agent("You are a chatbot...")
					->prompt($prompt)
					->model(config('ollama-laravel.model'))
					->options(['temperature' => floatval(config('ollama-laravel.temprature'))])
					->stream(false)
					->ask();

		Log::info("Resposta do Ollama: ", $response);

		$responseText = $response['response'];

		return response()->json(['response' => $responseText]);
	}
}
