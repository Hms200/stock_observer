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
