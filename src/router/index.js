import { createRouter, createWebHashHistory } from 'vue-router'
import Home from '../views/Home.vue'
import NotFound from '../views/frame/NotFound.vue'
import addProduct from '../views/product/AddProduct.vue'
import allProduct from '../views/product/AllProduct.vue'
import allFeedback from '../views/AllFeedback.vue'
import Chat from '../views/Chat.vue'
import User from '../views/user/UserManage.vue'
import Order from '../views/order/OrderManage.vue'
import Test from '../components/Test/Test.vue'

const routes = [
  {
    path: '/',
    component: Home,
    hidden: true,
    redirect: '/login',
  },
  {
    path: '/home',
    name: '首页',
    component: Home,
    children: [
      {
        path: 'echarts',
        name: 'echarts',
        component: () => import('../components/Echarts/Echarts.vue'),
      },
      {
        path: 'add',
        name: 'add',
        component: addProduct,
        hidden: true  //隐藏
      },
      {
        path: 'all',
        name: 'allProduct',
        component: allProduct,
        hidden: true  //隐藏
      },
      {
        path: 'feedback',
        name: 'allFeedback',
        component: allFeedback,
        hidden: true  //隐藏
      },
      {
        path: 'user',
        name: 'user',
        component: User,
        hidden: true  //隐藏
      },
      {
        path: 'order',
        name: 'order',
        component: Order,
        hidden: true  //隐藏
      },
      {
        path: 'chat',
        name: 'chat',
        component: Chat,
        hidden: true  //隐藏
      },
      {
        path: 'test',
        name: 'Test',
        component: Test,
        hidden: true  //隐藏
      },
    ]
  },

  { path: '/:pathMatch(.*)*', name: 'NotFound', component: NotFound, hidden: true },

  {
    path: '/login',
    name: '登录',
    component: () => import('../views/Login.vue'),
    hidden: true  //隐藏
  },

]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})


export default router
