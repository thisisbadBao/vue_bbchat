<template>
  <div class="wrapper">
    <div class="box header">
      <NavMenu></NavMenu>
    </div>
    <div class="box content">
      <router-view/>
    </div>
    <div class="box footer">
      byc_wbj_xr_yzl@COSE2021
    </div>
  </div>
</template>

<script>
import NavMenu from './common/NavMenu'
export default {
  name: 'Home',
  components: {NavMenu}
}
</script>

<style scoped>

.header {
  grid-area: header;
  /*border: #42b983 solid 5px;*/
}

.footer {
  grid-area: footer;
  /*border: #222222 solid 5px;*/
}

.content{
  grid-area: content;
  /*border: #222222 solid 5px;*/
  position: fixed;
  bottom: 60px;
  top: 70px;
  right: 5px;
  left: 5px;
}

.wrapper {
  display: grid;
  grid-gap: 5px;
  grid-template-columns: 1fr 1fr 1fr;
  grid-template-rows: 1fr 12fr 1fr;
  grid-template-areas:
    "header header header"
    "content content content"
    "footer  footer  footer";
  color: #444;
  height: 100%;
  width: 100%;
  background: url("../assets/background.png") no-repeat center;
  background-size: cover;
  position: fixed;
  top: 0;
  /*right: 0;*/
  /*bottom: 0;*/
}
.box {
  color: #222222;    /*字体颜色*/
  border-radius: 5px;
  font-size: 150%;
}
</style>
