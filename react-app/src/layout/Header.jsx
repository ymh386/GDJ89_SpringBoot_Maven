import { Link } from "react-router-dom"

function Header() {

  return (
    <>
      <h1>Header입니다</h1>
      <div>
        <Link to="/">Home</Link>
        <Link to="/notice/list">Noitce</Link>
        <Link to="/qna/list">Qna</Link>
        <Link to="/user/join">회원가입</Link>
      </div>
    </>
  )
}

export default Header