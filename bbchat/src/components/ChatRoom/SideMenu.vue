<template>
  <el-menu :default-openeds="[]"
           default-active="1"
           class="el-menu-vertical-demo"
           background-color="transparent"
           active-text-color="#FFF"
  >
<!--    <create-room :show.sync="createRoomVisible"></create-room>-->
<!--    创建聊天室表单-->
    <el-dialog title="创建聊天室"
               :visible.sync="createRoomVisible"
               :modal-append-to-body="false"
               :center="true"
    >
      <el-form :model="form" @submit.native.prevent>
        <el-form-item label="房间名" :label-width="formLabelWidth">
          <el-input v-model="form.name" auto-complete="off" style="width: 400px"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="createRoomVisible = false">取 消</el-button>
        <el-button type="primary" @click="toAddRoom(form.name)">确 定</el-button>
      </div>
    </el-dialog>
<!--    <el-button type="primary" plain @click="createRoomVisible = true">创建房间</el-button>-->
<!--    -->
    <el-menu-item v-for="item in rooms" :index="item.name" :key="item.name" @click="getCurrentActive(item.name)">
      <i class="el-icon-menu"></i>
      <span slot="title">{{item.title}}</span>
    </el-menu-item>
  </el-menu>
</template>

<script>
import bus from '../../assets/eventBus'
import CreateRoom from '../common/CreateRoom'
export default {
  name: 'SideMenu',
  components: {
    CreateRoom
  },
  // created () {
  //   this.addRoom('room2')
  //   this.addRoom('room3')
  // },
  data () {
    return {
      // 房间列表
      currentRoom: '1',
      rooms: [
        {
          title: 'room1',
          name: '1'
        },
        {
          title: 'room2',
          name: '2'
        }
      ],
      roomIndex: 2,
      // 聊天室
      currentChat: '1',
      chatSections: [
        {
          chatTitle: 'room1',
          chatName: '1',
          isActive: true
        },
        {
          chatTitle: 'room2',
          chatName: '2',
          isActive: false
        }
      ],
      chatIndex: 2,
      // 创建聊天室表单
      createRoomVisible: false,
      form: {
        name: ''
      },
      formLabelWidth: '100px'
    }
  },
  methods: {
    toAddRoom (name) {
      this.createRoomVisible = false // 关闭创建聊天室表单
      this.$message({
        type: 'success',
        message: '创建聊天室成功'
      })
      this.addRoom(name)
    },
    addRoom (roomTitle) {
      let newRoomName = ++this.roomIndex + ''
      this.rooms.push({
        title: roomTitle,
        name: newRoomName
      })
      this.currentRoom = newRoomName
      this.chatIndex = this.roomIndex
      bus.$emit('sendChatIndex', this.chatIndex)
      this.currentChat = newRoomName
      bus.$emit('sendCurrentChat', this.currentChat)
      //
      for (let room of this.chatSections) {
        room.isActive = false
      }
      this.chatSections.push({
        chatTitle: roomTitle,
        chatName: newRoomName,
        isActive: true
      })
      bus.$emit('sendNewChatTitle', roomTitle)
    },
    getCurrentActive (index) {
      console.log('打开房间' + index)
      this.currentChat = index
      bus.$emit('sendOpenRoom', this.currentChat)
      console.log('房间' + index + '已打开')
    }
  }
}
</script>

<style scoped>

</style>
