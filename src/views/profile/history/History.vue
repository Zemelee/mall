<template>
  <div>

    <el-table :data="histories" height="550" stripe style="width: 100%">
      <el-table-column prop="order_time" label="Date" width="180">
        <template #default="scope">
          {{ new Date(scope.row.order_time).toISOString().slice(0, 19).replace("T", " ") }}
        </template>
      </el-table-column>
      <el-table-column prop="name" label="Name" width="100" />
      <el-table-column prop="attrval" label="规格" />
      <el-table-column prop="quantity" label="数量" />
    </el-table>
  </div>
</template>
<script setup>
import { ref } from "vue";
import service from "@/request/index.js";
let histories = ref([]);

const userid = localStorage.getItem("userid");
service.get(`/mall/history/get/${userid}`)
  .then((res) => {
    histories.value = res.data; // 更新 ref 数据需访问 value 属性
    console.log(histories.value);
  })
  .catch((err) => {
    console.error(err);
  });
</script>
