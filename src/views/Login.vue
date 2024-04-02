<template>
  <div class="login">
    <div class="up">
      <el-card>
        <h2 style="margin-bottom: 20px 30px 50px 30px">MALL后台管理系统</h2>

        <el-form label-position="left" :model="user" class="demo-form-inline">
          <el-form-item>
            <el-input prefix-icon="User" v-model="user.username" placeholder="请输入账号">
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-input prefix-icon="Lock" v-model="user.password" placeholder="请输入密码">
            </el-input>
          </el-form-item>
          <el-form-item>
            <div class="button1">
              <el-button type="primary" @click="submit">管理员登录</el-button>
            </div>
          </el-form-item>
        </el-form>
      </el-card>
    </div>

    <div class="footer">
      <el-divider></el-divider>
      <div>
        <div style="display: flex">
          <span style="align-self: center; margin: 10px">合作伙伴</span>
          <span>
            <a href="http://www.sicnu.edu.cn/" target="_blank" title="四川师范大学">
              <img id="imgConac" alt="SICNU" style="width: 120px; height: 40px" vspace="0" hspace="0" border="0"
                src="https://img2.baidu.com/it/u=1444793896,2136642666&fm=253&fmt=auto&app=138&f=JPEG?w=1034&h=380"
                data-bd-imgshare-binded="1" />
            </a>
            <a style="margin-left: 10px" href="http://www.chengdu.gov.cn/" target="_blank" title="成都市政府">
              <img id="imgConac" alt="成都市政府" style="width: 120px; height: 40px" vspace="0" hspace="0" border="0"
                src="http://www.58eventer.com/uploadfile/20201022/17311412.jpg" data-bd-imgshare-binded="1" />
            </a>
            <a style="margin-left: 10px" href="https://www.huawei.com/cn/?ic_medium=direct&ic_source=surlent"
              target="_blank" title="HUAWEI">
              <img id="imgConac" alt="HUAWEI" style="width: 120px; height: 40px" vspace="0" hspace="0" border="0"
                src="http://www.doubleflyer.com/static/images/friendship_link/huawei.png" data-bd-imgshare-binded="1" />
            </a>
          </span>
        </div>

        <div style="background-color: #e6e8eb">
          <span style="margin: 2px 10px">主办单位：四川师范大学 </span>

          <span style="margin: 2px 10px">电话: 86-84485151 </span>

          <span style="margin: 2px 10px"> Email: contact@doubelflyer.com </span>
          <span style="margin: 2px 10px"> 地址：成都市锦江区静安路5号</span>
          <span style="margin: 2px 10px"> 邮编：100835</span>
          <span style="margin: 2px 10px">
            承办单位：成都市住房和城乡建设部信息中心</span>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
export default {
  data() {
    return {
      user: {
        username: "admin",
        password: "123",
      },
    };
  },
  methods: {
    submit() {
      if ((this.user.username == "admin") & (this.user.password == "123")) {
        //将用户添加在本地存储中
        localStorage.setItem("identity", 0);
        this.$router.push("/home/echarts");
        this.$message.success("欢迎登陆，超级管理员");
      } else {
        this.$message.error("用户名或密码错误");
      }
    },

  },
};
</script>



<style lang="less">
* {
  margin: 0;
  padding: 0;
}

.login {
  h2 {
    text-align: center;
    color: #79bbff;
  }

  background-color: #f0f2f5;
  background: url("../../public/login.jpg") no-repeat;
  background-size: 100%;
  position: relative;

  overflow: hidden;

  .up {
    margin: 66px auto;
    width: 23%;
    height: 40%;
    display: flex;
    text-align: center;
    vertical-align: center;

    el-card {
      position: absolute;

      .demo-form-inline {
        margin-top: 30px;
        border: 1px solid red;
        width: 230px;

        .el-input__inner {
          width: 220px;
        }
      }
    }
  }

  .footer {
    margin-top: 180px;
    width: 110%;
    position: relative;

    li {
      list-style: none;
    }

    background-color: #f0f2f5;
    color: #a8abb2;
    font-size: 10px;
    line-height: 25px;

    a {
      color: #a8abb2;
      text-decoration: none;
    }

    a:hover {
      color: #79bbff;
    }
  }
}
</style>