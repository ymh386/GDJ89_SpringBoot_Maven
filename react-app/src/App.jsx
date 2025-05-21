import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
// import './App.css'
import Header from './layout/Header'
import Footer from './layout/Footer'
import AppRoutes from './layout/AppRoutes'
import { Base_URL } from './contexts/UrlContext'
import { LoginStateProvider } from './contexts/LoginStateContext'
import { BrowserRouter } from 'react-router-dom'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <BrowserRouter>
        <Base_URL.Provider value="http://localhost:81">
          <LoginStateProvider>
            <Header></Header>
            
            <AppRoutes></AppRoutes>

            <Footer></Footer>
          </LoginStateProvider>
        </Base_URL.Provider>
     </BrowserRouter>
    </>
  )
}

export default App
