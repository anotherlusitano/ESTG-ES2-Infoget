import { render, screen } from '@testing-library/react';
import React from 'react';
import DadosPessoais from './DadosPessoais';

// Mock do `usePage` do Inertia.js
jest.mock('@inertiajs/react', () => ({
    ...jest.requireActual('@inertiajs/react'),
    usePage: () => ({
        props: {
            auth: {
                user: {
                    name: 'António Silva', // Nome mockup do aluno
                    email: 'antonio.silva@example.com', // E-mail mockup do aluno
                },
            },
        },
    }),
}));

describe('Teste da página de Dados Pessoais do Aluno', () => {
    test('verifica se o título "Dados Pessoais" aparece', () => {
        render(<DadosPessoais />);
        expect(screen.getByText(/Dados Pessoais/i)).toBeInTheDocument();
    });

    test('verifica se o nome e o e-mail do aluno são exibidos corretamente', () => {
        render(<DadosPessoais />);

        // Verifica se o nome do aluno está na página
        expect(screen.getByText(/Nome Completo: António Silva/i)).toBeInTheDocument();

        // Verifica se o e-mail do aluno está na página
        expect(screen.getByText(/E-Mail Oficial: antonio.silva@example.com/i)).toBeInTheDocument();
    });

    test('verifica se a imagem é renderizada corretamente', () => {
        render(<DadosPessoais />);

        // Verifica se a imagem com o alt text "ÉS TU >:)" está na página
        const image = screen.getByAltText(/ÉS TU >:\)/i);
        expect(image).toBeInTheDocument();

        // Verifica se a imagem tem a classe "rounded-full size-72"
        expect(image).toHaveClass('rounded-full', 'size-72');
    });
});
