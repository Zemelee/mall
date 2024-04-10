<template>
  <div>

    <el-table :data="histories" height="550" stripe style="width: 100%">
      <el-table-column type="selection" width="55" />

      <el-table-column prop="name" label="商品名" width="100" />
      <el-table-column prop="attrval" label="规格" width="120" />
      <el-table-column prop="quantity" label="数量" width="80" />
      <el-table-column label="小计" width="130">
        <template #default="scope">
          ￥{{ scope.row.price * scope.row.quantity }}
        </template>
      </el-table-column>
      <el-table-column prop="order_time" label="下单时间" width="180">
        <template #default="scope">
          {{ new Date(scope.row.order_time).toISOString().slice(0, 19).replace("T", " ") }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="120">
        <template #default="scope">
          <el-button type="danger" size="mini" @click="delByTime(scope.row.order_time)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script setup>
import { ref } from "vue";
import service from "@/request/index.js";
import { ElMessage, ElMessageBox } from 'element-plus'
let histories = ref([]);

const userid = localStorage.getItem("userid");
service.get(`/mall/history/get/${userid}`)
  .then((res) => {
    histories.value = res.data; // 更新 ref 数据需访问 value 属性
    console.log(histories.value);
    // service.post("/mall/history/delete", histories.value[0].order_time, {
    //   headers: { 'Content-Type': 'application/json' }
    // })
    //   .then(res => {
    //     console.log(res)
    //   })
  })
  .catch((err) => {
    console.error(err);
  });

const delByTime = (time) => {
  ElMessageBox.confirm(
    `确认删除此订单记录吗?`,
    'Warning',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(
    async () => {
      const isDel = await service.post('/mall/history/delete', time, {
        headers: { 'Content-Type': 'application/json' }
      })
      if (isDel) {
          ElMessage({
            type: 'success',
            message: '删除成功!'
          });
          service.get(`/mall/history/get/${userid}`)
            .then((res) => {
              histories.value = res.data; // 更新 ref 数据需访问 value 属性
            })
            .catch((err) => {
              console.error(err);
            });
      }
    }





  )

}


</script>
