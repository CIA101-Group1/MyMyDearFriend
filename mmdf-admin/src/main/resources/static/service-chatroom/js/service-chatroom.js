document.addEventListener('DOMContentLoaded', () => {
    // 初始化 WebSocket 連線
    const websocketURL = 'ws://' + window.location.hostname+':8001' + '/service-live';
    const socket = new WebSocket(websocketURL);

    let currentCustomerId = null;
    let cannedMessages = ["Hello, how can I help you?", "Please provide more details."];

    // 更新罐頭訊息下拉選單和刪除選擇框
    function updateCannedMessages() {
        const select = document.getElementById('canned-message-select');
        const deleteSelect = document.getElementById('delete-canned-message-select');
        select.innerHTML = '';
        deleteSelect.innerHTML = '';

        cannedMessages.forEach((message, index) => {
            const option = document.createElement('option');
            option.value = message;
            option.textContent = message;
            select.appendChild(option);

            const deleteOption = document.createElement('option');
            deleteOption.value = index;
            deleteOption.textContent = message;
            deleteSelect.appendChild(deleteOption);
        });
    }

    // 初始化罐頭訊息
    updateCannedMessages();

    // WebSocket onopen 事件
    socket.onopen = () => {
        console.log('WebSocket 連線成功');
        // 請求等待處理的會員清單
        const authorization = localStorage.getItem('authorization');
        socket.send(JSON.stringify({ type: 'service' ,'authorization':authorization}));
        console.log('已連線')
    };

    // WebSocket onmessage 事件
    socket.onmessage = (event) => {
        const data = JSON.parse(event.data);

        switch (data.type) {
            case 'getCustomer':
                // 更新等待處理的會員清單
                updateCustomerList(data.memberIdList);
                break;
            case 'member_message':
                // 顯示新訊息
                displayMessage(data.message, data.message_type === currentCustomerId ? 'sent' : 'received');
                if (data.senderId !== currentCustomerId) {
                    // 如果訊息來自不同的會員，則標記為未讀
                    markUnread(data.mebmerId);
                    moveCustomerToTop(data.memberId);
                }
                break;
            case 'chatHistory':
                // 載入聊天紀錄
                loadChatHistory(data.history);
                break;
        }
    };

    // 更新會員清單
    function updateCustomerList(customers) {
        const customerList = document.getElementById('customer-list');
        customerList.innerHTML = '';
        customers.forEach(customer => {
            const li = document.createElement('li');
            li.id = 'customer-' + customer.id;
            li.textContent = `ID: ${customer.id}, 最後訊息: ${customer.lastMessage}`;
            // if (customer.unread) {
            //     li.classList.add('unread');
            // }
            li.addEventListener('click', () => {
                currentCustomerId = customer.id;
                socket.send(JSON.stringify({ type: 'getChatHistory', customerId: customer.id }));
            });
            customerList.appendChild(li);
        });
    }

    // 在聊天視窗中顯示訊息
    function displayMessage(message, type) {
        const messages = document.getElementById('messages');
        const messageElement = document.createElement('div');
        messageElement.classList.add('message', type);
        messageElement.textContent = message.text;
        if (message.image) {
            const img = document.createElement('img');
            img.src = message.image;
            img.alt = '圖片訊息';
            img.style.maxWidth = '200px';
            img.style.maxHeight = '200px';
            messageElement.appendChild(img);
        }
        messages.appendChild(messageElement);
        messages.scrollTop = messages.scrollHeight;
    }

    // 標記會員為未讀
    function markUnread(customerId) {
        const customerElement = document.getElementById('customer-' + customerId);
        if (customerElement) {
            customerElement.classList.add('unread');
        }
    }

    // 將會員移到清單頂部
    function moveCustomerToTop(customerId) {
        const customerElement = document.getElementById('customer-' + customerId);
        if (customerElement) {
            const customerList = document.getElementById('customer-list');
            customerList.removeChild(customerElement);
            customerList.insertBefore(customerElement, customerList.firstChild);
        }
    }

    // 載入聊天紀錄
    function loadChatHistory(history) {
        const messages = document.getElementById('messages');
        messages.innerHTML = '';
        history.forEach(message => {
            displayMessage(message, message.memberId === currentCustomerId ? 'sent' : 'received');
        });
    }

    // 監聽事件：發送罐頭訊息
    document.getElementById('send-canned-message').addEventListener('click', () => {
        const cannedMessage = document.getElementById('canned-message-select').value;
        displayMessage(cannedMessage,'sent');
        sendMessage(cannedMessage);
    });

    // 監聽事件：發送一般訊息
    document.getElementById('send-message').addEventListener('click', () => {
        const messageText = document.getElementById('message-text').value;
        sendMessage(messageText);
    });

    // 發送訊息
    function sendMessage(text) {
        if (currentCustomerId && text.trim() !== '') {
            const message = {
                type: 'service_message',
                memberId: currentCustomerId,
                message: text,
                image: null
            };
            socket.send(JSON.stringify(message));
            displayMessage(message, 'sent');
        }
    }

    // 監聽事件：上傳圖片訊息
    document.getElementById('message-image').addEventListener('change', (event) => {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = (e) => {
                if (currentCustomerId) {
                    const message = {
                        type: 'service_message',
                        memberId: currentCustomerId,
                        message: '',
                        image: e.target.result
                    };
                    socket.send(JSON.stringify(message));
                    displayMessage(message, 'sent');
                }
            };
            reader.readAsDataURL(file);
        }
    });

    // 監聽事件：新增罐頭訊息
    document.getElementById('add-canned-message').addEventListener('click', () => {
        const newMessage = document.getElementById('new-canned-message').value;
        if (newMessage.trim() !== '') {
            cannedMessages.push(newMessage);
            updateCannedMessages();
        }
    });

    // 監聽事件：刪除罐頭訊息
    document.getElementById('delete-canned-message').addEventListener('click', () => {
        const index = document.getElementById('delete-canned-message-select').value;
        if (index !== '') {
            cannedMessages.splice(index, 1);
            updateCannedMessages();
        }
    });
});
