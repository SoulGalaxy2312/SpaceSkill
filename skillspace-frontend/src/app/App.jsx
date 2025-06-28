import HomePage from '../commonPages/HomePage.jsx'
import { Routes, Route } from 'react-router-dom'
import RegisterPage from '../pages/auth/RegisterPage.jsx'
import LoginPage from '../pages/auth/LoginPage.jsx'
import Home from '../pages/Home.jsx'
import { Navigate } from 'react-router-dom'

function App() {
  return (
    <>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/login" element={<LoginPage />} />

        {/* Home sau khi đăng nhập */}
        <Route path="/home" element={<Home />}/>
      </Routes>
    </>
  )
}

export default App