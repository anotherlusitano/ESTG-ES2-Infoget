import { render, screen, waitFor } from '@testing-library/react';
import React from 'react';
import DadosCurriculares from './DadosCurriculares';

// Mock para `fetch`
global.fetch = jest.fn();

describe('Teste da página de Dados Curriculares do Aluno', () => {
    afterEach(() => {
        fetch.mockClear(); // Limpa o mock após cada teste
    });

    test('verifica se o texto de carregamento é exibido inicialmente', () => {
        fetch.mockResolvedValueOnce({
            json: jest.fn().mockResolvedValue({ disciplinas_com_notas: [] }),
        });

        render(<DadosCurriculares />);
        
        // Verifica se "Loading..." é exibido enquanto os dados não foram carregados
        expect(screen.getByText(/Loading.../i)).toBeInTheDocument();
    });

    test('verifica se as disciplinas são exibidas corretamente após carregar os dados', async () => {
        const mockData = {
            disciplinas_com_notas: [
                [{ nome_disciplina: 'Álgebra', nota_aluno: 16 }],
                [{ nome_disciplina: 'Programação Orientada a Objetos', nota_aluno: 14 }],
            ],
        };

        fetch.mockResolvedValueOnce({
            json: jest.fn().mockResolvedValue(mockData),
        });

        render(<DadosCurriculares />);

        // Espera que o estado de carregamento desapareça e os dados sejam exibidos
        await waitFor(() => {
            expect(screen.getByText(/Na disciplina de Álgebra tiveste a nota final de 16/i)).toBeInTheDocument();
            expect(screen.getByText(/Na disciplina de Programação Orientada a Objetos tiveste a nota final de 14/i)).toBeInTheDocument();
        });
    });

    test('verifica se a mensagem "O aluno não tem nenhuma disciplina." aparece quando a lista está vazia', async () => {
        const mockData = { disciplinas_com_notas: [] };

        fetch.mockResolvedValueOnce({
            json: jest.fn().mockResolvedValue(mockData),
        });

        render(<DadosCurriculares />);

        // Espera que o estado de carregamento desapareça e a mensagem seja exibida
        await waitFor(() => {
            expect(screen.getByText(/O aluno não tem nenhuma disciplina\./i)).toBeInTheDocument();
        });
    });

    test('verifica se um erro de fetch é tratado corretamente', async () => {
        fetch.mockRejectedValueOnce(new Error('Erro na API'));

        render(<DadosCurriculares />);

        // Espera que o estado de carregamento desapareça
        await waitFor(() => {
            expect(screen.queryByText(/Loading.../i)).not.toBeInTheDocument();
        });
    });
});
