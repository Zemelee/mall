// vite.config.js
import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': 'src', 
    },
  },
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8081', //这里填写你的API服务器地址
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, "")
      }
    }
  }
});
