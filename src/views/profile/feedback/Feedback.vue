<template>
  <div class="feedback">
    <h4>任何反馈可通过此页面反馈</h4>
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
        <el-input id="feedback" type="textarea" placeholder="请输入反馈内容..." v-model="form.feedback" required></el-input>
      </div>
      <br>
      <button type="submit">提交</button>
    </form>
    本项目开源至：https://github.com/Zemelee/mall
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

function submitForm() {
  service.post("/user/feedback/post", form)
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
  /* margin: auto; */
}
</style>
