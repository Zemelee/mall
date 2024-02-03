<template>
  <div class="feedback">
    <h1>用户反馈</h1>
    <form @submit.prevent="submitForm">
      <div>
        <label for="name">姓名：</label>
        <input type="text" id="username" v-model="form.username" required />
      </div>
      <div>
        <label for="email">邮箱：</label>
        <input type="email" id="email" v-model="form.email" required />
      </div>
      <div>
        <label for="feedback">反馈信息：</label>
        <textarea id="feedback" v-model="form.feedback" required></textarea>
      </div>
      <button type="submit">提交</button>
    </form>
  </div>
</template>

<script setup>
import { reactive } from "vue";
import service from "../../../request/index.js"
const form = reactive({
  username: "",
  email: "",
  feedback: "",
});

function submitForm() {
  console.log(form);
  service.post("/user/feedback", form)
    .then(res => console.log(res))
    .catch(err => console.log(err))
}
</script>

<style scoped>
.feedback {
  width: 80%;
  margin: auto;
}
</style>
