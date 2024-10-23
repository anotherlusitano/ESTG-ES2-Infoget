export default function InputError({ message, className = '', ...props }) {
    return message ? (
        <p
            {...props}
            className={'text-sm text-white ' + className}
        >
            {message}
        </p>
    ) : null;
}
