<template>
    <div>
        <div v-if="true" style="
          display: flex;
          flex-wrap: wrap;
          justify-content: flex-start;
        ">
            <!-- 对话展示 -->
            <div class="chat-page reply-left" style="border: 1px solid red">
                <!-- logo -->
                <img style="width: 100px; margin-left: 20px; border-radius: 4px"
                    src="https://xinghuo.xfyun.cn/static/media/sparkdesk.34d76de5c2313b16ec3beacbe4269b9f.svg" />
                <div class="message-container">
                    <div style="display: flex; align-items: center" v-for="(item, index) in msgHistory" :key="index"
                        class="message" :style="{
                            backgroundColor: item.role == 'user' ? '#c8c9cc' : '#c6e2ff',
                        }">
                        <div class="avavatar" style="border-radius: 50%">
                            <img style="width: 24px; border-radius: inherit" :src="item.role == 'user'
                                ? 'https://himg.bdimg.com/sys/portrait/item/public.1.34814738.pVyNT_BiankdF5rQj7Qscg.jpg'
                                : 'https://xinghuo.xfyun.cn/static/media/gpt-logo.e9ad4150a385435f5a90b50c44dad847.svg'
                                " alt="" />
                        </div>
                        <div>{{ new Date(item.time).toISOString().slice(0, 19).replace("T", " ") }}</div>
                        <el-divider direction="vertical" />
                        <div class="message-content" :class="{ 'message-sent': item.role }">
                            {{ item.content }}
                        </div>
                    </div>
                </div>
            </div>
            <el-tag style="z-index: 1; margin-left: 10px">{{ message.role }}</el-tag>
            <div class="message-input">
                <div>
                    <el-input style="width: 400px;height: 100px;" type="textarea" v-model="message.content"
                        placeholder="输入消息..." />
                    <br>
                    <button @click="sendMessage">发送</button>
                </div>
            </div>

        </div>
        <div v-else>WebSocket连接失败</div>

    </div>
</template>

<script>
import axios from 'axios';
import Box from '@/components/Box';
export default {
    components: {
        Box
    },
    data() {
        return {
            host: 'localhost',
            port_sql: 3000,
            websocket: null,
            connected: false,
            message: {
                role: 'admin',
                content: 'admin-info',
                time: new Date().getTime()
            },
            rcvMessage: '',
            msgHistory: []
        };
    },
    created() {
        this.connectWebSocket('admin');
    },
    mounted() {
        axios.get(`http://${this.host}:${this.port_sql}/chatua/query?uid=2`).then(res => {
            console.log(res.data)
            this.msgHistory = res.data;
        })
    },
    methods: {
        connectWebSocket(role) {
            this.websocket = new WebSocket(`ws://${this.host}:3100/chatua`);
            this.websocket.onopen = () => {
                console.log('WebSocket连接成功');
                this.connected = true;
                this.websocket.send(JSON.stringify({ role }));
            };
            this.websocket.onmessage = (event) => {
                this.rcvMessage = JSON.parse(event.data);
                // 对方发送的消息
                if (this.rcvMessage.time) {
                    this.msgHistory.push(this.rcvMessage)
                } else {
                    // 对方上线的消息
                    this.$message({
                        message: "用户已上线",
                        type: 'success',
                        duration: 2000
                    });
                }
            };
            this.websocket.onclose = () => {
                console.log('WebSocket连接关闭');
                this.connected = false;
            };
            this.websocket.onerror = (error) => {
                console.error('WebSocket连接发生错误:', error);
                this.connected = false;
            };
        },

        async sendMessage() {
            if (this.message.content !== '') {
                let curMsgBody = { role: this.message.role, content: this.message.content, time: new Date().getTime() }
                this.websocket.send(JSON.stringify(curMsgBody));
                const addRes = await axios.post(`http://${this.host}:${this.port_sql}/chatua/add`, {
                    uid: 2,
                    ...curMsgBody
                })
                if (addRes.data == "ok") {
                    const queryRes = await axios.get(`http://${this.host}:${this.port_sql}/chatua/query?uid=2`)
                    this.msgHistory = queryRes.data
                    this.message.content = ''
                }
            }
        }
    }
};
</script>

<style lang="less" scoped>
.message-input {
    position: fixed;
    bottom: 28px;
    right: 0;
    height: 80vh;
    width: 40%;
    padding: 10px;
    background-color: #f2f2f2;
    display: flex;
    align-items: center;
}

.message-input input {
    flex: 1;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 5px;
    margin-right: 10px;
}

.message-input button {
    padding: 8px 16px;
    background-color: #007bff;
    color: #fff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

.message-input button:hover {
    background-color: #0056b3;
}


.reply-left {
    display: flex;
    width: 57%;
    margin-right: 10px;
    flex-direction: column;
    height: 85vh;

    .message-container {
        flex: 1;
        overflow-y: auto;
        max-height: 500px;
        padding: 16px;

        .message-content {
            padding: 8px;
            border-radius: 4px;
        }
    }

    .input-container {
        display: flex;
        align-items: center;
        padding: 16px;
    }
}

.message {
    margin-bottom: 8px;
}


</style>