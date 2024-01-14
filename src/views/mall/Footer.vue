<template>
  <footer>
    <div class="footer-links">
      <div class="links">
        <span class="title">卖家服务&nbsp;&nbsp;</span>
        <a href="#">支付方式</a>
        <span>|</span>
        <a href="#">快递方式</a>
        <span>|</span>
        <a href="#">其他事项</a>
      </div>

      <div class="links">
        <span class="title">关于我们&nbsp;&nbsp;</span>
        <a href="#">关于公司</a>
        <span>|</span>
        <a href="#">联系我们</a>
        <span>|</span>
        <a href="#">投诉建议</a>
      </div>
    </div>

    <div class="footer-links">
        <a class="markdown" target="blank" href="https://beian.miit.gov.cn">
          蜀ICP备2023034014号-1</a>
    </div>
  </footer>
</template>

<style scoped>


.footer-links {
  display: flex;
  justify-content: space-evenly;
}

.links {
  display: flex;
  align-items: center;
}

.title {
  font-size: 16px;
  font-weight: bold;
}
</style>