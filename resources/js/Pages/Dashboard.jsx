import InitialLayout from '@/Layouts/InicialLayout';
import { Head, usePage } from '@inertiajs/react';

export default function Dashboard() {
    const user = usePage().props.auth.user;

    return (
        <InitialLayout
            botoes={
                (
                    user.role == 2 && BotoesAluno()
                )
            }
        >
            <Head title="Dashboard" />

            {/* isto é o conteudo da prop "children"
            <div className="py-12 botoes">
                <div className="mx-auto max-w-7xl sm:px-6 lg:px-8">
                    <div className="overflow-hidden bg-black shadow-sm sm:rounded-lg">
                        <div className="p-6 text-white">
                            You're logged in! {user.name} !!!
                        </div>
                    </div>
                </div>
            </div> */}
        </InitialLayout>
    );
}

function BotoesAluno() {
    return (
        <div className="py-12 text-center">
            <div className="mx-auto max-w-7xl sm:px-6 lg:px-8 pb-8">
                <div className="overflow-hidden bg-white shadow-sm sm:rounded-lg">
                    <div className="p-6 text-black">
                        Dados Pessoais
                    </div>
                </div>
            </div>
            <div className="mx-auto max-w-7xl sm:px-6 lg:px-8 pb-8">
                <div className="overflow-hidden bg-white shadow-sm sm:rounded-lg">
                    <div className="p-6 text-black">
                        Dados Curriculares
                    </div>
                </div>
            </div>
            <div className="mx-auto max-w-7xl sm:px-6 lg:px-8 pb-8">
                <div className="overflow-hidden bg-white shadow-sm sm:rounded-lg">
                    <div className="p-6 text-black">
                        Disciplinas
                    </div>
                </div>
            </div>
        </div>)
}