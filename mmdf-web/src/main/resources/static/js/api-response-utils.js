/**
 * 網頁請求登入所得到的回應之JSON拆解
 * @param response 後端api回應之JSON物件
 * @returns {boolean} 是否成功登入
 */
function apiResponseJSON(response) {
    if (null === response) {
        return false;
    }
    // 解析 JSON 字串為 JavaScript 物件
    if (response.code === "0") {
        return true;
    } else {
        alert(response.message)
        return false;
    }
}