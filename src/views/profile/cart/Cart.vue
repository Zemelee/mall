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
    <el-dialog v-model="showPay">
      当前订单总价：{{ totalPrice.toFixed(2) }} <span v-if="discountNum != 0">- {{ discountNum.toFixed(2) }}
        = {{ (totalPrice - discountNum).toFixed(2) }}</span>元
      <br>
      <el-form>
        <el-form-item label="请输入支付密码">
          <el-input v-model="payPassword" show-password></el-input>
        </el-form-item>
        <el-form-item label="有优惠码?">
          <el-switch v-model="useCoupon" inline-prompt active-text="是" inactive-text="否" />
        </el-form-item>
        <el-form-item v-show="useCoupon" label="优惠码">
          <el-input placeholder="请输入优惠码" :disabled="validateDisabled" v-model="couponCode"></el-input>
        </el-form-item>
        <el-form-item v-show="useCoupon" label="验证优惠码">
          <el-button :disabled="validateDisabled" @click="validateCoupon">验证</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :disabled="!payPassword" @click="pay"
            v-loading.fullscreen.lock="fullscreenLoading">确认支付</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";
import service from "@/request/index";
import { debounce } from "@/utils/debounce";
import { ref, computed, h } from "vue";
import {md5} from "@/utils/md5";
import { useCartStore } from "@/store/cart.js";
import { ElMessage, ElMessageBox, ElLoading } from 'element-plus'
const fullscreenLoading = ref(false)
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
const submitOrder = debounce(function () {
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
}, 500)
let useCoupon = ref(false)
let payPassword = ref(null);
let couponCode = ref("");
let discountNum = ref(0);
let validateDisabled = ref(false)
const validateCoupon = () => {
  if (couponCode.value) {
    service.get(`coupon/validate/${couponCode.value}`).then(couponObj => {
      console.log(couponObj.data)
      if (couponObj.data == "") {
        ElMessage.error("优惠券无效")
        return;
      } else if (new Date(couponObj.data.expire) <= new Date()) {
        ElMessage.error("优惠券已过期")
        return;
      } else if (couponObj.data.status === 1) {
        ElMessage.error("优惠券已使用")
        return;
      }
      discountNum.value = totalPrice.value - (totalPrice.value * (couponObj.data.discount) / 10);
      totalPrice.value = totalPrice.value * (couponObj.data.discount) / 10;
      ElMessage.success(`${couponObj.data.discount}折优惠券验证成功,已优惠${discountNum.value.toFixed(2)}元!`)
      validateDisabled.value = true;
    })
  }

};
const open = () => {
  ElMessageBox.confirm(
    `确认提交此订单吗?`,
    'Warning',
    {
      confirmButtonText: '提交',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(() => {
      showPay.value = true;
    }).catch(() => {

    })
}


const pay = async () => {
  fullscreenLoading.value = true
  const loginResponse = await service.post("/user/login", {
    username: localStorage.getItem("username"),
    password: md5(payPassword.value)
  })
  console.log(loginResponse)
  if (!loginResponse.data.success) {
    fullscreenLoading.value = false
    ElMessage.error("密码错误");
    return;
  }
  const orderItems = []
  const orderTime = new Date();
  selectedItems.value.forEach(item => {
    orderItems.push({
      userid: userid,
      product_id: item.productid,
      name: item.name,
      attrval: item.attrval,
      attrid: item.attrid,
      quantity: item.num,
      price: item.price,
      order_time: orderTime
    })
  })
  // 验证库存是否充足
  let IQS = orderItems.map(orderItem => ({
    attrid: orderItem.attrid,
    quantity: orderItem.quantity
  }));
  //IQS      ----  [{attrid: 11, quantity: 2},{attrid: 11, quantity: 2}]
  //inventory----  {1:true,5:false}
  let inventory = await service.post("/mall/product/inventory", IQS);
  for (let i = 0; i < IQS.length; i++) {
    let attrid = IQS[i].attrid;  //前端传的attrid
    if (!inventory.data[attrid]) {  //不是用下标获取，是通过键值获取
      fullscreenLoading.value = false
      let notFull = orderItems.find(item => item.attrid === attrid); //找到库存不足的商品
      ElMessage.error(`${notFull.name} - ${notFull.attrval}库存不足`);
      return;
    }
  }
  let consume = await service.post("/user/consume", {
    id: userid,
    amount: totalPrice.value,
  });
  fullscreenLoading.value = false
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
        }, 1500);
      })
      .catch((err) => {
        console.log(err);
      });
    // 如果 validateDisabled 为true，说明使用了优惠券，则调用优惠券接口
    if (validateDisabled.value) {
      service.post("/coupon/use", {
        uid: userid,
        code: couponCode.value,
        time: orderTime,
        discountNum: discountNum.value
      }).then((res) => {
        if (res.data) {
          ElMessage.success("优惠券使用成功")
        } else {
          ElMessage.error("优惠券使用失败")
        }
      })
    }
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