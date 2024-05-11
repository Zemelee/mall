<template>


  <el-descriptions class="margin-top" title="基本信息" :column="3" :size="size" border>
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
      {{ profile.balance }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          <el-icon :style="iconStyle">
            <location />
          </el-icon>
          累计消费
        </div>
      </template>
      {{ profile.accumulated }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          <el-icon :style="iconStyle">
            <location />
          </el-icon>
          等级
        </div>
      </template>
      <el-tag size="small">
        <span v-if="profile.accumulated < 1000">普通会员</span>
        <span v-else-if="profile.accumulated >= 1000">黄金会员</span>
        <span v-else-if="profile.accumulated >= 5000">黑金会员</span>
      </el-tag>
    </el-descriptions-item>
  </el-descriptions>

  <el-button @click="showUpdate = true">修改</el-button>
  <el-button @click="showRecharge = true">充值</el-button>
  <el-dialog style="width: 500px" v-model="showUpdate">
    <updateInfo :info="profile" @updateData="handleUpdateInfo"></updateInfo>
  </el-dialog>
  <el-dialog style="width: 500px" v-model="showRecharge">
    <Recharge></Recharge>
  </el-dialog>
  <br>
  <br>
</template>



<script setup>
import service from "@/request/index";
import { ElMessage } from "element-plus";
import updateInfo from './updateInfo'
import Recharge from './Recharge'
import { onMounted, computed, ref, reactive } from "vue";
import {
  Iphone,
  Location,
  OfficeBuilding,
  Tickets,
  User,
} from "@element-plus/icons-vue";
const profile = reactive({
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
      profile.accumulated = data.accumulated;
    })
    .catch((err) => {
      ElMessage.error("数据渲染失败:", JSON.stringify(err));
    });
});
const handleUpdateInfo = (data) => {
  profile.username = data.username;
  profile.address = data.address;
  profile.balance = data.balance;
  profile.password = data.password;
  profile.phone = data.phone;
  showUpdate.value = false;
};


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

// 更新用户信息
const showUpdate = ref(false);
const showRecharge = ref(false);




</script>

<style scoped></style>