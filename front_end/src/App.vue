<template>

  <div>
    <section>
      <div class="common-layout" style="">
        <el-container class="container">
          <!-- it's header -->
          <el-header style="font-family: 'Pixel',sans-serif;" >
            <el-menu class="el-menu" mode="horizontal" :ellipsis="false">
              <el-menu-item index="1" @click="router.push({name:'default'})">Cato courses catalog</el-menu-item>
              <el-sub-menu index="2" close-on-click-outside="true">
                <template #title> User center </template>
                <el-menu-item index="2-1"><div @click="router.push({name:'Login'})">Log in</div></el-menu-item>
                <el-menu-item index="2-2"><div @click="changePassword">Change Password</div></el-menu-item>
              </el-sub-menu>
            </el-menu>
          </el-header>
          <!-- it's container -->
          <el-container>
            <el-main class="component" style="overflow: hidden;">
              <router-view></router-view>
            </el-main>
          </el-container>
        </el-container>
        <el-dialog
        v-if="dialogVisible"
        v-model="dialogVisible"
        title="Change Password"
        width="800"
        align-center
        destroy-on-close
      >
      <el-form :model="form">
      <el-form-item label="Account" :label-width="formLabelWidth">
        <el-input v-model="form.account"  />
      </el-form-item>
      <el-form-item label="Password" :label-width="formLabelWidth">
        <el-input v-model="form.password"  />
      </el-form-item>
      <el-form-item label="Confirm password" :label-width="formLabelWidth">
        <el-input v-model="form.confirm"  />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="dialogVisible = false">
          Confirm
        </el-button>
      </div>
    </template>
        </el-dialog>
      </div>
    </section>
  </div>


 </template>

 <script setup lang='ts'>
 import { reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
 const router = useRouter()

 const dialogVisible = ref(false)
const formLabelWidth = '140px'
const form = reactive({
  account:'',
  password:'',
  confirm:''
})
 const changePassword = () => {
  dialogVisible.value = true
 }
 </script>
 <style scoped>
 .common-layout{
  height: 100vh;
  display: flex;
  flex-direction: column;
  position: relative;
  z-index: 1;
 }
 .el-menu--horizontal > .el-menu-item:nth-child(1) {
  margin-right: auto;
}
.component{
  height: 100%;
}

 </style>
