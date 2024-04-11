<template>
  <div class="header">
    <div class="title">MALL管理系统</div>
    <div>
      <el-container> 
        <Menu></Menu>
      </el-container>
    </div>

    <div style="margin-left: 120px">
      <div style="display: flex">
        <el-popconfirm
          width="220"
          confirm-button-text="退出"
          cancel-button-text="取消"
          confirm-button-type="danger"
          @confirm="this.$router.push('/')"
          icon-color="#626AEF"
          title="确认退出登录？"
        >
          <template #reference>
            <el-avatar
              style="align-self: center; cursor: pointer"
              :size="30"
              src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"
            />
            
          </template>
          
        </el-popconfirm>
        <span>  超级管理员  </span>
      </div>
    </div>
  </div>
</template>

<script>
import Menu from "./Menu.vue";
export default {
  components: {
    Menu,
  },



};
</script>

<style lang="less">
.header {
  margin: 0;
  background-color: #409eff;
  color: #fff;
  line-height: 60px;
  display: flex;
  justify-content: space-between;
  .title {
    width: 200px;
    font-size: 24px;
  }
  el-container {
    width: 900px;
  }
}
</style>