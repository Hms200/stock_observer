import { ReactNode } from 'react'

const MainLayout = ({ children }: { children: ReactNode }) => {
    return (
        <div className={'w-screen h-screen flex items-center justify-center'}>
            <div className={'container h-full flex items-center justify-center border'}>{children}</div>
        </div>
    )
}

export default MainLayout
