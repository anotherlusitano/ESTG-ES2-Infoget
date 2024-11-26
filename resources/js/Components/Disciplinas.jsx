import React, { useEffect, useState } from 'react';

const Disciplinas = () => {
    const [disciplinas, setDisciplinas] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetch('/dashboard/disciplinas')
            .then(response => response.json())
            .then(data => {
                setDisciplinas(data.disciplinas);
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
                                <li key={disciplina.nome}>{disciplina.nome} com {disciplina.carga_horaria} horas de carga horária</li>
                            ))}
                        </ul>
                    ) : (
                        <p>Você não dá nenhuma disciplina.</p>
                    )}
                </div>
            </div>
        </div>
    );
};

export default Disciplinas;

