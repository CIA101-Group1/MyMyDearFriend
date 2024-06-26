$(document).ready(function () {
    // 取得mini購物車資料
    getMiniCart();

    // 移除mini購物車商品
    $("div.cart_gallery").on("click", "a#mini_cart_remove", function (){
        Swal.fire({
            title: "確認移除?",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "確認",
            cancelButtonText: "取消"
        }).then((result) => {
            if (result.isConfirmed) {
                Swal.fire({
                    title: "商品已移除!",
                    icon: "success",
                    confirmButtonText: "確認"
                });
                let productId = $(this).closest("div.cart_item").find("input#productId").val();

                $.ajax({
                    url: "/api/cart/remove/" + productId,
                    method: "DELETE",
                    headers: {
                        "authorization": localStorage.getItem("authorization")
                    },
                    success(response) {
                        // 清空購物車資料重新取得購物車資料
                        $("#cart_tbody").empty();
                        getCart();
                        // 清空mini購物車資料重新取得mini購物車資料
                        $("div.cart_gallery").empty();
                        getMiniCart();
                    },
                    error(xhr, status, error) {
                        console.error("There was a problem :", error);
                    },
                })
            }
        });
    })

})

// 新增商品至收藏
function addToFavorite(productId) {
    // 取得商品資料
    let data ={
        productId: productId,
    }

    $.ajax({
        url: "/api/favorite/add",
        method: "POST",
        headers: {
            "authorization": localStorage.getItem("authorization")
        },
        contentType: "application/json",
        data: JSON.stringify(data),
        success(response){
            if(response.code == 0){
                Swal.fire({
                    title: '加入成功!',
                    icon: 'success',
                    confirmButtonText: '確認'
                });
            }else{
                Swal.fire({
                    title: '加入失敗',
                    icon: 'error',
                    text: response.message,
                    confirmButtonText: '確認'
                });
            }
        },
        error(xhr, status, error){
            console.error("There was a problem :", error);
            Swal.fire({
                title: "Oops...!",
                icon: "error",
                text: "伺服器連線失敗: " + error,
                confirmButtonText: "確認"
            });
        }
    })
}

// 新增商品至購物車
function addToCart(productId, quantity) {
    // 取得商品資料
    let data ={
        productId: productId,
        quantity: quantity
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
            if(response.code == 0){
                Swal.fire({
                    title: '加入成功!',
                    icon: 'success',
                    confirmButtonText: '確認'
                });
                // 清空mini購物車資料重新取得mini購物車資料
                $("div.cart_gallery").empty();
                getMiniCart();
            }else{
                Swal.fire({
                    title: '加入失敗',
                    icon: 'error',
                    text: response.message,
                    confirmButtonText: '確認'
                });
            }
        },
        error(xhr, status, error){
            console.error("There was a problem :", error);
            Swal.fire({
                title: "Oops...!",
                icon: "error",
                text: "伺服器連線失敗: " + error,
                confirmButtonText: "確認"
            });
        }
    })
}

// 取得mini購物車資料
function getMiniCart() {
    $.ajax({
        url: "/api/cart/get",
        method: "GET",
        headers: {
            "authorization": localStorage.getItem("authorization")
        },
        success(response) {
            if(response.code == 0){
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
                                <a href="/buyer/product/getOne/${cartItem.productId}">${cartItem.productName}</a>
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
            }
        },
        error(xhr, status, error) {
            console.error("There was a problem :", error);
            Swal.fire({
                title: "Oops...!",
                icon: "error",
                text: "伺服器連線失敗: " + error,
                confirmButtonText: "確認"
            });
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
            if(response.code == 0){
                let cartList = response.data;
                let total = 0;
                let sellers = {};

                // 按照sellerId分類購物車項目
                cartList.forEach(function (cartItem) {
                    total += cartItem.subtotal;
                    if (!sellers[cartItem.sellerId]) {
                        sellers[cartItem.sellerId] = [];
                    }
                    sellers[cartItem.sellerId].push(cartItem);
                });

                // 清空現有的購物車表格
                $("#cart_tbody").empty();

                // 生成各個seller的表格
                for (let sellerId in sellers) {
                    let items = sellers[sellerId];
                    let sellerTotal = 0;

                    // 為每個賣家插入分類行
                    $("#cart_tbody").append(
                        `<tr>
                        <td colspan="1" class="seller">
                        <input type="checkbox" id="checkbox${sellerId}" class="seller-checkbox form-check-input" data-seller-id="${sellerId}" style="margin-right: 20px">
                        <label class="form-check-label" for="checkbox${sellerId}">賣家: ${items[0].sellerName}</label></td>
                        <td colspan="5"></td>
                    </tr>`
                    );

                    items.forEach(function (cartItem) {
                        sellerTotal += cartItem.subtotal;
                        $("#cart_tbody").append(
                            `<tr data-seller-id="${sellerId}">
                            <td class="product_thumb">
                                <a><img id="cart_image" src="data:image/jpeg;base64,${cartItem.imageBase64}" alt="商品圖片" style="height: 80px;width: 80px"/></a>
                            </td>
                            <td class="product_name">
                                <input id="productId" value="${cartItem.productId}" type="hidden" />
                                <a href="/buyer/product/getOne/${cartItem.productId}">${cartItem.productName}</a>
                            </td>
                            <td class="product_price">${cartItem.price}</td>
                            <td class="product_quantity">
                                <label>數量:</label>
                                <input id="quantity" min="1" max="100" value="${cartItem.quantity}" type="number" onkeydown="preventEnter(event)" />
                            </td>
                            <td class="product_total"><span>$</span>${cartItem.subtotal}</td>
                            <td class="product_remove">
                                <a id="remove"><i class="fa fa-trash-o"></i></a>
                            </td>
                        </tr>`
                        );
                    });
                }

                // 監聽賣家選擇框的點擊事件，確保只能勾選一個賣家
                $(".seller-checkbox").on('change', function() {
                    $(".seller-checkbox").not(this).prop('checked', false);
                    updateSelectedSellerTotal();
                });
            }
        },
        error(xhr, status, error) {
            console.error("There was a problem :", error);
            Swal.fire({
                title: "Oops...!",
                icon: "error",
                text: "伺服器連線失敗: " + error,
                confirmButtonText: "確認"
            });
        },
    });
}

// 取得選擇商品總計
function updateSelectedSellerTotal() {
    let selectedSellerId = $(".seller-checkbox:checked").data("seller-id");
    if (selectedSellerId) {
        let selectedTotal = 0;
        $(`tr[data-seller-id="${selectedSellerId}"]`).each(function() {
            let subtotal = parseFloat($(this).find(".product_total").text().trim().replace('$', ''));
            selectedTotal += subtotal;
        });
        $("#cart_total").text("$" + selectedTotal);
    } else {
        $("#cart_total").text("$0");
    }
}