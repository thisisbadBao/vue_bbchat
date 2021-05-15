<template>
  <div>
    <el-container>
      <el-header>Hello world, this is bbChat</el-header>
      <el-container>
        <el-aside width="200px">聊天室列表</el-aside>
        <el-container>
          <el-main>Main</el-main>
          <el-footer>@badbao</el-footer>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  name: 'AppIndex'
}
</script>

<style scoped>
.el-header, .el-footer {
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #D3DCE6;
  color: #333;
  text-align: center;
  line-height: 200px;
}

.el-main {
  background-color: #E9EEF3;
  color: #333;
  text-align: center;
  line-height: 450px;
}

body > .el-container {
  margin-bottom: 40px;
  margin-top: 10px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
</style>
