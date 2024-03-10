import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import { createPinia } from 'pinia'
import router from './router'
import { createI18n } from 'vue-i18n';
import en from './locals/en.js';
import zh from './locals/zh.js';
// 组合语言包对象
const messages = {
    en,
    zh
}
// 创建实例对象
const i18n = createI18n({
    legacy: false,  // 设置为 false，启用 composition API 模式
    messages,
    locale: navigator.language === 'zh-CN' ? 'zh' : 'en',
  })
  

const app = createApp(App)
const pinia = createPinia()
app.use(pinia)
app.use(router)
app.use(i18n)
app.use(ElementPlus)
app.mount('#app')
