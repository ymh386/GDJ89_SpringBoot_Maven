export function setHeaders(){

    const headers = new Headers();
    headers.append("Authorization", "Bearer "+sessionStorage.getItem("AccessToken"))
    headers.append("RefreshToken", localStorage.getItem("RefreshToken"))

    return headers;
}


export function getHeaders(res){

    let t =  res.headers.get("AccessToken");
    if(t != null){
        sessionStorage.setItem("AccessToken", t)
    }

}