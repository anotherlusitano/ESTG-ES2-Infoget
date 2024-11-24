import React, { useEffect, useState } from 'react';

const Turmas = () => {
    const [coursesWithStudents, setCoursesWithStudents] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetch('/dashboard/students')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                setCoursesWithStudents(data.coursesWithStudents);
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
                <h1 className="text-[#9D1717] text-2xl">Turmas</h1>
                <div className="pt-6">
                    {coursesWithStudents.length > 0 ? (
                        <ul className="list-disc pl-5">
                            {coursesWithStudents.map((course) => (
                                <li key={course.idcurso}>
                                    <h2 className="font-bold">{course.nome_curso} - {course.nome_coordenador}</h2>
                                    {course.students && course.students.length > 0 ? (
                                        <ul>
                                            {course.students.map((student) => (
                                                <li key={student.idaluno}>{student.nome_aluno}</li>
                                            ))}
                                        </ul>
                                    ) : (
                                        <p>O curso {course.nome_curso} ainda não tem alunos.</p>
                                    )}
                                </li>
                            ))}
                        </ul>
                    ) : (
                        <p>Você não dá aulas em nenhum curso.</p>
                    )}
                </div>
            </div>
        </div>
    );
};

export default Turmas;
