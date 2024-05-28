function loginGuard() {
    let TOKEN = localStorage.getItem("authorization");
    if (!TOKEN) {
        alert("請先登入");
        window.location.href = "/member/login";
    }
}