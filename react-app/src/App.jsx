import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
// import './App.css'
import Header from './layout/Header'
import Footer from './layout/Footer'
import AppRoutes from './layout/AppRoutes'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>

      <Header></Header>
      <AppRoutes></AppRoutes>
      <Footer></Footer>
    </>
  )
}

export default App
