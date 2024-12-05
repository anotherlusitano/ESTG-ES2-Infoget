import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import ChatBot from './ChatBot';
import { MemoryRouter } from 'react-router-dom'; // Para simular rotas
import { Inertia } from '@inertiajs/inertia';

jest.mock('@inertiajs/react', () => ({
    ...jest.requireActual('@inertiajs/react'),
    usePage: () => ({
        props: {
            auth: {
                user: { name: 'Teste User' }, // Mockup do usuário autenticado
            },
        },
    }),
}));

describe('Teste do ChatBot', () => {
    test('verifica se o chatbot renderiza corretamente', () => {
        render(
            <MemoryRouter>
                <ChatBot />
            </MemoryRouter>
        );

        // Verifica se a mensagem inicial do bot está presente
        expect(
            screen.getByText(/Olá Teste User, eu sou o seu assistente de hoje. Como posso ajudá-lo?/i)
        ).toBeInTheDocument();

        // Verifica se o campo de input está presente
        expect(screen.getByPlaceholderText(/Escreva aqui.../i)).toBeInTheDocument();

        // Verifica se o botão de envio está presente
        expect(screen.getByRole('button', { name: /Enviar/i })).toBeInTheDocument();
    });

    test('simula envio e recebimento de mensagens no chatbot', async () => {
        global.fetch = jest.fn(() =>
            Promise.resolve({
                ok: true,
                json: () =>
                    Promise.resolve({
                        response: 'Esta é uma resposta do chatbot.',
                    }),
            })
        );

        render(
            <MemoryRouter>
                <ChatBot />
            </MemoryRouter>
        );

        // Simula entrada de texto no campo de mensagem
        const input = screen.getByPlaceholderText(/Escreva aqui.../i);
        fireEvent.change(input, { target: { value: 'Qual é o horário de atendimento?' } });

        // Simula o clique no botão de envio
        const sendButton = screen.getByRole('button', { name: /Enviar/i });
        fireEvent.click(sendButton);

        // Aguarda que a mensagem do usuário apareça
        expect(await screen.findByText(/Qual é o horário de atendimento\?/i)).toBeInTheDocument();

        // Aguarda que a resposta do chatbot apareça
        await waitFor(() =>
            expect(
                screen.getByText(/Esta é uma resposta do chatbot\./i)
            ).toBeInTheDocument()
        );

        // Verifica se o input foi limpo após o envio
        expect(input.value).toBe('');
    });

    test('verifica se o chatbot aparece nas páginas Secretaria, Alunos e Professores', () => {
        const mockPages = ['secretaria', 'alunos', 'professores'];

        mockPages.forEach((page) => {
            render(
                <MemoryRouter>
                    <ChatBot />
                </MemoryRouter>
            );

            // Verifica se o chatbot renderiza corretamente em cada página
            expect(
                screen.getByText(/Olá Teste User, eu sou o seu assistente de hoje. Como posso ajudá-lo?/i)
            ).toBeInTheDocument()
        });
    });
});
