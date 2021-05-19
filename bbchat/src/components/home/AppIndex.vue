<template>
  <div class="main">
    <div class="wrapper">
      <div class="box header">Hello world, this is bbChat.</div>
      <el-aside class="box sidebar">
        <el-menu :default-openeds="[]">
          <el-menu-item-group>
            <template slot="title">房间列表</template>
            <el-menu-item index="1-1">room1</el-menu-item>
            <el-menu-item index="1-2">room2</el-menu-item>
            <el-menu-item index="1-2">room3</el-menu-item>
            <el-menu-item index="1-2">room4</el-menu-item>
          </el-menu-item-group>
        </el-menu>
      </el-aside>
      <div class="box content">
        <div class="chatWrapper">
          <el-tabs class="messageBox" stretch="true">
            <el-tab-pane >
              <span slot="label"><i class="el-icon-chat-line-round"></i></span>
              <div>消息区</div>
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
      messageInput: ''
    }
  },
  methods: {
    sendMessage () {
      console.log('sent!')
    },
    handleOpen (key, keyPath) {
      console.log(key, keyPath)
    },
    handleClose (key, keyPath) {
      console.log(key, keyPath)
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
