<template>
  <div>
    <div class="wrapper">
      <div class="box header">bbChat, just bb.</div>
      <div class="box sidebar">
        <el-aside class="sideBar" width="230px">
          <div class="title">聊天室列表</div>
          <el-row class="tac" >
            <el-col width="230">
              <el-menu
                default-active="2"
                class="el-menu-vertical-demo"
                @open="handleOpen"
                @close="handleClose"
                background-color="#545c64"
                text-color="#fff"
                active-text-color="#ffd04b">
                <el-submenu index="1">
                  <template slot="title">
                    <i class="el-icon-location"></i>
                    <span>导航一</span>
                  </template>
                  <el-menu-item-group>
                    <template slot="title">分组一</template>
                    <el-menu-item index="1-1">选项1</el-menu-item>
                    <el-menu-item index="1-2">选项2</el-menu-item>
                  </el-menu-item-group>
                  <el-menu-item-group title="分组2">
                    <el-menu-item index="1-3">选项3</el-menu-item>
                  </el-menu-item-group>
                  <el-submenu index="1-4">
                    <template slot="title">选项4</template>
                    <el-menu-item index="1-4-1">选项1</el-menu-item>
                  </el-submenu>
                </el-submenu>
                <el-menu-item index="2">
                  <i class="el-icon-menu"></i>
                  <span slot="title">导航二</span>
                </el-menu-item>
                <el-menu-item index="3" disabled>
                  <i class="el-icon-document"></i>
                  <span slot="title">导航三</span>
                </el-menu-item>
                <el-menu-item index="4">
                  <i class="el-icon-setting"></i>
                  <span slot="title">导航四</span>
                </el-menu-item>
                <el-menu-item index="5">
                  <i class="el-icon-setting"></i>
                  <span slot="title">导航四</span>
                </el-menu-item>
                <el-menu-item index="6">
                  <i class="el-icon-setting"></i>
                  <span slot="title">导航四</span>
                </el-menu-item>
              </el-menu>
            </el-col>
          </el-row>
        </el-aside>
      </div>
      <div class="box content">
        <div class="chatWrapper">
          <div class="messageBox">消息</div>
          <el-input v-model="messageInput" aria-placeholder="请输入要发送的内容" class="messageInputBox"></el-input>
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

/*聊天界面*/

/*消息界面*/
.messageBox {
  grid-area: messageBox;
  background-color: #42b983;
  width: 600px;
  height: 400px;
  position: absolute;
  left: 100px;
  right: 10px;
  top: -60px;
}

.messageInputBox{
  grid-area: messageInputBox;
}

.chatWrapper{
  display: grid;
  grid-gap: 5px;
  grid-template-columns: 300px;
  grid-template-rows: 100px 300px 50px;
  grid-template-areas:
    "......."
    "messageBox"
    "messageInputBox";
  background-color: #505458;
  color: #444;
  position: relative;
}

/*以下为布局*/

.sideBar{
  margin-left: 0px;
}
body {
  margin: 10px;
}

.sidebar {
  grid-area: sidebar;
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
  grid-gap: 10px;
  grid-template-columns: 235px 520px 520px;
  grid-template-rows: 30px 600px 30px;
  grid-template-areas:
    "....... header header"
    "sidebar content content"
    "footer  footer  footer";
  background-color: #fff;
  color: #444;
}

.box {
  background-color: #444;
  color: #fff;
  border-radius: 5px;
  padding: 0px;
  font-size: 150%;
}

.header,
.footer {
  background-color: #999;
}
</style>
