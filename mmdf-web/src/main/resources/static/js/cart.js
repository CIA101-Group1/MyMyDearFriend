$(document).ready(function () {
    // 取得mini購物車資料
    getMiniCart();

    // 移除mini購物車商品
    $("div.cart_gallery").on("click", "a#mini_cart_remove", function (){
        let productId = $(this).closest("div.cart_item").find("input#productId").val();

        $.ajax({
            url: "/api/cart/remove/" + productId,
            method: "DELETE",
            headers: {
                "authorization": localStorage.getItem("authorization")
            },
            success(response) {
                // 清空購物車資料重新取得購物車資料
                $("#cart_tbody").html("");
                getCart();
                // 清空mini購物車資料重新取得mini購物車資料
                $("div.cart_gallery").html("");
                getMiniCart();
            },
            error(xhr, status, error) {
                console.error("There was a problem :", error);
            },
        })
    })

    // 新增商品至購物車
    $("#add_to_cart").on("click", function (){
        // 取得商品資料
        let data ={
            productId: 102,
            quantity: 10
        }

        $.ajax({
            url: "/api/cart/add",
            method: "POST",
            headers: {
                "authorization": localStorage.getItem("authorization")
            },
            contentType: "application/json",
            data: JSON.stringify(data),
            success(response){
                alert(response.data);
                // 清空mini購物車資料重新取得mini購物車資料
                $("div.cart_gallery").html("");
                getMiniCart();
            },
            error(xhr, status, error){
                console.error("There was a problem :", error);
            }
        })
    })
})

// 取得mini購物車資料
function getMiniCart() {
    $.ajax({
        url: "/api/cart/get",
        method: "GET",
        headers: {
            "authorization": localStorage.getItem("authorization")
        },
        success(response) {
            let cartList = response.data;
            let total = 0;
            let count = 0;

            cartList.forEach(function (cartItem) {
                count++;
                total += cartItem.subtotal;
                $("div.cart_gallery").append(
                    `<div class="cart_item">
                            <div class="cart_img">
                                <a ><img id="cart_image" src="data:image/jpeg;base64,${cartItem.imageBase64}" alt="商品圖片" style="height: 50px;width: 50px"/></a>
                            </div>
                            <div class="cart_info">
                                <input id="productId" value="${cartItem.productId}" type="hidden" />
                                <a href="#">${cartItem.productName}</a>
                                <p>${cartItem.quantity} x <span> $${cartItem.price} </span></p>
                            </div>
                            <div class="cart_remove">
                                <a id="mini_cart_remove"><i class="ion-ios-close-outline"></i></a>
                            </div>
                        </div>`
                );
            });
            $("span.price").text("$" + total);
            if(count === 0){
                $("a#cart_count").html(`<i class="icon icon-FullShoppingCart"></i>`);
            }else {
                $("a#cart_count").html(`<i class="icon icon-FullShoppingCart"></i>
                <span id="count" class="item_count">${count}</span>`);
            }

        },
        error(xhr, status, error) {
            console.error("There was a problem :", error);
        },
    });

}
// 取得購物車資料
function getCart() {
    $.ajax({
        url: "/api/cart/get",
        method: "GET",
        headers: {
            "authorization": localStorage.getItem("authorization")
        },
        success(response) {
            // console.log(response);
            let cartList = response.data;
            let total = 0;

            cartList.forEach(function (cartItem) {
                total += cartItem.subtotal;

                $("#cart_tbody").append(
                    `<tr>
                        <td class="product_thumb">
                            <a><img id="cart_image" src="data:image/jpeg;base64,${cartItem.imageBase64}" alt="商品圖片" style="height: 80px;width: 80px"/></a>
                        </td>
                        <td class="product_name">
                            <input id="productId" value="${cartItem.productId}" type="hidden" />
                            <a href="#">
                                ${cartItem.productName}
                            </a>
                        </td>
                        <td class="product_price">
                            ${cartItem.price}
                        </td>
                        <td class="product_quantity">
                            <label>數量:</label>
                            <input id="quantity" min="1" max="100" value="${cartItem.quantity}" type="number" onkeydown="preventEnter(event)" />
                        </td>
                        <td class="product_total"> $
                            ${cartItem.subtotal}
                        </td>
                        <td class="product_remove">
                            <a id="remove"><i class="fa fa-trash-o"></i></a>
                        </td>
                    </tr>`
                );
            });
            $("#cart_total").text("$" + total);
        },
        error(xhr, status, error) {
            console.error("There was a problem :", error);
        },
    });
}
