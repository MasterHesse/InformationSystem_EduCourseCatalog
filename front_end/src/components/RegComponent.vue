<!-- eslint-disable @typescript-eslint/no-explicit-any -->
<template>
  <div
    style="
      width: 600px;
      height: 400px;
      display: flex;
      flex-direction: column;
      align-items: center;
    "
    id="whole"
  >
    <div class="title">PLEASE SELECT ROLE</div>
    <div class="roleButton">
      <el-button
        plain
        :type="chosenRole === 'student' ? 'success' : 'default'"
        @click="selectS"
        >Student</el-button
      >
      <el-button
        plain
        :type="chosenRole === 'teacher' ? 'success' : 'default'"
        @click="selectT"
        >Teacher</el-button
      >
    </div>
    <div class="form">
      <el-form
        ref="formRef"
        :label-position="labelPosition"
        :model="form"
        :rules="rules"
        label-width="200px"
        style="
          width: 100%;
          display: flex;
          flex-wrap: wrap;
          flex-direction: row;
          justify-content: center;
        "
      >
        <el-form-item label="User Account:" prop="account">
          <el-input v-model="form.account" />
        </el-form-item>
        <el-form-item label="User Password:" prop="password">
          <el-input show-password v-model="form.password" />
        </el-form-item>
        <el-form-item label="Confirm Password:" prop="confirm">
          <el-input show-password v-model="form.confirm" />
        </el-form-item>
        <el-form-item label="Real Name:" prop="realName">
          <el-input v-model="form.realName" />
        </el-form-item>
        <el-form-item label="Gender:" prop="gender">
          <el-select
            v-model="form.gender"
            placeholder="Select"
            style="width: 192px"
          >
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="Contact:" prop="contact">
          <el-input v-model="form.contact" />
        </el-form-item>
        <el-form-item
          label="Position:"
          prop="position"
          v-if="chosenRole === 'teacher'"
        >
          <el-input v-model="form.position" />
        </el-form-item>
        <el-form-item
          label="academicAchievement:"
          prop="academicAchievements"
          v-if="chosenRole === 'teacher'"
        >
          <el-input v-model="form.academicAchievements" />
        </el-form-item>
        <el-form-item
          label="ResearchDirection:"
          prop="researchDirection"
          v-if="chosenRole === 'teacher'"
        >
          <el-input v-model="form.researchDirection" />
        </el-form-item>
        <el-form-item
          label="MajorId:"
          prop="majorId"
          v-if="chosenRole === 'student'"
        >
          <el-input v-model="form.majorId" />
        </el-form-item>
        <el-form-item
          label="Semester:"
          prop="semesterId"
          v-if="chosenRole === 'student'"
        >
          <el-input v-model="form.semesterId" />
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="submitForm" style="margin-left: 45%"
        >Submit</el-button
      >
    </div>
  </div>
</template>

<script setup lang="ts">
import axiosInstance from '@/axiosInstance';
import { ElMessage, type FormProps, type FormRules } from 'element-plus'
import { ref, reactive } from 'vue'
interface formItem {
  account: string
  password: string
  confirm?: string
  realName: string
  gender: string
  contact: string
  majorId?: string
  semesterId?: string
  position?: string
  academicAchievements?: string
  researchDirection?: string
}

const form = reactive<formItem>({
  account: '',
  password: '',
  confirm: '',
  realName: '',
  gender: '',
  contact: '',
  majorId: '',
  semesterId: '',
  position: '',
  academicAchievements: '',
  researchDirection: '',
})

const labelPosition = ref<FormProps['labelPosition']>('left')
const chosenRole = ref('student')
const validatePassword = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('Please input the password again'))
  } else if (value !== form.password) {
    callback(new Error('Two inputs do not match!'))
  } else {
    callback()
  }
}
const options = [
  {
    value: 'male',
    label: 'male',
  },
  {
    value: 'female',
    label: 'female',
  },
]
const rules = reactive<FormRules<formItem>>({
  account: [
    { required: true, message: 'Please input user Account', trigger: 'blur' },
  ],
  password: [
    { required: true, message: 'Please input password', trigger: 'blur' },
    {
      min: 6,
      message: 'Length should be at least 6 characters',
      trigger: 'blur',
    },
  ],
  confirm: [
    { required: true, message: 'Please confirm password', trigger: 'blur' },
    { validator: validatePassword, trigger: 'blur' },
  ],
  realName: [
    { required: true, message: 'Please input real name', trigger: 'blur' },
  ],
  gender: [
    { required: true, message: 'Please select gender', trigger: 'change' },
  ],
  contact: [
    {
      required: true,
      message: 'Please input contact information',
      trigger: 'blur',
    },
  ],
  majorId: [
    { required: true, message: 'Please input major ID', trigger: 'blur' },
  ],
  semesterId: [
    { required: true, message: 'Please input semester ID', trigger: 'blur' },
  ],
  position: [
    { required: true, message: 'Please input position', trigger: 'blur' },
  ],
  academicAchievements: [
    {
      required: true,
      message: 'Please input academic achievements',
      trigger: 'blur',
    },
  ],
  researchDirection: [
    {
      required: true,
      message: 'Please input research direction',
      trigger: 'blur',
    },
  ],
})

const selectT = () => {
  chosenRole.value = 'teacher'
}
const selectS = () => {
  chosenRole.value = 'student'
}
const submitForm = async() => {
  try{
    if (chosenRole.value === 'teacher') {
    const submit = reactive<formItem>({
      account: '',
      password: '',
      realName: '',
      gender: '',
      contact: '',
      position: '',
      academicAchievements: '',
      researchDirection: '',
    })
    submit.account = form.account
    submit.password = form.password
    submit.realName = form.realName
    submit.gender = form.gender
    submit.contact = form.contact
    submit.position = form.position
    submit.academicAchievements = form.academicAchievements
    submit.researchDirection = form.researchDirection
    console.log('submit is:',submit)
    const json = JSON.stringify(submit)
    console.log('json is:',json)
    await axiosInstance.post(`/auth/register/teacher`,json,{
        headers:{'Content-Type': 'application/json'}
    })
    ElMessage({
          message: 'Teacher registration successful',
          type: 'success',
        })
  } else {
    const submit = reactive<formItem>({
      account: '',
      password: '',
      realName: '',
      gender: '',
      contact: '',
      majorId: '',
      semesterId: '',
    })
    submit.account = form.account
    submit.password = form.password
    submit.realName = form.realName
    submit.gender = form.gender
    submit.contact = form.contact
    submit.majorId = form.majorId
    submit.semesterId = form.semesterId
    console.log('submit is:',submit)
    const json = JSON.stringify(submit)
    console.log('json is:',json)
    await axiosInstance.post(`/auth/register/student`,json,{
        headers:{'Content-Type': 'application/json'}
    })
    ElMessage({
          message: 'Student registration successful',
          type: 'success',
        })
  }
  }catch (error: any) {
    ElMessage({
      message: error.response?.data?.message || 'Registration failed',
      type: 'error',
    })
  }
  
}
</script>

<style scoped>
#whole > div {
  margin-bottom: 30px;
}
.title {
  margin-top: 30px;
}
.form {
  padding: 15px;
  border-style: double;
  border-color: rgb(219, 187, 176);
  border-width: 5px;
}
</style>
