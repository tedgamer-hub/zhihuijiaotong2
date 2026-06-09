<template>
  <div class="videos">
    <video
        ref="videoPlayer"
        class="video-player"
        controls
        preload="auto"
        width="640"
        height="264"
        autoplay
        muted
        playsinline
        loop
    >
      <source :src="videoSrc" type="video/mp4"/>
      您的浏览器不支持视频标签
    </video>
  </div>
</template>

<script setup>
import { onMounted, ref, watch } from 'vue';

// 定义props
const props = defineProps({
  videoSrc: {
    type: String,
    required: true
  }
});

const videoPlayer = ref(null);

// 监听视频源变化
watch(() => props.videoSrc, (newSrc) => {
  if (videoPlayer.value) {
    videoPlayer.value.load(); // 重新加载视频
    videoPlayer.value.play().catch(err => {
      console.error('视频播放失败:', err);
    });
  }
});

onMounted(() => {
  console.log('视频播放器已准备就绪!');

  // 添加事件监听
  if (videoPlayer.value) {
    videoPlayer.value.addEventListener('play', () => {
      console.log('视频开始播放');
    });

    videoPlayer.value.addEventListener('pause', () => {
      console.log('视频已暂停');
    });

    videoPlayer.value.addEventListener('ended', () => {
      console.log('视频播放结束');
    });

    videoPlayer.value.addEventListener('error', (e) => {
      console.error('视频播放错误:', e);
    });
  }
});
</script>

<style lang="scss">
.videos {
  position: relative;
  width: 100%;
  height: calc(100% - 20px);

  .video-player {
    width: 100%;
    height: 100%;
    object-fit: cover; // 保持视频比例并填充容器
    background-color: #000;
  }
}
</style>
