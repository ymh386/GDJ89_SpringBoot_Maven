import { Route, Routes } from "react-router-dom";
import Notice from "../component/boards/Notice";
import Join from "../component/users/Join";
import Qna from "../component/boards/Qna";
import Home from "../component/home";
import Detail from "../component/boards/detail";
import Add from "../component/boards/Add";
import Update from "../component/boards/Update";

function AppRoutes(){

    return (
        <Routes>
            <Route path="/" element={<Home/>}></Route>
            <Route path="/notice/">
                <Route path="list" element={<Notice/>}></Route>
                <Route path="detail" element={<Detail/>}></Route>
                <Route path="add" element={<Add/>}></Route>
                <Route path="update" element={<Update/>}></Route>
            </Route>
            <Route path="/qna/list" element={<Qna/>}></Route>
            <Route path="/user/join" element={<Join/>}></Route>
        </Routes>
    )
}

export default AppRoutes;