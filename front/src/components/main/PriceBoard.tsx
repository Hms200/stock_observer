import { StockPriceOutput } from '../../config/ApiConfig.ts'

interface PriceBoardProps {
    visible: boolean
    priceData: StockPriceOutput
}

const PriceBoard = ({ visible, priceData }: PriceBoardProps) => {
    const isPositive: boolean = priceData.prdy_vrss_sign === '2' || priceData.prdy_vrss_sign === '1'
    const priceGap = Number(priceData.stck_oprc) - Number(priceData.stck_hgpr)
    return (
        <div className={`w-1/4 border text-black items-start ${visible ? 'flex flex-col' : 'hidden'}`}>
            <div className={`flex justify-start gap-1 ${isPositive ? 'text-danger' : 'text-blue-700'}`}>
                <span>현재가: {priceData.stck_prpr}</span>
                <span>
                    {isPositive ? '+' : '-'}
                    {priceData.prdy_vrss}
                </span>
                <span>{priceData.prdy_ctrt}%</span>
            </div>
            <span>시가: {priceData.stck_oprc}</span>
            <span className={priceGap > 0 ? 'text-danger' : priceGap === 0 ? 'text-black' : 'text-blue-700'}>
                최고가: {priceData.stck_hgpr}
            </span>
            <span className={priceGap > 0 ? 'text-danger' : priceGap === 0 ? 'text-black' : 'text-blue-700'}>
                최저가: {priceData.stck_lwpr}
            </span>
            <span className={'text-danger'}>상한가: {priceData.stck_mxpr}</span>
            <span className={'text-blue-700'}>하한가: {priceData.stck_llam}</span>
        </div>
    )
}

export default PriceBoard
