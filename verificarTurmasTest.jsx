import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import { rest } from 'msw';
import { setupServer } from 'msw/node';
import Turmas from '../Turmas';

// Mock da API com MSW (Mock Service Worker)
const server = setupServer(
    rest.get('/dashboard/students', (req, res, ctx) => {
        return res(
            ctx.json({
                coursesWithStudents: [
                    {
                        idcurso: 1,
                        nome_curso: 'Curso de Cibersegurança',
                        nome_coordenador: 'Prof. João',
                        students: [
                            { idaluno: 1, nome_aluno: 'Aluno 1' },
                            { idaluno: 2, nome_aluno: 'Aluno 2' },
                        ],
                    },
                    {
                        idcurso: 2,
                        nome_curso: 'Curso de Engenharia Informática',
                        nome_coordenador: 'Prof. Maria',
                        students: [],
                    },
                ],
            })
        );
    })
);

// Configuração do MSW antes dos testes
beforeAll(() => server.listen());
afterEach(() => server.resetHandlers());
afterAll(() => server.close());

describe('Turmas Component', () => {
    test('exibe "Loading..." durante o carregamento', () => {
        render(<Turmas />);
        expect(screen.getByText(/Loading.../i)).toBeInTheDocument();
    });

    test('renderiza turmas e alunos corretamente', async () => {
        render(<Turmas />);

        // Aguarda o carregamento completo
        await waitFor(() => screen.getByText('Turmas'));

        // Verifica o título do componente
        expect(screen.getByText('Turmas')).toBeInTheDocument();

        // Verifica os cursos e coordenadores
        expect(screen.getByText('Curso de Cibersegurança - Prof. João')).toBeInTheDocument();
        expect(screen.getByText('Curso de Engenharia Informática - Prof. Maria')).toBeInTheDocument();

        // Verifica alunos do curso de Cibersegurança
        expect(screen.getByText('Aluno 1')).toBeInTheDocument();
        expect(screen.getByText('Aluno 2')).toBeInTheDocument();

        // Verifica mensagem de curso sem alunos
        expect(
            screen.getByText('O curso de Enegnharia Informática ainda não tem alunos.')
        ).toBeInTheDocument();
    });

    test('exibe mensagem quando não há turmas', async () => {
        // Configura API para retornar nenhuma turma
        server.use(
            rest.get('/dashboard/students', (req, res, ctx) => {
                return res(ctx.json({ coursesWithStudents: [] }));
            })
        );

        render(<Turmas />);

        // Aguarda o carregamento completo
        await waitFor(() => screen.getByText('Você não dá aulas em nenhum curso.'));

        // Verifica a mensagem de ausência de turmas
        expect(
            screen.getByText('Você não dá aulas em nenhum curso.')
        ).toBeInTheDocument();
    });

    test('exibe mensagem de erro quando a API falha', async () => {
        // Configura API para retornar erro
        server.use(
            rest.get('/dashboard/students', (req, res, ctx) => {
                return res(ctx.status(500));
            })
        );

        render(<Turmas />);

        // Aguarda a mensagem de erro na consola 
        await waitFor(() =>
            expect(console.error).toHaveBeenCalledWith(
                expect.stringContaining('Error fetching data:')
            )
        );

        // Verifica se a mensagem "Loading..." desapareceu
        expect(screen.queryByText('Loading...')).not.toBeInTheDocument();
    });
});
