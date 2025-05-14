import { useRef, useState } from "react";
import { Navigate, useNavigate } from "react-router-dom";



//작성자, 제목, 내용, 첨부파일3개 고정
export default function Add(){
    const navigate = useNavigate();

    const username = useRef();
    const title = useRef();
    const contents = useRef();

    const [datas, setDatas] = useState({
        userName:"",
        boardTitle:"",
        boardContents:""
    });


    function changeInput(e) {

        // setDatas((prevState)=>({
        //     ...prevState, //전개
        //     [e.target.name]:e.target.value
        // })
        // )
    }

    function add(e){
        e.preventDefault();
        let f = new FormData(e.target);
        fetch("http://localhost:81/notices", {
            method:'POST',
            headers:{
                
            },
            body:f
        })
        .then(r=>r.json())
        .then(r=>{
            if(r>0){
                navigate("/notice/list")
            }
        })
    }

    function add2(){
        // console.log(datas);
        // console.log(username.current.value)
        // console.log(title.current.value)
        // console.log(contents.current.value)

        // 1. URLSearchParams
        // let params = new URLSearchParams();
        // params.append("username", username.current.value)
        // params.append("boardTitle", title.current.value)
        // params.append("boardContents", contents.current.value)

        //2. formData 객체 이용
        let d = new FormData();
        d.append("userName", "");

        //Server 요청시 CORS(METHOD) 허용 Spring Security 사용

        fetch("http://localhost:81/notices", {
            method:'POST',
            headers:{
                
            },
            body:d//JSON.stringify(datas)
        })
        .then(r=>r.json())
        .then(r=>{
            console.log(r)
        })
    }

    return (
        <>
            <form onSubmit={add}>
                <div>
                    <label htmlFor="writer">작성자</label>
                    <input type="text" name="userName" id="writer" onChange={changeInput} ref={username}></input> 
                </div>
                <div>
                    <label htmlFor="title">제목</label>
                    <input type="text" name="boardTitle" id="title" onChange={changeInput} ref={title}></input> 
                </div>
                <div>
                    <label htmlFor="contents">내용</label>
                    <textarea id="contents" name="boardContents" onChange={changeInput} ref={contents}></textarea>
                </div>
                <div>
                    <label>첨부 파일</label><br/>
                    <input type="file" name="attaches"></input><br/>
                    <input type="file" name="attaches"></input><br/>
                    <input type="file" name="attaches"></input><br/>
                </div>
                <button type="submit" >글 작성</button>
            </form>
        </>
    )
}