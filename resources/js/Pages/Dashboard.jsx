import InitialLayout from '@/Layouts/InicialLayout';
import ChatBot from '@/Components/ChatBot';
import DadosPessoais from '@/Components/DadosPessoais';
import DadosCurriculares from '@/Components/DadosCurriculares';
import { Head, usePage } from '@inertiajs/react';
import React, { useState, useEffect } from 'react';
import Cursos from '@/Components/Cursos';
import Disciplinas from '@/Components/Disciplinas';
import Turmas from '@/Components/Turmas';

export default function Dashboard() {
    const user = usePage().props.auth.user;
    const [currentComponent, setCurrentComponent] = useState(null);
    
    const handleButtonClick = (component) => {
        setCurrentComponent(component);
    };

    useEffect(() => {
        fetch('/api/user')
          .then(response => response.json())
          .then(data => {
            if (data.role === 1) {
                window.location.href = '/admin';
            }
          })
          .catch(error => console.error("Erro ao buscar dados do utilizador:", error));
      }, []);

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
            {currentComponent === 'dadosCurriculares' && <DadosCurriculares />}
            {currentComponent === 'cursos' && <Cursos />}
            {currentComponent === 'disciplinas' && <Disciplinas />}
            {currentComponent === 'turmas' && <Turmas />}
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
                    <div onClick={() => onButtonClick('disciplinas')} className="p-6 text-black cursor-pointer">Disciplinas Lecionadas</div>
                </div>
            </div>
            <div className="mx-auto max-w-7xl sm:px-6 lg:px-8 pb-8">
                <div className="overflow-hidden bg-white sm:rounded-lg">
                    <div onClick={() => onButtonClick('turmas')} className="p-6 text-black cursor-pointer">Turmas</div>
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
                    <div onClick={() => onButtonClick('dadosCurriculares')} className="p-6 text-black cursor-pointer">Dados Curriculares</div>
                </div>
            </div>
            <div className="mx-auto max-w-7xl sm:px-6 lg:px-8 pb-8">
                <div className="overflow-hidden bg-white sm:rounded-lg">
                    <div onClick={() => onButtonClick('cursos')} className="p-6 text-black cursor-pointer">Cursos</div>
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
