import HomePage from '../commonPages/HomePage.jsx'
import { Routes, Route } from 'react-router-dom'
import RegisterPage from '../pages/auth/RegisterPage.jsx'
import LoginPage from '../pages/auth/LoginPage.jsx'
import Home from '../pages/Home.jsx'
import UserProfilePage from '../pages/profile/UserProfilePage.jsx'
import CompanyProfilePage from '../pages/profile/company/CompanyProfilePage.jsx'

function App() {
  return (
    <>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/login" element={<LoginPage />} />

        {/* Home sau khi đăng nhập */}
        <Route path="/home" element={<Home />}/>

        {/* Profile */}
        <Route path="/users/profile/:id" element={<UserProfilePage />} />
        <Route path="/companies/profile/:id" element={<CompanyProfilePage />} />
      </Routes>
    </>
  )
}

export default App