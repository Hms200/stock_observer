export const apiUrl = 'http://localhost:8085/'

export interface ApiResponse<T> {
    success: boolean
    body?: T
    errorMessage?: string
}

export interface Setting {
    id?: string
    appKey: string
    appSecret: string
    webHookUrl: string
    accessToken?: string
}

export interface SearchResult {
    rt_cd: string
    msg_cd: string
    output: {
        pdno: string
        prdt_type_cd: string
        prdt_name: string
        tr_stop_yn: string
        admn_item_yn: string
        thdt_clpr: string
        bfdy_clpr: string
    }
}

export const validateCode = (code: string): boolean => {
    const arabic = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9']
    let indicator = 0

    if (code.startsWith('Q')) {
        alert('ETN 미지원')
        return false
    }

    if (code.length > 6) {
        alert('종목코드는 6자리를 넘지 않음')
        return false
    }

    for (let i = 0; i < code.length; i++) {
        if (!arabic.includes(code.charAt(i))) {
            alert('숫자만 입력가능')
            indicator++
            break
        }
    }

    return indicator == 0
}
