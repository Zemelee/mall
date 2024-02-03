<template>
  <div>
    <h2>用户注册</h2>
    <form @submit.prevent="registerUser">
      <label for="username">用户名：</label>
      <input type="text" v-model="user.username" required />

      <label for="password">密码：</label>
      <input type="password" v-model="user.password" required />

      <label for="address">地址：</label>
      <input type="text" v-model="user.address" />

      <label for="phone">电话：</label>
      <input type="text" v-model="user.phone" />

      <button type="submit">注册</button>
    </form>
  </div>
</template>

<script setup>
import { reactive } from 'vue';

const user = reactive({
  username: "",
  password: "",
  address: "",
  password: ""
})


</script>

<style scoped>
/* 样式可以根据需要进行修改 */
form {
  display: flex;
  flex-direction: column;
  max-width: 300px;
  margin: 0 auto;
}
</style>
