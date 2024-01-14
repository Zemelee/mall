<template>
  <el-row class="tac">
    <el-col :span="24">
      <h5 class="mb-2">皆有杂货铺</h5>
      <el-menu
        default-active="2"
        class="el-menu-vertical-demo"
      >
        <el-sub-menu index="1">
          <template #title>
            <el-icon><icon-menu /></el-icon>
            <span>商品分类</span>
          </template>
          <el-sub-menu index="1-1">
            <template #title>1</template>
            
            <router-link to="/mall/test">
              <el-menu-item index="1-1-1">Test</el-menu-item>
            </router-link>
          </el-sub-menu>
          <el-sub-menu index="1-2">
            <template #title>2</template>
            <el-menu-item index="1-2-1">121</el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="1-3">
            <template #title>3</template>
            <el-menu-item index="1-3-1">131</el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="1-4">
            <template #title>4</template>
            <el-menu-item index="1-4-1">141</el-menu-item>
          </el-sub-menu>
        </el-sub-menu>
        
      </el-menu>
    </el-col>
  </el-row>
</template>

<script setup>
import {
  Document,
  Menu as IconMenu,
  Location,
  Setting,
} from "@element-plus/icons-vue";

</script>
<style scoped>
.router-link-active {
  text-decoration: none;
  color: #000;
}
.normal-link {
  text-decoration: none;
  color: #000;
}
</style>