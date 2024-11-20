import React, { useEffect, useState } from 'react';

const Cursos = () => {
    const [courses, setCourses] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetch('/dashboard/cursos')
            .then(response => response.json())
            .then(data => {
                setCourses(data.courses);
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
                <h1 className="text-[#9D1717] text-2xl">Cursos</h1>
                <div className="pt-6">
                    {courses.length > 0 ? (
                        <ul className="list-disc pl-5">
                            {courses.map((course) => (
                                <li key={course.id}>{course.nome}</li>
                            ))}
                        </ul>
                    ) : (
                        <p>Você não está matriculado em nenhum curso.</p>
                    )}
                </div>
            </div>
        </div>
    );
};

export default Cursos;
