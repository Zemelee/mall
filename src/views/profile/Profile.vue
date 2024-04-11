<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="200px" class="aside">
        <el-dropdown style="margin-top: 20px;">
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
        <el-menu router :default-active="$route.path" class="el-menu-vertical-demo">

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
          <el-menu-item index="/">
            <i class="el-icon-s-home"></i>
            <template #title>购物页</template>
          </el-menu-item>
        </el-menu>

      </el-aside>
      <el-main>
        <div class="main">
          <router-view />
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import avatarGif from "@/assets/avatar.gif";
import { useI18n } from "vue-i18n";
import { useRouter } from "vue-router";
const { t, locale } = useI18n();

const router = useRouter();
function newtab(url) {
  router.push({ path: url });
}

const logout = () => {
  router.push("/login");
};
</script>

<style lang="less" scoped>
.aside {
  position: fixed;
  top: 0;
  left: 0;
  height: 100%;
  background-color: #fff;
}

.main {
  padding: 20px;
  border: 1px solid blue;

}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-top: 5px;
  /* 调整垂直居中的位置 */
}
</style>