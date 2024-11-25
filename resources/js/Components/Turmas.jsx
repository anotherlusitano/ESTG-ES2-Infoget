import React, { useEffect, useState } from 'react';

const Turmas = () => {
    const [coursesWithStudents, setCoursesWithStudents] = useState([]);
    const [loading, setLoading] = useState(true);
    const [expandedCourses, setExpandedCourses] = useState({});

    useEffect(() => {
        fetch('/dashboard/estudados')
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

    const toggleCourse = (idcurso) => {
        setExpandedCourses((prevState) => ({
            ...prevState,
            [idcurso]: !prevState[idcurso],
        }));
    };

    if (loading) return <p>Loading...</p>;

    return (
        <div className="py-12 botoes">
            <div className="mx-auto sm:px-6 lg:px-8">
                <h1 className="text-[#9D1717] text-2xl">Turmas</h1>
                <div className="pt-6">
                    {coursesWithStudents.length > 0 ? (
                        <ul className="list-disc pl-5">
                            {coursesWithStudents.map((course) => {
                                const validStudents = course.estudados.filter(estudado => estudado.idaluno !== null);
                                const isExpanded = expandedCourses[course.idcurso];

                                return (
                                    <li key={course.idcurso} className="list-none mb-2">
                                        <div className="flex items-center cursor-pointer" onClick={() => toggleCourse(course.idcurso)}>
                                            <span className={`mr-2 inline-block w-0 h-0 border-t-4 border-b-4 border-l-8 border-transparent border-l-black transform ${isExpanded ? 'rotate-90' : 'rotate-0'}`}/>
                                            <h2 className="font-bold">{course.nome_curso} - {course.nome_coordenador}</h2>
                                        </div>
                                        {isExpanded && (
                                            <div className="pl-6">
                                                {validStudents.length > 0 ? (
                                                    <ul className="list-disc pl-5">
                                                        {validStudents.map((estudado) => (
                                                            <li key={estudado.idaluno}>{estudado.nome_aluno}</li>
                                                        ))}
                                                    </ul>
                                                ) : (
                                                    <p>O curso {course.nome_curso} ainda não tem alunos.</p>
                                                )}
                                            </div>
                                        )}
                                    </li>
                                );
                            })}
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
