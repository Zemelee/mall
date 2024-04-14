<template>
  <div class="outLogin">
    <div class="login" style="background-color: aliceblue;">
      <h2>{{ $t('Login.title') }}</h2>
      <vuetyped :showCursor="false" :smart-backspace="false" :strings="writeTextArr" :type-speed="50" :loop="true">
        <div class="typing" />
      </vuetyped>
      <el-form ref="form">
        <el-form-item prop="username">
          <el-input v-model="username" :placeholder="$t('Login.username')" :prefix-icon="User"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="password" :placeholder="$t('Login.password')" type="password" :prefix-icon="Lock"
            show-password></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <div style="display: flex; align-items: center; justify-content: flex-end">
            <div class="code-left">
              <el-input v-model="Bcode" @keydown.enter="login" :placeholder="$t('Login.verify')" />
            </div>
            <div class="code-right" style="margin-left: 20px">
              <img :src="codeImg" @click="createCode" />
            </div>
          </div>
        </el-form-item>
        <el-form-item>

          <button @click="changeLocale('zh')">中文</button>
          <button @click="changeLocale('en')">English</button>
          <div><el-button type="primary" @click="login">{{ $t("Login.login") }}</el-button>
            <el-button class="thirdparty-button" type="primary" @click="dialog">
              {{ $t("Login.register") }}
            </el-button>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <a href="http://sugarblack.top/music/#/default/home" target="_blank" rel="noopener noreferrer">
              {{ $t("Login.music") }}
            </a>
          </div>
        </el-form-item>
      </el-form>
      <el-dialog title="新用户注册" ref="dialogRef" v-model="showDialog">
        新用户注册
        <br />
        <br />
        <br />
        <Dialog />
      </el-dialog>
    </div>
  </div>
</template>

<script setup>
import { generateCodeImg, generateCode } from "../../utils/code";
import crypto from 'crypto-js';
import service from "../../request/index";
import Dialog from "./Register.vue";
import { ref } from "vue";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
const router = useRouter();

import { useI18n } from "vue-i18n";
const { locale } = useI18n();
// 切换语言
const changeLocale = (lang) => {
  locale.value = lang;
};

const writeTextArr = ref(["MALL，世界，触手可及...", "这是一个最好的时代，科技的发展给予了每个人创造价值的可能性；这也是一个最充满想象的时代，每一位心怀梦想的人，终会奔向星辰大海..."]);

//验证码逻辑
var Acode = ref(generateCode()); //系统生成的code
var Bcode = ref(""); //用户输入的code
const codeImg = ref(generateCodeImg(Acode.value));
const createCode = () => {
  var newCodeText = generateCode();
  Acode.value = newCodeText;
  var newCode = generateCodeImg(newCodeText);
  codeImg.value = newCode;
};

// 获取用户信息
const username = ref("12");
const password = ref("21");

function md5Test() {
  var md5 = crypto.MD5(password.value).toString()
  console.log(md5);
}
async function login() {
  if (Bcode.value == "") {
    ElMessage.error("请输入验证码！");
    return;
  } else if (Bcode.value != Acode.value) {
    ElMessage.error("验证码不正确！");
    return;
  }
  await service
    .post("/user/login", {
      username: username.value,
      // password: crypto.MD5(password.value).toString(),
      password: password.value,
    })
    .then((res) => {
        console.log(res)
      if (res.data.success == false) {
        ElMessage.error("用户名或密码错误！");
      } else {
        localStorage.setItem("token", JSON.stringify(res.data.user.token));
        localStorage.setItem("userid", JSON.stringify(res.data.user.user_id));
        localStorage.setItem("username", res.data.user.username);
        router.push("/mall");
      }
    })
    .catch((err) => {
    });
}


// 弹出框逻辑
const showDialog = ref(false);
function dialog() {
  showDialog.value = true;
}
</script>


<style scoped>
.login {
  width: 400px;
  margin: 30px auto;
  border: 1px solid #ddd;
  padding: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.outLogin {
  height: 80vh;
  width: 90vw;
  border: 1px solid #ddd;
  padding: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  background-image: url('@/assets/loginBackground.png');
  background-size: cover;
}
</style>