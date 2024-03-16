<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="200px" class="aside">
        <el-menu router :default-active="$route.path" class="el-menu-vertical-demo" @open="handleOpen"
          @close="handleClose">
          <el-menu-item index="/profile/self">
            <i class="el-icon-s-home"></i>
            <template #title>个人中心</template>
          </el-menu-item>
          <el-menu-item index="/profile/history">
            <i class="el-icon-s-home"></i>
            <template #title>历史订单</template>
          </el-menu-item>
          <el-menu-item index="/profile/cart">
            <i class="el-icon-s-home"></i>
            <template #title>购物车</template>
          </el-menu-item><el-menu-item index="/profile/feedback">
            <i class="el-icon-s-home"></i>
            <template #title>反馈</template>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
</script>

<style lang="less" scoped>
.aside {
  position: fixed;
  top: 0;
  left: 0;
  height: 100%;
  background-color: #fff;
}
</style>