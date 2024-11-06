import { ChangeEvent, HTMLAttributes } from 'react'
import clsx from 'clsx'

interface InputProps extends HTMLAttributes<HTMLInputElement> {
    type: 'text' | 'password' | 'email'
    value?: string
    label: string
    required?: boolean
    placeholder?: string
    className?: string
    disabled?: boolean
    onChange?: (e: ChangeEvent<HTMLInputElement>) => void
}

const Input = ({
    type,
    value,
    label,
    required,
    placeholder,
    className,
    disabled,
    onChange,
    ...rest
}: InputProps) => {
    return (
        <label className={'w-full text-black font-bold px-2 text-xl'}>
            {label}
            <input
                className={clsx(
                    `w-full h-12 rounded-lg border bg-white text-black text-xl font-normal p-4 mt-2`,
                    required && 'placeholder-danger placeholder-shown:font-bold',
                    className && className,
                )}
                type={type}
                value={value}
                placeholder={placeholder}
                disabled={disabled}
                onChange={onChange}
                {...rest}
            />
        </label>
    )
}

export default Input
