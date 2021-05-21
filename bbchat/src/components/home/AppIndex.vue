<template>
  <div class="main">
    <div class="wrapper">
      <div class="box header">Hello world, this is bbChat.</div>
<!--      侧边房间列表-->
      <el-aside class="box sidebar">
        <el-menu :default-openeds="[]"
                 default-active="1"
                 class="el-menu-vertical-demo"
        >
          <el-button type="primary" plain @click="addRoom">创建房间</el-button>
          <el-menu-item v-for="item in rooms" :index="item.name" :key="item.name" @click="getCurrentActive(item.name)">
            <i class="el-icon-menu"></i>
            <span slot="title">{{item.title}}</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
<!--      聊天区域-->
      <div class="box content"  v-for="item in chatSections" :key="item.chatName" :id="item.chatName">
        <div class="chatWrapper">
          <el-tabs class="messageBox" stretch="true">
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
                <el-input v-model="messageInput" aria-placeholder="输入你想发送的内容" style="width: 600px"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="sendMessage">发送</el-button>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </div>
      <div class="box footer">byc wbj xr yzl@SE2021</div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AppIndex',
  data () {
    return {
      messageInput: '',
      // 房间列表
      currentRoom: '1',
      rooms: [
        {
          title: 'initial room',
          name: '1'
        }
      ],
      roomIndex: 1,
      // 聊天区域
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
  methods: {
    sendMessage () {
      console.log('sent!')
    },
    addRoom (targetName) {
      let newRoomName = ++this.roomIndex + ''
      this.rooms.push({
        title: 'New Room' + newRoomName,
        name: newRoomName
      })
      this.currentRoom = newRoomName
      for (let room of this.chatSections) {
        room.isActive = false
        document.getElementById(room.chatName).style.display = 'none'
      }
      this.chatSections.push({
        chatTitle: 'New Room' + newRoomName,
        chatName: newRoomName,
        isActive: true
      })
      this.chatIndex = newRoomName
    },
    getCurrentActive (index) {
      console.log('打开房间' + index)
      this.openRoom(index)
    },
    openRoom (index) {
      console.log('房间' + index + '已打开')
      for (let room of this.chatSections) {
        room.isActive = false
        document.getElementById(room.chatName).style.display = 'none'
      }
      this.chatSections[index - 1].isActive = true
      document.getElementById(this.chatSections[index - 1].chatName).style.display = 'block'
    }
  }
}
</script>

<style>

.main{
  height: 100vh;
}
.wrapper{
  height: 100%;
  width: 100%;
  background: url("background.png") no-repeat center;
  background-size: cover;
  position: fixed;
}

/*消息界面*/
.messageBox {
  grid-area: messageBox;
  margin: 10px 10px 5px 10px;
  border-radius: 10px;
  /*border: 3px solid #444444;*/
  padding: 5px;
  background: url("message_bg.png");
}

.messageInputBox{
  grid-area: messageInputBox;
  margin: 1px 10px 10px 10px;
  /*border: 3px solid #444444;*/
}

.chatWrapper{
  display: grid;
  grid-gap: 5px;
  /*grid-template-columns: 1fr 9fr 1fr;*/
  grid-template-rows: 5fr 1fr;
  height: 100%;
  grid-template-areas:
    "messageBox"
    "messageInputBox";
  /*border: #26BCD5 solid;*/
  border-radius: 10px;
  margin-right: 5px;
  color: #444444;
}

/*以下为总体布局*/
body {
  margin: 0px;
}

.sidebar {
  grid-area: sidebar;
  /*border: #444444 solid;*/
  overflow: auto;
  padding: 8px;
  background: url("list_bg.png");
}

.content {
  grid-area: content;
}

.header {
  grid-area: header;
}

.footer {
  grid-area: footer;
}

.wrapper {
  display: grid;
  grid-gap: 5px;
  grid-template-columns: 2fr 4fr 4fr;
  grid-template-rows: 1fr 18fr 1fr;
  grid-template-areas:
    "header header header"
    "sidebar content content"
    "footer  footer  footer";
  background-color: #fff;
  color: #444;
  height: 100vh;
}

.box {
  color: #1C7887;    /*字体颜色*/
  border-radius: 5px;
  font-size: 150%;
}

.header,
.footer {
  background-color: #A7DDE0;
}
</style>
