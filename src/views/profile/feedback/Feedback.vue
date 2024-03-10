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

    <hr>
    <h2>用户反馈列表</h2>
    <button @click="getFeedbackList()">111</button>
    <div v-for="item in feedbackList" :key="item.id">
      <p>用户：{{ item.username }}</p>
      <p>邮箱：{{ item.email }}</p>
      <p>反馈信息：{{ item.feedback }}</p>
    </div>
  </div>
</template>

<script setup>
import { reactive, onMounted, onBeforeMount } from "vue";
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

let feedbackList = reactive([])
onBeforeMount(async () => {
  const res = await service.get("/user/feedback/get")
  console.log(res);
  feedbackList.value = res.data

})

function getFeedbackList() {
  console.log(feedbackList.value);
}
</script>

<style scoped>
.feedback {
  width: 80%;
  margin: auto;
}
</style>
