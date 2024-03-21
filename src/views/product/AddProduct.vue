<template>
  <div>
    <h2>商品上新</h2>

  </div>
  <hr>
  <el-form ref="form" :inline="true" :rules="rules" :model="product" label-width="80px">
    <el-form-item label="商品名称" prop="name">
      <el-input v-model="product.name"></el-input>
    </el-form-item>
    <el-form-item label="商品种类" prop="category">
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

    <el-form-item label="最低价" prop="price">
      <el-input-number v-model="product.price" :min="1" :max="99999"></el-input-number>
    </el-form-item>
    <br>
    <el-form-item label="商品图片" prop="picsrc">
      <el-input v-model="product.picsrc"></el-input>
    </el-form-item>
    <el-form-item label="商品描述" prop="description">
      <el-input type="textarea" v-model="product.description"></el-input>
    </el-form-item>
    <el-button type="primary" @click="addAttribution">添加规格</el-button>
    <el-button type="primary" @click="subAttribution">删除规格</el-button>

    <h3>规格设置</h3>
    <hr>
    <div v-for="(attribution, index) in product.attributions" :key="index">
      <el-form-item label="规格名称" :prop="'attributions.' + index + '.attrval'">
        <el-input v-model="attribution.attrval"></el-input>
      </el-form-item>
      <el-form-item label="现有库存" :prop="'attributions.' + index + '.inventory'">
        <el-input-number v-model="attribution.inventory" :min="1" :max="1000"></el-input-number>
      </el-form-item>
      <el-form-item label="差价" :prop="'attributions.' + index + '.more'">
        <el-input-number v-model="attribution.more"></el-input-number>
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
        picsrc: "https://img0.baidu.com/it/u=3570662280,3859669424&fm=253&fmt=auto&app=138&f=PNG?w=500&h=500",
        attributions: [{ attrval: "", inventory: 1, more: 0 }],
      },
      rules: {
        name: [{ required: true, message: '输入商品名称', trigger: 'blur' },],
        price: [{ required: true, message: '输入商品最低价', trigger: 'change' }],
        category: [{ required: true, message: '选择商品种类', trigger: 'change' }],
        description: [
          { required: true, message: '至少一句简单描述', trigger: 'blur' },
          { min: 5, message: '至少5个字符', trigger: 'blur' }
        ],
        picsrc: [{ required: true, message: '图片链接为必填', trigger: 'blur' }],
        'attributions.*.attrval': [{ required: true, message: '规格属性为必填', trigger: 'blur' },],
        'attributions.*.inventory': [{ type: 'number', min: 1, max: 1000, message: '库存应在1-1000之间', trigger: 'blur' }],
        'attributions.*.more': [{ type: 'number', message: '差价必填', trigger: 'blur' }]
      }
    };
  },
  methods: {
    addAttribution() {
      this.product.attributions.push({ attrval: "", inventory: 1, more: 0 });
    },
    subAttribution() {
      if (this.product.attributions.length <= 1) {
        this.$message.warning("至少一个规格");
      } else {
        this.product.attributions.pop();
      }
    },

    submitForm() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          console.log("表单校验通过");
          console.log("this.product", this.product);
          service.post("/mall/product/add", this.product)
            .then((response) => {
              if (response === "ok") {
                this.$message.success("添加成功");
                setTimeout(() => {
                  this.$router.push("/home/all");
                }, 2000);
              }
            })
            .catch((error) => {
              this.$message.error("添加失败", JSON.stringify(error));
            });
        } else {
          console.log("表单校验不通过");
          return false;
        }
      });
    },

  },
};
</script>