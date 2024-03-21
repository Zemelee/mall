const { defineConfig } = require('@vue/cli-service')
// const { resolve } = require('core-js/fn/promise')

module.exports = defineConfig({
  transpileDependencies: true,
  // resolve: {
    // alias: {
      // '@': resolve(__dirname, './src')
    // },
  // },
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        pathRewrite: {
          '^/api': ''
        }
      }
    }
  }
})
