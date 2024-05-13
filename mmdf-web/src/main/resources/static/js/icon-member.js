// 當頁面載入時檢查登入狀態
$(document).ready(function () {
    if (localStorage.getItem("authorization")) {
        // 如果存在驗證碼，表示已登入，顯示登出按鈕，隱藏登入按鈕
        $("#sign-in").hide();
        $("#sign-out").show();
    } else {
        // 如果不存在驗證碼，表示未登入，顯示登入按鈕，隱藏登出按鈕
        $("#sign-in").show();
        $("#sign-out").hide();
    }

    // 點擊人頭icon時觸發事件
    $("#icon-User").click(function (e) {
        e.preventDefault();
        // 檢查 localstorage 中是否存在驗證碼
        let verifyCode = localStorage.getItem("authorization");

        // 如果本地存儲中存在驗證碼，則跳轉到會員資料畫面
        if (verifyCode) {
            window.location.href = "/member/detail";
        } else {
            // 如果本地存儲中不存在驗證碼，則跳轉到登入畫面
            window.location.href = "/member/login";
        }
    });
});

// 登出按鈕的點擊事件
$("#sign-out").click(function () {
    localStorage.removeItem("authorization");
});