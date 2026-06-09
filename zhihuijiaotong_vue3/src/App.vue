<template>
  <div>
    <router-view/>
  </div>
</template>

<script setup>
</script>

<style lang="scss">
html, body {
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
}
p {
  margin: 0;
}

@font-face {
  font-family: 'PangMenZhengDao';
  src: url('./fonts/PangMenZhengDao.ttf');
}

.hideScrollBar::-webkit-scrollbar {
  width: 0
}
</style>