import { SearchResult } from '../../config/ApiConfig.ts'
import clsx from 'clsx'

interface SearchResultProps {
    data?: SearchResult
    visible: boolean
}

const SearchResultBox = ({ data, visible = false }: SearchResultProps) => {
    return (
        <div className={`${clsx(`w-3/4 border my-5 text-black p-5`, visible ? 'flex flex-col' : 'hidden')}`}>
            <span className={'font-bold text-2xl'}>
                {data?.output?.prdt_name} {data?.output?.pdno.slice(6)}{' '}
                {data?.output?.admn_item_yn === 'Y' && (
                    <span className={'text-danger text-sm'}>관리종목</span>
                )}
                {data?.output?.tr_stop_yn === 'Y' && <span className={'text-base'}>거래 정지</span>}
            </span>
            <div className={'w-full flex flex-row justify-end pe-5'}>
                <span className={'mx-10'}>전일 종가: {data?.output?.bfdy_clpr}</span>
                <span>당일 종가: {data?.output?.thdt_clpr}</span>
            </div>
        </div>
    )
}

export default SearchResultBox
