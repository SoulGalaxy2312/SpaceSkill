import NavBar from '../components/NavBar.jsx'
import HomePage from '../commonPages/HomePage.jsx'
import { Routes, Route } from 'react-router-dom'

function App() {
  return (
    <>
      <Routes>
        <Route path="/" element={<HomePage />} />
      </Routes>
    </>
  )
}

export default App