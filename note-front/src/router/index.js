import Vue from 'vue'
import VueRouter from 'vue-router'
import { isLogin } from '@/utils/userUtil'
import { Message } from 'element-ui'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
NProgress.configure({ showSpinner: false }) // 关闭进度条圆环

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: () => import('../views/index/Index.vue'),
    redirect: '/info',
    children: [
      {
        path: '/info',
        component: () => import('../views/index/childrens/Info.vue'),
        name: 'info'
      },
      {
        path: '/notes',
        component: () => import('../views/notes/Index.vue')
      },
      {
        path: '/submit-note',
        component: () => import('../views/submitNote/Index.vue'),
        meta: {
          requiresAuth: true
        }
      },
      {
        path: '/self',
        component: () => import('../views/self/Index.vue'),
        redirect: '/self/notes',
        children: [
          {
            path: 'award',
            component: () => import('../views/self/childrens/Award.vue'),
            meta: {
              requiresAuth: true
            }
          },
          {
            path: 'notes',
            component: () => import('../views/self/childrens/Notes.vue'),
            meta: {
              requiresAuth: true
            }
          },
          {
            path: 'collect',
            component: () => import('../views/self/childrens/Collect.vue'),
            meta: {
              requiresAuth: true
            }
          }
        ]
      },
      {
        path: '/group',
        component: () => import('../views/group/index.vue')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('../views/login/Login.vue')
  },
  {
    path: '/register',
    component: () => import('../views/login/Register.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  // 开启进度条
  NProgress.start()

  const result = isLogin()

  // 用户已登录，自行进入登录和注册页面，不让进入，跳转回之前页面
  if ((to.fullPath == '/login' || to.fullPath == '/register') && result) {
    Message.warning('已登录，请勿重复登录与注册')
    next(from.fullPath)
  }

  // 判断用户进入的页面是否需要权限，未登录跳转登录页面，已登录放行用户
  if (to.meta.requiresAuth) {
    if (result) {
      next()
    } else {
      Message.warning('请先登录')
      next('/login')
    }
  }

  next()
})

router.afterEach(() => {
  // 关闭进度条
  NProgress.done()
})

export default router
