import { render, screen, fireEvent } from '@testing-library/react';
import ForgotPassword from './ForgotPassword';
import { MemoryRouter } from 'react-router-dom'; // Para simular rotas

describe('Página de Recuperação de Senha', () => {
    test('verifica se os elementos principais aparecem', () => {
        render(
            <MemoryRouter>
                <ForgotPassword />
            </MemoryRouter>
        );

        // Verifica o título "Recuperação de Conta"
        expect(
            screen.getByText('Recuperação de Conta')
        ).toBeInTheDocument();

        // Verifica o texto informativo
        expect(
            screen.getByText(/Forgot your password\? No problem\./i)
        ).toBeInTheDocument();

        // Verifica o campo de email
        expect(
            screen.getByLabelText('Email:')
        ).toBeInTheDocument();

        // Verifica o botão de envio
        expect(
            screen.getByRole('button', { name: /enviar link para redefinição de senha/i })
        ).toBeInTheDocument();
    });

    test('simula o envio do formulário de recuperação', () => {
        const mockPost = jest.fn(); // Simula a função post

        render(
            <MemoryRouter>
                <ForgotPassword />
            </MemoryRouter>
        );

        // Simula preencher o campo de email
        const emailInput = screen.getByLabelText('Email:');
        fireEvent.change(emailInput, { target: { value: 'teste@exemplo.com' } });

        // Simula o clique no botão de envio
        const submitButton = screen.getByRole('button', { name: /enviar link para redefinição de senha/i });
        fireEvent.click(submitButton);

        // Verifica se o mockPost foi chamado (simula o envio do formulário)
        expect(mockPost).toHaveBeenCalled();
    });
});
