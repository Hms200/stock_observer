import SearchBox from '../components/main/SearchBox.tsx'
import { ChangeEvent, useEffect, useState } from 'react'
import { useQuery } from '@tanstack/react-query'
import {
    ApiResponse,
    apiUrl,
    SearchResultOutput,
    StockApiResponse,
    StockPriceOutput,
    stockPriceOutputInitialValue,
    validateCode,
} from '../config/ApiConfig.ts'
import SearchResultBox from '../components/main/SearchResultBox.tsx'
import PriceBoard from '../components/main/PriceBoard.tsx'
import stompClient, { StompMessage } from '../config/StompConfig.ts'

const MainPage = () => {
    const [code, setCode] = useState<string>('')
    const [doSearch, setDoSearch] = useState<boolean>(false)
    const [priceBoardVisible, setPriceBoardVisible] = useState<boolean>(false)
    const [isStompConnected, setIsStompConnected] = useState<boolean>(false)
    const [priceData, setPriceData] = useState<StockPriceOutput>(stockPriceOutputInitialValue)

    const { data, isSuccess } = useQuery({
        queryKey: ['search', code],
        queryFn: async (): Promise<ApiResponse<StockApiResponse<SearchResultOutput>>> => {
            return await fetch(apiUrl + `stock/search?pdno=${code}`).then((result) => {
                if (result.ok) {
                    return result.json()
                } else {
                    throw new Error(result.status + result.statusText)
                }
            })
        },
        enabled: doSearch,
        retry: false,
    })

    useEffect(() => {
        stompClient.activate()
        stompClient.onConnect = () => {
            setIsStompConnected(true)
        }
        stompClient.onDisconnect = () => {
            setIsStompConnected(false)
        }
    }, [])

    useEffect(() => {
        if (isSuccess) {
            setDoSearch(false)
        } else {
            if (stompClient.connected) {
                stompClient.deactivate()
            }
        }
    }, [isSuccess])

    useEffect(() => {
        if (priceBoardVisible && isStompConnected) {
            const message: StompMessage<string> = {
                code: code,
                command: 'START_GET_PRICE',
            }
            stompClient.publish({
                destination: `/pub/price`,
                body: JSON.stringify(message),
            })
            stompClient.subscribe(`/sub/price/${code}`, (message) => {
                const body: StompMessage<StockApiResponse<StockPriceOutput>> = JSON.parse(
                    new TextDecoder('utf-8').decode(message.binaryBody),
                )
                // eslint-disable-next-line @typescript-eslint/no-unused-expressions
                body.data?.output && setPriceData(body.data.output)
            })
        } else {
            if (stompClient.connected) {
                const message: StompMessage<string> = {
                    code: code,
                    command: 'STOP_GET_PRICE',
                }
                stompClient.publish({
                    destination: `/pub/price`,
                    body: JSON.stringify(message),
                })
                stompClient.unsubscribe(`/sub/price/${code}`)
            }
        }
    }, [priceBoardVisible, isStompConnected])

    return (
        <div className={'w-full h-full flex flex-col justify-start items-center border p-10'}>
            <SearchBox
                code={code}
                onChange={(e: ChangeEvent<HTMLInputElement>) => setCode(e.currentTarget.value)}
                onClick={() => {
                    if (validateCode(code)) {
                        setDoSearch(true)
                    }
                }}
            />
            {isStompConnected ? (
                <span className={'text-success'}>ws 연결됨</span>
            ) : (
                <span className={'text-danger'}>ws 연결 해제됨</span>
            )}
            <SearchResultBox
                data={data?.body}
                visible={isSuccess}
                onClick={() => setPriceBoardVisible(true)}
                onCancelClick={() => setPriceBoardVisible(false)}
                isConnected={isStompConnected}
            />
            <div className={'w-full flex justify-between'}>
                <PriceBoard visible={priceBoardVisible && isStompConnected} priceData={priceData} />
            </div>
        </div>
    )
}

export default MainPage
