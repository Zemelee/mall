import { createRouter, createWebHashHistory } from 'vue-router'
import NotFound from '../views/NotFound.vue'
import Mall from '../views/mall/Mall.vue'
import Login from '../views/login/Login.vue'
import Chat from '../views/chat/Chat.vue'
import Details from '../views/Details.vue'
import Cart from '../views/Profile/cart/Cart.vue'

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
        path: '/:catchAll(.*)', // 404
        name: 'NotFound',
        component: NotFound,
    },
]

const router = createRouter({
    history: createWebHashHistory(),
    routes: routes
})


export default router