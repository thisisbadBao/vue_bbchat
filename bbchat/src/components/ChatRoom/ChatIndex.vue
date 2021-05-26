<template>
  <div class="chatIndexWrapper">
    <div class="box sidebar">
      {{username}}
      <SideMenu></SideMenu>
    </div>
    <div class="box content">
      <chats></chats>
    </div>
  </div>
</template>

<script>
import SideMenu from './SideMenu'
import Chats from './Chats'
import bus from '../../assets/eventBus'
export default {
  name: 'ChatIndex',
  components: {Chats, SideMenu},
  comments: {SideMenu, Chats},
  data () {
    return {
      username: this.$cookies.get('username')
    }
  },
  mounted () {
    // this.$cookies.set('username', name, '1d')
    bus.$on('sendUsername', para => {
      console.log(para)
      this.username = para
      this.$cookies.set('username', name, '1d')
    })
  }
}
</script>

<style scoped>
/*聊天室列表*/
.sidebar {
  grid-area: sidebar;
  /*border: #444444 solid;*/
  padding: 8px;
  background: url("../../assets/list_bg.png");
  background-size: cover;
  /*margin-left: 20px;*/
  position: absolute;
  top: 5px;
  bottom: 5px;
  width: 25%;
}

/*聊天界面*/
.content {
  grid-area: content;
  /*border: #222222 solid 5px;*/
  /*margin-right: 20px;*/
  position: absolute;
  top: 5px;
  bottom: 5px;
  right: 5px;
  width: 70%;
  /*height: 100%;*/
}

/*组件页面布局*/
.chatIndexWrapper {
  display: grid;
  grid-gap: 5px;
  grid-template-columns: 4fr 10fr;
  grid-template-areas:
    "sidebar content";
  color: #444;
  height: 100%;
  /*width: 100%;*/
}

.box {
  color: #1C7887;    /*字体颜色*/
  border-radius: 5px;
  font-size: 150%;
}
</style>
