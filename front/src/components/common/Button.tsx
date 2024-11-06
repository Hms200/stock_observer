import { HTMLAttributes, ReactNode } from 'react'
import { borderColorConfig, Color, Size, textColorConfig } from '../../config/LayoutConfig.ts'
import clsx from 'clsx'

interface ButtonProps extends HTMLAttributes<HTMLButtonElement> {
    className?: string
    value?: string | ReactNode
    rounded?: boolean
    border?: boolean
    borderColor?: Color
    size?: Size
    onClick?: () => void
    color?: Color
    textColor?: Color
    disabled?: boolean
}

const buttonColorConfig = {
    primary: `bg-primary hover:bg-blue-700 focus:bg-blue-800 disabled:bg-blue-300`,
    secondary: 'bg-secondary hover:bg-gray-700 focus:bg-gray-800 disabled:bg-gray-300',
    danger: 'bg-danger hover:bg-red-700 focus:bg-red-800 disabled:bg-red-300',
    success: 'bg-success hover:bg-green-700 focus:bg-green-800 disabled:bg-green-300',
    warning: 'bg-warning hover:bg-yellow-700 focus:bg-yellow-800 disabled:bg-yellow-300',
    black: 'bg-black',
    white: 'bg-white',
}

const Button = ({
    className,
    value,
    rounded,
    borderColor,
    border,
    size = 'md',
    onClick,
    color = 'primary',
    textColor = 'black',
    disabled = false,
    ...rest
}: ButtonProps) => {
    const classNames = clsx(
        `flex items-center justify-center cursor-pointer py-2 px-5`,
        `${buttonColorConfig[color]}`,
        `text-xl ${textColorConfig[textColor]}`,
        {
            rounded: rounded,
            border: border,
        },
        typeof borderColor === 'string' && border && `${borderColorConfig[borderColor]}`,
        size === 'sm' && 'w-12',
        size === 'md' && 'w-24',
        size === 'lg' && 'w-30 h-12',
        size === 'xlg' && 'w-36 h-14',
        className && className,
    )
    return (
        <button className={classNames} onClick={onClick} disabled={disabled} {...rest}>
            {typeof value === 'string' ? <span className={'align-middle'}>{value}</span> : value}
        </button>
    )
}

export default Button
