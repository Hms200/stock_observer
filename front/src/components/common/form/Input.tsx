import { HTMLAttributes } from 'react'
import clsx from 'clsx'

interface InputProps extends HTMLAttributes<HTMLInputElement> {
    type: 'text' | 'password' | 'email'
    value?: string
    label: string
    required?: boolean
    placeholder?: string
}

const Input = ({ type, value, label, required, placeholder, ...rest }: InputProps) => {
    return (
        <label className={'text-black font-bold px-2 text-xl'}>
            {label}
            <input
                className={clsx(
                    `w-full h-12 rounded-lg border bg-white text-black text-xl font-normal p-4 mt-2`,
                    required &&
                        (value === undefined || value === null || value === '') &&
                        'placeholder-danger placeholder-shown:font-bold',
                )}
                type={type}
                value={value}
                placeholder={placeholder}
                {...rest}
            />
        </label>
    )
}

export default Input
