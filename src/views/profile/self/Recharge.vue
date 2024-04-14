<template>
    <div class="recharge-container">
        <h2>用户充值余额</h2>

        <div class="amount-selection">
            <h3>选择充值金额</h3>
            <div class="amount-options">
                <div v-for="amount in amounts" :key="amount" @click="selectAmount(amount)"
                    :class="{ 'selected': selectedAmount === amount }">
                    {{ amount }}
                </div>
            </div>
            <!-- <div class="custom-amount">
          <input v-model="customAmount" type="number" min="1" step="1" placeholder="自定义金额">
          <button @click="selectCustomAmount">确定</button>
        </div> -->
        </div>

        <div class="password-input">
            <label for="password">请输入登录密码：</label>
            <input v-model="password" type="password" id="password">
            <button @click="recharge" :disabled="!selectedAmount && !password">确认充值</button>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import { ElMessage } from "element-plus";
import service from '../../../request';

const amounts = [10, 20, 50, 100];
const selectedAmount = ref(0);
const password = ref('');

const selectAmount = (amount) => {
    selectedAmount.value = amount;
};


const recharge = () => {
    if (!password.value) {
        ElMessage.error('请输入密码');
        return;
    }
    if (password.value != "21") {
        ElMessage.error('密码错误');
        return;
    }
    if (password.value == "21") {
        let recharge = {
            id: localStorage.getItem("userid"),
            amount: selectedAmount.value
        }
        service.post("/user/recharge", recharge).then(res => {
            if (res.data == 1) {
                ElMessage.success('充值成功');
                // 父传子更新数据
            }
        })
    }
};
</script>

<style scoped>
.recharge-container {
    max-width: 400px;
    margin: 0 auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
}

.amount-selection {
    margin-bottom: 20px;
}

.amount-options {
    display: flex;
    justify-content: space-between;
}

.amount-options div {
    cursor: pointer;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
}

.selected {
    background-color: #f0f0f0;
}

.custom-amount {
    margin-top: 10px;
    display: flex;
    align-items: center;
}

.custom-amount input {
    margin-right: 10px;
    width: 100px;
}

.password-input {
    display: flex;
    align-items: center;
}

.password-input label {
    margin-right: 10px;
}
</style>