<template>
  <div class="social-signup-container">
    暂不接受新用户注册
    <br>
    Not accepting new user registration at the moment
    </div>
</template>

<script>
// import openWindow from '@/utils/open-window'

export default {
  name: "SocialSignin",
  methods: {
    wechatHandleClick() {
      alert("ok");
    },
    tencentHandleClick() {
      alert("ok");
    },
  },
};
</script>

<style lang="less" scoped>
.social-signup-container {
  margin: 20px 0;
  .sign-btn {
    display: inline-block;
    cursor: pointer;
  }
  .icon {
    color: #fff;
    font-size: 24px;
    margin-top: 8px;
  }
  .wx-svg-container,
  .qq-svg-container {
    display: inline-block;
    width: 40px;
    height: 40px;
    line-height: 40px;
    text-align: center;
    padding-top: 1px;
    border-radius: 4px;
    margin-bottom: 20px;
    margin-right: 5px;
  }
  .wx-svg-container {
    background-color: #24da70;
  }
  .qq-svg-container {
    background-color: #6ba2d6;
    margin-left: 50px;
  }
}
</style>
