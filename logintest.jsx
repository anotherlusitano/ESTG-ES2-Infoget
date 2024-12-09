import { render, screen } from '@testing-library/react';
import Login from './Login'; // Caminho para o componente de login
import { MemoryRouter } from 'react-router-dom'; // Para simular rotas se necessário
//teste

describe('Página de Login', () => {
    test('verifica se os elementos principais aparecem', () => {
        render(
            <MemoryRouter> 
                <Login />
            </MemoryRouter>
        );

        // Verifica o título "Bem-vindo ao Infoget"
        expect(
            screen.getByText('Bem-vindo ao Infoget')
        ).toBeInTheDocument();

        // Verifica o campo de email
        expect(
            screen.getByLabelText('Email')
        ).toBeInTheDocument();

        // Verifica o campo de senha
        expect(
            screen.getByLabelText('Password')
        ).toBeInTheDocument();

        // Verifica o botão "Entrar"
        expect(
            screen.getByRole('button', { name: /entrar/i })
        ).toBeInTheDocument();

        // Verifica o checkbox "Lembrar-me"
        expect(
            screen.getByLabelText('Lembrar-me')
        ).toBeInTheDocument();
    });
});
