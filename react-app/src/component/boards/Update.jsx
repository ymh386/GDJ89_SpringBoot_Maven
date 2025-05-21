import { useEffect, useState } from "react";
import { Navigate, useLocation, useNavigate, useSearchParams } from "react-router-dom";

export default function Update(){

    const p = useLocation();

    const navigate = useNavigate();

    const [detail, setDetail] = useState({});

    const  [query, setQuery] = useSearchParams();
    console.log(query.get('boardNum'))

    useEffect(()=>{
                console.log("useEffect")
        
                fetch(`http://localhost:81/notices/${p.state.boardNum}`)
                .then(r=>r.json())
                .then(r=>{
                    console.log(r)
                    setDetail(r)
                })
            }, [])

    function update(e){
        e.preventDefault();
        let f = new FormData(e.target);
        fetch("http://localhost:81/notices", {
            method:'PATCH',
            headers:{
                
            },
            body:f
        })
        .then(r=>r.json())
        .then(r=>{
            if(r>0){
                navigate("/notice/detail", {state : {boardNum:p.state.boardNum}})
            }
        })
    }

    return (
        <>
            <h1>Notice Update</h1>

            <form onSubmit={update}>
                <div>
                    <input type="hidden" defaultValue={detail.boardNum} name="boardNum"></input> 
                </div>
                <div>
                    <label htmlFor="writer">작성자</label>
                    <input type="text" value={detail.userName} name="userName" id="writer"></input> 
                </div>
                <div>
                    <label htmlFor="title">제목</label>
                    <input type="text" defaultValue={detail.boardTitle} name="boardTitle" id="title"></input> 
                </div>
                <div>
                    <label htmlFor="contents">내용</label>
                    <textarea id="contents" defaultValue={detail.boardContents} name="boardContents"></textarea>
                </div>
                <div>
                    <label>첨부 파일</label><br/>
                    <input type="file" name="attaches"></input><br/>
                    <input type="file" name="attaches"></input><br/>
                    <input type="file" name="attaches"></input><br/>
                </div>
                <button type="submit" >글 수정</button>
            </form>
        </>
    )
}