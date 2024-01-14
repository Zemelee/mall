<template>
  <div>
    <div v-for="item in renderedProducts.cartProducts" :key="item">
      <img
        v-if="item.product?.picsrc"
        width="10px"
        height="10px"
        :src="item.product?.picsrc"
        alt=""
        srcset=""
      />
    </div>
    {{ renderedProducts.cartProducts[0] }}
  </div>
</template>

<script setup>
import service from "@/request/index";
import { ref, watch, reactive, onBeforeMount, computed } from "vue";
import { useRoute, useRouter } from "vue-router";
import { ElMessage } from "element-plus";
const route = useRoute();
const router = useRouter();
//购物车数据
// {productid: 1, attrid: 2, num: 3}
const cartList = JSON.parse(localStorage.getItem("cartList")) || []; // {productid: 1, attrid: 3, num: 2}
// 请求购物车商品的详细信息
const products = ref([]);
onBeforeMount(() => {
  fetchProducts();
});

// 获取商品列表
const fetchProducts = async () => {
  const uniqueIds = [...new Set(cartList.map((item) => item.productid))];
  console.log("uniqueIds", uniqueIds);
  const productData = await Promise.all(
    uniqueIds.map((id) =>
      service
        .get(`/mall/product/id=${id}`)
        .then((res) => res.data)
        .catch((err) => {
          console.log(err);
        })
    )
  );
  products.value = productData; //请求到的全部数据
};

// 渲染函数 计算属性
const renderedProducts = reactive({
  // 购物车商品 = 购物车数据 + 商品详细数据
  get cartProducts() {
    return cartList.map((item) => {
      //  从请求到的商品数据中，依次遍历购物车里的商品
      const product = products.value.find((p) => p.id === item.productid);
      return {
        num: item.num, //购物车数据
        ...product, //商品详情数据
      };
    });
  },
});
console.log("renderedProducts", renderedProducts);
function goMall() {
  router.push({ name: "mall" });
}
</script>

<style>
</style>