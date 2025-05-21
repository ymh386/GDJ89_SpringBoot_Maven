import { Route, Routes } from "react-router-dom";
import Notice from "../component/boards/Notice";
import Qna from "../component/boards/Qna";
import Home from "../component/home";
import Detail from "../component/boards/detail";
import Add from "../component/boards/Add";
import Update from "../component/boards/Update";
import List from "../component/boards/List";
import SignUp from "../component/users/SignUp";
import SignIn from "../component/users/SignIn";

function AppRoutes(){

    return (
        <Routes>
            <Route path="/" element={<Home/>}></Route>
            <Route path="/notice/">
                <Route path="list" element={<List/>}></Route>
                <Route path="detail" element={<Detail/>}></Route>
                <Route path="add" element={<Add/>}></Route>
                <Route path="update" element={<Update/>}></Route>
            </Route>
            <Route path="/qna/list" element={<Qna/>}></Route>
            <Route path="/user/">
                <Route path="signup" element={<SignUp />}></Route>
                <Route path="signin" element={<SignIn />}></Route>
            </Route>
        </Routes>
    )
}

export default AppRoutes;