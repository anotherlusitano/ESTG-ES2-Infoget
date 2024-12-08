import { usePage } from '@inertiajs/react';
import MyImage from '/resources/js/Components/pinheirao.png';

export default function ChatBot() {
    const user = usePage().props.auth.user;

    return (
        <div className="py-12 botoes">
            <div className="mx-auto sm:px-6 lg:px-8">
                <h1 className="text-[#9D1717] text-2xl">Dados Pessoais</h1>
                <div className="pt-6">
                    Nome Completo: {user.name}
                    <br />
                    E-Mail Oficial: {user.email}
                    <br />
                    <div className='md-flex'>
                        <div className="pt-10">Este é o nosso grande lider: -&gt; </div>
                        <img src={MyImage} alt="Isto vai fazer que os testes do Aguiar não funcionem lol" className="rounded-full size-72" />
                    </div>

                </div>
            </div>
        </div>
    );
}
