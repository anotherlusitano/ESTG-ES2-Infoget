import InitialLayout from '@/Layouts/InicialLayout';
import ChatBot from '@/Components/ChatBot';
import { Head, usePage } from '@inertiajs/react';
import React, { useState, useEffect, useRef } from 'react';

export default function Dashboard() {
    const user = usePage().props.auth.user;
    const [showChat, setShowChat] = useState(false);

    const toggleChat = () => {
        if (!showChat) {
            setShowChat(true);
        }
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
            botoes={user.role == 2 && (
                <BotoesProfessor toggleChat={toggleChat}/>
            )}
            botoes={user.role == 3 && (
                <BotoesAluno toggleChat={toggleChat}/>
            )}
        >
            <Head title="Dashboard" />

            {showChat && <ChatBot />}
        </InitialLayout>
    );
}

function BotoesProfessor({toggleChat}) {
    return (
        <div className="py-12 text-center">
            <div className="mx-auto max-w-7xl sm:px-6 lg:px-8 pb-8">
                <div className="overflow-hidden bg-white sm:rounded-lg">
                    <div className="p-6 text-black">Dados Pessoais</div>
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
                <div onClick={toggleChat} className="overflow-hidden bg-white sm:rounded-lg cursor-pointer">
                    <div className="p-6 text-black">Chatbot</div>
                </div>
            </div>
        </div>
    );
}

function BotoesAluno({toggleChat}) {
    return (
        <div className="py-12 text-center">
            <div className="mx-auto max-w-7xl sm:px-6 lg:px-8 pb-8">
                <div className="overflow-hidden bg-white sm:rounded-lg">
                    <div className="p-6 text-black">Dados Pessoais</div>
                </div>
            </div>
            <div className="mx-auto max-w-7xl sm:px-6 lg:px-8 pb-8">
                <div className="overflow-hidden bg-white sm:rounded-lg">
                    <div className="p-6 text-black">Dados Curriculares</div>
                </div>
            </div>
            <div className="mx-auto max-w-7xl sm:px-6 lg:px-8 pb-8">
                <div className="overflow-hidden bg-white sm:rounded-lg">
                    <div className="p-6 text-black">Disciplinas</div>
                </div>
            </div>
            <div className="mx-auto max-w-7xl sm:px-6 lg:px-8 pb-8">
                <div onClick={toggleChat} className="overflow-hidden bg-white sm:rounded-lg cursor-pointer">
                    <div className="p-6 text-black">Chatbot</div>
                </div>
            </div>
        </div>
    );
}
