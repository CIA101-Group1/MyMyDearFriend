document.addEventListener('DOMContentLoaded', function () {
    const chatMessages = document.getElementById('chat-messages');
    const messageInput = document.getElementById('message-input');
    const sendButton = document.getElementById('send-button');
    const customerMessage = document.getElementById("customer");

    const helperURL = "ws://" + window.location.host + "/helper";
    const servicLiveeURL = "ws://" + window.location.host + "/serivce";

    const socket = new WebSocket(helperURL);

    socket.onopen = function () {
        console.log('WebSocket 連接已建立');

    };

    socket.onmessage = function (event) {
        var jsonObj = JSON.parse(event.data);
        var typeChat = jsonObj.type;
        if (jsonObj.type === "answer" || jsonObj.type === "welcome") {
            console.log("接收訊息中")
            // var messageElement = document.createElement('div');
            // messageElement.textContent = jsonObj.aiMessage;
            // chatMessages.appendChild(messageElement);
            // chatMessages.scrollTop = chatMessages.scrollHeight;
            addMessage(jsonObj.aiMessage,'helper')
        }
        if (jsonObj.type === "serviceLive") {
            var h1text = document.getElementById("text-primarys");
            h1text.innerText = '專員客服';
            // socket.close();
        }
    };

    sendButton.addEventListener('click', function () {
        var message = messageInput.value.trim();
        const div = document.createElement('div');
        if (message !== '') {
            var jsonObj = {type: 'question', message: message};
            // div.classList.add('col','border','p-3','text-end');
            // div.textContent = message;
            // chatMessages.appendChild(div);
            // chatMessages.scrollTop = chatMessages.scrollHeight;
            addMessage(message,'self');
            socket.send(JSON.stringify(jsonObj));
            messageInput.value = '';
        }
    });

    messageInput.addEventListener('keypress', function (event) {
        if (event.key === 'Enter') {
            sendButton.click();
        }
    });
    function addMessage(text, type) {
        const chatMessages = document.getElementById('chat-messages');
        const messageDiv = document.createElement('div');
        messageDiv.classList.add('message', type);

        if (type === 'helper') {
            const avatarImg = document.createElement('img');
            avatarImg.src = '/service-helper/img/helper.jpg';
            avatarImg.alt = '好友大頭貼';
            avatarImg.classList.add('avatar');
            messageDiv.appendChild(avatarImg);
        }
        const messageText = document.createElement('div');
        messageText.textContent = text;
        messageDiv.appendChild(messageText);

        chatMessages.appendChild(messageDiv)
    }
});
