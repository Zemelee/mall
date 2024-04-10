<template>
  <div class="menu">
    <el-menu background-color="" :default-active="activeIndex" class="el-menu-demo" mode="horizontal">
      
        <router-link :to="{ path: '/home/echarts' }">
          <el-menu-item index="1-1">首页</el-menu-item>
        </router-link>
      <router-link :to="{ path: '/home/add' }">
        <el-menu-item index="2">
          <el-menu-item index="2-1">上新</el-menu-item>
        </el-menu-item>
      </router-link>
      
      <router-link :to="{ path: '/home/all' }">
        <el-menu-item index="3">
          <el-menu-item index="3-2">全部</el-menu-item>
        </el-menu-item>
      </router-link>
      
      <router-link :to="{ path: '/home/feedback' }">
        <el-menu-item index="4">
          <el-menu-item index="4-1">用户反馈</el-menu-item>
        </el-menu-item>
      </router-link>


      <router-link :to="{ path: '/home/user' }">
        <el-menu-item index="5">
          <el-menu-item index="5-1">用户管理</el-menu-item>
        </el-menu-item>
      </router-link><router-link :to="{ path: '/home/order' }">
        <el-menu-item index="6">
          <el-menu-item index="6-1">订单管理</el-menu-item>
        </el-menu-item>
      </router-link>
    </el-menu>
  </div>
</template>

<script>
// import { ref } from "vue";
export default {
  data() {
    return {
      menus: [],
      activeIndex: "1",
      tableData: [],
      vendorNav: false,
      managerNav: false,
      id: 0,
    };
  }
};
</script>

<style lang="less" scoped>
el-menu {
  width: 900px;

  a {
    text-decoration: none;
  }
}

.layout-container-demo .el-header {
  position: relative;
}

.layout-container-demo .el-aside {
  background-color: #f2f3f5;
}

.layout-container-demo .el-menu {
  border-right: none;
}

.layout-container-demo .el-main {
  padding: 0;
}

.layout-container-demo .toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;
}
</style>