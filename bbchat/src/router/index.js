import Vue from 'vue'
import Router from 'vue-router'
// 导入刚才编写的组件
// import AppIndex from '@/components/home/AppIndex'
import Login from '../components/Login'
import Home from '../components/Home'
import ChatIndex from '../components/ChatRoom/ChatIndex'
import SettingIndex from '../components/Setting/SettingIndex'
Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    // 下面都是固定的写法
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/home',
      name: 'Home',
      component: Home,
      // home页面并不需要被访问
      redirect: '/index',
      children: [
        {
          path: '/index',
          name: 'ChatRoom',
          component: ChatIndex,
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/setting',
          name: 'setting',
          component: SettingIndex,
          meta: {
            requireAuth: true
          }
        }
        // {
        //   path: '/index',
        //   name: 'AppIndex',
        //   component: AppIndex,
        //   meta: {
        //     requireAuth: true
        //   }
        // }
      ]
    }
  ]
})
