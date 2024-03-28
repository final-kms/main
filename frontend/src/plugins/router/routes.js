export const routes = [
  { path: '/', redirect: '/dashboard' },
  {
    path: '/',
    component: () => import('@/layouts/default.vue'),
    children: [
      {
        path: 'dashboard',
        component: () => import('@/pages/dashboard.vue'),
      },
      ///////////////// 대호

      {
        path: 'wiki',
        component: () => import('@/d_wiki/wikilist.vue'),
      },
      {
        path: '/readwiki/:id',
        name: 'readwiki',
        component: () => import('@/d_wiki/readwiki.vue'),
        props: true // URL 파라미터를 컴포넌트의 props로 전달합니다.
      }, 

      {
        path: 'writewiki',
        component: () => import('@/d_wiki/wikieditorwrite.vue')
      },

      {
        path: 'updatewiki/:id',
        component: () => import('@/d_wiki/wikiupdate.vue'),
        props: true
      },

      {
        path: 'wikilog/:id',
        component: () => import('@/d_wiki/viewwikilogs.vue'),
        props: true
      },


      /////////////////////////////////////
      /*
      {
        path: 'wiki',
        component: () => import('@/pages/wiki.vue'),
      },

      {
        path: 'writewiki',
        component: () => import('@/pages/writewiki.vue')
      },

      */

      /////////////////////////////////////



      // updatepost
      //////////////////////////// 규혁
      {
        path: 'updatepost/:id',
        component: () => import('@/k_pages/updatepost.vue'),
        props: true
      },
      {
        path: 'writepost',
        component: () => import('@/k_pages/writepost.vue')
      },

      /*
      {
        path: 'writepost',
        component: () => import('@/pages/writepost.vue')
      },

      */
      /////////////////////////////////////

      {
        path: 'account-settings',
        component: () => import('@/pages/account-settings.vue'),
      },



      {
        path: 'table',
        component: () => import('@/pages/table.vue')
      },
      {
        path: 'createtable',
        component: () => import('@/pages/createtable.vue')
      },

      
      {
        path: 'chat',
        component: () => import('@/pages/chathome.vue')
      },
      {
        path: 'chatroom/:roomId',
        component: () => import('@/pages/chatroom.vue'),
        props: true
      },



      {
        path: 'follow',
        component: () => import('@/pages/follow.vue')
      },




      {
        path: `/search/:keyword/Post`,
        component: () => import('@/pages/dashboard.vue'),
        props: true
      },
      {
        path: `/search/:keyword/Wiki`,
        component: () => import('@/pages/wiki.vue'),
        props: true
      },



      {
        path: '/search',
        component: () => import('@/pages/dashboard.vue'),
        props: true
      }
    ],
  },
  {
    path: '/',
    component: () => import('@/layouts/blank.vue'),
    children: [
      {
        path: 'login',
        component: () => import('@/pages/login.vue'),
      },
      {
        path: 'logout',
        component: () => import('@/pages/logout.vue'),
      },
      {
        path: 'register',
        component: () => import('@/pages/register.vue'),
      },
      {
        path: '/:pathMatch(.*)*',
        component: () => import('@/pages/[...error].vue'),
      },
    ],
  },
  // mypage
  {
    path: '/',
    component: () => import('@/layouts/mypage.vue'),
    children: [
      {
        path: 'mypage',
        component: () => import('@/pages/mypage.vue'),
      },
      {
        path: 'myinfo',
        component: () => import('@/pages/mypage.vue'),
      },
      {
        path: 'mytables',
        component: () => import('@/pages/mytables.vue'),
      },
      {
        path: 'mytables/:tableId',
        component: () => import('@/pages/tabledetail.vue'),
        props: true
      },
      {
        path: 'writepost/:tableId',
        component: () => import('@/pages/writetablepost.vue'),
        props: true
      },
      {
        path: 'writewiki/:tableId',
        component: () => import('@/pages/writetablewiki.vue'),
        props: true
      },




      {
        path: 'myposts',
        component: () => import('@/pages/myposts.vue'),
      },
      {
        path: 'mywikis',
        component: () => import('@/pages/mywikis.vue'),
      },
      {
        path: 'myreplys',
        component: () => import('@/pages/myreplys.vue'),
      },
      {
        path: 'mylikes',
        component: () => import('@/pages/mylikes.vue'),
      },
      {
        path: 'mystars',
        component: () => import('@/pages/mystars.vue'),
      },

    ],
  },
]

export default routes