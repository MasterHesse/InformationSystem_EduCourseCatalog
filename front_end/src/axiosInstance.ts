import axios from 'axios'
import { useMainStore } from './stores/Main'
const store = useMainStore()

const axiosInstance = axios.create({
  baseURL: 'http://localhost:8080/api',
  withCredentials: true
})


axiosInstance.interceptors.request.use(
  config => {
    const token = store.token
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  error => {
    return Promise.reject(error)
  },
)

// 响应拦截器
axiosInstance.interceptors.response.use(
  response => response,
  error => {
    if (error.response?.status === 401) {
      store.token = ''
      // 可以选择重定向到登录页
      // window.location.href = '/login'
    }
    return Promise.reject(error)
  },
)

export default axiosInstance
