// script.js

new Vue({
    el: '#app',
    data: {
        messages: [],
        newMessage: '',
        socket: null,
    },
    mounted() {
        this.socket = new WebSocket('ws://你的WebSocket伺服器地址');

        this.socket.onmessage = (event) => {
            const message = JSON.parse(event.data);
            this.messages.push({
                id: this.messages.length + 1,
                type: message.type === 'file' ? 'received-file' : 'received',
                content: message.content
            });
            this.scrollToBottom();
        };
    },
    methods: {
        sendMessage() {
            if (this.newMessage) {
                this.messages.push({
                    id: this.messages.length + 1,
                    type: 'sent',
                    content: this.newMessage
                });
                this.socket.send(JSON.stringify({ type: 'text', content: this.newMessage }));
                this.newMessage = '';
                this.scrollToBottom();
            }
        },
        triggerFileInput() {
            this.$refs.fileInput.click();
        },
        sendFile(event) {
            const file = event.target.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = (e) => {
                    const fileContent = e.target.result;
                    this.messages.push({
                        id: this.messages.length + 1,
                        type: 'sent-file',
                        content: fileContent
                    });
                    this.socket.send(JSON.stringify({ type: 'file', content: fileContent }));
                    this.scrollToBottom();
                };
                reader.readAsDataURL(file);
            }
        },
        scrollToBottom() {
            this.$nextTick(() => {
                const chatMessages = this.$el.querySelector('.chat-messages');
                chatMessages.scrollTop = chatMessages.scrollHeight;
            });
        }
    }
});
