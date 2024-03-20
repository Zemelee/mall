// vite.config.js
import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import {resolve} from 'path'
export default defineConfig({
  publicPath: './',
  plugins: [vue()],
  resolve: {
    alias: {
      "@": resolve(__dirname, 'src'), 
    },
    extensions: ['.vue','.js'] // 导入时想要省略的扩展名列表
  },
  server: {
    proxy: {
      '/api': {
        target: 'http://sugarblack.top:8081',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, "")
      }
    },
    fsServe: {
      root: '.' // 指定静态资源的根目录
    }
  },
  
});
