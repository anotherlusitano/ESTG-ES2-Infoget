import React, { useState, useEffect, useRef } from 'react';
import { usePage } from '@inertiajs/react';

export default function ChatBot() {
    const user = usePage().props.auth.user;
    const [messages, setMessages] = useState([
        { sender: 'bot', text: `Olá ${user.name}, eu sou o seu assistente de hoje, como posso ajudar?` }
    ]);
    const [inputMessage, setInputMessage] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const [dots, setDots] = useState('');
    const messagesContainerRef = useRef(null);

    const handleSendMessage = async () => {
        if (inputMessage.trim() === '' || isLoading) return;

        const newMessages = [
            ...messages,
            { sender: 'user', text: inputMessage },
        ];
        setMessages(newMessages);
        setInputMessage('');
        setIsLoading(true);

        const userMessage = inputMessage;
        const csrfToken = document.head.querySelector('meta[name="csrf-token"]').content;

        try {
            const response = await fetch("/dashboard/generate", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "X-CSRF-TOKEN": csrfToken,
                },
                body: JSON.stringify({
                    model: "llama3.2",
                    prompt: userMessage,
                }),
            });

            if (!response.ok) {
                throw new Error('Erro na comunicação com o servidor');
            }

            const data = await response.json();
            const botResponse = data.response;

            setMessages((prevMessages) => [
                ...prevMessages,
                { sender: 'bot', text: botResponse },
            ]);
            setIsLoading(false);

        } catch (error) {
            console.error("Erro ao se comunicar com o Ollama:", error);
            setMessages((prevMessages) => [
                ...prevMessages,
                { sender: 'bot', text: "Desculpe, houve um erro ao se comunicar com o assistente." },
            ]);
            setIsLoading(false);
        }
    };

    const handleKeyDown = (e) => {
        if (e.key === 'Enter' && !e.shiftKey) {
            e.preventDefault();
            handleSendMessage();
        }
    };

    useEffect(() => {
        if (messagesContainerRef.current) {
            messagesContainerRef.current.scrollTop = messagesContainerRef.current.scrollHeight;
        }
    }, [messages]);

    useEffect(() => {
        let interval;
        let count = 1;

        if (isLoading) {
            setDots('.');
            interval = setInterval(() => {
                setDots('.'.repeat(count));
                count = count === 3 ? 1 : count + 1;
            }, 500);
        } else {
            setDots('');
        }

        return () => clearInterval(interval);
    }, [isLoading]);

    return (
        <div className="mx-auto w-full p-4 rounded-md relative">
            <div 
                ref={messagesContainerRef}
                className="flex flex-col items-start p-2 z-10 h-[calc(100vh-250px)] overflow-y-auto scrollbar-thin scrollbar-thumb-gray-400 scrollbar-track-gray-200 rounded-lg"
                style={{ scrollBehavior: 'smooth' }}
            >
                <div className="p-2 rounded-md w-full">
                    {messages.map((msg, index) => (
                        <div
                            key={index}
                            className={`flex items-start mb-4 ${msg.sender === 'user' ? 'justify-end' : ''}`}
                        >
                            {msg.sender === 'bot' && (
                                <img 
                                    src="https://ollama.com/public/ollama.png" 
                                    alt="Ollama Chatbot" 
                                    className="w-12 h-12 mr-2 object-contain bg-white"
                                />
                            )}
                            <div
                                className={`inline-block p-3 mr-4 rounded-lg ${msg.sender === 'user' ? 'bg-black text-white' : 'bg-black text-white'}`}
                                style={{
                                    wordBreak: 'break-word',
                                    whiteSpace: 'pre-wrap',
                                    overflowWrap: 'break-word',
                                }}
                            >
                                {msg.text}
                            </div>
                        </div>
                    ))}
                    {isLoading && (
                        <div className="flex items-start mb-4">
                            <img 
                                src="https://ollama.com/public/ollama.png" 
                                alt="Ollama Chatbot" 
                                className="w-12 h-12 mr-2 object-contain bg-white"
                            />
                            <div className="inline-block p-3 mr-4 rounded-lg bg-black text-white w-8">
                                {dots}
                            </div>
                        </div>
                    )}
                </div>
            </div>

            <div className="sticky bottom-0 flex items-center rounded-lg w-full p-4 mt-4 bg-white">
                <textarea
                    className="w-full h-12 border border-gray-300 rounded-lg resize-none mt-2"
                    placeholder="Escreva aqui..."
                    value={inputMessage}
                    onChange={(e) => setInputMessage(e.target.value)}
                    onKeyDown={handleKeyDown}
                    disabled={isLoading}
                />
                <button 
                    className="ml-4 w-12 h-12 p-0 bg-transparent border-none mt-2"
                    onClick={handleSendMessage}
                    disabled={isLoading}
                >
                    <img 
                        src="https://cdn-icons-png.flaticon.com/512/5611/5611871.png" 
                        alt="Enviar"
                        className="w-full h-full object-contain"
                    />
                </button>
            </div>
        </div>
    );
}