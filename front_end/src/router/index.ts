import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path:'/',
      name:'default',
      component:() => import('@/views/defaultView.vue')
    },
    {
      path:'/reg',
      name:'Register',
      component:() => import('@/views/RegisterView.vue')
    },
    {
      path:'/student',
      name:'Student',
      component:() => import('@/views/StudentView.vue')
    },
    {
      path:'/teacher',
      name:'Teacher',
      component:() => import('@/views/TeacherView.vue')
    },
    {
      path:'/addClass',
      name:'AddClass',
      component:()=>import('@/views/addClassView.vue'),
    },
    {
      path:'/editClass',
      name:'EditClass',
      component:()=>import('@/views/editClassView.vue'),
    },
    {
      path:'/administer',
      name:'Administer',
      component:()=>import('@/views/ManagerView.vue')
    }
  ],
})

export default router
