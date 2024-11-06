import SearchBox from '../components/main/SearchBox.tsx'
import { ChangeEvent, useEffect, useState } from 'react'
import { useQuery } from '@tanstack/react-query'
import { ApiResponse, apiUrl, SearchResult, validateCode } from '../config/ApiConfig.ts'
import SearchResultBox from '../components/main/SearchResultBox.tsx'

const MainPage = () => {
    const [code, setCode] = useState<string>('')
    const [doSearch, setDoSearch] = useState<boolean>(false)

    const { data, isSuccess } = useQuery({
        queryKey: ['search', code],
        queryFn: async (): Promise<ApiResponse<SearchResult>> => {
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
        // eslint-disable-next-line @typescript-eslint/no-unused-expressions
        isSuccess && setDoSearch(false)
    }, [isSuccess])

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
            <SearchResultBox data={data?.body} visible={isSuccess} />
        </div>
    )
}

export default MainPage
