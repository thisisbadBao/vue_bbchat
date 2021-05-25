<template>
  <div id="backgroundImg">
    <h1>bbChat</h1>
    <el-form class="login-container"
             label-position="left"
             label-width="0px" size="medium">
      <h3 class="login_title">Login</h3>
      <el-form-item>
        <el-input type="text" v-model="loginForm.username"
                  auto-complete="on" placeholder="账号"></el-input>
      </el-form-item>
      <el-form-item>
        <el-input type="password" v-model="loginForm.password"
                  auto-complete="off" placeholder="密码"></el-input>
      </el-form-item>
      <el-form-item style="width: 50%" id="submitLogin">
        <el-button type="primary"
                   style="width: 100%;background: #505458;border: none"
                   v-on:click="login">
          登录
        </el-button>
      </el-form-item>
      <el-form-item style="width: 50%" id="submitRegister">
        <el-button type="primary"
                   style="width: 100%;background: #505458;border: none"
                   v-on:click="toRegister">
          注册
        </el-button>
      </el-form-item>
    </el-form>
    <!--    注册表单-->
    <el-dialog title="注册账号"
               :visible.sync="registerVisible"
               :modal-append-to-body="false"
               :center="true"
    >
      <el-form :model="registerForm" @submit.native.prevent status-icon :rules="rules" ref="ruleForm" class="demo-ruleForm">
        <el-form-item label="用户名" :label-width="formLabelWidth" prop="username">
          <el-input type="primary" v-model="registerForm.username" auto-complete="off" style="width: 400px"></el-input>
        </el-form-item>
<!--        <el-form-item label="账号(数字)" :label-width="formLabelWidth" prop="accountId">-->
<!--          <el-input type="primary" v-model.number="registerForm.accountId" auto-complete="off" style="width: 400px"></el-input>-->
<!--        </el-form-item>-->
        <el-form-item label="密码" :label-width="formLabelWidth" prop="password">
          <el-input type="password" v-model="registerForm.password" auto-complete="off" style="width: 400px"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" :label-width="formLabelWidth" prop="checkPass">
          <el-input type="password" v-model="registerForm.checkPass" auto-complete="off" style="width: 400px"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm('ruleForm')">重置</el-button>
        <el-button type="primary" @click="register('ruleForm')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import bus from '../assets/eventBus'
export default {
  name: 'Login',
  data () {
    // let checkId = (rule, value, callback) => {
    //   if (!value) {
    //     return callback(new Error('账号不能为空'))
    //   }
    //   setTimeout(() => {
    //     if (!Number.isInteger(value)) {
    //       callback(new Error('请输入数字值'))
    //     } else {
    //       if (value < 9999) {
    //         callback(new Error('账号至少为5位数字'))
    //       } else {
    //         callback()
    //       }
    //     }
    //   }, 1000)
    // }
    let validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        if (this.registerForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass')
        }
        callback()
      }
    }
    let validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.registerForm.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      loginForm: {
        username: '',
        password: ''
      },
      responseResult: [],
      // 注册表单
      registerVisible: false,
      registerForm: {
        username: '',
        // accountId: '',
        password: '',
        checkPass: ''
      },
      formLabelWidth: '100px',
      rules: {
        pass: [
          {validator: validatePass, trigger: 'blur'}
        ],
        checkPass: [
          {validator: validatePass2, trigger: 'blur'}
        ]
        // accountId: [
        //   {validator: checkId, trigger: 'blur'}
        // ]
      }
    }
  },
  methods: {
    login () {
      if (this.loginForm.username === '' && this.loginForm.password === '') {
        this.$message({
          type: 'warning',
          message: '请输入账号和密码'
        })
        return
      } else if (this.loginForm.username === '') {
        this.$message({
          type: 'warning',
          message: '账号不能为空'
        })
        return
      } else if (this.loginForm.password === '') {
        this.$message({
          type: 'warning',
          message: '密码不能为空'
        })
        return
      }
      let _this = this
      console.log(this.$store.state)
      this.$axios
        .post('/login', {
          accountId: this.loginForm.username,
          code: this.loginForm.password
        })
        .then(successResponse => {
          console.log(successResponse.data)
          if (successResponse.data.code === 2) {
            this.$message({
              type: 'error',
              message: '密码错误'
            })
          } else if (successResponse.data.code === 1) {
            this.$message({
              type: 'error',
              message: '没有该账号'
            })
          } else if (successResponse.data.name !== '') {
            // 外部变量 用于接收data
            // let __this = this
            // console.log(successResponse.data.name)
            let name = successResponse.data.name
            bus.$emit('sendUsername', name)
            _this.$store.commit('login', _this.loginForm)
            let path = this.$route.query.redirect
            this.$router.replace({path: path === '/' || path === undefined ? '/index' : path})
            this.$message({
              type: 'success',
              message: '登录成功'
            })
          }
        })
        .catch(failResponse => {
          console.log(failResponse.data)
        })
    },
    toRegister () {
      this.registerVisible = true
    },
    register (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // alert('submit!')
        } else {
          console.log('error submit!!')
          return false
        }
      })
      this.$axios
        .post('/register', {
          // accountId: this.registerForm.accountId,
          name: this.registerForm.username,
          code: this.registerForm.password,
          avatar: ''
        })
        .then(successResponse => {
          console.log(successResponse.data)
          if (successResponse.data.code === 3) {
            this.$message({
              type: 'warning',
              message: '已存在该用户'
            })
          } else if (successResponse.data !== '') {
            this.$message({
              type: 'success',
              message: '注册成功'
            })
            this.registerVisible = false
            let id = successResponse.data
            this.$alert('这是你的账号：' + id, '', {
              confirmButtonText: '确定'
              // callback: action => {
              //   this.$message({
              //     type: 'info',
              //     message: ''
              //   })
              // }
            })
          }
        })
        .catch(failResponse => {
          console.log(failResponse.data)
        })
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
    }
  }
}
</script>

<style>

.login-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 90px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 45px #cac6c6;
}

.login_title {
  margin: 0px auto 20px auto;
  text-align: center;
  color: #505458;
}
#backgroundImg{
  background: url("../assets/background.jpeg") no-repeat center;
  height: 100%;
  width: 100%;
  background-size: cover;
  position: fixed;
}
#submitLogin, #submitRegister{
  margin-left: 87px;
}

body{
  margin: 0px;
}
</style>
