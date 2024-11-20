import InitialLayout from '@/Layouts/InicialLayout';
import ChatBot from '@/Components/ChatBot';
import DadosPessoais from '@/Components/DadosPessoais';
import { Head, usePage } from '@inertiajs/react';
import React, { useState, useEffect } from 'react';
import Cursos from '@/Components/Cursos';

export default function Dashboard() {
    const user = usePage().props.auth.user;
    const [currentComponent, setCurrentComponent] = useState(null);

    const handleButtonClick = (component) => {
        setCurrentComponent(component);
    };

    useEffect(() => {
        const hasReloaded = localStorage.getItem('hasReloaded');
        
        if (!hasReloaded) {
            localStorage.setItem('hasReloaded', 'true');
            window.location.reload();
        }
    }, []);

    useEffect(() => {
        document.body.style.overflow = 'hidden';
        document.documentElement.style.overflow = 'hidden';
        
        return () => {
            document.body.style.overflow = '';
            document.documentElement.style.overflow = '';
        };
    }, []);

    return (
        <InitialLayout
            botoesprofessor={user.role == 2 && (
                <BotoesProfessor onButtonClick={handleButtonClick} />
            )}
            botoesaluno={user.role == 3 && (
                <BotoesAluno onButtonClick={handleButtonClick} />
            )}
        >
            <Head title="Dashboard" />

            {currentComponent === 'dadosPessoais' && <DadosPessoais />}
            {currentComponent === 'cursos' && <Cursos />}
            {currentComponent === 'chat' && <ChatBot />}
        </InitialLayout>
    );
}

function BotoesProfessor({ onButtonClick }) {
    return (
        <div className="py-12 text-center">
            <div className="mx-auto max-w-7xl sm:px-6 lg:px-8 pb-8">
                <div className="overflow-hidden bg-white sm:rounded-lg">
                    <div onClick={() => onButtonClick('dadosPessoais')} className="p-6 text-black cursor-pointer">Dados Pessoais</div>
                </div>
            </div>
            <div className="mx-auto max-w-7xl sm:px-6 lg:px-8 pb-8">
                <div className="overflow-hidden bg-white sm:rounded-lg">
                    <div className="p-6 text-black">Disciplinas Lecionadas</div>
                </div>
            </div>
            <div className="mx-auto max-w-7xl sm:px-6 lg:px-8 pb-8">
                <div className="overflow-hidden bg-white sm:rounded-lg">
                    <div className="p-6 text-black">Turmas</div>
                </div>
            </div>
            <div className="mx-auto max-w-7xl sm:px-6 lg:px-8 pb-8">
                <div onClick={() => onButtonClick('chat')} className="overflow-hidden bg-white sm:rounded-lg cursor-pointer">
                    <div className="p-6 text-black">Chatbot</div>
                </div>
            </div>
        </div>
    );
}

function BotoesAluno({ onButtonClick }) {
    return (
        <div className="py-12 text-center">
            <div className="mx-auto max-w-7xl sm:px-6 lg:px-8 pb-8">
                <div className="overflow-hidden bg-white sm:rounded-lg">
                    <div onClick={() => onButtonClick('dadosPessoais')} className="p-6 text-black cursor-pointer">Dados Pessoais</div>
                </div>
            </div>
            <div className="mx-auto max-w-7xl sm:px-6 lg:px-8 pb-8">
                <div className="overflow-hidden bg-white sm:rounded-lg">
                    <div className="p-6 text-black">Dados Curriculares</div>
                </div>
            </div>
            <div className="mx-auto max-w-7xl sm:px-6 lg:px-8 pb-8">
                <div className="overflow-hidden bg-white sm:rounded-lg">
                    <div onClick={() => onButtonClick('cursos')} className="p-6 text-black">Disciplinas</div>
                </div>
            </div>
            <div className="mx-auto max-w-7xl sm:px-6 lg:px-8 pb-8">
                <div onClick={() => onButtonClick('chat')} className="overflow-hidden bg-white sm:rounded-lg cursor-pointer">
                    <div className="p-6 text-black">Chatbot</div>
                </div>
            </div>
        </div>
    );
}
