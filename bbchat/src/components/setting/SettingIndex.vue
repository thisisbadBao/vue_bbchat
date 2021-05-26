<template>
  <div class="settingWrapper">
<!--    <h5>settings</h5>-->
    <el-form :model="form">
      原来的用户名: {{oldName}}
      <el-form-item>
        <el-input type="text" v-model="form.username"
                  auto-complete="on" placeholder="账号"
                  style="width: 75%"
        ></el-input>
      </el-form-item>
      <el-form-item style="width: 50%">
        <el-button type="primary"
                   style="width: 50%;"
                   v-on:click="submitName">
          修改id
        </el-button>
      </el-form-item>
      <el-form-item>
        <el-input type="password" v-model="form.oldPassword"
                  auto-complete="off" placeholder="旧密码"
                  style="width: 75%"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-input type="password" v-model="form.password"
                  auto-complete="off" placeholder="新密码"
                  style="width: 75%"
        ></el-input>
      </el-form-item>
      <el-form-item style="width: 50%">
        <el-button type="primary"
                   style="width: 50%;"
                   v-on:click="submitCode">
          修改密码
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: 'SettingIndex',
  data () {
    return {
      oldName: this.$cookies.get('username'),
      form: {
        name: '',
        password: '',
        oldPassword: ''
      }
    }
  },
  methods: {
    submitCode () {
      let _this = this
      this.$axios
        .post('/code', {
          name: _this.$cookies.get('username'),
          old_code: this.form.oldPassword,
          new_code: this.form.password
        })
        .then(successResponse => {
          if (successResponse.data.code === 13) {
            this.$message({
              type: 'error',
              message: '密码错误，无法修改'
            })
          } else if (successResponse.data.code === 14) {
            this.$message({
              type: 'success',
              message: '修改密码成功'
            })
          }
        })
        .catch(failResponse => {
          console.log(failResponse.data)
        })
    },
    submitName () {
      this.$axios
        .post('/name', {
          old_name: this.oldName,
          new_name: this.form.username
        })
        .then(successResponse => {
          if (successResponse.data.code === 11) {
            this.$message({
              type: 'info',
              message: '用户名不变'
            })
          } else if (successResponse.data.code === 12) {
            this.$message({
              type: 'success',
              message: '修改用户名成功'
            })
            this.$cookies.set('username', this.form.username, '1d')
          } else if (successResponse.data.code === 15) {
            this.$message({
              type: 'error',
              message: '用户名已经存在'
            })
            this.$cookies.set('username', this.form.username, '1d')
          }
        })
        .catch(failResponse => {
          console.log(failResponse.data)
        })
    }
  }
}
</script>

<style scoped>

.settingWrapper{
  background: url('../../assets/message_bg.png');
  position: absolute;
  right: 250px;
  left: 250px;
  /*width: 50%;*/
}
</style>
