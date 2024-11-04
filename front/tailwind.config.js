import colors from 'tailwindcss/colors'

/** @type {import('tailwindcss').Config} */

export default {
    content: ['./src/**/*.{html,js,jsx,ts,tsx}'],
    theme: {
        colors: {
            transparent: 'transparent',
            primary: '#3b82f6',
            secondary: '#9ca3af',
            danger: '#dc2626',
            warning: '#facc15',
            success: '#22c55e',
            black: colors.black,
            white: colors.white,
            blue: colors.blue,
            slate: colors.slate,
            gray: colors.gray,
            zinc: colors.zinc,
            neutral: colors.neutral,
            stone: colors.stone,
            red: colors.red,
            orange: colors.orange,
            amber: colors.amber,
            yellow: colors.yellow,
            lime: colors.lime,
            green: colors.green,
            emerald: colors.emerald,
            teal: colors.teal,
            cyan: colors.cyan,
            sky: colors.sky,
            indigo: colors.indigo,
            violet: colors.violet,
            purple: colors.purple,
            fuchsia: colors.fuchsia,
            pink: colors.pink,
            rose: colors.rose,
        },
        extend: {},
    },
    plugins: [],
}
