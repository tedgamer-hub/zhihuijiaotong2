<template>
  <div class="titleCom">
    <div class="titleComBg">

    </div>
  </div>
</template>

<script>
export default {
  name: "titlebg"
}
</script>

<style lang="scss" scoped>
.titleCom {
  position: relative;
  width: 100%;
  height: 70PX;

  .titleComBg {
    position: absolute;
    top: 0;
    left: 0;
    z-index: 0;
    width: 100%;
    height: calc(100% - 0px);
    background-image: url('./assets/01.png'),
    url('./assets/02.png'),
    url('./assets/03.png');
    background-repeat: no-repeat,
    no-repeat,
    no-repeat;
    background-position: top left,
    182PX top,
    right top;
    background-size: 182PX 70PX,
    calc(100% - 182PX - 177PX) 70PX,
    177PX 70PX;
  }
}

</style>
