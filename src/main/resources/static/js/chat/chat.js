
//----- Memo
try{

    const memoSend = document.getElementById("memoSend");
    const memoContents = document.getElementById("memoContents");
    const memoReceiver = document.getElementById("memoReceiver");
    const memoDetails = document.getElementsByClassName("memoDetails");

    memoSend.addEventListener("click", ()=>{
        let m = new Message();
        m.body = memoContents.value;
        m.receiver=memoReceiver.value;
        m.status="3"
        m.date=new Date();

        websocket.send(JSON.stringify(m))
    })

    for(let md of memoDetails){
        md.addEventListener("click", ()=>{
            const readCount = document.getElementById("readCount");
            const chatNum = md.getAttribute("data-chat-num")*1;
            let count = readCount.innerHTML.trim()*1;
            readCount.innerHTML = count-1

            fetch(`/chat/updateReadStatus?chatNum=${chatNum}`)
            .then(r=>r.json())
            .then(r=>{
                // md.remove()
                location.href="/"
            })
            .catch(e=>{
                console.log(e)
            })
        })
    }



}catch(e) {

}


//----- Chatting
try {
    //Modal창에 메세지 전송 버튼
    const send = document.getElementById("send")
    //Modal창에 메세지 입력 태그
    const message = document.getElementById("message")
    
    //Table에 회원목록에서 가져오는 수신자 정보
    const receivername = document.getElementsByClassName("receiver-name");
    
    //Modal에 hidden에 들어갈 특정 수신자 정보
    const rec = document.getElementById("receiver")
    
    //Modal창의 메세지들을 출력하는곳
    const chatBody = document.getElementById("chat-body");
    
    //현재 로그인한 사용자 유저네임(sender)
    const chat = document.getElementById("chat");
    
    for(let r of receivername){
        r.addEventListener("click", ()=>{
            let receiver=r.getAttribute("data-receiver-name");
            let sender = chat.getAttribute("data-sender-name");
            rec.value=receiver;
            chatBody.innerHTML="";
    
            //DB에서 이전 대화정보 불러오기
            fetch(`/chat/room?receiver=${receiver}&sender=${sender}`)
            .then(r=>r.json())
            .then(r=>{
                console.log(r)
                r.forEach(e => {
                    let d = makeData(e);
                    chatBody.append(d);
                });
            })
            .catch(e=>{
                console.log(e)
            })
    
    
        })
    }
    
    
    //webSocket 연결이 되었을 때
    websocket.onopen=()=>{
        let m = new Message()
        m.body="님이 입장했습니다"
        //websocket.send(JSON.stringify(m))
    }
    
    //websocket으로 메세지를 수신 했을 때
    websocket.onmessage=(m)=>{
        let result =JSON.parse(m.data)
        let start = result.sender;//송신자
        let end = result.receiver;//수신자
        let status = result.status;

        //------------------------- Memo(쪽지)
        if(status == 3) {
            makeMemo(result)
            const readCount = document.getElementById("readCount");
            let count = readCount.innerHTML.trim()*1;
            readCount.innerHTML = count+1

            return;
        }


        //----------------------- 1:1 채팅 관련 코드
        let my = chat.getAttribute("data-sender-name");//내정보
        let re = rec.value; //현재 채팅하고 있는 상대방 정보
        if(start != my && start != re){
            return;
        }
    
        
        let r = makeData(result);
        chatBody.append(r)
    }
    
    //webSocket연결이 종료 되었을 때
    websocket.onclose=()=>{
        websocket.send("님이 나갔습니다")
    }
    
    //개발자가 메세시 송신 할 때
    send.addEventListener("click", ()=>{
        let m = message.value
    
        let mes = new Message();
        mes.sender=chat.getAttribute("data-sender-name")
        mes.body=m;
        mes.receiver=rec.value;
        mes.date=new Date();
        mes.status="1";
        
        
        websocket.send(JSON.stringify(mes))
        message.value="";
    
    })
    
    //websocke error 발생시
    // websocket.onerror=()=>{
    
    // }
    
    websocket.onerror=webSocketError;
    
    function webSocketError(){
    
    }

}catch(e) {

}


//------------------------------------
class Message {
    roomNum="";
    sender="";
    body="";
    receiver="";
    date="";
    status="0"  //0->전체 1->1:1 2->1:N 3->Memo
}

//chatBody(메세지를 출력)에 출력할 엘리먼트 구조 생성
function makeData(data){

    const div = document.createElement("div");
    div.innerHTML=data.body

    return div;

}

//Memo(쪽지)출력 구조 생성
function makeMemo(data){
    let a = document.createElement("a");
    a.classList.add("memoDetails", "dropdown-item", "d-flex", "align-items-center")
    a.href="#"

    let div = document.createElement("div");
    div.classList.add("dropdown-list-image", "mr-3")

    let img = document.createElement("img");
    img.classList.add("rounded-circle")
    img.src="";

    let div2 = document.createElement("div");
    div2.classList.add("status-indicator", "bg-success")

    div.appendChild(img)
    div.appendChild(div2)

    a.appendChild(div)

    div = document.createElement("div");
    div.classList.add("font-weight-bold")

    div2 = document.createElement("div");
    div2.classList.add("text-truncate")
    div2.innerText=data.body;

    div.appendChild(div2)

    div2 = document.createElement("div");
    div2.classList.add("small", "text-gray-500")
    div2.innerText=data.sender + " " + data.date

    div.appendChild(div2)

    a.appendChild(div)

    // let attr = document.createAttribute("data-chat-num");
    // attr.value

    document.getElementById("memoAdd").before(a);

}
