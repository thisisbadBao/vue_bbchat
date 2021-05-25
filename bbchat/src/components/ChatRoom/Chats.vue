<template>
  <div>
    <div class="chatWrapper" v-for="item in chatSections" :key="item.chatName" :id="item.chatName">
      <div class="messageBox" >
        <el-tabs stretch>
          <el-tab-pane>
            <span slot="label"><i class="el-icon-chat-line-round"></i></span>
            <div class="messageWrapper">
              <div class="roomTitle">{{item.chatTitle}}
                <el-button class="primary" @click="initWebSocket">连接webSocket</el-button>
                <el-button class="primary" @click="closeWebSocket">断开连接</el-button>
              </div>
              <div id="message">
                <MessageBox v-for="(msg, index) in item.message" :key="index" :message-text="msg" :avatar-src="avatarUrl">
<!--                  {{item.message}}-->
                </MessageBox>
                <!--                          message <br>message <br>message <br>message <br>message <br>message <br>message <br>message <br>message <br>message <br>message <br>message <br>message <br>message <br>-->
              </div>
            </div>
          </el-tab-pane>
          <el-tab-pane>
            <span slot="label"><i class="el-icon-user-solid"></i></span>
            用户列表
            <h5>{{username}}</h5>
          </el-tab-pane>
        </el-tabs>
      </div>
      <div class="messageInputBox">
        <el-form :inline="true" :model="formInline" class="demo-form-inline">
          <el-form-item>
            <el-input v-model="formInline.messageInput" type="textarea" aria-placeholder="输入你想发送的内容" style="width: 400px" id="textArea"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="sendMessage">发送</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import bus from '../../assets/eventBus'
import MessageBox from '../common/MessageBox'
export default {
  name: 'Chats',
  components: {MessageBox},
  data () {
    return {
      avatarUrl: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png',
      username: '',
      webSocket: null,
      formInline: {
        messageInput: ''
      },
      // 聊天室
      currentChat: '1',
      chatSections: [
        {
          chatTitle: 'initial room',
          chatName: '1',
          isActive: true,
          message: []
        }
      ],
      chatIndex: 1
    }
  },
  created () {
    // let name = localStorage.getItem('username')
    this.username = localStorage.getItem('username')
    // this.initWebSocket()
  },
  destroyed () {
    this.closeWebSocket()
  },
  mounted () {
    bus.$on('sendCurrentChat', para => {
      this.currentChat = para
    })
    bus.$on('sendChatIndex', para => {
      this.chatIndex = para
    })
    bus.$on('sendNewChatTitle', para => {
      for (let room of this.chatSections) {
        room.isActive = false
        document.getElementById(room.chatName).style.display = 'none'
      }
      this.chatSections.push({
        chatTitle: para,
        chatName: this.currentChat,
        isActive: true,
        message: ''
      })
    })
    bus.$on('sendOpenRoom', para => {
      this.currentChat = para
      for (let room of this.chatSections) {
        room.isActive = false
        document.getElementById(room.chatName).style.display = 'none'
        if (room.chatName === para) {
          room.isActive = true
        }
      }
      document.getElementById(this.currentChat).style.display = 'grid'
    })
    bus.$on('sendUsername', para => {
      // console.log(para)
      // this.username = para
      // this.initWebSocket()
      localStorage.setItem('username', para)
    })
  },
  methods: {
    sendMessage () {
      let msg = this.formInline.messageInput
      this.webSocket.send(msg)
      this.formInline.messageInput = ''
    },
    initWebSocket () {
      let _this = this
      // 判断当前浏览器是否支持WebSocket
      if ('WebSocket' in window) {
        this.webSocket = new WebSocket('ws://localhost:8446/websocket/' + this.username)
        console.log('当前用户：' + this.username)
      } else {
        alert('Not support websocket')
      }
      // 连接发生错误的回调方法
      this.webSocket.onerror = function () {
        _this.$message({
          type: 'warning',
          message: 'webSocket Error!'
        })
        // _this.initWebSocket()
        // console.log('error!')
      }
      // 连接成功建立的回调方法
      this.webSocket.onopen = function (event) {
        _this.$message({
          type: 'success',
          message: '成功建立连接!'
        })
      }
      // 接收到消息的回调方法
      this.webSocket.onmessage = function (event) {
        _this.showMessage(event.data)
      }
      // 连接关闭的回调方法
      this.webSocket.onclose = function () {
        _this.$message({
          type: 'success',
          message: '关闭连接！'
        })
      }
      // 监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
      window.onbeforeunload = function () {
        this.webSocket.close()
      }
    },
    closeWebSocket () {
      this.webSocket.close()
    },
    // 显示消息
    showMessage (msg) {
      for (let room of this.chatSections) {
        if (room.isActive === true) {
          // document.getElementById('message').innerHTML += msg + '<br/>'
          room.message.push(msg)
        }
        let div = document.getElementById('message')
        div.scrollTop = div.scrollHeight
      }
    }
  }
}
</script>

<style scoped>
#message{
  grid-area: message;
  border: #444444 solid 2px;
  overflow: auto;
  /*margin-bottom: 20px;*/
  /*position: fixed;*/
  /*top: 180px;*/
  /*bottom: 170px;*/
  /*right: 10px;*/
  /*left: 390px;*/
  position: absolute;
  right: 5px;
  left: 5px;
  top: 5px;
  bottom: 5px;
}

.roomTitle{
  grid-area: roomTitle;
  /*border: #222222 solid 5px;*/
  /*position: absolute;*/
  /*position: absolute;*/
}

.messageWrapper{
  display: grid;
  grid-gap: 5px;
  grid-template-rows: 1fr 9fr;
  grid-template-areas:
    "roomTitle"
    "message";
  /*border: #42b983 solid;*/
  border-radius: 5px;
  /*color: #444444;*/
  position: fixed;
  top: 130px;
  bottom: 140px;
  right: 30px;
  width: 67%;
}

/*消息界面*/
.messageBox {
  grid-area: messageBox;
  /*margin: 10px 10px 5px 10px;*/
  border-radius: 10px;
  /*border: 3px solid #444444;*/
  padding: 5px;
  background: url("../../assets/message_bg.png");
  background-size: cover;
  position: absolute;
  top: 5px;
  bottom: 5px;
  left: 5px;
  width: 95%;
  font-size: 50%;
}

/*输入区域*/
.messageInputBox{
  grid-area: messageInputBox;
  /*margin: 1px 10px 10px 10px;*/
  border: 3px solid #444444;
  position: absolute;
  left: 100px;
  bottom: 5px;
  height: 100%;
}

/*组件页面布局*/
.chatWrapper{
  display: grid;
  grid-gap: 5px;
  grid-template-rows: 7fr 1fr;
  height: 100%;
  grid-template-areas:
    "messageBox"
    "messageInputBox";
  /*border: #26BCD5 solid;*/
  border-radius: 10px;
  color: #444444;
  position: absolute;
  width: 100%;
}
</style>
