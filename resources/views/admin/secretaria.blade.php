<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Área da Secretaria</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: linear-gradient(to bottom, #800000, #4d0000);
            color: white;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }
        h1 {
            position: absolute;
            top: 20px;
            font-size: 36px;
            text-align: center;
            width: 100%;
            margin: 0;
            color: white;
        }
        .container {
            background: white;
            color: #800000;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 6px 20px rgba(0, 0, 0, 0.6);
            width: 90%; 
            max-width: 1200px; 
            min-height: 200px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
        }
        .buttons {
            display: flex;
            justify-content: space-around;
            width: 100%;
            margin-top: 20px;
        }
        .buttons a {
            text-decoration: none;
            background: #800000;
            color: white;
            width: 250px; 
            height: 120px; 
            display: flex;
            justify-content: center;
            align-items: center;
            border-radius: 10px;
            transition: all 0.3s ease;
            font-size: 26px; 
        }
        .buttons a:hover {
            background: #4d0000;
        }
        .back-link {
            position: absolute;
            top: 20px;
            left: 20px;
            text-decoration: none;
            z-index: 100;
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
    <a href="{{ url('/admin') }}" class="back-link">
        <svg xmlns="http://www.w3.org/2000/svg" fill="white" viewBox="0 0 14 10">
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
    <h1>Área da Secretaria</h1>
    <div class="container">
        <div class="buttons">
            <a href="{{ route('admin.curso.index') }}">Cursos</a>
            <a href="{{ route('admin.menudisciplina.index') }}">Disciplinas</a>
        </div>
    </div>
</body>
</html>
