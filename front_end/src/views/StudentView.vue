<template>
  <div class="whole">
    <div class="title">WELCOME to STUDENT PAGE</div>
    <div class="container">
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
        <div class="buttons">
          <el-button plain @click="download(item)">download file</el-button>
        </div>
      </div>
    </div>
 
    <el-pagination
      v-model:current-page="nowPage"
      layout="prev, pager, next"
      :total="totalPage * 10"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import axiosInstance from '@/axiosInstance'

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

const allcs = ref<Course[]>([])
const nowPage = ref<number>(1)
const totalPage = ref<number>(0)

const download = (item: Course) => {
  try {
    window.open(
      `http://localhost:8080/api/course/syllabus/download/${item.courseId}`,
    )
  } catch (error) {
    console.error(error)
  }
}
const getcourse = async () => {
  try {
    const current = (await axiosInstance.get(`/user/current`)).data
    const id = current.majorId
  
    await axiosInstance
      .get(`/course/major_id/${id}/${nowPage.value - 1}`)
      .then(response => {
        allcs.value = response.data.content
        totalPage.value = response.data.totalPages
        console.log('allcs is',allcs.value)
      })

    // 为每个课程获取教师名字
    for (const course of allcs.value) {
      const teacherResponse = await axiosInstance.get(
        `/user/teacher/${course.teacherId}`,
      )
      console.log('teacherResponse',teacherResponse)
      course.teacherName = teacherResponse.data.realName
      console.log('realname',teacherResponse.data.realName)
      const majorResponse = await axiosInstance.get(`/major/${course.majorId}`)
      course.majorName = majorResponse.data.majorName
      console.log('major',majorResponse.data.majorName)
      const semesterResponse = await axiosInstance.get(`/semester/${course.semesterId}`)
      course.semesterName = semesterResponse.data.semesterName
      console.log('semester',semesterResponse.data.semesterName)
    }
  } catch (error) {
    console.log(error)
  }
}
watch(nowPage, () => {
  getcourse()
})
onMounted(async() => {
   await getcourse()
})
    
</script>
<style scoped>
.whole{
    width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.title{
    margin-bottom: 15px;
}
.container{
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
.buttons {
  height: 30px;
  display: flex;
}
.yihang {
  margin-right: 15px;
}
</style>
