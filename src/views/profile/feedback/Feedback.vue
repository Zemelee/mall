<template>
  <div class="feedback">
    <h1>用户反馈</h1>
    <form @submit.prevent="submitForm">
      <div>
        <label for="name">姓名：</label>
        <input type="text" id="name" v-model="form.name" required />
      </div>
      <div>
        <label for="email">邮箱：</label>
        <input type="email" id="email" v-model="form.email" required />
      </div>
      <div>
        <label for="message">反馈信息：</label>
        <textarea id="message" v-model="form.message" required></textarea>
      </div>
      <button type="submit">提交</button>
    </form>
    <ul>
      <li v-for="(item, index) in feedbacks" :key="index">
        <p>{{ item.name }} - {{ item.email }} - {{ item.message }}</p>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { reactive } from "vue";

var form = reactive({
  name: "",
  email: "",
  message: "",
});
var feedbacks = reactive([]);

function submitForm() {
  feedbacks.push({ ...this.form });
  form.name = "";
  form.email = "";
  form.message = "";
}
</script>

<style scoped>
.feedback {
  width: 80%;
  margin: auto;
}
</style>
