<template>
  <canvas ref="animationCanvas" class="animation_canvas" id="animation_canvas"></canvas>
</template>

<script>
export default {
  props: {
    // 文件数量
    fileLength: {
      type: Number,
      default: 74
    },
    // 动画间隔
    IntervalTime: {
      type: Number,
      default: 50
    },
    // 图片路径
    imgPath: {
      type: String,
      default: 'lan'
    }
  },
  data() {
    return {
      animationCanvas: null
    };
  },
  methods: {
    async loadImages2(sources) {
      for (let i = 1; i <= this.fileLength; i++) {
        const image = await import(`./${this.imgPath}/dizuo/${i}.png`);
        sources.push(image.default);
      }
    },
    loadImages(sources, callback) {
      let loadedImages = 0;
      const numImages = sources.length;
      const images = [];

      for (let i = 0; i < numImages; i++) {
        images[i] = new Image();
        images[i].onload = () => {
          if (++loadedImages >= numImages) {
            callback(images);
          }
        };
        images[i].src = sources[i];
      }
    },
    playImages(images, ctx, width, height, intervalTime) {
      let imageNow = 0;
      let isFirstLoop = true; // 添加标志判断是否是第一次循环

      setInterval(() => {
        ctx.clearRect(0, 0, width, height);
        ctx.drawImage(images[imageNow], 0, 0, width, height);

        imageNow++;
        if (imageNow >= images.length) {
          if (isFirstLoop) {
            imageNow = 0; // 第二次循环从第74张开始（索引从0开始，所以是73）
            isFirstLoop = false;
          } else {
            imageNow = 0; // 之后的循环都从第74张开始
          }
        }
      }, intervalTime);
    }
  },
  async mounted() {
    this.$nextTick(async () => {
      const canvas = this.$refs.animationCanvas;
      if (canvas) {
        const ctx = canvas.getContext('2d');
        if (!ctx) {
          console.error('Failed to get canvas context');
          return;
        }
        const sources = [];

        // 初始化 canvas 尺寸
        const width = canvas.offsetWidth;
        const height = canvas.offsetHeight;
        canvas.width = width;
        canvas.height = height;

        // 加载第一张图片并展示
        const firstImage = await import(`./${this.imgPath}/dizuo/0.png`);
        sources.push(firstImage.default);

        const firstImgElement = new Image();
        firstImgElement.src = sources[0];
        firstImgElement.onload = () => {
          ctx.drawImage(firstImgElement, 0, 0, width, height);
        };

        // 加载剩余的图片
        await this.loadImages2(sources);

        // 执行图片预加载，加载完成后执行动画
        this.loadImages(sources, (images) => {
          this.playImages(images, ctx, width, height, this.IntervalTime);
        });
      }
    });
  }
};
</script>

<style scoped>
.animation_canvas {
  width: 85%; /* 宽度占满父容器 */
  aspect-ratio: 133 / 102; /* 保持宽高比为133:102 */
  position: absolute;
  z-index: 1;
}
</style>