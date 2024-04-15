<template>
    <div>
        <el-form inline="true" style="width:1000px">
            <el-form-item label="优惠券生成:">
            </el-form-item>
            <el-form-item label="折扣力度">
                <el-select v-model="discount" placeholder="请选择">
                    <el-option label="2折" value="2"></el-option>
                    <el-option label="3折" value="3"></el-option>
                    <el-option label="4折" value="4"></el-option>
                    <el-option label="5折" value="5"></el-option>
                    <el-option label="6折" value="6"></el-option>
                    <el-option label="7折" value="7"></el-option>
                    <el-option label="8折" value="8"></el-option>
                    <el-option label="9折" value="9"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="有效时长">
                <el-select v-model="duration" placeholder="请选择">
                    <el-option label="1天" value="1"></el-option>
                    <el-option label="7天" value="7"></el-option>
                    <el-option label="10天" value="10"></el-option>
                    <el-option label="30天" value="30"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button @click="genCoupon">生成优惠券</el-button>
            </el-form-item>
        </el-form>
        <el-table :data="couponList" border>
            <el-table-column prop="cid" label="优惠码ID" width="100">
            </el-table-column>
            <el-table-column prop="code" label="优惠码" width="200"></el-table-column>
            <el-table-column prop="discount" label="折扣" width="100">
                <template #default="scope">{{ scope.row.discount + "0%" }}</template>
            </el-table-column>
            <el-table-column prop="created" label="创建时间" width="210">
                <template #default="scope">{{ new Date(scope.row.created).toISOString().slice(0, 19).replace("T", " ")
                    }}</template>
            </el-table-column>
            <el-table-column prop="expire" label="过期时间" width="210">
                <template #default="scope">{{ new Date(scope.row.expire).toISOString().slice(0, 19).replace("T", " ")
                    }}</template></el-table-column>
            <el-table-column prop="status" label="使用状态" width="130">
                <template #default="scope">
                    <el-tag :type="scope.row.status == 0 ? 'success' : 'danger'">{{ scope.row.status == 0 ? "未使用" :
                        "已使用" }}</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作">
                <template #default="scope">
                    <el-button @click="showCouponInfo(scope.row.code)" :disabled="scope.row.status == 0">详情</el-button>
                    <el-button @click="delCoupon(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-dialog v-model="dialogVisible" style="width: 400px;">
            <h3>优惠券使用详情</h3>
            <div>优惠券id {{ couponInfo.cid }}</div>
            <div>优惠券折扣 {{ couponInfo.discount }}</div>
            <div>优惠金额￥ {{ couponInfo.discountNum }}</div>
            <div>使用时间 {{ new Date(couponInfo.orderTime).toISOString().slice(0, 19).replace('T', ' ') }}</div>
            <div>使用用户ID {{ couponInfo.uid }}</div>
            <div>使用用户名 {{ couponInfo.uname }}</div>
        </el-dialog>
    </div>
</template>

<script>
import service from "@/request/index.js"
export default {
    data() {
        return {
            discount: 0,
            duration: 0,
            couponList: [],
            dialogVisible: false,
            couponInfo: null
        }
    },
    created() {
        service.get("/coupon/get").then(res => {
            console.log(res);
            this.couponList = res
        })
    },
    methods: {
        genCoupon() {
            if (this.discount == 0 || this.duration == 0) {
                this.$message({
                    message: "请选择折扣力度和有效时长",
                    type: 'warning',
                    duration: 2000
                });
                return;
            }
            service.post("/coupon/gen", { discount: this.discount, duration: this.duration }).then(res => {
                this.$message({
                    message: "优惠券提交成功",
                    type: 'success',
                    duration: 2000
                });
                this.couponList.push(res)
            })
        },
        async showCouponInfo(code) {
        
            this.couponInfo = await service.get(`/coupon/record/${code}`)
            console.log(this.couponInfo)
            this.dialogVisible = true;
        }
    }
}
</script>

<style></style>