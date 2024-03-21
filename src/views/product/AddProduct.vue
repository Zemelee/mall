<template>
  <div>
    <h2>商品上新</h2>

  </div>
  <hr>
  <el-form ref="form" :inline="true" :model="product" label-width="80px">
    <el-form-item label="商品名称">
      <el-input v-model="product.name"></el-input>
    </el-form-item>
    <el-form-item label="商品种类">
      <el-select v-model="product.category" placeholder="选择种类">
        <el-option label="电子设备" value="1"></el-option>
        <el-option label="食品" value="2"></el-option>
        <el-option label="装饰" value="3"></el-option>
        <el-option label="问卷" value="4"></el-option>
        <el-option label="周边" value="5"></el-option>
        <el-option label="衣服" value="6"></el-option>
        <el-option label="信息" value="7"></el-option>
        <el-option label="二手" value="8"></el-option>
        <el-option label="超低价" value="9"></el-option>
      </el-select>
    </el-form-item>

    <el-form-item label="最低价">
      <el-input-number v-model="product.price" :min="1" :max="10000"></el-input-number>
    </el-form-item>
    <br>
    <el-form-item label="商品图片">
      <el-input v-model="product.picsrc"></el-input>
    </el-form-item>
    <el-form-item label="商品描述">
      <el-input type="textarea" v-model="product.description"></el-input>
    </el-form-item>
    <el-button type="primary" @click="addAttribution">添加规格</el-button>
    <el-button type="primary" @click="subAttribution">删除规格</el-button>

    <h3>规格设置</h3>
    <hr>
    <div v-for="(attribution, index) in product.attributions" :key="index">
      <el-form-item label="规格名称">
        <el-input v-model="attribution.attrval"></el-input>
      </el-form-item>
      <el-form-item label="现有库存">
        <el-input-number v-model="attribution.inventory" :min="1" :max="1000"></el-input-number>
      </el-form-item>
      <el-form-item label="差价">
        <el-input-number v-model="attribution.more" :min="0" :max="10000"></el-input-number>
      </el-form-item>
    </div>
    <el-form-item>
      <el-button type="primary" @click="submitForm">立即创建</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import service from "@/request/index.js";

export default {
  data() {
    return {
      product: {
        name: "",
        price: 0,
        category: 0,
        description: "",
        picsrc: "",
        attributions: [{ attrval: "", inventory: 0, more: 0 }],
      }
    };
  },
  methods: {
    addAttribution() {
      this.product.attributions.push({ attrval: "", inventory: 0, more: 0 });
    },
    subAttribution() {
      this.product.attributions.pop();
    },

    submitForm() {
      console.log("this.product", this.product);
      service
        .post("/mall/product/add", this.product)
        .then((response) => {
          if (response == "ok"){
              this.$message.success("添加成功");
              setTimeout(() => {
                this.$router.push("/home/all");
              }, 2000);
          }
        })
        .catch((error) => {
        });
    },
  },
};
</script>