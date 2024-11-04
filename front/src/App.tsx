import './App.css'
import './output.css'
import MainPage from './pages/main/MainPage.tsx'
import { QueryClient, QueryClientProvider } from '@tanstack/react-query'
import MainLayout from './pages/main/MainLayout.tsx'

function App() {
    const queryClient = new QueryClient()

    return (
        <QueryClientProvider client={queryClient}>
            <MainLayout>
                <MainPage />
            </MainLayout>
        </QueryClientProvider>
    )
}

export default App
