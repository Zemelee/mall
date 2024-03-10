<template>
  <div class="header">
    <div class="header-left">
      <div class="logo">
        <img src="@/assets/logo.png" class="logo-img" />
        <div style="font-size: 5px">{{ $t("Head.title") }}</div>
      </div>
    </div>
    <div class="header-center">
      <el-button v-for="button in buttons" :key="button.text" :type="button.type"
        :class="{ 'highlighted': button.category === selectedCategory }" @click="shift(button.category)" link>{{
          button.text }}</el-button>
    </div>
    <el-input v-model="searchKey" @keydown.enter="searchProduct" style="width: 150px" :placeholder="$t('Head.search')"
      :prefix-icon="Search" />
    <div class="header-right">
      <el-dropdown>
        <span class="el-dropdown-link">
          {{ $t("Head.switch") }}&nbsp;&nbsp;&nbsp;
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="changeLocale('zh')"> 中 文 </el-dropdown-item>
            <el-dropdown-item @click="changeLocale('en')">English</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
      <el-dropdown>
        <span class="el-dropdown-link">
          <img :src="avatarGif" style="border: 1px solid black" class="avatar" />
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="newtab('/chat')">{{ $t("Head.AIservice") }}</el-dropdown-item>
            <el-dropdown-item @click="newtab('/profile/self')">{{ $t("Head.center") }}</el-dropdown-item>
            <el-dropdown-item>
              <el-popconfirm confirm-button-text="Yes" cancel-button-text="No" :icon="InfoFilled" icon-color="#626AEF"
                title="确认退出?" @confirm="logout">
                <template #reference> {{ $t("Head.logout") }} </template>
              </el-popconfirm>
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>

  </div>
</template>


<script>
import { ref, watch, reactive, computed } from "vue";
import service from "../../request/index.js";
import { useRoute, useRouter } from "vue-router";
import avatarGif from "@/assets/avatar.gif";
import { ArrowDown, InfoFilled, Search } from "@element-plus/icons-vue";
import { useI18n } from "vue-i18n";


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

    const { t, locale } = useI18n();
    // 切换语言
    const changeLocale = (lang) => {
      locale.value = lang;
    };

    const logout = () => {
      router.push("/login");
    };
    function newtab(url) {
      router.push({ path: url });
    }
    //导航栏
    const buttons = computed(() => {
      return [
        { type: "info", category: 1, text: t('Head.category.device') },
        { type: "info", category: 2, text: t('Head.category.food') },
        { type: "info", category: 3, text: t('Head.category.decoration') },
        { type: "info", category: 4, text: t('Head.category.questionNaire') },
        { type: "info", category: 5, text: t('Head.category.peripheral') },
        { type: "info", category: 6, text: t('Head.category.clothes') },
        { type: "info", category: 7, text: t('Head.category.message') },
        { type: "info", category: 8, text: t('Head.category.secondHand') },
        { type: "info", category: 9, text: t('Head.category.discount') },
        { type: "info", category: 10, text: t('Head.category.casual') },
      ];
    })

    //切换id
    function shift() {
      //自定义事件-数据 
      context.emit("send-category", category);
    }
    // 搜索商品
    const searchKey = ref("");
    function searchProduct() {
      context.emit("send-keyword", searchKey);

    }


    return {
      breadcrumbs,
      avatarGif,
      ArrowDown,
      InfoFilled,
      logout,
      newtab,
      buttons,
      shift,
      changeLocale,
      searchKey,
      searchProduct,

    };
  },
};
</script>

<style lang="less" scoped>
.highlighted {
  background-color: #409eff; // 高亮时的背景颜色
  color: #ffffff; // 高亮时的文字颜色
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header-left {
  display: flex;
  /* 使用flex来控制内部div */
  width: 10%;

  .logo {
    margin-right: 10px;
    /* 调整logo和breadcrumb之间的间距 */
    width: 80px;

    .logo-img {
      width: 80px;
      height: 40px;
    }
  }
}

.breadcrumb {
  flex-grow: 1;
  /* 允许breadcrumb占据剩余空间 */
  text-align: center;
  /*水平居中*/
  align-items: center;
  /*垂直居中*/
  display: flex;
  /*使用flex布局 */
}

.active {
  background-color: yellow;
}

.header-right {
  display: flex;
  align-items: center;
  width: 10%;
  justify-content: flex-end;
  /* 将内容推到右侧 */
  height: 100%;
  position: relative;

  /* 添加相对定位 */
  .avatar {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    margin-top: 5px;
    /* 调整垂直居中的位置 */
  }
}

.example-showcase .el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}
</style>