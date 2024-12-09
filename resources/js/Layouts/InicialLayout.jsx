import { usePage, Link } from '@inertiajs/react';

export default function InitialLayout({ botoesprofessor, botoesaluno, children }) {
    const user = usePage().props.auth.user;

    return (
        <div className="bg-gradient-to-br from-[rgb(157,23,23)] from-30% to-[#4F0E00]">
            <nav>
                <div className="ml-16 max-w-7xl px-4 sm:px-6 lg:px-8">
                    <div className="flex h-32 justify-between items-center">
                        <div className="text-white text-4xl">
                            Bem-vindo/a {user.name}!
                        </div>
                        <div className="absolute top-8 right-6">
                            <Link
                                method="post"
                                href={route('logout')}
                                as="button"
                                className="flex items-center text-white hover:text-gray-300 transition"
                            >
                                <svg
                                    xmlns="http://www.w3.org/2000/svg"
                                    width="50"
                                    height="50"
                                    fill="currentColor"
                                    className="bi bi-box-arrow-right"
                                    viewBox="0 0 16 16"
                                >
                                    <path
                                        fillRule="evenodd"
                                        d="M10 12.5a.5.5 0 0 1-.5.5h-8a.5.5 0 0 1-.5-.5v-9a.5.5 0 0 1 .5-.5h8a.5.5 0 0 1 .5.5v2a.5.5 0 0 0 1 0v-2A1.5 1.5 0 0 0 9.5 2h-8A1.5 1.5 0 0 0 0 3.5v9A1.5 1.5 0 0 0 1.5 14h8a1.5 1.5 0 0 0 1.5-1.5v-2a.5.5 0 0 0-1 0z"
                                    />
                                    <path
                                        fillRule="evenodd"
                                        d="M15.854 8.354a.5.5 0 0 0 0-.708l-3-3a.5.5 0 0 0-.708.708L14.293 7.5H5.5a.5.5 0 0 0 0 1h8.793l-2.147 2.146a.5.5 0 0 0 .708.708z"
                                    />
                                </svg>
                            </Link>
                        </div>
                    </div>
                </div>
            </nav>

            <div className="flex w-screen h-screen">
                <div className="flex-none w-64 h-full">
                    <div>{user.role == 2 && botoesprofessor}</div>
                    <div>{user.role == 3 && botoesaluno}</div>
                </div>
                <div className="flex-grow bg-white border-8 h-full">
                    <main>{children}</main>
                </div>
            </div>
        </div>
    );
}
