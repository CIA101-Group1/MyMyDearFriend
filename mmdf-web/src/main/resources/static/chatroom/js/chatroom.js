// script.js

document.addEventListener('DOMContentLoaded', () => {
    const friendList = document.getElementById('friendList');
    const chatMessages = document.getElementById('chatMessages');
    const messageInput = document.getElementById('messageInput');
    const sendButton = document.getElementById('sendButton');
    const fileInput = document.getElementById('fileInput');
    const chatWith = document.getElementById('chatWith');
    const chatWithId = document.getElementById('chatWithId');
    const chatAvatar = document.getElementById('chatAvatar');
    const toggleFriendsList = document.getElementById('toggleFriendsList');
    const friendsList = document.getElementById('friendsList');
    const closeFriendsListButtons = document.querySelectorAll('.close-friends-list');

    let currentChat = null;

    const init = "ws://" + window.location.host + "/chatroom";
    const chat = "ws://" + window.location.host + "/message";
    // var memberId = null;
    const socketInit = new WebSocket(init);
    const socketChat = new WebSocket(chat);
    let memberId;

    // var socketChat = null;
    socketInit.onopen = (event) => {
        console.log('connected - init');
        const authorization = localStorage.getItem('authorization');
        socketInit.send(JSON.stringify({"type": "init", "authorization": authorization}));
        socketInit.send(JSON.stringify({"type": "getFriends"}));
        // socketInit.send(JSON.stringify({"type": "sendChatWebsocket"}));
    }

    socketChat.onopen = (event) => {
        const authorization = localStorage.getItem('authorization');
        socketChat.send(JSON.stringify({"type": "init", "authorization": authorization}));
        console.log('connected - chat');
    }

    <!--============ 初始化、好友列表、提醒 ============ -->
    socketInit.onmessage = (event) => {
        const jsonObj = JSON.parse(event.data);
        if (jsonObj.type === "getFriends") {
            loadFriends(jsonObj.friends);

        }
        // console.log(jsonObj.type);
        // if(jsonObj.type === "sendChatOk") {
        //     // socketChat = new WebSocket(chat);
        //     socketChat.onopen = (event) => {
        //
        //         memberId = jsonObj.memberId;
        //         console.log('connected - chat');
        //
        //
        //     }
        // }
    }

    <!--============ 傳送訊息、讀取歷史紀錄 ============-->
    // const socketChat = new WebSocket(chat);
    socketChat.onmessage = (chat) => {
        const jsonObj = JSON.parse(chat.data);
        console.log("取得續錫:" + jsonObj.type);

        if (jsonObj.type === "getHistory") {
            console.log('get history');
            loadChatHistory(jsonObj.message, jsonObj.sender);
        }
        if (jsonObj.type === "chat") {

        }
        if (jsonObj.type === 'newMessage') {
            if (jsonObj.img) {
                displayImg(jsonObj.img, 'received');
            } else {
                displayMessage(jsonObj.message, 'received');
            }
            updateLatestMessage(jsonObj.sender, jsonObj.message);
            // 如果當前聊天對象不是發送訊息的人，則顯示提醒並將好友移到最頂部
            console.log("取得資料")
            if (jsonObj.sender !== currentChat) {
                markUnread(jsonObj.sender);
                moveFriendToTop(jsonObj.sender);
            }
        }
    };
    <!--============ 方法區域 ============-->

    // 更新最新訊息
    function updateLatestMessage(friend, message) {
        // const listItem = document.querySelector(`#friendList li[data-friend="${friend}"]`);
        const listItem = document.getElementById(friend);
        if (listItem) {
            listItem.textContent = message;
            // const latestMessage = listItem.querySelector('.latest-message');
            // if (latestMessage) {
            //     latestMessage.textContent = message;
            // }
        }
    }

    // 標記未讀訊息
    function markUnread(friend) {
        // const listItem = document.querySelector(`#friendList li[data-friend="${friend}"]`);
        const listItem = document.getElementById(friend);
        if (listItem) {
            listItem.classList.add('unread');
        }
    }

    // 移動好友項目至最頂部
    function moveFriendToTop(friend) {
        // const listItem = document.querySelector(`#friendList li[data-friend="${friend}"]`);
        const listItem = document.getElementById(friend + "info");
        if (listItem) {
            friendList.prepend(listItem);
        }
    }

    // 加載好友列表
    function loadFriends(friends) {
        if (friends === undefined || friends === null) {
            return;
        }
        friends.forEach(friend => {
            var friendId = friend.id;
            const li = document.createElement('li');
            li.dataset.friend = friend.name;
            li.id = friendId + "info";
            const img = document.createElement('img');
            img.src = friend.avatar;
            img.classList.add('chatroomIMG');
            const div = document.createElement('div');
            div.classList.add('friend-info');
            const spanName = document.createElement('span');
            spanName.classList.add('name');
            spanName.textContent = friend.name;
            const spanId = document.createElement('span');
            spanId.classList.add('id');
            spanId.textContent = `ID: ${friend.id}`;
            const p = document.createElement('p');
            // p.id = `${friend.id}`.trim();
            p.id = friendId;
            p.classList.add('latest-message');
            p.textContent = friend.latestMessage;
            console.log(friend.latestMessage);
            div.appendChild(spanName);
            div.appendChild(spanId);
            div.appendChild(p);
            li.appendChild(img);
            li.appendChild(div);
            li.addEventListener('click', () => {
                chatWith.textContent = friend.name;
                chatWithId.textContent = `ID: ${friend.id}`;
                chatAvatar.src = friend.avatar;
                currentChat = chatWithId.textContent.split(":")[1].trim();
                socketChat.send(JSON.stringify({"type": "getHistory", "friendId": friend.id, "memberId": memberId}));
                // loadChatHistory(friend.name);
                // friendsList.classList.remove('open');
                // 移除未讀標記
                li.classList.remove('unread');
            });
            friendList.appendChild(li);
        });
    }


    // 加載聊天歷史記錄
    function loadChatHistory(friendMessages, memberId) {
        // 這裡可以加入獲取聊天記錄的程式碼
        chatMessages.innerHTML = '';
        var messages = JSON.parse(friendMessages);
        messages.forEach(message => {
            var messageJson = JSON.parse(message);
            console.log(messageJson.message);
            if (messageJson.img) {
                displayImg(messageJson.img, messageJson.sender == memberId ? 'sent' : 'received')
                return;
            }
            displayMessage(messageJson.message, messageJson.sender == memberId ? 'sent' : 'received');
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

    function displayImg(message, type) {
        // const div = document.createElement('div');
        const img = document.createElement('img');
        var blob = new Blob([new Uint8Array(message)], { type: 'image/*' })
        img.classList.add('message', type);
        img.src = URL.createObjectURL(blob);
        img.alt = '圖片';
        // img.style.maxWidth = '20%';
        // img.style.maxHeight = '20%';
        chatMessages.appendChild(img);
        chatMessages.scrollTop = chatMessages.scrollHeight;
    }

    // 點擊發送按鈕
    sendButton.addEventListener('click', () => {
        const message = messageInput.value;
        if (message && currentChat) {
            displayMessage(message, 'sent');
            socketChat.send(JSON.stringify({
                type: 'chat',
                message: message,
                receiver: currentChat,
                memberId: memberId
            }));
            var pid = document.getElementById(currentChat);
            pid.textContent = message;
            // socketInit.send(JSON.stringify({"type": "getFriends"}));
            messageInput.value = '';

        }
    });

    // document.getElementById('fileInput').addEventListener('change', (event) => {
    //     const file = event.target.files[0];
    //     if (file) {
    //         const reader = new FileReader();
    //         reader.onload = (e) => {
    //             if (currentChat) {
    //                 const message = {
    //                     type: 'chat',
    //                     memberId: memberId,
    //                     receiver: currentChat,
    //                     message: '',
    //                     image: e.target.result
    //                 };
    //                 socketChat.send(JSON.stringify(message));
    //                 displayImg(message, 'sent');
    //             }
    //         };
    //         reader.readAsDataURL(file);
    //     }
    // });
    document.getElementById('fileInput').addEventListener('change', function (event) {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                const div = document.createElement('div');
                div.classList.add('message', "sent");
                const image = document.createElement('img');
                image.classList.add('modal-content');
                image.src = e.target.result;
                // document.getElementById('imageContainer').appendChild(image);
                div.appendChild(image);
                chatMessages.appendChild(div);
                chatMessages.scrollTop = chatMessages.scrollHeight;
                const imageData = event.target.result;
                console.log(imageData);
                console.log(currentChat);
                const message = {
                    type: 'chat',
                    img: image.src,
                    message: "",
                    receiver: currentChat,
                    memberId: memberId

                };
                socketChat.send(JSON.stringify(message));
            }

            reader.readAsDataURL(file);
        }
    });
    // 選擇檔案
    // fileInput.addEventListener('change', () => {
    //     const file = fileInput.files[0];
    //     if (file && currentChat) {
    //         const reader = new FileReader();
    //         reader.onload = () => {
    //             const base64 = reader.result;
    //             socket.send(JSON.stringify({type: 'file', content: base64, fileName: file.name, to: currentChat}));
    //         };
    //         reader.readAsDataURL(file);
    //     }
    // });

    // // 點擊好友欄按鈕
    // toggleFriendsList.addEventListener('click', () => {
    //     friendsList.classList.toggle('open');
    // });
    //
    // // 點擊收回好友欄按鈕
    // closeFriendsListButtons.forEach(button => {
    //     button.addEventListener('click', () => {
    //         friendsList.classList.remove('open');
    //     });
    // });
    // function addListener() {
    //     var container = document.getElementById("row");
    //     container.addEventListener("click", function (e) {
    //         var friend = e.srcElement.textContent;
    //         updateFriendName(friend);
    //         var jsonObj = {
    //             "type": "getHistory",
    //             "memberId": self,
    //             "friend": friend,
    //             "message": ""
    //         };
    //         webSocket.send(JSON.stringify(jsonObj));
    //     });
    // }
    //

    // 加載好友列表
    messageInput.addEventListener('keypress', function (event) {
        if (event.key === 'Enter') {
            sendButton.click();
        }
    });
    document.getElementById('fileInput').addEventListener('change', function (event) {
        const file = event.target.files[0];
        if (file && !file.type.startsWith('image/')) {
            alert('只能上傳圖片檔案');
            event.target.value = ''; // 清空選擇的文件
        }
    });
    loadFriends();
});





