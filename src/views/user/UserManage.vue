<template>
  <div class="userManage">
    <el-table v-loading="loading" :data="userList" border style="width: 100%"
      :default-sort="{ prop: 'submitTime', order: 'descending' }" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column prop="user_id" label="用户ID" width="90">
      </el-table-column>
      <el-table-column prop="username" label="用户昵称" width="90">
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="phone" label="电话" width="150">
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="address" label="E-mail" width="150">
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="balance" label="余额" width="150">
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="accumulated" label="累计消费" width="100">
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="accumulated" label="会员等级" width="100">
        <template #default="scope">
          <!-- 假设scope.row.status表示状态，已处理为'handled' -->
          <el-tag :type="scope.row.accumulated > 10000 ? '' : 'info'">{{ scope.row.accumulated > 10000 ? '会员' : '用户'
            }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="200">
        <template #default="scope">
          <el-button size="mini" @click="checkHistory(scope.row.user_id)">订单记录</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="showOrderHistory" title="订单记录" width="500" center>
      <!-- <span>
        It should be noted that the content will not be aligned in center by
        default
      </span> -->
      <el-table :data="orderHistoryList">
        <el-table-column type="selection" width="55">
        </el-table-column>
        <el-table-column prop="userid" label="用户ID" width="90">
        </el-table-column>
        <el-table-column prop="name" label="商品名称" width="90">
        </el-table-column>
        <el-table-column prop="attrval" label="规格" width="170">
        </el-table-column>
        <el-table-column prop="quantity" label="数量" width="100">
        </el-table-column>

        <el-table-column prop="price" label="价格" width="90">
        </el-table-column>

        <el-table-column show-overflow-tooltip label="总价" width="100">
          <template #default="scope">
            {{ scope.row.price * scope.row.quantity }}
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import service from "@/request/index.js";
export default {
  data() {
    return {
      userList: [],
      columns: [],
      loading: false,
      showOrderHistory: false,
      orderHistoryList: []
    }
  },
  async created() {
    this.userList = await service.get("/user/get")
    console.log(this.userList)
    this.columns = this.userList.map(item => {
      return {
        title: item.name,
        key: item.name,
        width: 100,
        fixed: 'left'
      }
    })
    console.log(this.columns)
  },
  methods: {
    checkHistory(uid) {
      service.get(`/mall/history/get/${uid}`).then(res => {
        console.log(res)
        this.orderHistoryList = res;
        this.showOrderHistory = true;
      })
    }
  }
}
</script>

<style></style>