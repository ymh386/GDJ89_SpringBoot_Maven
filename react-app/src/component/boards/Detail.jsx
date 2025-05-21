import { useEffect, useState } from "react";
import { Link, useLocation, useNavigate, useParams, useSearchParams } from "react-router-dom";
import * as usermanager from '../../commons/UserManager';

function Detail(){
    // 파라미터 : URL/파라미터값/파라미터값
    // 쿼리스트링 : URL?이름=값&이름=값
    // state : <Link to="" state={{키:밸류}}>

    //1. useParam (파라미터 데이터를 가져 올때 사용)
    //const 변수명 = useParams();
    //const 변수명 = useParams().파라미터명;
    // const num = useParams();
    // console.log(num.boardNum);

    //2. useSearchParams (쿼리스트링 데이터를 가져 올때 사용)
    const  [query, setQuery] = useSearchParams();
    console.log(query.get('boardNum'))

    //3. useLocation (Link태그에 state속성을 넣은 데이터를 가져 올때 사용)
    // hash : 주소의 #문자열 뒤의 값
    // pathname : 현재 주소 경로
    // search : ?를 포함한 쿼리스트링
    // state : 페이지 이동시 임의로 넣을 수 있는 상태 값
    // key : location 객체의 고유 값, 페이지가 변경 될 때 마다 고유의 값이 생성
    const p = useLocation();
    console.log(p.state.boardNum);

    //4. useNavigate
    // state :
    // replace : boolean : 뒤로가기 방지
    // relative : route -> 절대경로, path -> 상대경로, 기본값 route
    // /notice/list
    // navigate("/detail", {relative:"route"}) => /notice/detail
    // navigate("/detail", {relative:"path"}) => /notice/list/detail

    const [detail, setDetail] = useState({vo:""});

    const navigate = useNavigate()

    useEffect(()=>{
            console.log("useEffect")
    
            let params = new URLSearchParams();
            params.append('boardNum', query.get('boardNum'))
    
            fetch(`http://localhost:81/notices/${p.state.boardNum}`, {
                headers:usermanager.setHeaders()
            })
            .then(r=>r.json())
            .then(r=>{
                console.log(r)
                setDetail(r)
            })
        }, [])

    function deleteHandler(){
        fetch(`http://localhost:81/notices/${p.state.boardNum}`, {
            method:'DELETE',
            headers:usermanager.setHeaders()    
        })
        .then(r=>r.json())
        .then(r=>{
            if(r>0){
                navigate("/notice/list")
            }
        })
    }
    


    return (
        <>
            <h1>Detail Page</h1>
            <h3>{detail.boardNum}</h3>
            <h3>{detail.userName}</h3>
            <h3>{detail.boardDate}</h3>
            <h3>{detail.boardTitle}</h3>
            <h3>{detail.boardContents}</h3>
            <h3>{detail.boardHit}</h3> 

            <Link to={"/notice/update"} state={{boardNum:detail.boardNum}}>Update</Link><br/>
            <button onClick={deleteHandler}>Delete</button>
        </>
    )
}

export default Detail;