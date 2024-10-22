import Checkbox from '@/Components/Checkbox';
import InputError from '@/Components/InputError';
import InputLabel from '@/Components/InputLabel';
import PrimaryButton from '@/Components/PrimaryButton';
import TextInput from '@/Components/TextInput';
import GuestLayout from '@/Layouts/GuestLayout';
import { Head, Link, useForm } from '@inertiajs/react';

export default function Login({ status, canResetPassword }) {
    const { data, setData, post, processing, errors, reset } = useForm({
        email: '',
        password: '',
        remember: false,
    });

    const submit = (e) => {
        e.preventDefault();
        post(route('login'), {
            onFinish: () => reset('password'),
        });
    };

    return (
        <GuestLayout>
            <Head title="Log in" />

            <div className="flex flex-col items-center justify-center min-h-screen">
                <div className="text-white text-5xl text-center mb-11">
                    Bem-vindo ao Infoget
                </div>

                <div className="w-full max-w-md mt-8 p-6 rounded-lg">
                    {status && (
                        <div className="mb-8 text-sm font-medium text-white text-center">
                            {status}
                        </div>
                    )}

                    <form onSubmit={submit} className="space-y-10">
                        <div>
                            <InputLabel htmlFor="email" value="Email" className="text-white" />
                            <TextInput
                                id="email"
                                type="email"
                                name="email"
                                value={data.email}
                                className="mt-1 block w-full py-3 px-4 rounded-lg" 
                                autoComplete="username"
                                isFocused={true}
                                onChange={(e) => setData('email', e.target.value)}
                            />
                            <InputError message={errors.email} className="mt-2 text-white" />
                        </div>

                        <div>
                            <InputLabel htmlFor="password" value="Password" className="text-white" />
                            <TextInput
                                id="password"
                                type="password"
                                name="password"
                                value={data.password}
                                className="mt-1 block w-full py-3 px-4 rounded-lg" 
                                autoComplete="current-password"
                                onChange={(e) => setData('password', e.target.value)}
                            />
                            <InputError message={errors.password} className="mt-2 text-white" />
                            
                            {canResetPassword && (
                                <Link
                                    href={route('password.request')}
                                    className="mt-2 block text-sm text-white underline hover:text-gray-300 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
                                >
                                    Esqueceu-se da palavra-chave?
                                </Link>
                            )}
                        </div>

                        <div className="flex items-center">
                            <Checkbox
                                name="remember"
                                checked={data.remember}
                                onChange={(e) => setData('remember', e.target.checked)}
                            />
                            <label htmlFor="remember" className="ml-2 text-sm text-white">
                                Lembrar-me
                            </label>
                        </div>

                        <div className="flex items-center justify-center">
                            <PrimaryButton 
                                className="w-[200px] max-w-xs py-5 bg-white text-black hover:bg-[#9D1717] hover:text-white flex items-center justify-center" disabled={processing}>
                               
                                <span className="text-black">Entrar</span>
                            </PrimaryButton>
                        </div>
                    </form>
                </div>
            </div>
        </GuestLayout>
    );
}
