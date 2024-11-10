import { SearchResultOutput, StockApiResponse } from '../../config/ApiConfig.ts'
import clsx from 'clsx'
import Button from '../common/Button.tsx'

interface SearchResultProps {
    data?: StockApiResponse<SearchResultOutput>
    visible: boolean
    onClick: () => void
    onAskingPriceButtonClick: () => void
    onCancelClick: () => void
    isConnected: boolean
}

const SearchResultBox = ({
    data,
    visible = false,
    onClick,
    onAskingPriceButtonClick,
    onCancelClick,
    isConnected,
}: SearchResultProps) => {
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
            <div className={'w-full flex justify-start'}>
                <Button value={'감시'} textColor={'white'} onClick={onClick} disabled={!isConnected} />
                <Button
                    value={'중지'}
                    textColor={'white'}
                    color={'secondary'}
                    onClick={onCancelClick}
                    disabled={!isConnected}
                />
                <Button
                    value={'호가감시'}
                    color={'success'}
                    textColor={'white'}
                    disabled={!isConnected}
                    onClick={onAskingPriceButtonClick}
                />
            </div>
        </div>
    )
}

export default SearchResultBox
