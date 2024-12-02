<template>
  <div style="display: flex; justify-content: center;">
    <div class="bkg" :style="store.mask"></div>
    <LogComponent></LogComponent>
  </div>
</template>



<script setup lang="ts">
import LogComponent from '@/components/LogComponent.vue'
import { useMainStore } from '@/stores/Main'


const store = useMainStore()

</script>



<style scoped>
.bkg {
  width: 100%;
  height: 100%;
  background-image: url('../assets/bkg.gif');
  position: absolute;
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
  filter: blur(0px);
  transition: filter 0.66s ease;
}
</style>
