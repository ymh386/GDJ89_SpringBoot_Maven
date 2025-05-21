import SockJS from "sockjs-client";
import { setHeaders } from "../commons/UserManager";
import { useLoginStateContext } from "../contexts/LoginStateContext";

function Footer() {
  const loginState = useLoginStateContext();

  if(loginState.isLogin){
    getSocket();
  }


  function getSocket(){
    console.log("socket 연결 시도")
    const socket = new SockJS(`/ws/chat?t=Bearer ${sessionStorage.getItem("AccessToken")}`, {
      Headers: setHeaders()
    })//webSocket("/ws/chat") //new SockJS("http://localhost:81/ws/chat");

    socket.onopen=function(){
      console.log("socket 연결 성공", socket);
    }

    socket.onmessage=function(e){
      console.log("메세지 수신")
      console.log(e.data);
    }

    socket.onclose=function(){
      console.log("socket 연결 해제")
    }

    socket.onerror=function(){
      console.log("socket error")
    }

    function send(m){
      socket.send(m);
    }
  }

  return (
    <>
      <hr></hr>
      <h1>Footer입니다</h1>
    </>
  )
}

export default Footer