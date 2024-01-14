<template>
  <div class="header">
    <div class="header-left">
      <div class="logo">
        <img src="../../../public/logo.png" class="logo-img" />
        <div style="font-size: 5px">皆有杂货铺</div>
      </div>
      <div class="breadcrumb">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item
            v-for="breadcrumb in breadcrumbs"
            :key="breadcrumb.text"
            :to="breadcrumb.to"
          >
            {{ breadcrumb.text }}
          </el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <div class="header-center">
      <el-button
        v-for="button in buttons"
        :key="button.text"
        :type="button.type"
        @click="shift(button.category)"
        link
        >{{ button.text }}</el-button
      >
    </div>

    <div class="header-right">
      <el-dropdown>
        <span class="el-dropdown-link">
          <img
            :src="avatarGif"
            style="border: 1px solid black"
            class="avatar"
          />
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="newtab('/chat')"
              >智能客服</el-dropdown-item
            >
            <el-dropdown-item @click="newtab('profile/self')"
              >个人中心</el-dropdown-item
            >
            <el-dropdown-item>
              <el-popconfirm
                confirm-button-text="Yes"
                cancel-button-text="No"
                :icon="InfoFilled"
                icon-color="#626AEF"
                title="确认退出?"
                @confirm="logout"
              >
                <template #reference> 退出登录 </template>
              </el-popconfirm>
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>


<script>
import { ref, watch, reactive, defineEmits, onMounted } from "vue";
import service from "../../request/index.js";
// import Screenfull from "../../components/Screenfull.vue";
import { useRoute, useRouter } from "vue-router";
import avatarGif from "../../assets/avatar.gif";
import { ArrowDown, InfoFilled } from "@element-plus/icons-vue";
export default {
  // 组件的 props 上下文对象
  setup(props, context) {
    const route = useRoute();
    const router = useRouter();
    const breadcrumbs = ref([]);
    watch(route, generateBreadcrumbs);
    function generateBreadcrumbs() {
      breadcrumbs.value = [];
      route.matched.forEach((matchedRoute) => {
        if (matchedRoute.name) {
          breadcrumbs.value.push({
            text: matchedRoute.name,
            to: matchedRoute.path,
          });
        }
      });
    }
    const logout = () => {
      router.push("/login");
    };
    function newtab(url) {
      router.push({ path: url });
    }
    //导航栏
    const activeButton = ref({ value: null });
    const buttons = [
      { type: "info", category: 1, text: "手机" },
      { type: "info", category: 2, text: "食品" },
      { type: "info", category: 3, text: "装饰" },
      { type: "info", category: 4, text: "问卷" },
      { type: "info", category: 5, text: "周边" },
      { type: "info", category: 6, text: "衣服" },
      { type: "info", category: 7, text: "信息" },
      { type: "info", category: 8, text: "二手" },
      { type: "info", category: 9, text: "超低价" },
      { type: "info", category: 10, text: "随便逛逛" },
    ];
    const products = reactive({
      list: [],
    });
    //切换id
    function shift(category) {
      context.emit("send-category", category);
    }
    onMounted(()=>{
      console.log(1);
    })
      

    return {
      breadcrumbs,
      avatarGif,
      ArrowDown,
      InfoFilled,
      // Screenfull,
      logout,
      newtab,
      activeButton,
      buttons,
      shift,
    };
  },
};
</script>

<style lang="less" scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header-left {
  display: flex; /* 使用flex来控制内部div */
  width: 20%;
  .logo {
    margin-right: 10px; /* 调整logo和breadcrumb之间的间距 */
    width: 80px;
    .logo-img {
      width: 80px;
      height: 40px;
    }
  }
}

.breadcrumb {
  flex-grow: 1; /* 允许breadcrumb占据剩余空间 */
  text-align: center; /*水平居中*/
  align-items: center; /*垂直居中*/
  display: flex; /*使用flex布局 */
}

.active {
  background-color: yellow;
}

.header-right {
  display: flex;
  align-items: center;
  width: 10%;
  justify-content: flex-end; /* 将内容推到右侧 */
  height: 100%;
  position: relative; /* 添加相对定位 */
  .avatar {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    margin-top: 5px; /* 调整垂直居中的位置 */
  }
}

.example-showcase .el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}
</style>