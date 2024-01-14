import { createRouter, createWebHashHistory } from 'vue-router'
import NotFound from '../views/NotFound.vue'
import Mall from '../views/mall/Mall.vue'
import Login from '../views/login/Login.vue'
import Chat from '../views/chat/Chat.vue'
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
    }, {
        path: '/chat',
        name: 'chat',
        component: Chat,
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