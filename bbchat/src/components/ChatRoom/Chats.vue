<template>
  <div>
    <div class="chatWrapper" v-for="item in chatSections" :key="item.chatName" :id="item.chatName">
<!--      历史聊天记录窗口-->
      <el-dialog :visible.sync="chatHistoryVisible"
                 :modal-append-to-body="false"
                 :center="true"
                  title="历史聊天记录">
        <MessageBox class="chatHistoryWrapper" v-for="(msgHisItem, index) in item.chatHistory" :key="index" :message-text="msgHisItem.msg" :message-date="msgHisItem.date" :avatar-src="avatarUrl">
        </MessageBox>
      </el-dialog>
      <div class="messageBox" >
        <el-tabs stretch>
          <el-tab-pane>
            <span slot="label"><i class="el-icon-chat-line-round"></i></span>
            <div class="messageWrapper">
              <div class="roomTitle">{{item.chatTitle}}
                <el-button type="success" round :disabled="item.isConnected" @click="initWebSocket(item.chatName)">连接房间</el-button>
                <el-button type="warning" round @click="closeWebSocket(item.chatName)">断开连接</el-button>
                <el-button type="primary" round @click="getChatHistory(item.chatName)">查询历史记录</el-button>
              </div>
              <div id="message">
                <MessageBox v-for="(msgItem, index) in item.message" :key="index" :message-text="msgItem.msg" :message-date="msgItem.date" :avatar-src="avatarUrl">
                </MessageBox>
              </div>
            </div>
          </el-tab-pane>
          <el-tab-pane>
            <span slot="label"><i class="el-icon-user-solid"></i></span>
            用户列表 <br/>
            <el-popconfirm
              confirm-button-text='禁言'
              cancel-button-text='取消禁言'
              icon="el-icon-info"
              icon-color="red"
              title="确定禁言该用户?"
              v-for="(user, index) in item.userList" :key="index"
              @confirm="muteUser(user)"
              @cancel="unMuteUser(user)"
            >
              <el-button slot="reference" icon="el-icon-user">{{user}}</el-button>
            </el-popconfirm>
          </el-tab-pane>
        </el-tabs>
      </div>
      <div class="messageInputBox">
        <el-form :inline="true" :model="formInline" class="demo-form-inline">
          <el-form-item>
            <el-input v-model="formInline.messageInput" type="textarea" placeholder="输入你想发送的内容" style="width: 400px;" id="textArea"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="sendMessage(item.chatName)">发送</el-button>
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
      chatHistoryVisible: false,
      username: '',
      webSocket: [null, null],
      formInline: {
        messageInput: ''
      },
      // 聊天室
      currentChat: '1',
      chatSections: [
        {
          chatTitle: 'room1',
          chatName: '1',
          isActive: true,
          message: [],
          userList: [],
          isConnected: false,
          chatHistory: []
        },
        {
          chatTitle: 'room2',
          chatName: '2',
          isActive: false,
          message: [],
          userList: [],
          isConnected: false,
          chatHistory: []
        }
      ],
      chatIndex: 2
    }
  },
  created () {
    // let name = localStorage.getItem('username')
    // this.username = localStorage.getItem('username')
    this.username = this.$cookies.get('username')
    // this.initWebSocket(1)
  },
  destroyed () {
    for (let room of this.webSocket) {
      if (room !== null) room.close()
    }
  },
  mounted () {
    document.getElementById('2').style.display = 'none'
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
        message: [],
        userList: [],
        isConnected: false
      })
    })
    bus.$on('sendOpenRoom', para => {
      this.currentChat = para
      // this.closeWebSocket()
      for (let room of this.chatSections) {
        room.isActive = false
        document.getElementById(room.chatName).style.display = 'none'
        if (room.chatName === para) {
          room.isActive = true
          // this.initWebSocket(room.chatName)
        }
      }
      document.getElementById(this.currentChat).style.display = 'grid'
    })
    // bus.$on('sendUsername', para => {
    //   this.username = para
    // })
  },
  methods: {
    sendMessage (roomName) {
      let roomIndex = Number(roomName)
      let msg = this.formInline.messageInput
      this.webSocket[roomIndex - 1].send(msg)
      this.formInline.messageInput = ''
    },
    initWebSocket (roomName) {
      let _this = this
      let roomIndex = Number(roomName)
      _this.chatSections[roomIndex - 1].isConnected = true
      // 判断当前浏览器是否支持WebSocket
      if ('WebSocket' in window) {
        this.webSocket[roomIndex - 1] = new WebSocket('ws://localhost:8446/websocket/' + roomName + '/' + this.username)
        console.log('当前用户：' + this.username + '加入房间' + roomName)
      } else {
        alert('Not support websocket')
      }
      // 连接发生错误的回调方法
      this.webSocket[roomIndex - 1].onerror = function () {
        _this.$message({
          type: 'warning',
          message: '网络错误!'
        })
        // _this.initWebSocket()
        // console.log('error!')
      }
      // 连接成功建立的回调方法
      this.webSocket[roomIndex - 1].onopen = function (event) {
        // _this.$notify({
        //   type: 'success',
        //   message: _this.username + '进入房间' + roomName
        // })
      }
      // 接收到消息的回调方法
      this.webSocket[roomIndex - 1].onmessage = function (event) {
        console.log('来自服务端：' + event.data)
        let msg = event.data
        if (msg.indexOf('10001/') !== -1) {
          let index = msg.lastIndexOf('/')
          let str = msg.substring(index + 1, msg.length)
          console.log(str + '重复登录')
          _this.$notify({
            title: '警告',
            message: '当前用户已连接聊天室' + roomName + ', 不可重连',
            type: 'warning'
          })
        } else if (msg.indexOf('10002/') !== -1) {
          let index = msg.lastIndexOf('/')
          let str = msg.substring(index + 1, msg.length)
          console.log(str + '加入房间' + roomName)
          _this.addUser(roomIndex - 1, str)
          _this.$notify({
            title: '',
            message: str + '加入房间' + roomName,
            type: 'success'
          })
        } else if (msg.indexOf('10003/') !== -1) { // 用户退出房间
          let index = msg.lastIndexOf('/')
          let str = msg.substring(index + 1, msg.length)
          _this.deleteUser(roomIndex - 1, str)
          _this.$notify({
            title: '',
            message: str + '退出房间' + roomName,
            type: 'info'
          })
        } else if (msg.indexOf('10004/') !== -1) { // 有新用户进来时 更新当前用户列表
          console.log(msg)
          let index = msg.lastIndexOf('/')
          let str = msg.substring(index + 1, msg.length)
          let list = str.split('-')
          console.log(list)
          for (let user of list) {
            if (user !== _this.username) _this.addUser(roomIndex - 1, user)
          }
        } else if (msg.indexOf('10007/') !== -1) { // 被禁言 无法发言
          let index = msg.lastIndexOf('/')
          let str = msg.substring(index + 1, msg.length)
          _this.$notify({
            title: '',
            message: str,
            type: 'error'
          })
        } else if (msg.indexOf('10009/') !== -1) {
          let msgIndex = msg.lastIndexOf('/')
          let str = msg.substring(msgIndex + 1, msg.length)
          let msgAndDate = str.split('-')
          _this.showMessage(msgAndDate[0], msgAndDate[1], '1')
        } else if (msg.indexOf('10010/') !== -1) {
          let msgIndex = msg.lastIndexOf('/')
          let str = msg.substring(msgIndex + 1, msg.length)
          let msgAndDate = str.split('-')
          _this.showMessage(msgAndDate[0], msgAndDate[1], '2')
        }
      }
      // 连接关闭的回调方法
      this.webSocket[roomIndex - 1].onclose = function () {
        _this.$message({
          type: 'success',
          message: '关闭连接！'
        })
      }
      // 监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
      window.onbeforeunload = function () {
        _this.webSocket[roomIndex - 1].close()
      }
    },
    closeWebSocket (roomName) {
      let roomIndex = Number(roomName)
      this.webSocket[roomIndex - 1].close()
      this.chatSections[roomIndex - 1].isConnected = false
      for (let user of this.chatSections[roomIndex - 1].userList) {
        this.deleteUser(roomIndex - 1, user)
      }
      // this.$notify({
      //   title: '',
      //   message: this.username + '离开房间' + 'roomName',
      //   type: 'info'
      // })
    },
    // 显示消息
    showMessage (msg, date, roomName) {
      for (let room of this.chatSections) {
        if (room.chatName === roomName) {
          // document.getElementById('message').innerHTML += msg + '<br/>'
          room.message.push({msg: msg, date: date})
        }
        let div = document.getElementById('message')
        div.scrollTop = div.scrollHeight
      }
    },
    addUser (room, nickname) {
      for (let user in this.chatSections[room].userList) {
        if (nickname === user) return
      }
      this.chatSections[room].userList.push(nickname)
    },
    deleteUser (room, nickname) {
      this.chatSections[room].userList = this.chatSections[room].userList.filter(function (item) {
        return item !== nickname
      })
    },
    muteUser (name) {
      console.log('mute')
      let _this = this
      this.$axios
        .post('/stop', {
          from_who: _this.username,
          to_who: name
        })
        .then(response => {
          if (response.data.code === 4) {
            _this.$notify({
              title: '',
              message: '只有管理员才有禁言权限！',
              type: 'warning'
            })
          } else if (response.data.code === 5) {
            _this.$notify.error({
              title: '',
              message: '无法禁言管理员'
            })
          } else if (response.data.code === 6) {
            _this.$notify({
              title: '',
              message: name + '已经被禁言过了',
              type: 'warning'
            })
          } else if (response.data.code === 7) {
            _this.$notify({
              title: '',
              message: '成功禁言' + name,
              type: 'success'
            })
          } else if (response.data.code === 10) {
            _this.$notify({
              title: '',
              message: name + '已经不在房间了',
              type: 'warning'
            })
          }
        })
        .catch(fail => {
          console.log(fail.data)
        })
    },
    unMuteUser (name) {
      console.log('Unmute')
      let _this = this
      this.$axios
        .post('/enable', {
          from_who: _this.username,
          to_who: name
        })
        .then(response => {
          if (response.data.code === 4) {
            _this.$notify({
              title: '',
              message: '只有管理员才有禁言权限！',
              type: 'warning'
            })
          } else if (response.data.code === 5) {
            _this.$notify.error({
              title: '',
              message: '无法禁言管理员'
            })
          } else if (response.data.code === 8) {
            _this.$notify({
              title: '',
              message: name + '能正常发言',
              type: 'warning'
            })
          } else if (response.data.code === 9) {
            _this.$notify({
              title: '',
              message: '解除' + name + '的禁言',
              type: 'success'
            })
          } else if (response.data.code === 10) {
            _this.$notify({
              title: '',
              message: name + '已经不在房间了',
              type: 'warning'
            })
          }
        })
        .catch(fail => {
          console.log(fail.data)
        })
    },
    getChatHistory (roomName) {
      let roomIndex = Number(roomName)
      this.chatHistoryVisible = true
      this.$axios
        .post('/message', {
          msg_source: roomName
        })
        .then(successResponse => {
          console.log(successResponse.data)
          let str = successResponse.data
          if (successResponse.data !== '') {
            this.$notify({
              type: 'success',
              message: '查询房间' + roomIndex + '的历史成功'
            })
            let allMessage = str.split('/')
            for (let msgItem of allMessage) {
              let msgDate = msgItem.split('-')
              this.chatSections[roomIndex - 1].chatHistory.push({msg: msgDate[0], date: msgDate[1]})
            }
          }
        })
        .catch(failResponse => {
          console.log(failResponse.data)
        })
    }
  }
}
</script>

<style scoped>
.chatHistoryWrapper{
}

#message{
  grid-area: message;
  /*border: #444444 solid 2px;*/
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
  padding: 5px;
  border-radius: 20px;
  border-bottom: #505458 solid 3px;
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
  /*border: 3px solid #444444;*/
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
