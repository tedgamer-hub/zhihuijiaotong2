<template>
  <div class="pageItemBg">

  </div>
</template>

<script>

export default {
  name: "pageItemBg"
}
</script>

<style lang="scss" scoped>
.pageItemBg {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  bottom: 0;
  right: 0;
  z-index: -1;
  border: 1px solid rgba(#2b89c8, 0.3);
  background: linear-gradient(180deg, rgba(#2b89c8, 0.04) 0%, rgba(#2b89c8, 0.5) 100%);
  pointer-events: none; //避免div层被点击
}
</style>
