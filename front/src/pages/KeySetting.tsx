import Input from '../components/common/form/Input.tsx'
import Button from '../components/common/Button.tsx'
import { ChangeEvent, useEffect, useState } from 'react'
import { useMutation, useQuery } from '@tanstack/react-query'
import { ApiResponse, apiUrl, Setting } from '../config/ApiConfig.ts'
import { useNavigate } from 'react-router-dom'

type KeyValues = {
    id?: string
    appKey?: string
    appSecret?: string
    webHookUrl?: string
}
const keyValuesInitialValue: KeyValues = {
    id: undefined,
    appKey: '',
    appSecret: '',
    webHookUrl: '',
}

const KeySetting = () => {
    const navigate = useNavigate()

    const [disabled, setDisabled] = useState<boolean>(false)
    const [keyValues, setKeyValues] = useState<KeyValues>(keyValuesInitialValue)

    const { isLoading, isError, data } = useQuery({
        queryKey: ['getSettings', keyValues],
        queryFn: async (): Promise<ApiResponse<Setting>> => {
            return await fetch(apiUrl + 'setting/get').then((response) => response.json())
        },
        retry: false,
    })
    // mutation -> db 저장된 키 변경하는 뮤테이션
    const updateAndGetAccessToken = useMutation({
        mutationFn: async (keys: KeyValues): Promise<ApiResponse<Setting>> => {
            return await fetch(apiUrl + 'setting/get_access', {
                method: 'PUT',
                body: JSON.stringify(keys),
                headers: {
                    'Content-Type': 'application/json',
                },
            }).then((res) => {
                if (res.ok) {
                    return res.json()
                } else {
                    alert('error')
                    throw new Error()
                }
            })
        },
        onSuccess: (data) => {
            if (data.errorMessage) {
                alert(data.errorMessage)
                throw new Error()
            }
        },
    })

    const onStartButtonClicked = () => {
        try {
            updateAndGetAccessToken.mutate(keyValues)
            navigate('/main')
        } catch {
            return
        }
    }

    useEffect(() => {
        if (keyValues.appKey !== '' && keyValues.appSecret !== '' && keyValues.webHookUrl !== '') {
            setDisabled(false)
        } else {
            setDisabled(true)
        }
    }, [keyValues])

    useEffect(() => {
        if (data?.success) {
            setKeyValues({
                id: data.body?.id,
                appKey: data.body?.appKey,
                appSecret: data.body?.appSecret,
                webHookUrl: data.body?.webHookUrl,
            })
        }
    }, [data])

    if (isLoading) return <span>Loading...</span>

    return (
        <div className={'w-1/2 h-1/2 border'}>
            <div className={'w-full h-3/4 flex flex-col justify-end items-center'}>
                <div className={'w-3/4 gap-4 flex flex-col justify-center items-start'}>
                    {(isError || !data?.success) && <span>{data?.errorMessage}</span>}
                    <Input
                        type={'text'}
                        label={'app key'}
                        value={keyValues.appKey}
                        placeholder={'한투 app Key 입력'}
                        onChange={(e: ChangeEvent<HTMLInputElement>) =>
                            setKeyValues({
                                ...keyValues,
                                appKey: e.currentTarget?.value,
                            })
                        }
                        required
                    />
                    <Input
                        type={'text'}
                        label={'app secret'}
                        value={keyValues.appSecret}
                        placeholder={'한투 app secret 입력'}
                        onChange={(e: ChangeEvent<HTMLInputElement>) =>
                            setKeyValues({
                                ...keyValues,
                                appSecret: e.currentTarget?.value,
                            })
                        }
                        required
                    />
                    <Input
                        type={'text'}
                        label={'web-hook url'}
                        value={keyValues.webHookUrl}
                        placeholder={'slack web-hook url 입력'}
                        onChange={(e: ChangeEvent<HTMLInputElement>) =>
                            setKeyValues({
                                ...keyValues,
                                webHookUrl: e.currentTarget?.value,
                            })
                        }
                        required
                    />
                </div>
            </div>
            <div className={'w-full h-1/4 flex justify-end items-center pe-20 gap-3'}>
                <Button
                    color={'secondary'}
                    value={'재설정'}
                    size={'xlg'}
                    textColor={'white'}
                    disabled={disabled}
                    onClick={() => {
                        setKeyValues(keyValuesInitialValue)
                    }}
                />
                <Button
                    color={'primary'}
                    value={'시작'}
                    size={'xlg'}
                    textColor={'white'}
                    disabled={disabled}
                    onClick={onStartButtonClicked}
                />
            </div>
        </div>
    )
}

export default KeySetting
