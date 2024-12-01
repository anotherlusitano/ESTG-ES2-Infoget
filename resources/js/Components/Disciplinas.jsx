import React, { useEffect, useState } from 'react';

const Disciplinas = () => {
    const [disciplinesWithStudents, setDisciplinesWithStudents] = useState([]);
    const [loading, setLoading] = useState(true);
    const [expandedDisciplines, setExpandedDisciplines] = useState({});
    const [modalData, setModalData] = useState(null);
    const [successModalVisible, setSuccessModalVisible] = useState(false);
    const [nota, setNota] = useState("");

    useEffect(() => {
        fetch('/dashboard/disciplinas')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                setDisciplinesWithStudents(data.disciplinesWithStudents);
                setLoading(false);
            })
            .catch(error => {
                console.error('Error fetching data:', error);
                setLoading(false);
            });
    }, []);

    const toggleDiscipline = (iddisciplina) => {
        setExpandedDisciplines((prevState) => ({
            ...prevState,
            [iddisciplina]: !prevState[iddisciplina],
        }));
    };

    const handleOpenModal = (aluno, disciplina) => {
        setModalData({ aluno, disciplina });
        setNota("");
    };

    const handleCloseModal = () => {
        setModalData(null);
    };

    const handleSubmitNota = async (idaluno, iddisciplina, notaInput) => {
        try {
            console.log('Nota recebida:', notaInput);
            const nota = parseInt(notaInput, 10);
            if (isNaN(nota) || nota < 0 || nota > 20) {
                alert('A nota deve ser um número inteiro entre 0 e 20');
                return;
            }
    
            const metaTag = document.querySelector('meta[name="csrf-token"]');
            if (!metaTag) {
                throw new Error('Meta tag CSRF não encontrada no DOM');
            }
    
            const csrfToken = metaTag.getAttribute('content');
    
            const response = await fetch('/disciplinas/submit-grade', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-CSRF-TOKEN': csrfToken,
                },
                body: JSON.stringify({ idaluno, iddisciplina, nota }),
            });
    
            if (!response.ok) {
                const errorData = await response.json();
                console.error('Erro no servidor:', errorData.message);
                return;
            }
    
            const data = await response.json();
            console.log('Nota submetida com sucesso:', data.message);
    
            handleCloseModal();
    
            setSuccessModalVisible(true);
        } catch (error) {
            console.error('Erro ao submeter nota:', error);
        }
    };                

    if (loading) return <p>Loading...</p>;

    return (
        <div className="py-12 botoes">
            <div className="mx-auto sm:px-6 lg:px-8">
                <h1 className="text-[#9D1717] text-2xl">Disciplinas</h1>
                <div className="pt-6">
                    {disciplinesWithStudents.length > 0 ? (
                        <ul className="list-disc pl-5">
                            {disciplinesWithStudents.map((disciplina) => {
                                const validStudents = disciplina.alunos.filter(aluno => aluno.idaluno !== null);
                                const isExpanded = expandedDisciplines[disciplina.iddisciplina];

                                return (
                                    <li key={disciplina.iddisciplina} className="list-none mb-2">
                                        <div className="flex items-center cursor-pointer" onClick={() => toggleDiscipline(disciplina.iddisciplina)}>
                                            <span
                                                className={`mr-2 inline-block w-0 h-0 border-t-4 border-b-4 border-l-8 border-transparent border-l-black transform ${
                                                    isExpanded ? 'rotate-90' : 'rotate-0'
                                                }`}
                                            />
                                            <h2 className="font-bold">
                                                {disciplina.nome_disciplina} com {disciplina.carga_horaria} horas de carga horária
                                            </h2>
                                        </div>
                                        {isExpanded && (
                                            <div className="pl-6">
                                                {validStudents.length > 0 ? (
                                                    <ul className="list-disc pl-5">
                                                        {validStudents.map((aluno) => (
                                                            <li key={aluno.idaluno}>
                                                                {aluno.nome_aluno} -{' '}
                                                                {aluno.nota !== null ? (
                                                                    <span>Obteve {aluno.nota} valores</span>
                                                                ) : (
                                                                    <button
                                                                        className="bg-blue-500 text-white px-2 py-1 rounded"
                                                                        onClick={() => handleOpenModal(aluno, disciplina)}
                                                                    >
                                                                        Submeter Nota
                                                                    </button>
                                                                )}
                                                            </li>
                                                        ))}
                                                    </ul>
                                                ) : (
                                                    <p>A disciplina {disciplina.nome_disciplina} ainda não tem alunos.</p>
                                                )}
                                            </div>
                                        )}
                                    </li>
                                );
                            })}
                        </ul>
                    ) : (
                        <p>Você não dá aulas em nenhuma disciplina.</p>
                    )}
                </div>
            </div>

            {modalData && (
                <div className="fixed inset-0 bg-gray-600 bg-opacity-50 flex justify-center items-center">
                    <div className="bg-white p-6 rounded shadow-lg">
                        <h2 className="text-lg font-bold">Submeter Nota</h2>
                        <p>
                            Disciplina: <strong>{modalData.disciplina.nome_disciplina}</strong>
                        </p>
                        <p>
                            Aluno: <strong>{modalData.aluno.nome_aluno}</strong>
                        </p>
                        <input
                            type="number"
                            min="0"
                            max="20"
                            value={nota}
                            onChange={(e) => setNota(Number(e.target.value))}
                            className="border p-2 mt-2 w-full"
                            placeholder="Insira a nota (0-20)"
                        />
                        <div className="mt-4 flex justify-end">
                            <button className="bg-red-500 text-white px-4 py-2 mr-2 rounded" onClick={handleCloseModal}>
                                Cancelar
                            </button>
                            <button className="bg-green-500 text-white px-4 py-2 rounded" onClick={() => handleSubmitNota(modalData.aluno.idaluno, modalData.disciplina.iddisciplina, nota)}>
                                Submeter
                            </button>
                        </div>
                    </div>
                </div>
            )}

            {successModalVisible && (
                <div className="fixed inset-0 bg-gray-600 bg-opacity-50 flex justify-center items-center">
                    <div className="bg-white p-6 rounded shadow-lg">
                        <h2 className="text-lg font-bold text-green-500">Sucesso!</h2>
                        <p>A nota foi submetida com sucesso.</p>
                        <div className="mt-4 flex justify-center">
                            <button
                                className="bg-blue-500 text-white px-4 py-2 rounded"
                                onClick={() => window.location.reload()}
                            >
                                OK
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default Disciplinas;
