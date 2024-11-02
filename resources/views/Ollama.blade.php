<!DOCTYPE html>
<html lang="pt-PT">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ollama</title>
</head>
<body>
    <h1>Gerador de Texto</h1>
    <form action="{{ route('ollama.generate') }}" method="POST">
        @csrf
        <textarea name="prompt" placeholder="Digite seu prompt aqui..." required></textarea>
        <button type="submit">Gerar</button>
    </form>

    @if (isset($response))
        <h2>Resposta:</h2>
        <p>{{ $response }}</p>
    @endif
</body>
</html>
