<template>
  <div>
    <el-container style="height: 100vh;">


      <el-header style="height: 10%">
        <Header @send-category="shift"></Header>
      </el-header>


      <el-main style="border: 1px solid red;">
        点击右上角头像--智能客服可以体验对话，还可以通过预设对话设置大模型人格
        <br />注意：任何人格嘻嘻
        <div class="products-display">
          <Product v-for="(item, index) in products.list" :product="item" :key="index" @click="goDetails(item.id)" />
        </div>
      </el-main>


      <el-footer class="el-footer">
        <Footer />
      </el-footer>


    </el-container>
  </div>
</template>


<script setup>
import service from "../../request/index.js";
import Header from "./Header.vue";
import Footer from "./Footer.vue";
import Product from "../../components/Product.vue";
import { ref, reactive, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { ElMessage } from "element-plus";

const route = useRoute();
const router = useRouter();
const products = reactive({
  list: [],
});
onMounted(async () => {
  try {
    // 按种类请求product
    const res = await service.get(`/mall/product/category=1`);
    products.list = res.data;
  } catch (err) {
    if (err.response.status == 401) {
      ElMessage.error("非法登录");
      router.push("/login");
    }
  }
});


async function shift(category) {
  // 按种类请求product
  await service
    .get(`/mall/product/category=${category}`)
    .then((res) => {
      products.list = res.data;
    })
    .catch((err) => {
      if (err.response.status == 401) {
        ElMessage.error("非法登录");
        router.push("/login");
      }
    });
}

const goDetails = (id) => {
  router.push({ name: "details", params: { id: id }, target: "_blank" });
}
</script>

<style scoped>
.el-container {
  width: 90vw;
}

.el-footer {
  position: relative;
  background-color: aliceblue;
}

.product {
  width: 20%;
  height: 350px;
  /* 设置产品之间的间距 */
}

.products-display {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}
</style>