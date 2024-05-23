document.addEventListener('DOMContentLoaded', () => {
    const friendList = document.getElementById('friendList');
    const chatMessages = document.getElementById('chatMessages');
    const messageInput = document.getElementById('messageInput');
    const sendButton = document.getElementById('sendButton');
    const fileInput = document.getElementById('fileInput');
    const chatWith = document.getElementById('chatWith');
    const toggleFriendsList = document.getElementById('toggleFriendsList');
    const friendsList = document.getElementById('friendsList');
    const closeFriendsListButtons = document.querySelectorAll('.close-friends-list');

    let currentChat = null;

    const socket = new WebSocket('ws://你的WebSocket伺服器地址');

    // 當收到新訊息時執行
    socket.onmessage = (event) => {
        const message = JSON.parse(event.data);
        if (message.type === 'newMessage') {
            displayMessage(message.content, 'received');
            updateLatestMessage(message.from, message.content);
            // 如果當前聊天對象不是發送訊息的人，則顯示提醒並將好友移到最頂部
            if (message.from !== currentChat) {
                markUnread(message.from);
                moveFriendToTop(message.from);
            }
        }
    };

    // 更新最新訊息
    function updateLatestMessage(friend, message) {
        const listItem = document.querySelector(`#friendList li[data-friend="${friend}"]`);
        if (listItem) {
            const latestMessage = listItem.querySelector('.latest-message');
            if (latestMessage) {
                latestMessage.textContent = message;
            }
        }
    }

    // 標記未讀訊息
    function markUnread(friend) {
        const listItem = document.querySelector(`#friendList li[data-friend="${friend}"]`);
        if (listItem) {
            listItem.classList.add('unread');
        }
    }

    // 移動好友項目至最頂部
    function moveFriendToTop(friend) {
        const listItem = document.querySelector(`#friendList li[data-friend="${friend}"]`);
        if (listItem) {
            friendList.prepend(listItem);
        }
    }

    // 加載好友列表
    function loadFriends() {
        // 這裡可以加入獲取好友列表的程式碼
        const friends = [
            { name: '好友A', avatar: 'https://example.com/avatarA.jpg', latestMessage: '最新訊息A' },
            { name: '好友B', avatar: 'https://example.com/avatarB.jpg', latestMessage: '最新訊息B' },
            { name: '好友C', avatar: 'https://example.com/avatarC.jpg', latestMessage: '最新訊息C' }
        ];
        friends.forEach(friend => {
            const li = document.createElement('li');
            li.dataset.friend = friend.name;
            const img = document.createElement('img');
            img.src = friend.avatar;
            const span = document.createElement('span');
            span.textContent = friend.name;
            const p = document.createElement('p');
            p.classList.add('latest-message');
            p.textContent = friend.latestMessage;
            li.appendChild(img);
            li.appendChild(span);
            li.appendChild(p);
            li.addEventListener('click', () => {
                chatWith.textContent = friend.name;
                currentChat = friend.name;
                loadChatHistory(friend.name);
                friendsList.classList.remove('open');
                // 移除未讀標記
                li.classList.remove('unread');
            });
            friendList.appendChild(li);
        });
    }

    // 加載聊天歷史記錄
    function loadChatHistory(friend) {
        // 這裡可以加入獲取聊天記錄的程式碼
        chatMessages.innerHTML = '';
        const messages = [
            { sender: '好友A', content: '嗨！' },
            { sender: '我', content: '你好！' }
        ];
        messages.forEach(message => {
            displayMessage(message.content, message.sender === '我' ? 'sent' : 'received');
        });
    }

    // 顯示訊息
    function displayMessage(message, type) {
        const div = document.createElement('div');
        div.textContent = message;
        div.classList.add('message', type);
        chatMessages.appendChild(div);
        chatMessages.scrollTop = chatMessages.scrollHeight;
    }

    // 點擊發送按鈕
    sendButton.addEventListener('click', () => {
        const message = messageInput.value;
        if (message && currentChat) {
            displayMessage(message, 'sent');
            socket.send(JSON.stringify({ type: 'newMessage', content: message, to: currentChat }));
            messageInput.value = '';
        }
    });

    // 選擇檔案
    fileInput.addEventListener('change', () => {
        const file = fileInput.files[0];
        if (file && currentChat) {
            const reader = new FileReader();
            reader.onload = () => {
                const base64 = reader.result;
                socket.send(JSON.stringify({ type: 'file', content: base64, fileName: file.name, to: currentChat }));
            };
            reader.readAsDataURL(file);
        }
    });

    // 點擊好友欄按鈕
    toggleFriendsList.addEventListener('click', () => {
        friendsList.classList.toggle('open');
    });

    // 點擊收回好友欄按鈕
    closeFriendsListButtons.forEach(button => {
        button.addEventListener('click', () => {
            friendsList.classList.remove('open');
        });
    });

    // 加載好友列表
    loadFriends();
});
