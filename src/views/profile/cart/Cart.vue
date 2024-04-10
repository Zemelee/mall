<template>
  <div>
    <h3>MALL-CART</h3>
    <strong>商品名</strong>&nbsp;&nbsp;
    <strong>商品规格</strong>&nbsp;&nbsp;
    <strong>商品单价</strong>&nbsp;&nbsp;
    <strong>商品数量</strong>&nbsp;&nbsp;&nbsp;&nbsp;
    <strong>小计</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <strong>管理</strong>
    <main>
      <form @submit.prevent="submitOrder">
        <div v-for="product in Cart.products" :key="product.id">
          <div v-for="attr in product.attributions" :key="attr.id">
            <div v-if="attr.num" class="product">
              <div>
                <input type="checkbox"
                  :value="{ productid: product.id, name: product.name, attrid: attr.id, attrval: attr.attrval, inventory: attr.inventory, num: attr.num, price: attr.more + product.price }"
                  v-model="selectedItems" />
                <img :src="product.picsrc" :alt="product.description" />
              </div>
              <div class="product-info"> {{ product.name }} </div>
              <div class="product-info">{{ attr.attrval }}</div>
              <div class="product-info">{{ (attr.more + product.price).toFixed(2) }}</div>
              <div class="product-info">{{ attr.num }}</div>
              <div class="product-info">{{ (attr.num * (product.price + attr.more)).toFixed(2) }}</div>

              <br>
              <div class="product-action">
                <el-button class="add" @click="add(attr.id)">+</el-button>
                <el-button class="sub" @click="sub(attr.id)">-</el-button>
                <el-button class="sub" @click="del(attr.id)">删除</el-button>
              </div>
            </div>

          </div>
        </div>
        <div class="total">
          <span>总价</span>
          <span>{{ totalPrice.toFixed(2) }}</span>
        </div>
        <el-button type="submit" @click="submitOrder">提交订单</el-button>
      </form>
    </main>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";
import service from "@/request/index";
import { ref, computed } from "vue";
import { useCartStore } from "@/store/cart.js";
import { ElMessage, ElMessageBox } from 'element-plus'
const router = useRouter();
const userid = localStorage.getItem("userid");
const Cart = useCartStore();
const cartList = Cart.cartList  // 商品、属性、数量
const ids = [...new Set(cartList.map((item) => item.productid))];
// 获取商品列表
Cart.fetchProducts(ids).then(() => {
  //获取到了购物车的详细数据数据products
  Cart.updateProducts(cartList)
});

const add = (attrid) => {
  const item = selectedItems.value.find(item => item.attrid === attrid);
  if (!item) {
    ElMessage.warning("未选中!");
    return;
  }
  if (item && item.num < item.inventory) {
    item.num += 1;
    Cart.addProduct(attrid);
  }
  else if (item && item.num >= item.inventory) {
    ElMessage.warning("库存不足");
  }
};
const sub = (attrid) => {
  const item = selectedItems.value.find(item => item.attrid === attrid)
  if (!item) {
    ElMessage.warning("未选中!");
    return;
  }
  if (item && item.num > 1) {
    Cart.subtractProduct(attrid);
    item.num -= 1;
  } else if (item && item.num === 1) {
    ElMessage.warning("数量已减至1!");
  }
};
const del = (attrid) => {
  Cart.deleteProduct(attrid);
  selectedItems.value = selectedItems.value.filter(item => item.attrid !== attrid);
};

const totalPrice = computed(() => {
  let sum = 0;
  selectedItems.value.forEach(item => {
    // 假设item对象中有一个price属性代表单价
    sum += item.num * item.price; // 这里假设product是通过id从products数据中获取的商品对象，包含price属性
  });
  return sum;
});


const selectedItems = ref([]);
const showPay = ref(false);
const submitOrder = () => {
  if (selectedItems.value.length === 0) {
    ElMessage.warning("请选择商品");
    return;
  }
  service
    .get(`/user/id=${userid}`)
    .then((res) => {
      if (!res.data.address || !res.data.phone) {
        ElMessage.warning("先完善个人信息");
        setTimeout(() => {
          router.push("/profile/self");
        }, 1000);
        return
      }
    })
  open()
};

const open = () => {
  ElMessageBox.confirm(
    `总价格为${totalPrice.value.toFixed(2)},确认提交订单吗?`,
    'Warning',
    {
      confirmButtonText: '提交',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(() => {
      ElMessageBox.prompt('输入支付密码', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
      })
        .then(async ({ value }) => {
          const loginResponse = await service.post("/user/login", {
            username: localStorage.getItem("username"),
            password: value
          })
          console.log(loginResponse.data);
          if (loginResponse.data.success) {
            pay()
          } else {
            console.log(localStorage.getItem("username"),value)
            ElMessage.error("支付密码错误")
          }
        })
        .catch(() => {
          ElMessage({
            type: 'info',
            message: '取消支付',
          })
        })
    })
}


const pay = async () => {
  const orderItems = []
  selectedItems.value.forEach(item => {
    orderItems.push({
      userid: userid,
      product_id: item.productid,
      name: item.name,
      attrval: item.attrval,
      attrid: item.attrid,
      quantity: item.num,
      price: item.price,
      order_time: new Date().getTime()
    })
  })
  let consume = await service.post("/user/consume", {
    id: userid,
    amount: totalPrice.value,
  });
  if (!consume.data.status) {
    ElMessage.error("余额不足");
    return;
  } else {
    // 扣费成功，增加订单记录
    service
      .post(`/mall/history/add`, orderItems)
      .then((res) => {
        ElMessage.success("支付成功");
        showPay.value = false;
        selectedItems.value = [];
        setTimeout(() => {
          router.push("/profile/history");
        }, 3000);
      })
      .catch((err) => {
        console.log(err);
      });
  }

};


</script>

<style lang="less" scoped>
img {
  width: 75px;
  height: 75px;
}

.product {
  display: flex;
  border: 1px solid rgb(18, 11, 11);
  justify-content: space-between;

  width: 100%;

  .product-info {
    width: 50%;
    margin: 1px;
    border: 1px solid rgb(13, 96, 34);
  }

  .product-action {
    width: 90%;
    margin: auto;
  }
}
</style>