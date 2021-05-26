<template>
  <div>
    <div class="MessageBoxWrapper">
      <div class="avatar">
        <el-avatar :size="50" shape="circle" :src="avatarSrc"></el-avatar>
      </div>
      <div class="MessageBlock">
        <div class="msg">{{messageText}}</div>
        <div class="date">{{messageDate}}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ['avatarSrc', 'messageText', 'messageDate'],
  name: 'MessageBox'
}
</script>

<style scoped>

.date{
  text-align: right;
}

.avatar{
  grid-area: avatar;
}

.MessageBlock{
  grid-area: messageBlock;
  border-bottom: #505458 solid 1px;
  text-align: left;
}

.MessageBoxWrapper{
  display: grid;
  grid-gap: 5px;
  grid-template-columns: 1fr 9fr;
  grid-template-areas:
    "avatar messageBlock";
  /*border: #42b983 solid 5px;*/
  height: 20%;
}

</style>
