<template>
  <div class="home">
    <div class="common-layout">
      <el-container>
        <el-header class="header" style="margin: 0">
          <Header/>
        </el-header>
        <el-container>
          <el-container>
            <el-main>
              <router-view></router-view>
            </el-main>
          </el-container>
        </el-container>
      </el-container>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import Header from "./frame/Header.vue";
import Footer from "./frame/Footer.vue";
import Menu from "./frame/Menu.vue";
export default {
  name: "Home",
  components: {
    Header,
    Footer,
    Menu,
  },
  
};
</script>
<style lang="less" scoped>
.home {
  margin: 0;
  .header {
    margin: 0px;
    height: 10vh;
  }
  .aside {
    height: 100%;
    background-color: #f2f3f5;
  }
  .footer {
    margin: 0px;
  }
}
</style>
