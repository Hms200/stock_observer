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

export interface StockApiResponse<T> {
    rt_cd: string
    msg_cd: string
    output: T
}

export interface SearchResultOutput {
    pdno: string
    prdt_type_cd: string
    prdt_name: string
    tr_stop_yn: string
    admn_item_yn: string
    thdt_clpr: string
    bfdy_clpr: string
}

export interface StockPriceOutput {
    // 현재가
    stck_prpr: string
    // 전일 대비
    prdy_vrss: string
    // 대비 부호 1: 상 2: 상승 3: 보합: 4: 하 5: 하락
    prdy_vrss_sign: string
    // 전일 대비율
    prdy_ctrt: string
    // 시가
    stck_oprc: string
    // 최고가
    stck_hgpr: string
    // 최저가
    stck_lwpr: string
    // 상한가
    stck_mxpr: string
    // 하한가
    stck_llam: string
}

export const stockPriceOutputInitialValue: StockPriceOutput = {
    stck_hgpr: '',
    prdy_ctrt: '',
    prdy_vrss: '',
    prdy_vrss_sign: '',
    stck_llam: '',
    stck_lwpr: '',
    stck_mspr: '',
    stck_oprc: '',
    stck_prpr: '',
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
