// 當頁面載入時檢查登入狀態
$(document).ready(function () {
    errorMessage = "";
    if (localStorage.getItem("authorization")) {
        // 如果存在驗證碼，表示已登入，顯示登出按鈕，隱藏登入按鈕
        $("#sign-in").hide();
        $("#sign-in-android").hide();
        $("#sign-out").show();
        $("#sign-out-android").show();
    } else {
        // 如果不存在驗證碼，表示未登入，顯示登入按鈕，隱藏登出按鈕
        $("#sign-in").show();
        $("#sign-in-android").show();
        $("#sign-out").hide();
        $("#sign-out-android").hide();
    }
    if (localStorage.getItem("authorization")) {
        $.ajax({
            url: `/api/memberNotice/all?page=0&sizePerPage=5`,
            method: "post",
            contentType: "application/json",
            headers: {
                "authorization": localStorage.getItem("authorization")
            },
            data: JSON.stringify({}),
            success(response) {
                apiResponseJSON(response, {
                    onSuccess(data) {
                        let unReadCount = data.unReadCount;
                        if (unReadCount > 0) {
                            $("#unreadNoticeCount").text(unReadCount).show();
                        } else {
                            $("#unreadNoticeCount").hide();
                        }
                    },
                    onError(message) {
                        console.log(message);
                    }
                });
            },
            error(xhr, status, error) {
                $("#modalBody").html("伺服器連線失敗: " + error);
                $("#myModal").modal("show");
            }
        });
    }
});

// 登出按鈕的點擊事件
$("#sign-out").click(function () {
    localStorage.removeItem("authorization");
});

// 行動版登出按鈕的點擊事件
$("#sign-out-android").click(function () {
    localStorage.removeItem("authorization");
});

// 點擊人頭icon、"查看/修改會員資料"按鈕時觸發事件
$("#icon-User, #icon-User-android, #member-detail, #member-detail-android").click(function (e) {
    e.preventDefault();
    // 檢查 localstorage 中是否存在驗證碼
    let verifyCode = localStorage.getItem("authorization");

    // 如果本地存儲中存在驗證碼，則跳轉到會員資料畫面
    if (verifyCode) {
        window.location.href = "/member/detail";
    } else {
        window.location.href = "/member/login";
    }
});

// 點擊"錢包交易紀錄"按鈕時觸發事件
$("#wallet-history, #wallet-history-android").click(function (e) {
    e.preventDefault();
    let verifyCode = localStorage.getItem("authorization");
    if (verifyCode) {
        window.location.href = "/member/walletHistory";
    } else {
        let targetPage = "/member/walletHistory";
        localStorage.setItem("targetPage", targetPage); // 儲存目標頁面
        errorMessage += "<li>請先登入</li>";
        $("#modalBody").html("<ul>" + errorMessage + "</ul>");
        $("#myModal").modal("show");
        setTimeout(function () {
            window.location.href = "/member/login";
        }, 1500);
    }
});

// 點擊"儲值錢包"按鈕時觸發事件
$("#wallet-add, #wallet-add-android").click(function (e) {
    e.preventDefault();
    let verifyCode = localStorage.getItem("authorization");
    if (verifyCode) {
        window.location.href = "/member/walletAdd";
    } else {
        let targetPage = "/member/walletAdd";
        localStorage.setItem("targetPage", targetPage);
        errorMessage += "<li>請先登入</li>";
        $("#modalBody").html("<ul>" + errorMessage + "</ul>");
        $("#myModal").modal("show");
        setTimeout(function () {
            window.location.href = "/member/login";
        }, 1500);
    }
});

// 點擊"申請提領"按鈕時觸發事件
$("#wallet-withdraw, #wallet-withdraw-android").click(function (e) {
    e.preventDefault();
    let verifyCode = localStorage.getItem("authorization");
    if (verifyCode) {
        window.location.href = "/member/walletWithdraw";
    } else {
        let targetPage = "/member/walletWithdraw";
        localStorage.setItem("targetPage", targetPage);
        errorMessage += "<li>請先登入</li>";
        $("#modalBody").html("<ul>" + errorMessage + "</ul>");
        $("#myModal").modal("show");
        setTimeout(function () {
            window.location.href = "/member/login";
        }, 1500);
    }
});

// 點擊"申請提領紀錄"按鈕時觸發事件
$("#wallet-withdraw-history, #wallet-withdraw-history-android").click(function (e) {
    e.preventDefault();
    let verifyCode = localStorage.getItem("authorization");
    if (verifyCode) {
        window.location.href = "/member/walletWithdrawHistory";
    } else {
        let targetPage = "/member/walletWithdrawHistory";
        localStorage.setItem("targetPage", targetPage);
        errorMessage += "<li>請先登入</li>";
        $("#modalBody").html("<ul>" + errorMessage + "</ul>");
        $("#myModal").modal("show");
        setTimeout(function () {
            window.location.href = "/member/login";
        }, 1500);
    }
});

// 點擊通知icon時觸發事件
$("#icon-Notice, #icon-Notice-android").click(function (e) {
    e.preventDefault();
    // 檢查 localstorage 中是否存在驗證碼
    let verifyCode = localStorage.getItem("authorization");

    // 如果本地存儲中存在驗證碼，則跳轉到通知頁面，同時將未讀改成已讀狀態
    if (verifyCode) {
        $.ajax({
            url: "/api/memberNotice/updateReadingTime",
            method: "get",
            headers: {
                "authorization": localStorage.getItem("authorization")
            },
            success(response) {
                apiResponseJSON(response, {
                    onSuccess(data) {
                        switch (data.status) {
                            case "-1":
                                $("#modalBody").html("查無會員資料");
                                $("#myModal").modal("show");
                                break;
                        }
                    },
                    onError(message) {
                        console.log(message);
                    }
                });
            },
            error(xhr, status, error) {
                $("#modalBody").html("伺服器連線失敗: " + error);
                $("#myModal").modal("show");
            }
        });
        window.location.href = "/memberNotice/all";
    } else {
        let targetPage = "/memberNotice/all";
        localStorage.setItem("targetPage", targetPage);
        errorMessage += "<li>請先登入</li>";
        $("#modalBody").html("<ul>" + errorMessage + "</ul>");
        $("#myModal").modal("show");
        setTimeout(function () {
            window.location.href = "/member/login";
        }, 1500);
    }
});