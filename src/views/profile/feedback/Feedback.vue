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
import { reactive, onMounted, onBeforeMount } from "vue";
import { ElMessage } from "element-plus";
import service from "../../../request/index.js"
const form = reactive({
  username: "",
  email: "",
  feedback: "",
});

function submitForm() {  service.post("/user/feedback/post", form)
    .then(res => {
      if (res.data == "feedback successfully") {
        ElMessage.success("反馈成功");
        form.username = ""
        form.email = ""
        form.feedback = ""
      }
    })
    .catch(err =>
      ElMessage.success("提交失败"))
}

let feedbackList = reactive([])
onBeforeMount(async () => {
  const res = await service.get("/user/feedback/get")
  feedbackList.value = res.data

})


</script>

<style scoped>
.feedback {
  width: 80%;
  margin: auto;
}
</style>
