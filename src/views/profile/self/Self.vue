<template>
  <el-descriptions
      class="margin-top"
      title="基本信息"
      :column="3"
      :size="size"
      border
    >
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon :style="iconStyle">
              <user />
            </el-icon>
            昵称
          </div>
        </template>
        {{ profile.username }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon :style="iconStyle">
              <iphone />
            </el-icon>
            电话
          </div>
        </template>
        {{ profile.phone }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon :style="iconStyle">
              <location />
            </el-icon>
            地址
          </div>
        </template>
        {{ profile.address }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon :style="iconStyle">
              <tickets />
            </el-icon>
            余额
          </div>
        </template>
        <el-tag size="small">{{ profile.balance }}</el-tag>
      </el-descriptions-item>
    </el-descriptions>
</template>



<script setup>
import service from "../../../request/index";
import axios from "axios";
import { ElMessage } from "element-plus";
import { onMounted, computed, ref, reactive } from "vue";
import {
  Iphone,
  Location,
  OfficeBuilding,
  Tickets,
  User,
} from "@element-plus/icons-vue";
var profile = reactive({
  username: "",
  address: "",
  balance: 0,
  password: "",
  phone: "",
  token: null,
  userid: 0,
});
onMounted(() => {
  service
    .get(`/user/id=${localStorage.getItem("userid")}`)
    .then((res) => {
      const data = res.data;
      profile.username = data.username;
      profile.address = data.address;
      profile.balance = data.balance;
      profile.password = data.password;
      profile.phone = data.phone;
      profile.token = data.token;
      profile.userid = data.user_id;
      console.log(profile);
    })
    .catch((err) => {
      ElMessage.error("数据渲染失败，原因：", err);
      console.error("err:", err);
    });
});

const size = ref("");
const iconStyle = computed(() => {
  const marginMap = {
    large: "8px",
    default: "6px",
    small: "4px",
  };
  return {
    marginRight: marginMap[size.value] || marginMap.default,
  };
});
const blockMargin = computed(() => {
  const marginMap = {
    large: "32px",
    default: "28px",
    small: "24px",
  };
  return {
    marginTop: marginMap[size.value] || marginMap.default,
  };
});
</script>

<style>
/* 个人中心样式 */
</style>