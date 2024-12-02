<template>
  <div>
    <div class="container" ref="container">
      <div class="teaTitle">WELCOME to TEACHER PAGE</div>
      <el-button class="add" @click="addClass">add class</el-button>
      <div class="downcontainer">
        <div v-for="(item, index) in allcs" :key="index" class="card">
          <div class="details">
            <div>
              <div class="yihang">name:</div>
              {{ item.courseName }}
            </div>
            <div>
              <div class="yihang">teacher:</div>
              {{ item.teacherName }}
            </div>
            <div>
              <div class="yihang">major:</div>
              {{ item.majorName }}
            </div>
            <div>
              <div class="yihang">semester:</div>
              {{ item.semesterName }}
            </div>
            <div>
              <div class="yihang">planned hour:</div>
              {{ item.plannedHour }}
            </div>
            <div>
              <div class="yihang">description:</div>
              {{ item.briefDescription }}
            </div>
          </div>
          <div class="application">
            <div class="button">
              <el-button type="danger" @click="deletethis(item)">
                delete this one
              </el-button>
              <el-button type="primary" @click="editthis(item)">
                edit this one
              </el-button>
              <el-button plain @click="download(item)">download file</el-button>
            </div>
            <div class="upload" style="margin-left: 10px">
              <el-upload
                class="elUpload"
                :auto-upload="false"
                :on-change="(file: UploadFile) => handleChange(file, item)"
                ref="uploadRef"
                :file-list="fileList"
              >
                <el-button plain>upload file</el-button>
              </el-upload>
            </div>
          </div>
        </div>
      </div>
      <el-pagination
        v-model:current-page="nowPage"
        layout="prev, pager, next"
        :total="totalPage * 10"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import axiosInstance from '@/axiosInstance'
import { useMainStore } from '@/stores/Main'
import { ElMessage } from 'element-plus'

import type { UploadFile, UploadInstance } from 'element-plus'
const router = useRouter()
const store = useMainStore()
interface Course {
  briefDescription: string
  courseId: number
  courseName: string
  majorId: number
  plannedHour: number
  semesterId: number
  semesterName:string
  teacherId: number
  teacherName?: string
  majorName?: string
}

const fileList = ref<UploadFile[]>([])
const allcs = ref<Course[]>([])
const totalPage = ref<number>(0)
const nowPage = ref<number>(1)
const uploadRef = ref<UploadInstance>()
const getcourse = async () => {
  try {
    await axiosInstance
      .get(`/course/all/${nowPage.value - 1}`)
      .then(response => {
        allcs.value = response.data.content
        totalPage.value = response.data.totalPages
        console.log(allcs.value)
      })

    // 为每个课程获取教师名字
    for (const course of allcs.value) {
      const teacherResponse = await axiosInstance.get(
        `/user/teacher/${course.teacherId}`,
      )
      console.log(teacherResponse)
      course.teacherName = teacherResponse.data.realName
      console.log(teacherResponse.data.realName)
      const majorResponse = await axiosInstance.get(`/major/${course.majorId}`)
      course.majorName = majorResponse.data.majorName
      console.log(majorResponse.data.majorName)
      const semesterResponse = await axiosInstance.get(`/semester/${course.semesterId}`)
      course.semesterName = semesterResponse.data.semesterName
      console.log(semesterResponse.data.semesterName)
    }
  } catch (error) {
    console.log(error)
  }
}

const deletethis = async (item: Course) => {
  await axiosInstance.delete(`/course/id/${item.courseId}`)
  await getcourse()
}

const editthis = (item: Course) => {
  router.push({ name: 'EditClass' })
  store.editId = item.courseId
  console.log('editId is:',store.editId)
}

const handleChange = async (uploadFile: UploadFile, item: Course) => {
  try {
    const formData = new FormData()
    formData.append('file', uploadFile.raw!)

    const response = await axiosInstance.post(
      `/course/syllabus/upload/${item.courseId}`,
      formData,
      {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      },
    )

    if (response.status === 200) {
      ElMessage({
        message: '上传成功',
        type: 'success',
      })
    }
  } catch (error) {
    ElMessage({
      message: '上传失败',
      type: 'error',
    })
    console.error(error)
  }
}

const download = (item: Course) => {
  try {
    window.open(`http://localhost:8080/api/course/syllabus/download/${item.courseId}`)
  } catch (error) {
    console.error(error)
  }
}

const addClass = () => {
  router.push('addClass')
}
const container = ref()
console.log(container)

onMounted(async () => {
  await getcourse()
  console.log(allcs.value)
  const store = useMainStore()
  console.log('Current token on page load:', store.token) // 调试用
  console.log('Is authenticated:', store.isAuthenticated()) // 调试用

  if (!store.isAuthenticated()) {
    console.log('No authentication, redirecting to login') // 调试用
    router.push('/login')
    return
  }
})
watch(nowPage, () => {
  fileList.value = []
})

watch(nowPage, () => {
  getcourse()
})
</script>

<style scoped>
.container {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  /* justify-content: center; */
  align-items: center;
}
.teaTitle {
  margin-bottom: 15px;
}
.add {
  margin-bottom: 15px;
}

.downcontainer {
  height: 700px;
  width: 100%;
  background-color: #2d6a4f;
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow: hidden;
}
.card {
  width: 90%;
  height: 200px;
  margin-top: 8px;
  margin-bottom: 4px;
  background-color: #f8f9fa;
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 10px;
  border-radius: 20px;
}
.application {
  height: 30px;
  display: flex;
}

.details {
  height: 170px;
  display: flex;
  flex-wrap: wrap;
}
.details > div {
  display: flex;
  height: 22px;
  width: 100%;
  color: black;
}
.elUpload {
  display: flex;
}
.yihang {
  margin-right: 15px;
}
</style>
