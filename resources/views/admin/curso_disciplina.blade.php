<!DOCTYPE html>
<html lang="ar-SA"{{-- https://www.reddit.com/media?url=https%3A%2F%2Fpreview.redd.it%2Fikxt7fca2nbb1.jpg%3Fauto%3Dwebp%26s%3D2795ff24ecc3fa5a40d349e74ab70a9ff5eefdcd --}}> 
    <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Associar Disciplina a Curso</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: linear-gradient(to bottom, #4d0000, #800000);
            color: white;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }
        .form-container {
            background: white;
            color: #800000;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 6px 20px rgba(0, 0, 0, 0.6);
            width: 90%;
            max-width: 600px;
        }
        h1 {
            text-align: center;
        }
        form {
            display: flex;
            flex-direction: column;
        }
        label {
            margin: 10px 0 5px;
        }
        input, button, select, option {
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #800000;
            border-radius: 5px;
        }
        button {
            background: #800000;
            color: white;
            cursor: pointer;
            transition: background 0.3s ease;
        }
        button:hover {
            background: #4d0000;
        }
        .back-link {
            position: absolute;
            top: 20px;
            left: 20px;
            text-decoration: none;
        }
        .back-link svg {
            width: 32px;
            height: 32px;
            fill: white;
        }
    </style>
</head>
<body>
    <a href="{{ url('/admin/menudisciplina') }}" class="back-link">
        <svg class="w-6 h-6" xmlns="http://www.w3.org/2000/svg" fill="white" viewBox="0 0 14 10">
            <path stroke="white" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 5H1m0 0 4 4M1 5l4-4"/>
        </svg>
    </a>
    <div class="form-container">
        <h1>Associar Disciplina ao Curso</h1>
        <form method="POST" action="{{url('/admin/curso_disciplina/associarDisciplinaCurso')}}">
            @csrf
            <select name="idcurso" id="idcurso">
                @foreach($cursos as $curso)
                    <option value="{{ $curso->id }}">{{ $curso->nome }}</option>
                @endforeach
            </select>

            <select name="iddisciplina" id="iddisciplina">
                @foreach($disciplinas as $disciplina)
                    <option value="{{ $disciplina->id }}">{{ $disciplina->nome }}</option>
                @endforeach
            </select>

            <button type="submit">Associar Disciplina</button>
        </form>
        @if(session('message'))
        <script>
            const message = {!! session('message') !!};
            alert(message.message);
        </script>
        @endif
    </div>
</body>
</html>
