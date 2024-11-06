import './App.css'
import './output.css'
import KeySetting from './pages/main/KeySetting.tsx'
import MainLayout from './pages/main/MainLayout.tsx'
import { Route, Routes } from 'react-router-dom'
import MainPage from './pages/main/MainPage.tsx'

function App() {
    return (
        <MainLayout>
            <Routes>
                <Route path={'/'} element={<KeySetting />} />
                <Route path={'/main'} element={<MainPage />} />
            </Routes>
        </MainLayout>
    )
}

export default App
