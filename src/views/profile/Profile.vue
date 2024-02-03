<template>
  <div>
    <div class="top-navbar">
      <router-link to="/profile/history">历史订单</router-link> |
      <router-link to="/profile/self">个人信息</router-link> |
      <router-link to="/profile/cart">购物车</router-link> |
      <router-link to="/profile/feedback">反馈中心</router-link>
    </div>
    <router-view></router-view>
  </div>
</template>

<script setup>
import service from "../../request/index";
import axios from "axios";  
import { ElMessage } from "element-plus";
import { onMounted } from "vue";
</script>

<style>
/* 个人中心样式 */
</style>