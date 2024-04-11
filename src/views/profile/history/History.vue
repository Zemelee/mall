<template>
  <div>

    <el-table :default-sort="{ prop: 'order_time', order: 'descending' }" :data="histories" height="550"
      style="width: 100%" border >
      <el-table-column type="selection" width="55">

      </el-table-column>
      <el-table-column type="index" label="序号" width="70">
        <template #default="scope">
          {{ scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="name" label="商品名" width="100" />
      <el-table-column prop="attrval" label="规格" width="120" />
      <el-table-column prop="quantity" label="数量" width="80" />
      <el-table-column label="小计" width="130">
        <template #default="scope">
          ￥{{ (scope.row.price * scope.row.quantity).toFixed(2) }}
        </template>
      </el-table-column>
      <el-table-column prop="order_time" sortable label="下单时间&nbsp;&nbsp;&nbsp; | &nbsp;&nbsp;&nbsp;操作" width="280">
        <template #default="scope">
          {{ new Date(scope.row.order_time).toISOString().slice(0, 19).replace("T", " ") }} &nbsp; &nbsp; &nbsp;
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


// const spanMethod = ({ row, column, rowIndex, columnIndex }) => {
//   if (columnIndex === 6) { // 判断是否是“下单时间”列
//     const orders = histories;
//     const prevRow = orders.value[rowIndex - 1];
//     const nextRow = orders.value[rowIndex + 1];
//     console.log("rowIndex:", rowIndex)
//     if (
//       // (prevRow && row.order_time === prevRow.order_time) || // 当前行的 order_time 与上一行相同
//       (nextRow && row.order_time == nextRow.order_time)    // 当前行的 order_time 与下一行相同
//     ) {
//       return {
//         rowspan: 0,
//         colspan: 0
//       };
//     } else {
//       return {
//         rowspan: 2,
//         colspan: 1,
//       };
//     }
//   }
// };


const userid = localStorage.getItem("userid");
service.get(`/mall/history/get/${userid}`)
  .then((res) => {
    histories.value = res.data; // 更新 ref 数据需访问 value 属性
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
