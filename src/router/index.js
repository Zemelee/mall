import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import NotFound from '../views/frame/NotFound.vue'
import addProduct from '../views/product/AddProduct.vue'
import allProduct from '../views/product/AllProduct.vue'

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
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
