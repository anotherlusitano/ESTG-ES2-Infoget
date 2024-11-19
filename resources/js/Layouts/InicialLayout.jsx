import { usePage } from '@inertiajs/react';

export default function InitialLayout({ botoesprofessor, botoesaluno, children }) {
    const user = usePage().props.auth.user;

    return (
        <div className="bg-gradient-to-br from-[rgb(157,23,23)] from-30% to-[#4F0E00]">
            <nav>
                <div className="ml-16 max-w-7xl px-4 sm:px-6 lg:px-8">
                    <div className="flex h-32 justify-between">
                        <div className="flex items-center text-white text-4xl">
                            Bem-vindo/a {user.name}!
                        </div>
                    </div>
                </div>
            </nav>


            <div class="flex w-screen h-screen">
                <div class="flex-none w-64 h-full">
                    <botoes>{user.role == 2 && (botoesprofessor)}</botoes>
                    <botoes>{user.role == 3 && (botoesaluno)}</botoes>
                </div>
                <div class="flex-grow bg-white border-8 h-full">
                    <main>{children}</main>
                </div>
            </div>

        </div>
    );
}

