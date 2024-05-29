document.addEventListener('DOMContentLoaded', function () {
    const chatMessages = document.getElementById('chat-messages');
    const messageInput = document.getElementById('message-input');
    const sendButton = document.getElementById('send-button');
    const customerMessage = document.getElementById("customer");

    const helperURL = "ws://" + window.location.host + "/helper";
    console.log(helperURL);
    const servicLiveeURL = "ws://" + window.location.hostname + ':8001' + "/service-live";

    var socket = new WebSocket(helperURL);
    var websocketService = new WebSocket(servicLiveeURL);
    var currentSocekt =socket;
    var sendServiceMemberId;
    var helperType = 'question';
    var serviceInitType = 'member';
    var serviceType = 'member_message';
    var currentMessageType;
    var serviceId;
    socket.onopen = function () {
        const authorization = localStorage.getItem('authorization');
        currentMessageType = helperType;
        socket.send(JSON.stringify({type:"init","authorization":authorization}));
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
            addMessage(jsonObj.aiMessage, 'helper')
        }
        if (jsonObj.type === "serviceLive") {
            var h1text = document.getElementById("text-primarys");
            const id = jsonObj.memberId;
            sendServiceMemberId = id;
            h1text.innerText = '客服專員';
            addMessage("已接通客服專員，請輸入您的問題", 'helper')
            switchWebSocket();
            socket.close();
        }

    };
    socket.onclose = function (event) {
        console.log('小幫手已離開');
    }
    websocketService.onopen = function (event) {
        console.log('serviceLive - ON!');

    }
    websocketService.onmessage = function (event) {
        var jsonObj = JSON.parse(event.data);
        switch (jsonObj.type) {
            case 'service_message':
                addMessage(jsonObj.message, 'helper')
                break;
            case 'getServiceId':
                serviceId = jsonObj.serviceId;
                console.log(serviceId);
                currentMessageType = serviceType;
                console.log('OPEN')

        }
    }
    websocketService.onclose = function () {
        console.log('serviceLive - OFF!');
        console.log('WebSocket B 已關閉', event);
        // 打印關閉的原因和代碼
        console.log('關閉代碼: ', event.code);
        console.log('關閉原因: ', event.reason);
        console.log('是否正常關閉: ', event.wasClean)
    }
    websocketService.onerror =function (event){
        console.error(event)
    }


    sendButton.addEventListener('click', function () {
        var message = messageInput.value.trim();
        const div = document.createElement('div');
        if (message !== '') {
            console.log(currentMessageType);
            var jsonObj = {type: currentMessageType, message: message,serviceId:serviceId,memberId:sendServiceMemberId  };
            // div.classList.add('col','border','p-3','text-end');
            // div.textContent = message;
            // chatMessages.appendChild(div);
            // chatMessages.scrollTop = chatMessages.scrollHeight;
            addMessage(message, 'self');
            currentSocekt.send(JSON.stringify(jsonObj));
            messageInput.value = '';
        }
    });


    // sendButton.addEventListener('click', function () {
    //     var message = messageInput.value.trim();
    //     const div = document.createElement('div');
    //     if (socket.readyState === 1) {
    //         if (message !== '') {
    //             var jsonObj = {type: 'question', message: message};
    //             // div.classList.add('col','border','p-3','text-end');
    //             // div.textContent = message;
    //             // chatMessages.appendChild(div);
    //             // chatMessages.scrollTop = chatMessages.scrollHeight;
    //             addMessage(message, 'self');
    //             socket.send(JSON.stringify(jsonObj));
    //             messageInput.value = '';
    //         }
    //     }
    //
    // });

    messageInput.addEventListener('keypress', function (event) {
        if (event.key === 'Enter') {
            sendButton.click();
        }
    });

    function addMessage(text, type) {
        const chatMessages = document.getElementById('chat-messages');
        const messageDiv = document.createElement('div');
        console.log(type);
        messageDiv.classList.add('message', type);
        // if(type != 'helper'){
        //     messageDiv.classList.add('float-right');
        // }else {
        //     messageDiv.classList.add('float-left');
        // }

        if (type === 'helper') {
            const avatarImg = document.createElement('img');
            avatarImg.src = '/service-helper/img/helper.jpg';
            avatarImg.alt = '小幫手頭貼';
            avatarImg.classList.add('avatar');
            messageDiv.appendChild(avatarImg);
        }else{
            const avatarImg = document.createElement('img');
            avatarImg.src = '/service-helper/img/servicelive.jpg';
            avatarImg.alt = '客服頭貼';
            avatarImg.classList.add('avatar');
            messageDiv.appendChild(avatarImg);

        }
        const messageText = document.createElement('div');
        // const helloworld = document.createElement('div');
        messageText.textContent = text;
        messageDiv.appendChild(messageText);

        chatMessages.appendChild(messageDiv);
        // chatMessages.appendChild(helloworld);
        scrollToBottom();
    }
    function switchWebSocket(){
        websocketService.send(JSON.stringify({type: 'member', 'memberId': sendServiceMemberId}));
        currentSocekt = websocketService;
        currentMessageType = serviceInitType;

    }
    function scrollToBottom() {
        const container = document.getElementById("chat-messages");
        container.scrollTop = container.scrollHeight;
    }

});
