<template>
  <div class="whole">
    <el-container>
      <el-aside width="200px">
        <el-menu
          default-active="1"
          style="margin-top: 30px"
          @open="handleOpen"
          @close="handleClose"
        >
          <el-menu-item index="1" @click="checkPending">
            <span>Check Pending</span>
          </el-menu-item>
          <el-menu-item index="2" @click="checkPass">
            <span>Check Pass</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main style="height: 800px; background-color: #cdc1ff">
        <el-scrollbar max-height="800px">
          <transition
            enter-active-class="animate__animated animate__fadeInUp"
          >
            <div class="showPending" v-if="pending">
              <div v-for="(item, index) in show" :key="index" class="card">
                <div>
                  <div>User Name:</div>
                  {{ item.realName }}
                </div>
                <div>
                  <div>User Role:</div>
                  {{ item.role.type }}
                </div>
                <div>
                  <div>User Gender:</div>
                  {{ item.gender }}
                </div>
                <div>
                  <div>User status:</div>
                  {{ item.status }}
                </div>

                <div class="buttons">
                  <el-button
                    plain
                    @click="changeStatus(item.userId)"
                    size="default"
                    type="success"
                    style="margin-bottom: 5px"
                    >click to pass</el-button
                  >
                  <el-button
                    plain
                    @click="modifyDetail(item)"
                    size="default"
                    type="primary"
                    >modify details</el-button
                  >
                </div>
              </div>
            </div>
          </transition>
          <transition
            enter-active-class="animate__animated animate__fadeInUp"
            
          >
            <div class="showPass" v-if="!pending">
              <div v-for="(item, index) in show" :key="index" class="card">
                <div>
                  <div>User Name:</div>
                  {{ item.realName }}
                </div>
                <div>
                  <div>User Role:</div>
                  {{ item.role.type }}
                </div>
                <div>
                  <div>User Gender:</div>
                  {{ item.gender }}
                </div>
                <div>
                  <div>User status:</div>
                  {{ item.status }}
                </div>

                <div class="buttons">
                  <el-button
                    plain
                    @click="modifyDetail(item)"
                    size="default"
                    type="primary"
                    style="margin-bottom: 5px"
                    >modify details</el-button
                  >
                  <el-button
                    plain
                    @click="changeRole(item.userId)"
                    size="default"
                    type="info"
                  >
                    change role
                  </el-button>
                </div>
              </div>
            </div>
          </transition>
        </el-scrollbar>
      </el-main>
      <el-dialog
        v-if="dialogVisible"
        v-model="dialogVisible"
        title="DETAILS"
        width="800"
        align-center
        destroy-on-close
      >
        <div>
          <div>User ID:</div>
          {{ selectedItem?.userId }}
        </div>
        <div>
          <div>Account:</div>
          {{ selectedItem?.account }}
        </div>
        <div>
          <div>Password:</div>
          {{ selectedItem?.password }}
        </div>
        <div>
          <div>Real Name:</div>
          {{ selectedItem?.realName }}
        </div>
        <div>
          <div>Gender:</div>
          {{ selectedItem?.gender }}
        </div>
        <div>
          <div>Contact:</div>
          {{ selectedItem?.contact }}
        </div>
        <div>
          <div>Major ID:</div>
          {{ selectedItem?.majorId }}
        </div>
        <div>
          <div>Semester ID:</div>
          {{ selectedItem?.semesterId }}
        </div>
        <div>
          <div>Status:</div>
          {{ selectedItem?.status }}
        </div>
        <div>
          <div>Role Type:</div>
          {{ selectedItem?.role.type }}
        </div>
      </el-dialog>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import axiosInstance from '@/axiosInstance'
import { ref, onMounted } from 'vue'

interface userItem {
  userId: string
  account: string
  password: string
  realName: string
  gender: string
  contact: string
  majorId: string
  semesterId: string
  status: string
  major: {
    majorId: string
    majorName: string
  }
  role: {
    roleId: string
    type: string
  }
  semester: {
    semesterId: string
    semesterName: string
  }
}
const show = ref<userItem[]>([])
const selectedItem = ref<userItem>()
const pending = ref(true)
const dialogVisible = ref(false)
const checkPending = async () => {
  getPending()
  pending.value = true
  console.log('pending')
}
const checkPass = () => {
  getPass()
  pending.value = false
  console.log('pass')
}
const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const getPending = async () => {
  const response = await (await axiosInstance.get('/user/pending')).data
  show.value = response
  console.log('show is', show)
}
const getPass = async () => {
  const response = await (await axiosInstance.get('/user/pass')).data
  show.value = response
  console.log('show is', show)
}
const changeStatus = async (id: string) => {
  await axiosInstance.post(`/user/update-status`, null, {
    params: {
      userId: id,
      status: 'pass',
    },
  })
  await getPending()
}
const changeRole = async (id: string) => {
  await axiosInstance.post('/user/switch-role', null, {
    params: { userId: id },
  })
  await getPass()
}
const modifyDetail = (item: userItem) => {
  selectedItem.value = item
  setTimeout(() => {
    dialogVisible.value = true
  }, 0)
}
onMounted(async () => {
  await getPending()
})
</script>
<style scoped>
.card {
  display: flex;
  height: 100px;
  width: 100%;
  justify-content: space-around;
  margin-bottom: 20px;
  background-color: #fbfbfb;
  border-radius: 40px;
}
.card > div {
  display: flex;
  align-items: center;
  justify-content: center;
}
.buttons {
  display: flex;
  flex-direction: column;
  align-items: center;
}
:deep(.el-dialog__body) {
  padding: 20px;
}

:deep(.el-dialog__body) > div {
  margin-bottom: 15px;
  display: flex;
  gap: 10px;
}

:deep(.el-dialog__body) > div > div:first-child {
  font-weight: bold;
  min-width: 120px;
}
</style>
