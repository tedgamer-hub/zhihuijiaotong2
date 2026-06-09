<template>
  <div class="particle-container" ref="particle-container">
    <div
        v-for="particle in particles"
        :key="particle.id"
        class="particle"
        :style="{
        left: particle.x + 'px',
        top: particle.y + 'px',
        width: particle.size + 'px',
        height: particle.size + 'px',
        backgroundColor: particle.color,
        boxShadow: `0 0 8px 2px ${particle.glowColor}`,
      }"
    ></div>
  </div>
</template>

<script>
export default {
  props: {
    particleNumber: {
      type: Number,
      default: 2,
    },
    particleColor: {
      type: String,
      default: 'white',
    },
    glowColor: {
      type: String,
      default: 'rgba(255, 255, 255, 0.8)',
    },
    minSize: {
      type: Number,
      default: 1,
    },
    maxSize: {
      type: Number,
      default: 2,
    },
  },
  data() {
    return {
      particles: [],
    };
  },
  mounted() {
    this.generateParticles();
    this.animateParticles();
  },
  methods: {
    generateParticles() {
      const container = this.$refs['particle-container'];
      const containerWidth = container.offsetWidth;
      const containerHeight = container.offsetHeight;

      this.particles = []; // 清空现有粒子，以便重新生成

      for (let i = 0; i < this.particleNumber; i++) {
        this.particles.push(this.createParticle(containerWidth, containerHeight, Date.now() + i));
      }
    },
    createParticle(containerWidth, containerHeight, id) {
      return {
        id: id,
        x: Math.random() * containerWidth,
        y: containerHeight,
        size: Math.random() * 2 + 1,
        velocity: Math.random() * 0.5 + 0.3,
        color: this.particleColor,
        glowColor: this.glowColor,
      };
    },
    animateParticles() {
      const container = this.$refs['particle-container'];
      const containerWidth = container.offsetWidth;
      const containerHeight = container.offsetHeight;

      this.particles = this.particles.map(particle => {
        particle.y -= particle.velocity;
        if (particle.y < 0) {
          // 替换消失的粒子
          return this.createParticle(containerWidth, containerHeight, Date.now());
        }
        return particle;
      });

      this.$nextTick(() => {
        requestAnimationFrame(this.animateParticles);
      });
    },
  },
};
</script>

<style>
.particle-container {
  position: absolute;
  width: 100%; /* 容器宽度 */
  height: 100%; /* 容器高度 */
  overflow: hidden;
  z-index: 0;
}

.particle {
  position: absolute;
  border-radius: 50%;
}
</style>

