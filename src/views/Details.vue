<template>
  <div>

    <div v-if="details">
      <h1>{{ details.name }}</h1>
      <div>
        <img style="width: 100px; height: 100px" :src="details.picsrc" />
      </div>
      <p>{{ details.description }}</p>
      <!-- 将 id 的值和 attrid 绑定-->
      <el-select v-model="attrid" placeholder="选择规格">
        <el-option v-for="attr in details.attributions" :key="attr.id" :label="attr.attrval" :value="attr.id" />
      </el-select>
      <!-- 通过 find 方法和 id 匹配来获取对应的 attributions 对象 -->
      <div>
        {{
          attrid == 0
          ? details.price
          : details.price +
          details.attributions.find((a) => a.id === attrid).more
        }}
      </div>
      <el-input-number v-model="numSelect" :min="1" :max="10" />
    </div>

    <div>
      <el-button @click="addCart"> 加入购物车 </el-button>
    </div>
    <div>
      <el-button @click="router.push('/cart')"> 去购物车 </el-button>
    </div>
  </div>
</template>

<script setup>
import service from "../request/index.js";
import { ref, watch, reactive, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { ElMessage } from "element-plus";
const route = useRoute();
const router = useRouter();

const details = ref(null);
onMounted(async () => {
  //route关注当前路由的参数，路径
  details.value = await getById(route.params.id);
});

const attrid = ref(0); // 用户选择
// watch(attrid, (n, o) => {
//   console.log(n);
// });
const numSelect = ref(1); // 数量选择

async function getById(id) {
  return service
    .get(`/mall/product/id=${id}`)
    .then((res) => {
      console.log(res.data);
      return res.data;
    })
    .catch((err) => {
      console.log("err:", err);
    });
}

// 防抖函数
let cartTimeout;
function addCart() {
  clearTimeout(cartTimeout);
  cartTimeout = setTimeout(() => {
    if (attrid.value == 0) {
      ElMessage.error("未选择规格");
      return;
    }
    const newGoods = {
      productid: Number(route.params.id),
      attrid: attrid.value,
      num: numSelect.value,
    };
    console.log("newGoods:", newGoods);
    let cartList = JSON.parse(localStorage.getItem("cartList")) || [];
    // 查找购物车是否已存在该商品(判断商品ID + 规格ID)
    let productInCart = cartList.find((item) => {
      return (
        item.productid === newGoods.productid && item.attrid === newGoods.attrid
      );
    });
    // 有就直接+1，否则添加到购物车
    if (productInCart) {
      productInCart.num += newGoods.num;
      ElMessage.success("添加成功");
    } else {
      cartList.push(newGoods);
      ElMessage.success("添加成功");
    }

    localStorage.setItem("cartList", JSON.stringify(cartList));
  }, 300);
}
</script>

<style></style>