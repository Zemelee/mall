<template>
  <div>
    <h2>历史订单</h2>

    <div v-for="order in orders" :key="order.id" class="order-item">
      <div>
        <span>订单编号:</span>
        {{ order.id }}
      </div>

      <div>
        <span>下单时间:</span>
        {{ order.orderTime }}
      </div>

      <div>
        <span>总价:</span>
        ¥{{ order.totalPrice }}
      </div>

      <div>
        <span>订单详情:</span>
        <ul>
          <li v-for="item in order.orderItems" :key="item.id">
            {{ item.name }} x {{ item.quantity }}
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import { onBeforeMount } from "vue";
import axios from "axios";
export default {
  data() {
    return {
      orders: [
        {
          id: "1",
          orderTime: "2021-01-01 12:00:00",
          totalPrice: 99.5,
          orderItems: [
            {
              name: "商品1",
              quantity: 2,
            },
            {
              name: "商品2",
              quantity: 1,
            },
          ],
        },
        {
          id: "2",
          orderTime: "2022-01-01 12:00:00",
          totalPrice: 100.5,
          orderItems: [
            {
              name: "手机1",
              quantity: 2,
            },
            {
              name: "华为2",
              quantity: 1,
            },
          ],
        },
        // 更多订单
      ],
    };
  },

  
};
</script>

<style>
/* 订单项样式 */
</style>