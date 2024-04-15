<template>
  <div>
    <div class="userManage">
      <el-table v-loading="loading" :data="orderList" border style="width: 100%"
        @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55">
        </el-table-column>
        <el-table-column prop="user_id" label="用户ID" width="90">
        </el-table-column>
        <el-table-column prop="name" label="商品名称" width="90">
        </el-table-column>
        <el-table-column prop="attrval" label="规格" width="170">
        </el-table-column>
        <el-table-column show-overflow-tooltip prop="quantity" label="数量" width="100">
        </el-table-column>

        <el-table-column prop="price" label="价格" width="90">
        </el-table-column>

        <el-table-column show-overflow-tooltip label="总价" width="100">
          <template #default="scope">
            {{ scope.row.price * scope.row.quantity }}
          </template>
        </el-table-column>

        <el-table-column sortable prop="order_time" label="下单时间" width="200">
          <template #default="scope">
            {{ new Date(scope.row.order_time).toISOString().slice(0, 19).replace("T", " ") }}
          </template>
        </el-table-column>
        <el-table-column show-overflow-tooltip label="订单状态" width="100">
          <template #default="scope">
            <el-tag v-if="scope.row.status == 0" type="danger">未收货</el-tag>
            <el-tag v-else type="success">已收货</el-tag>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button size="mini" :disabled="scope.row.status == 1"
              @click="modifyStatus(scope.row.order_time, scope.row.user_id)">收货</el-button>
            <el-button size="mini" type="danger" @click="delHistory(scope.row.order_time)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import service from "@/request/index.js";
export default {
  data() {
    return {
      orderList: [],
    }
  },
  created() {
    service.post("/mall/history/get", { page: 1, size: 100 }).then(res => {
      this.orderList = res
    })
  },
  methods: {
    delHistory(orderTime) {
      this.$confirm('是否删除此订单记录?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        const isDel = await service.post("/mall/history/delete", orderTime, {
          headers: {
            'Content-Type': 'application/json'
          }
        })
        if (isDel) {
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
          this.orderList = this.orderList.filter(item => item.order_time !== orderTime)
        } else {
          this.$message({
            type: 'error',
            message: `${JSON.parse(isDel)}`
          });
        }
      })
    },
    modifyStatus(orderTime, uid) {
      service.post("/mall/history/modify", { orderTime: orderTime, uid: uid }).then(async (res) => {
        console.log(res)
        if (res) {
          this.orderList = await service.post("/mall/history/get", { page: 1, size: 100 })
          this.$message({
            type: 'success',
            message: '修改成功!'
          });
        }
      })
    }
  }

}
</script>

<style></style>