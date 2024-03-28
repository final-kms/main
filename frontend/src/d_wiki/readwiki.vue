<script setup>
import axios from 'axios'
import { useRoute, useRouter } from 'vue-router'

// 토큰 브라우저에서 받아오기
const serverAddress = inject('serverAddress')
const auth = inject('auth')

// 토큰 브라우저에서 받아오기
let authToken = 'Bearer '
let jwtToken = auth

authToken = authToken + jwtToken

const route = useRoute()
const router = useRouter()

let date = ''
const state = reactive({
  items: [],
  like: [],
  star: [],
})

const likedItems = reactive([]) // 사용자가 좋아하는 항목의 ID를 저장하는 배열
const starredItems = reactive([])

axios
  .get(`http://${serverAddress}/api/wiki/read/${route.params.id}`, {
    headers: {
      Authorization: authToken,
    },
  })
  .then(res => {
    state.items = res.data
    date = new Date(state.items?.date)

    date =
      date.getFullYear() +
      '-' +
      (date.getMonth() + 1).toString().padStart(2, '0') +
      '-' +
      date.getDate().toString().padStart(2, '0') +
      ' ' +
      date.getHours().toString().padStart(2, '0') +
      ':' +
      date.getMinutes().toString().padStart(2, '0') +
      ':' +
      date.getSeconds().toString().padStart(2, '0')

    console.log(state.items)
    console.log(state.items.user.username)

    getLikes(state.items?.id)
    getStars(state.items?.id)

    axios
      .get(`http://${serverAddress}/api/wikilike/readLikePersonal/${state.items?.id}`, {
        headers: {
          Authorization: authToken,
        },
      })
      .then(res2 => {
        state.like = res2.data

        if (state.like.wiki != undefined) {
          likedItems.push(state.items?.id)
        }
      })

    axios
      .get(`http://${serverAddress}/api/wikistar/readStarPersonal/${state.items?.id}`, {
        headers: {
          Authorization: authToken,
        },
      })
      .then(res3 => {
        state.star = res3.data
        if (state.star.wiki != undefined) {
          starredItems.push(state.items?.id)
        }
      })
  })

function sendIdToUpdate(id) {
  router.push({ path: `/updatewiki/${id}` })
}

function sendIdToViewLogs(id) {
  router.push({ path: `/wikilog/${id}` })
}

function deleteAlert() {
  if (confirm('정말로 삭제하시겠습니까?')) {
    axios
      .delete(`http://${serverAddress}/api/wiki/delete/${route.params.id}`, {
        headers: {
          Authorization: authToken,
        },
      })
      .then(res => {
        // 삭제가 성공하면 어떤 동작을 수행할지 정의
        router.push('/wiki')
      })
      .catch(error => {
        console.error('삭제 중 에러가 발생했습니다:', error)
      })
  }
}

const likestar = reactive({
  like: [],
  star: [],
})

async function toggleLike(wikiId) {
  if (isLiked(wikiId)) {
    likedItems.splice(likedItems.indexOf(wikiId), 1)
  } else {
    likedItems.push(wikiId)
  }
  await checkLike(wikiId)
}

async function toggleStar(wikiId) {
  if (isStarred(wikiId)) {
    starredItems.splice(starredItems.indexOf(wikiId), 1)
  } else {
    starredItems.push(wikiId)
  }

  await checkStar(wikiId)
}
function isLiked(wikiId) {
  return likedItems.includes(wikiId)
}

function isStarred(wikiId) {
  return starredItems.includes(wikiId)
}

async function checkLike(wikiId) {
  const res = await axios
    .create({
      baseURL: `http://${serverAddress}/api/wikilike/check/${wikiId}`,
      headers: { Authorization: authToken },
      withCredentials: true,
    })
    .post()

  getLikes(wikiId)
}

async function checkStar(wikiId) {
  const res = await axios
    .create({
      baseURL: `http://${serverAddress}/api/wikistar/check/${wikiId}`,
      headers: { Authorization: authToken },
      withCredentials: true,
    })
    .post()

  getStars(wikiId)
}

async function getLikes(wikiId) {
  const res = await axios
    .create({
      baseURL: `http://${serverAddress}/api/wikilike/read/${wikiId}`,
      headers: { Authorization: authToken },
      withCredentials: true,
    })
    .get()
  const likes = res.data
  likestar.like[wikiId] = likes
}

async function getStars(wikiId) {
  const res = await axios
    .create({
      baseURL: `http://${serverAddress}/api/wikistar/read/${wikiId}`,
      headers: { Authorization: authToken },
      withCredentials: true,
    })
    .get()
  const stars = res.data
  likestar.star[wikiId] = stars
}

const cancelForm = () => {
  // 이전 페이지로 이동
  router.go(-1)
}
</script>

<template>
  <VCardTitle>Wiki List</VCardTitle>
  <!-- <table class="wikititle" border="1" style="border-collapse: collapse" width="50%">
        <div style="margin-left:20px">{{ wiki?.user.username }} &nbsp; {{ wiki?.date.substring(0,10) }}
        {{ wiki?.date.substring(12,19) }} &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
         </div>
    <div align="right" margin-right="20px" float="left">
      <button type="button" v-if="userLikes[wiki?.id] !== undefined" @click="checkLike(`${wiki?.id}`)">
          <font-awesome-icon :icon="['fas', 'thumbs-up']" /> 
        </button>  
        <button type="button" v-else @click="checkLike(`${wiki?.id}`)">
          <font-awesome-icon :icon="['far', 'thumbs-up']" />
        </button>
        {{ likestar.like[wiki?.id] }}
         &nbsp; 

         <button v-if="userStars[wiki?.id] !== undefined" type="button" @click="checkStar(`${wiki?.id}`)">
          <font-awesome-icon :icon="['fas', 'star']" />
        </button>
        <button v-else type="button" @click="checkStar(`${wiki?.id}`)">
          <font-awesome-icon :icon="['far', 'star']" />
        </button>
        {{ likestar.star[wiki?.id] }} &nbsp;
    </div>
    <br/>
    <div>
      <table border="1" width="100%" style="border-collapse: collapse;">
        <tr align="center">
          <td>{{ wiki?.title }}</td>
        </tr>
        <tr align="center" height="500">
          <td>{{ wiki?.content }}</td>
        </tr>
      </table>
    </div>
    </table>' -->

  <div style="display: flex; justify-content: space-between">
    <div style="text-align: left; color: #905dff; margin: 10px">{{ state.items?.tag }}</div>
    <div style="margin: 10px; margin-left: auto">
      <button
        type="button"
        @click="toggleLike(state.items?.id)"
        :class="{ active: isLiked(state.items?.id) }"
      >
        <font-awesome-icon :icon="isLiked(state.items?.id) ? ['fas', 'thumbs-up'] : ['far', 'thumbs-up']" />
      </button>
      {{ likestar.like[state.items?.id] }}
    </div>
    <div style="margin: 10px; float: right">
      <button
        type="button"
        @click="toggleStar(state.items?.id)"
        :class="{ active: isStarred(state.items?.id) }"
      >
        <font-awesome-icon :icon="isStarred(state.items?.id) ? ['fas', 'star'] : ['far', 'star']" />
      </button>
      {{ likestar.star[state.items?.id] }}
    </div>
  </div>
  <div
    width="100%"
    style="color: gray"
  >
    <h1>{{ state.items?.title }}</h1>
    <p>&nbsp;{{ state.items.user?.username }} &nbsp; {{ date }}에 최종 변경</p>
  </div>
  <br />
  <div
    v-html="`${state.items?.content}`"
    style="width: 100%; height: 50%"
  />

  <div
    class="button-container"
    align="center"
    style="flex: auto; float: right; padding-top: 50px"
  >
    <VBtn @click="cancelForm"> 목록</VBtn>
    <VBtn @click="sendIdToViewLogs(state.items?.id)">변경 이력</VBtn>
    <VBtn @click="sendIdToUpdate(state.items?.id)">수정</VBtn>
    <VBtn @click="deleteAlert()">삭제</VBtn>
  </div>
  <!-- <button v-if="isAuthorized" @click="edit">수정</button> -->
</template>

<style>
.button-container button {
  margin-right: 10px; /* 각 버튼 오른쪽에 마진을 추가합니다. */
}
</style>
