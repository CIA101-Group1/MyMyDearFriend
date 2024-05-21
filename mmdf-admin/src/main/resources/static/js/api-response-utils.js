/**
 * 網頁請求登入所得到的回應之JSON拆解
 *
 * @param response 後端api回應之JSON物件
 * @param callbackListener 拆解response的監聽器
 */
function apiResponseJSON(response, callbackListener) {
    if (null === response) {
        return;
    }
    switch (response.code) {
        case "0":
            callbackListener.onSuccess(response.data);
            break;
        default:
            callbackListener.onError(response.message);
            break;
    }
}