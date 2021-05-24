<template>
  <div class="chatIndexWrapper">
    <div class="box sidebar">
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
export default {
  name: 'ChatIndex',
  components: {Chats, SideMenu},
  comments: {SideMenu, Chats}
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
  margin-left: 20px;
  /*position: fixed;*/
}

/*聊天界面*/
.content {
  grid-area: content;
  /*border: #222222 solid 5px;*/
  margin-right: 20px;
  margin-bottom: 50px;
  /*position: fixed;*/
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
}

.box {
  color: #1C7887;    /*字体颜色*/
  border-radius: 5px;
  font-size: 150%;
}
</style>
