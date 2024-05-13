// 當頁面載入時檢查登入狀態
$(document).ready(function() {
    if (localStorage.getItem("authorization")) {
        // 如果存在驗證碼，表示已登入，顯示登出按鈕，隱藏登入按鈕
        $("#sign-in").hide();
        $("#sign-out").show();
    } else {
        // 如果不存在驗證碼，表示未登入，顯示登入按鈕，隱藏登出按鈕
        $("#sign-in").show();
        $("#sign-out").hide();
    }
});

// 登出按鈕的點擊事件
$("#sign-out").click(function () {
    localStorage.removeItem("authorization");
});