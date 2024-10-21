import ApplicationLogo from '@/Components/ApplicationLogo';
import { Link } from '@inertiajs/react';

export default function GuestLayout({ children }) {
    return (
        <div className="flex min-h-screen bg-[url('/resources/js/Pages/Auth/output(1).png')] bg-no-repeat bg-left bg-cover">
            <div className="flex flex-col bg-gradient-to-br from-[#9D1717] to-[#4F0E00] w-[700px] min-h-screen">
                <div className="mt-0 w-full overflow-hidden px-6 py-4 text-left flex-grow">
                    {children}
                </div>
            </div>
        </div>
    );
}
