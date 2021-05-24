<template>
  <div>
    <div class="chatWrapper" v-for="item in chatSections" :key="item.chatName" :id="item.chatName">
      <el-tabs class="messageBox" stretch>
        <el-tab-pane>
          <span slot="label"><i class="el-icon-chat-line-round"></i></span>
          <div>{{item.chatTitle}}</div>
        </el-tab-pane>
        <el-tab-pane>
          <span slot="label"><i class="el-icon-user-solid"></i></span>
          用户列表
        </el-tab-pane>
      </el-tabs>
      <div class="messageInputBox">
        <el-form :inline="true" :model="formInline" class="demo-form-inline">
          <el-form-item>
            <el-input v-model="formInline.messageInput" type="textarea" aria-placeholder="输入你想发送的内容" style="width: 600px"></el-input>
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
export default {
  name: 'Chats',
  data () {
    return {
      formInline: {
        messageInput: ''
      },
      // 聊天室
      currentChat: '1',
      chatSections: [
        {
          chatTitle: 'initial room',
          chatName: '1',
          isActive: true
        }
      ],
      chatIndex: 1
    }
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
        isActive: true
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
  },
  methods: {
    sendMessage () {
    }
  }
}
</script>

<style scoped>
/*消息界面*/
.messageBox {
  grid-area: messageBox;
  margin: 10px 10px 5px 10px;
  border-radius: 10px;
  /*border: 3px solid #444444;*/
  padding: 5px;
  background: url("../../assets/message_bg.png");
  background-size: cover;
}

/*输入区域*/
.messageInputBox{
  grid-area: messageInputBox;
  margin: 1px 10px 10px 10px;
  /*border: 3px solid #444444;*/
}

/*组件页面布局*/
.chatWrapper{
  display: grid;
  grid-gap: 5px;
  grid-template-rows: 6fr 1fr;
  height: 100%;
  grid-template-areas:
    "messageBox"
    "messageInputBox";
  /*border: #26BCD5 solid;*/
  border-radius: 10px;
  color: #444444;
}
</style>
