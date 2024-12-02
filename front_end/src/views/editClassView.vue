<!-- eslint-disable @typescript-eslint/no-explicit-any -->
<!-- eslint-disable @typescript-eslint/no-explicit-any -->
<!-- eslint-disable @typescript-eslint/no-explicit-any -->
<template>
  <div class="container">
    <div class="button">
      <el-button class="back" @click="toTeacher">BACK to techer page</el-button>
    </div>
    <div class="form">
      <el-form
        ref="ruleFormRef"
        style="width: 500px"
        :model="ruleForm"
        :rules="rules"
        label-width="auto"
        class="demo-ruleForm"
        :size="formSize"
        status-icon
      >
        <el-form-item label="Course Name" prop="name">
          <el-autocomplete
            v-model="name"
            :fetch-suggestions="querySearchName"
            clearable
            class="inline-input w-80"
            placeholder="Please Input"
            @select="handleSelectName"
          />
        </el-form-item>

        <el-form-item label="Major Name" prop="major">
          <el-autocomplete
            v-model="major"
            :fetch-suggestions="querySearchMajor"
            clearable
            class="inline-input w-80"
            placeholder="Please Input"
            @select="handleSelectMajor"
          />
        </el-form-item>

        <el-form-item label="Teacher Name" prop="teacher">
          <el-autocomplete
            v-model="teacher"
            :fetch-suggestions="querySearchTea"
            clearable
            class="inline-input w-80"
            placeholder="Please Input"
            @select="handleSelectTea"
          />
        </el-form-item>

        <el-form-item label="Semester Name" prop="semester">
          <el-autocomplete
            v-model="semester"
            :fetch-suggestions="querySearchSem"
            clearable
            class="inline-input w-80"
            placeholder="Please Input"
            @select="handleSelectSem"
          />
        </el-form-item>

        <el-form-item label="Planned Hour" prop="hour">
          <el-input v-model.number="ruleForm.plannedHour" />
        </el-form-item>
        <el-form-item label="Brief Description" prop="description">
          <el-input v-model="ruleForm.briefDescription" />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="EditForm(ruleForm)">
            Edit
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import {
  ElNotification,
  type ComponentSize,
  type FormRules,
} from 'element-plus'
import { useRouter } from 'vue-router'
import axiosInstance from '@/axiosInstance'
import { useMainStore } from '@/stores/Main'
const router = useRouter()
const store = useMainStore()
const name = ref()
const teacher = ref()
const semester = ref()
const major = ref()

interface RuleForm {
  majorId: string |number
  semesterId: string | number
  courseName: string
  plannedHour: string
  briefDescription?: string
  syllabus_profile_url?: string
  teacherId?: string | number
}

const toTeacher = () => {
  router.push('Teacher')
}
const submitted = () => {
  ElNotification({
    title: 'Success',
    message: 'This is a success message',
    type: 'success',
  })
}
const errorsubmit = () => {
  ElNotification({
    title: 'Warning',
    message: 'This is a warning message',
    type: 'warning',
  })
}

const formSize = ref<ComponentSize>('default')
const ruleForm = reactive<RuleForm>({
  majorId: '',
  semesterId: '',
  courseName: '',
  plannedHour: '',
  briefDescription: '',
  teacherId: '',
})

const rules = reactive<FormRules<RuleForm>>({
  majorId: [{ required: true, trigger: 'change', type: 'number' }],
  semesterId: [{ required: true, trigger: 'change', type: 'number' }],
  courseName: [{ required: true, trigger: 'change' }],
  plannedHour: [{ required: true, trigger: 'change', type: 'number' }],
  briefDescription: [{ trigger: 'change' }],
  teacherId: [{ required:true,trigger: 'change', type: 'number' }],
})

const EditForm = async (formEl: RuleForm) => {
  console.log(formEl)
  try {
    // 手动验证必填字段
    if (!ruleForm.majorId || 
        !ruleForm.semesterId || 
        !ruleForm.courseName || 
        !ruleForm.plannedHour || 
        !ruleForm.teacherId) {
      errorsubmit()
      return
    }
    // 创建要提交的数据对象
    const submitData = {
      ...ruleForm,
    }
    console.log('submit:',submitData)
    const transjson = JSON.stringify(submitData)
    
    await axiosInstance.post(`/course/${store.editId}`, transjson, {
      headers: { 'Content-Type': 'application/json' },
    })
    
    submitted()
    
    // 重置表单
    ruleForm.majorId = ''
    ruleForm.semesterId = ''
    ruleForm.courseName = ''
    ruleForm.plannedHour = ''
    ruleForm.briefDescription = ''
    ruleForm.teacherId = ''
    
    // 清空输入框的显示值
    name.value = ''
    teacher.value = ''
    semester.value = ''
    major.value = ''
    
  } catch (error) {
    console.log(error)
    errorsubmit()
  }
}



interface RestaurantItem {
  value: string
  id: string | number
}
interface MajorItem {
  majorName: string
  majorId: string
}
interface SemItem {
  semesterName: string
  semesterId:string
}
interface NameItem{
  courseName:string
}
interface TeaItem{
  realName:string
  userId:string
}
const allMajors = ref<RestaurantItem[]>([])
const allSems = ref<RestaurantItem[]>([])
const allNames = ref<RestaurantItem[]>([])
const allTeas = ref<RestaurantItem[]>([])
// 从这往下是专业匹配
const querySearchMajor = (queryString: string, cb: any) => {
  const results = queryString
    ? allMajors.value.filter(createFilterMajor(queryString))
    : allMajors.value
  // call callback function to return suggestions
  cb(results)
}
const createFilterMajor = (queryString: string) => {
  return (restaurant: RestaurantItem) => {
    return (
      restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0
    )
  }
}
const loadAllMajor = async () => {
  const response = await axiosInstance.get(`/major/all`)
  const allmajor = response.data
  return allmajor.map((major: MajorItem) => ({
    value: major.majorName,
    id: major.majorId,
  }))
}
const handleSelectMajor = (item: RestaurantItem) => {
  console.log('major selected:',item)
  ruleForm.majorId = item.id
}
// 从这往下是学期匹配
const querySearchSem = (queryString: string, cb: any) => {
  const results = queryString
    ? allSems.value.filter(createFilterSem(queryString))
    : allSems.value
  // call callback function to return suggestions
  cb(results)
}
const createFilterSem = (queryString: string) => {
  return (restaurant: RestaurantItem) => {
    return (
      restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0
    )
  }
}
const loadAllSem = async () => {
  const response = await axiosInstance.get(`/semester/all`)
  const allSem = response.data
  return allSem.map((sem: SemItem) => ({
    value: sem.semesterName,
    id: sem.semesterId,
  }))
}
const handleSelectSem = (item: RestaurantItem) => {
  console.log('sem selected:',item)
  ruleForm.semesterId = item.id
}
// 从这往下是课程名字匹配
const querySearchName = (queryString: string, cb: any) => {
  const results = queryString
    ? allNames.value.filter(createFilterName(queryString))
    : allNames.value
  // call callback function to return suggestions
  cb(results)
}
const createFilterName = (queryString: string) => {
  return (restaurant: RestaurantItem) => {
    return (
      restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0
    )
  }
}
const loadAllName = async () => {
  const response = await axiosInstance.get(`/course/all`)
  const allName = response.data
  return allName.map((name: NameItem) => ({
    value: name.courseName,
  }))
}
const handleSelectName = (item: RestaurantItem) => {
  console.log('selected name:',item)
  ruleForm.courseName = item.value
}
// 从这里往下是老师名字匹配
const querySearchTea = (queryString: string, cb: any) => {
  const results = queryString
    ? allTeas.value.filter(createFilterTea(queryString))
    : allTeas.value
  // call callback function to return suggestions
  cb(results)
}
const createFilterTea = (queryString: string) => {
  return (restaurant: RestaurantItem) => {
    return (
      restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0
    )
  }
}
const loadAllTea = async () => {
  const response = await axiosInstance.get(`/user/teacher/all`)
  const allTea = response.data
  return allTea.map((tea: TeaItem) => ({
    value: tea.realName,
    id: tea.userId
  }))
}
const handleSelectTea = (item: RestaurantItem) => {
  console.log('selected tea:',item)
  ruleForm.teacherId = item.id
}
const init = async() => {
  try {
    // 获取课程详情
    const response = await axiosInstance.get(`/course/course_id/${store.editId}`)
    console.log('course:',response)
    const courseData = response.data
    
    // 更新表单数据
    ruleForm.majorId = courseData.majorId
    ruleForm.semesterId = courseData.semesterId
    ruleForm.courseName = courseData.courseName
    ruleForm.plannedHour = courseData.plannedHour
    ruleForm.briefDescription = courseData.briefDescription || ''
    ruleForm.teacherId = courseData.teacherId
    
    // 更新显示值
    name.value = courseData.courseName
    major.value = await getMajorName(courseData.majorId)  // 需要根据ID获取名称
    semester.value = await getSemesterName(courseData.semesterId)  // 需要根据ID获取名称
    teacher.value = await getTeacherName(courseData.teacherId)  // 需要根据ID获取名称
    console.log(ruleForm)
  } catch (error) {
    console.error('Failed to initialize form:', error)
  }
}

// 辅助方法：根据ID获取专业名称
const getMajorName = async (majorId: number | string) => {
  const response = await axiosInstance.get(`/major/${majorId}`)
  return response.data.majorName
}

// 辅助方法：根据ID获取学期名称
const getSemesterName = async (semesterId: number | string) => {
  const response = await axiosInstance.get(`/semester/${semesterId}`)
  return response.data.semesterName
}

// 辅助方法：根据ID获取教师名称
const getTeacherName = async (teacherId: number | string) => {
  const response = await axiosInstance.get(`/user/teacher/${teacherId}`)
  console.log(response)
  return response.data.realName
}
  


onMounted(async () => {
  allMajors.value = await loadAllMajor()
  allSems.value = await loadAllSem()
  allNames.value = await loadAllName()
  allTeas.value = await loadAllTea()
  await init()
})
</script>
<style scoped>
.container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.button {
  margin-bottom: 20px;
}
.form {
  padding: 30px;
  border-style: double;
  border-color: rgb(219, 187, 176);
  border-width: 5px;
}
</style>
