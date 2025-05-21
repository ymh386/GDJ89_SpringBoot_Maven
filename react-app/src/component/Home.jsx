
import Button from '@mui/material/Button';
import { useLoginStateContext } from "../contexts/LoginStateContext"

export default function Home(){

    const loginState = useLoginStateContext();//islogin, setLogin, setLogout

    return(
        <>
            <h1>Index Page</h1>
            <Button variant='contained'>SAMPLE</Button>
            
            {  
                loginState.isLogin?
            <h1>Login 성공</h1>
                :
            <h1>Login 전</h1>
            }
        </>
    )
}