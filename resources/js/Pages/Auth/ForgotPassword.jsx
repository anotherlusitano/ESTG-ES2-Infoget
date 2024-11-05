import InputError from '@/Components/InputError';
import PrimaryButton from '@/Components/PrimaryButton';
import TextInput from '@/Components/TextInput';
import GuestLayout from '@/Layouts/GuestLayout';
import { Head, useForm } from '@inertiajs/react';
import { Link } from '@inertiajs/react';

export default function ForgotPassword({ status }) {
    const { data, setData, post, processing, errors } = useForm({
        email: '',
    });

    const submit = (e) => {
        e.preventDefault();
        post(route('password.email'));
    };

    return (
        <GuestLayout>
            <Head title="Recuperar Senha" />

            <div className="absolute top-6 left-6">
                <Link href={route('login')}>
                    <svg class="w-6 h-6 text-gray-800 dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 10">
						<path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 5H1m0 0 4 4M1 5l4-4"/>
					</svg>
                </Link>
            </div>

            <div className="text-white text-5xl text-center mb-11" style={{ marginTop: '200px', marginBottom: '100px' }}>
                Recuperação de Conta
            </div>

            <div className="mb-1 text-sm text-white">
                Email:
            </div>

            <form onSubmit={submit}>
                <TextInput
                    id="email"
                    type="email"
                    name="email"
                    value={data.email}
                    className="mt-1 block w-full"
                    isFocused={true}
                    onChange={(e) => setData('email', e.target.value)}
                />
				
				{status && (
					<div className="mt-2 text-sm font-medium text-orange-400 text-left">
					{status}
					</div>
				)}
				
                <InputError message={errors.email} className="mt-2 text-orange-400" />

                <div className="mt-4 flex items-center justify-end">
                    <PrimaryButton className="ms-4" disabled={processing}>
                        Enviar Link para Redefinição de Senha
                    </PrimaryButton>
                </div>
            </form>
        </GuestLayout>
    );
}
