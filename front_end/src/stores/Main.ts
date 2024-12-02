import { defineStore } from 'pinia'
import { ref, watch } from 'vue'
import { useRouter } from 'vue-router'

export const useMainStore = defineStore('Main', 
  () => {
    const loged = ref(false)
    const token = ref('')
    const whetherinside = ref<boolean>(false)
    const mask = ref('')
    const router = useRouter()
    const role = ref<string>('')
    const editId = ref(-1)

    // Watch for UI effects
    watch(whetherinside, (newVal) => {
      mask.value = newVal ? 'filter: blur(10px);' : 'filter: blur(0px);'
    })

    // Watch route changes
    watch(() => router.currentRoute.value.path, (newVal, oldVal) => {
      if (newVal !== oldVal) {
        whetherinside.value = false
        console.log('Route changed:', newVal)
      }
      role.value = newVal === '/teacher' ? 'Teacher' : ''
    })

    // Token management actions
    const setToken = (newToken: string) => {
      token.value = newToken
      loged.value = true
    }

    const clearToken = () => {
      token.value = ''
      loged.value = false
    }

    // Getters
    const isAuthenticated = () => !!token.value

    return {
      loged,
      whetherinside,
      mask,
      role,
      editId,
      token,
      setToken,
      clearToken,
      isAuthenticated
    }
  },
  {
    persist: true // 使用简单的持久化配置
  }
)