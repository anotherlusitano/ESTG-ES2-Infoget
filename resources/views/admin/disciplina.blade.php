<!DOCTYPE html>
<html lang="en-CA"{{-- Me dá emprego no Canadá, Toacy :) --}}> 
    <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro de Disciplina</title>
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
        .logout-link {
            position: absolute;
            top: 40px;
            right: 40px;
            text-decoration: none;
            display: flex;
            align-items: center;
            justify-content: center;
            z-index: 100;
            background: none;
            border: none;
            cursor: pointer;
            color: white; 
        }
        .logout-link svg {
            width: 50px;
            height: 50px;
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
    <form method="POST" action="{{ route('logout') }}" style="position: absolute; top: 20px; right: 20px;">
        @csrf
        <button type="submit" class="logout-link">
            <svg xmlns="http://www.w3.org/2000/svg" width="50" height="50" fill="currentColor" className="bi bi-box-arrow-right" viewBox="0 0 16 16">
                <path fillRule="evenodd" d="M10 12.5a.5.5 0 0 1-.5.5h-8a.5.5 0 0 1-.5-.5v-9a.5.5 0 0 1 .5-.5h8a.5.5 0 0 1 .5.5v2a.5.5 0 0 0 1 0v-2A1.5 1.5 0 0 0 9.5 2h-8A1.5 1.5 0 0 0 0 3.5v9A1.5 1.5 0 0 0 1.5 14h8a1.5 1.5 0 0 0 1.5-1.5v-2a.5.5 0 0 0-1 0z"/>
                <path fillRule="evenodd" d="M15.854 8.354a.5.5 0 0 0 0-.708l-3-3a.5.5 0 0 0-.708.708L14.293 7.5H5.5a.5.5 0 0 0 0 1h8.793l-2.147 2.146a.5.5 0 0 0 .708.708z"/>
            </svg>
        </button>
    </form>
    <div class="form-container">
        <h1>Adicionar Disciplina</h1>
        <form method="POST" action="{{url('/admin/disciplina/criarDisciplina')}}">
            <label for="nome">Nome da Disciplina:</label>
            @csrf
            <input type="text" id="nome" name="nome" required>

            <label for="carga_horaria">Carga horária:</label>
            @csrf
            <input type="number" id="carga_horaria" name="carga_horaria">

            <button type="submit">Criar Disciplina</button>
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
