<template>
  <div class="LogForm">
    <div
      class="FormItself"
      @mouseenter="store.whetherinside = true"
      @mouseleave="store.whetherinside = false"
    >
      <div class="left">
        <h1 class="title">Welcome to use<br />CATO course catalog system</h1>
        <div class="tip">
          your default id is your student id<br />default password is the last 6
          bytes of your id<br />plz change your password after login in user
          center
        </div>
      </div>
      <div class="right">
        <el-form
          :model="form"
          :label-position="labelPosition"
          label-width="120px"
          style="
            width: 100%;
            display: flex;
            flex-wrap: wrap;
            flex-direction: row;
            
          "
        >
          <el-form-item label="User ID">
            <el-input v-model="form.account" />
          </el-form-item>
          <el-form-item label="User Password">
            <el-input show-password v-model="form.password" />
           </el-form-item
        >
      </el-form>
        <el-button plain @click="submitForm" class="button">Login</el-button><br />
        <el-button plain @click="toRegister" class="button">Register</el-button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ElInput, type FormProps } from 'element-plus'
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useMainStore } from '@/stores/Main'
import axiosInstance from '@/axiosInstance'
const labelPosition = ref<FormProps['labelPosition']>('left')
const router = useRouter()
const store = useMainStore()

const form = reactive({
  account: '',
  password: '',
})

const submitForm = async () => {
  try {
    const submitted = JSON.stringify(form)
    console.log(submitted)
    const response = await axiosInstance.post('/auth/login', submitted, {
      headers: {
        'Content-Type': 'application/json',
      },
    })
    if (response.data.token) {
      // 假设后端返回token
      // 保存token
      store.setToken(response.data.token)
      // 设置axios默认header
      if (response.data.role == 'ROLE_STUDENT') {
        console.log(response.data)
        router.push('Student')
      }
      if (response.data.role == 'ROLE_TEACHER') {
        router.push('Teacher')
      }
      if (response.data.role == 'ROLE_ADMINISTRATOR') {
        router.push('Administer')
      }
    }
  } catch (error) {
    console.log(error)
  }
}

const toRegister = () => {
  router.push({ name: 'Register' })
  store.whetherinside = false
}
</script>

<style scoped>
body {
  font-family: 'MyCustomFont', sans-serif;
}

.LogForm {
  position: relative;
  z-index: 2;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-top: 10vh;
}
.FormItself {
  display: flex;
  justify-content: space-around;
  padding: 30px;
  width: 40vw;
  height: 40vh;
  background-color: whitesmoke;
  border-radius: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: transform 0.66s ease;
}

.FormItself:hover {
  transform: scale(1.3);
}

.register:hover {
  color: cadetblue;
}
.left {
  display: flex;
  flex-direction: column;
  width: 45%;
  /* font-family: 'Pixel',sans-serif; */
}

.right {
  width: 45%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.title {
  margin-top: 5%;
  margin-bottom: 15%;
  /* font-family: 'Pixel', sans-serif; */
  font-optical-sizing: auto;
  font-weight: 400;
  font-style: normal;
}
.tip {
  font-size: smaller;
  line-height: 1.5;
}
.button{
  width: 200px;
}
</style>
