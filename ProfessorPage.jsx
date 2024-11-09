import { usePage } from '@inertiajs/react';

export default function InitialLayout({ botoes, children }) {
    const user = usePage().props.auth.user;

    return (
        <div className="bg-gradient-to-br from-[rgb(157,23,23)] from-30% to-[#4F0E00] min-h-screen">
            <nav>
                <div className="ml-16 max-w-7xl px-4 sm:px-6 lg:px-8">
                    <div className="flex h-32 justify-between">
                        <div className="flex items-center text-white text-4xl">
                            Bem-vindo/a {user.name}!
                        </div>
                    </div>
                </div>
            </nav>

            <div className="flex w-screen h-full">
                <div className="flex-none w-64 h-full">
                    {botoes}
                </div>
                <div className="flex-grow bg-white border-8 h-full p-4">
                    <main>{children}</main>
                </div>
            </div>
        </div>
    );
}
import InitialLayout from '@/Layouts/InitialLayout';
import { Head, usePage } from '@inertiajs/react';

export default function Dashboard() {
    const user = usePage().props.auth.user;

            <InitialLayout
                botoes={
                    user.role === 2 ? <BotoesAluno /> : <BotoesProfessor />
                }
            >
                <Head title="Dashboard" />

                <div className="py-12 botoes">
                    <div className="mx-auto max-w-7xl sm:px-6 lg:px-8">
                        <div className="overflow-hidden bg-black shadow-sm sm:rounded-lg">
                            <div className="p-6 text-white">
                                Bem-vindo ao painel, {user.name}!
                            </div>
                        </div>
                    </div>
                </div>
            </InitialLayout>
    ;
}

function BotoesProfessor() {
    return (
        <div className="py-12 text-center">
            <div className="mx-auto max-w-7xl sm:px-6 lg:px-8 pb-8">
                <div className="overflow-hidden bg-white shadow-sm sm:rounded-lg">
                    <div className="p-6 text-black">
                        Plano de Aulas
                    </div>
                </div>
            </div>
            <div className="mx-auto max-w-7xl sm:px-6 lg:px-8 pb-8">
                <div className="overflow-hidden bg-white shadow-sm sm:rounded-lg">
                    <div className="p-6 text-black">
                        Atribuir Nota
                    </div>
                </div>
            </div>
            <div className="mx-auto max-w-7xl sm:px-6 lg:px-8 pb-8">
                <div className="overflow-hidden bg-white shadow-sm sm:rounded-lg">
                    <div className="p-6 text-black">
                        Lista de Alunos
                    </div>
                </div>
            </div>
            <div className="mx-auto max-w-7xl sm:px-6 lg:px-8 pb-8">
                <div className="overflow-hidden bg-white shadow-sm sm:rounded-lg">
                    <div className="p-6 text-black">
                        Calendário 
                    </div>
                </div>
            </div>
        </div>
    );
}