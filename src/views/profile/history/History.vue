<template>
  <div>
    <h2>历史订单</h2>
    <div v-for="(item, index) in histories" :key="index">
      <div class="order-item">
        <div class="order-item__header">
          <div class="order-item__header__title">
            <span>商品：{{ item.name }}</span>
          </div>
          <div class="order-item__header__status">
            <span>数量{{ item.quantity }}</span>
          </div>
          <div class="order-item__header__status">
            <span>时间{{ item.order_time }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from "vue";
import service from "@/request/index.js";
let histories = reactive([]);
const userid = localStorage.getItem("userid");
service.get(`/mall/history/get/${userid}`)
  .then((res) => {
    histories = res.data;
  })
  .catch((err) => {
    console.error(err);
  });
</script>

<style>
/* 订单项样式 */
</style>