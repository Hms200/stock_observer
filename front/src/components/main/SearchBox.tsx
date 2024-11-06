import Input from '../common/form/Input.tsx'
import Button from '../common/Button.tsx'
import { ChangeEvent } from 'react'

interface SearchBoxProps {
    code: string
    onChange: (e: ChangeEvent<HTMLInputElement>) => void
    onClick: () => void
}

const SearchBox = ({ code, onChange, onClick }: SearchBoxProps) => {
    return (
        <div className={'w-1/3 h-1/6 border flex justify-center items-center p-5'}>
            <div className={'w-3/4 self-start'}>
                <Input type={'text'} label={'종목코드'} value={code} onChange={onChange} />
            </div>
            <Button className={'ms-5'} value={'검색'} textColor={'white'} onClick={onClick} />
        </div>
    )
}

export default SearchBox
