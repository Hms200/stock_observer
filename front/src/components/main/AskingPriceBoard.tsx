import { StockAskingPriceOutput } from '../../config/ApiConfig.ts'

interface AskingPriceBoardProps {
    visible: boolean
    askingPriceData: StockAskingPriceOutput
}

const AskingPriceBoard = ({ visible, askingPriceData }: AskingPriceBoardProps) => {
    const isPositive: boolean = !askingPriceData.ntby_aspr_rsqn.startsWith('-')
    return (
        <div className={`w-1/4 border text-black items-start ${visible ? 'flex flex-col' : 'hidden'}`}>
            <div className={'flex flex-col items-start gap-1'}>
                <span>매도잔량: {askingPriceData.total_askp_rsqn}</span>
                <span>매수잔량: {askingPriceData.total_bidp_rsqn}</span>
                <span>
                    순매수잔량:{' '}
                    <span className={`${isPositive ? 'text-danger' : 'text-blue-700'}`}>
                        {askingPriceData.ntby_aspr_rsqn}
                    </span>
                </span>
            </div>
        </div>
    )
}

export default AskingPriceBoard
