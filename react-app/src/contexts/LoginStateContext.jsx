import { createContext, useContext, useState } from "react";

const LoginStateContext = createContext();

export const LoginStateProvider=({children})=>{

    //?????
    let accessToken = sessionStorage.getItem("AccessToken");
    let refreshToken = localStorage.getItem("RefreshToken")

    const [isLogin, setIsLogin]=useState(accessToken != null && refreshToken != null)

    function setLogin(){
        setIsLogin(true)
    }

    const setLogout = ()=>{
        setIsLogin(false)
    }

    return(
        <LoginStateContext.Provider value={{isLogin, setLogin, setLogout }}>
            {children}
        </LoginStateContext.Provider>    

    )

}

export const useLoginStateContext=()=>{
    const loginStateContext = useContext(LoginStateContext)

    if(!loginStateContext){
        throw new Error("error")
    }


    return loginStateContext;
}