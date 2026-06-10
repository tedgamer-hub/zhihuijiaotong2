import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';

export default () => {
  return defineConfig({
    plugins: [vue()],
    server: {
      port: 8230,
      proxy: {
        '/api': {
          target: 'http://localhost:8080',
          changeOrigin: true,
          rewrite: (path) => `/IntelliDetect${path}`,
        },
      },
    },
    base: '/',
    resolve: {
      alias: {
        '@': '/src',
      },
    },
    css: {
      preprocessorOptions: {
        scss: {
          api: 'modern',
          silenceDeprecations: ['legacy-js-api'],
          sassOptions: {
            outputStyle: 'compressed',
          },
        },
      },
    },
  });
};
