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
        // Obtém o texto inserido no formulário
        $prompt = $request->input('prompt');
        $model = 'llama3.2'; // Defina o modelo que deseja usar
        $responseText = '';

        // Faz uma requisição para a API do Ollama
        // $response = Http::withHeaders([
        //     'Content-Type' => 'application/json',
        // ])->post('http://localhost:11434/api/generate', [
        //     'prompt' => $prompt,
        //     'model' => $model // Adiciona o modelo à requisição
        // ]);


// $response = Ollama::agent('You are a weather expert...')
//     ->prompt($prompt)
//     ->model($model)
//     ->options(['temperature' => 0.8])
//     ->stream(false)
//     ->ask();

$response = Ollama::agent("You are a chatbot...")
->prompt($prompt)
->model(config('ollama-laravel.model'))
->options(['temperature' => floatval(config('ollama-laravel.temprature'))])
->stream(false)
->ask();

//$responseText = json_encode($response);
        // Verifica se a resposta foi bem-sucedida
        // if ($response->successful()) {
            // Exibir o conteúdo da resposta para debug
            $responseText = $response['response'];
            //$responseText = $response['response'];

            // // Log da resposta completa para verificar a estrutura
            // Log::info('Resposta da API do Ollama:', ['response' => $responseText]); // Passa um array como contexto

            // // Tente acessar o campo 'text' com base na estrutura correta
            // if (isset($responseText)) { 
            //     // Ajuste aqui conforme a estrutura real da resposta
            //     $responseText = $responseText->json();
            // } else {
            //     //$responseText = 'Campo "text" não encontrado na resposta.';
                
            // }
        // } else {
        //     Log::error('Erro na API do Ollama: ', [
        //         'status' => $response->status(),
        //         'body' => $response->body(),
        //         'request_payload' => ['prompt' => $prompt, 'model' => $model], // Registre o payload enviado
        //     ]);

        //     $responseText = 'Erro ao comunicar com o Ollama. Código de status: ' . $response->status() . '. Mensagem: ' . $response->body();
        // }

        // Retorna a view com a resposta
        return view('ollama', ['response' => $responseText]);
    }
}
