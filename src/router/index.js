import { createRouter, createWebHashHistory } from 'vue-router'
import NotFound from '../views/NotFound.vue'
import Mall from '../views/mall/Mall.vue'
import Login from '../views/login/Login.vue'
import Chat from '../views/chat/Chat.vue'
import Details from '../views/Details.vue'
import Cart from '../views/profile/cart/Cart.vue'
import Profile from '../views/profile/Profile.vue'
import Self from '../views/profile/self/Self.vue'
import History from '../views/profile/history/History.vue'
import Feedback from '../views/profile/feedback/Feedback.vue'
// import Test from '../views/Test.vue'
import { ElMessage } from "element-plus";

const routes = [
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/',
    redirect: "/mall",
  },
  {
    path: '/mall',
    name: 'mall',
    component: Mall,
  },
  {
    path: '/details/:id',
    name: 'details',
    component: Details,
  },
  {
    path: '/chat',
    name: 'chat',
    component: Chat,
  },
  {
    path: '/cart',
    name: 'cart',
    component: Cart,
  },
  {
    path: '/profile',
    name: 'profile',
    component: Profile,
    children: [
      {
        path: '/profile/self',
        name: 'self',
        component: Self
      },
      {
        path: '/profile/history',
        name: 'history',
        component: History
      },
      {
        path: '/profile/feedback',
        name: 'feedback',
        component: Feedback
      },
      {
        path: '/profile/cart',
        name: 'cart',
        component: Cart
      },
    ]
  },


  {
    path: '/:catchAll(.*)', // 404
    name: 'NotFound',
    component: NotFound,
  },
]

const router = createRouter({
  history: createWebHashHistory(),
  routes: routes
})

router.beforeEach((to, from, next) => {
  if (to.path === '/login') {
    // 如果跳转到登录页,清除 token
    localStorage.removeItem('token')
    localStorage.removeItem('userid')
    next()
  } else {
    // 如果跳转到其他页面
    const token = localStorage.getItem('token')
    if (!token) {
      // 如果没有token,重定向到登录页
      ElMessage.error("先登录");
      next('/login')
    } else {
      next() // 如果有token,放行
    }

  }

})


export default router