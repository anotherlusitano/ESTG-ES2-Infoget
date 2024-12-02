import React, { useEffect, useState } from 'react';

const DadosCurriculares = () => {
    const [disciplinas, setDisciplinas] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetch('/dashboard/dados-curriculares')
            .then(response => response.json())
            .then(data => {
                setDisciplinas(data.disciplinas_com_notas);
                setLoading(false);
            })
            .catch(error => {
                console.error('Error fetching data:', error);
                setLoading(false);
            });
    }, []);

    if (loading) return <p>Loading...</p>;

    return (
        <div className="py-12 botoes">
            <div className="mx-auto sm:px-6 lg:px-8">
                <h1 className="text-[#9D1717] text-2xl">Disciplinas</h1>
                <div className="pt-6">
                    {disciplinas.length > 0 ? (
                        <ul className="list-disc pl-5">
                            {disciplinas.map((disciplina) => (
                                <li key={disciplina[0].nome_disciplina}>Na disciplina de {disciplina[0].nome_disciplina} tiveste a nota final de {disciplina[0].nota_aluno};</li>
                            ))}
                        </ul>
                    ) : (
                        <p>Você não tem nenhuma disciplina.</p>
                    )}
                </div>
            </div>
        </div>
    );
};

export default DadosCurriculares;