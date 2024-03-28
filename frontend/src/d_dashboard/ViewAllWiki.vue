<!-- eslint-disable no-restricted-imports -->
<script setup>
import { hexToRgb } from '@layouts/utils'
import axios from 'axios'
import { useTheme } from 'vuetify'

const route = useRoute()

//import { useStore } from 'vuex';
// const store = useStore();
// console.log("auth: ",store.state.authToken)

// 토큰 브라우저에서 받아오기
let authToken = 'Bearer '
const cookies = document.cookie.split(';')
let jwtToken = ''

for (let i = 0; i < cookies.length; i++) {
  const cookie = cookies[i].trim()
  // 쿠키 이름이 'jwtToken'으로 시작하는 경우
  if (cookie.startsWith('jwtToken=')) {
    // 'jwtToken'의 값만 추출
    jwtToken = cookie.substring('jwtToken='.length)
    break
  }
}
authToken = authToken + jwtToken

const vuetifyTheme = useTheme()

const options = computed(() => {
  const currentTheme = ref(vuetifyTheme.current.value.colors)
  const variableTheme = ref(vuetifyTheme.current.value.variables)
  const disabledColor = `rgba(${hexToRgb(currentTheme.value['on-surface'])},${variableTheme.value['disabled-opacity']})`
  const borderColor = `rgba(${hexToRgb(String(variableTheme.value['border-color']))},${
    variableTheme.value['border-opacity']
  })`

  return {
    chart: {
      offsetY: -10,
      offsetX: -15,
      parentHeightOffset: 0,
      toolbar: { show: false },
    },
    plotOptions: {
      bar: {
        borderRadius: 6,
        distributed: true,
        columnWidth: '30%',
      },
    },
    stroke: {
      width: 2,
      colors: [currentTheme.value.surface],
    },
    legend: { show: false },
    grid: {
      borderColor,
      strokeDashArray: 7,
      xaxis: { lines: { show: false } },
    },
    dataLabels: { enabled: false },
    colors: [
      currentTheme.value['track-bg'],
      currentTheme.value['track-bg'],
      currentTheme.value['track-bg'],
      'rgba(var(--v-theme-primary),1)',
      currentTheme.value['track-bg'],
      currentTheme.value['track-bg'],
    ],
    states: {
      hover: { filter: { type: 'none' } },
      active: { filter: { type: 'none' } },
    },
    xaxis: {
      categories: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
      tickPlacement: 'on',
      labels: { show: false },
      crosshairs: { opacity: 0 },
      axisTicks: { show: false },
      axisBorder: { show: false },
    },
    yaxis: {
      show: true,
      tickAmount: 4,
      labels: {
        style: {
          colors: disabledColor,
          fontSize: '13px',
        },
        formatter: value => `${value > 999 ? `${(value / 1000).toFixed(0)}` : value}k`,
      },
    },
    responsive: [
      {
        breakpoint: 1560,
        options: { plotOptions: { bar: { columnWidth: '35%' } } },
      },
      {
        breakpoint: 1380,
        options: { plotOptions: { bar: { columnWidth: '45%' } } },
      },
    ],
  }
})

const date = reactive([])

const state = reactive({
  items: [],
  like: [],
  star: [],
})

const likedItems = reactive([]) // 좋아요에 대한 wikiId를 저장하는 배열
const starredItems = reactive([]) // 즐겨찾기에 대한 wikiId를 저장하는 배열

axios
  .get('http://localhost:8080/api/wiki/readall', {
    headers: {
      Authorization: authToken,
    },
  })
  .then(res => {
    state.items = res

    for (let i = 0; i < res.data.length; i++) {
      getLikes(state.items.data[i]?.id)
      getStars(state.items.data[i]?.id)

      date[i] = new Date(state.items.data[i]?.date)

      date[i] =
        date[i].getFullYear() +
        '-' +
        (date[i].getMonth() + 1).toString().padStart(2, '0') +
        '-' +
        date[i].getDate().toString().padStart(2, '0') +
        ' ' +
        date[i].getHours().toString().padStart(2, '0') +
        ':' +
        date[i].getMinutes().toString().padStart(2, '0') +
        ':' +
        date[i].getSeconds().toString().padStart(2, '0')

      axios
        .get(`http://localhost:8080/api/wikilike/readLikePersonal/${state.items.data[i]?.id}`, {
          headers: {
            Authorization: authToken,
          },
        })
        .then(res2 => {
          state.like = res2.data

          if (state.like.wiki != undefined) {
            likedItems.push(state.items.data[i]?.id)
          }
        })

      axios
        .get(`http://localhost:8080/api/wikistar/readStarPersonal/${state.items.data[i]?.id}`, {
          headers: {
            Authorization: authToken,
          },
        })
        .then(res3 => {
          state.star = res3.data
          if (state.star.wiki != undefined) {
            starredItems.push(state.items.data[i]?.id)
          }
        })
    }
  })

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
      baseURL: `http://localhost:8080/api/wikilike/check/${wikiId}`,
      headers: { Authorization: authToken },
      withCredentials: true,
    })
    .post()

  getLikes(wikiId)
}

async function checkStar(wikiId) {
  const res = await axios
    .create({
      baseURL: `http://localhost:8080/api/wikistar/check/${wikiId}`,
      headers: { Authorization: authToken },
      withCredentials: true,
    })
    .post()

  getStars(wikiId)
}

async function getLikes(wikiId) {
  const res = await axios
    .create({
      baseURL: `http://localhost:8080/api/wikilike/read/${wikiId}`,
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
      baseURL: `http://localhost:8080/api/wikistar/read/${wikiId}`,
      headers: { Authorization: authToken },
      withCredentials: true,
    })
    .get()
  const stars = res.data
  likestar.star[wikiId] = stars
}

// function checkLike(wikiId) {
//   console.log("check:", authToken)
//   axios.post(`http://localhost:8080/api/wiki/check/${wikiId}`, {
//     headers: {
//       'Authorization' : authToken
//     },
//   }).then(res => {
//     console.log(res)
//   }).catch((error) => {
//     console.log(error)
//   })
// }
</script>

<style>
.button {
  position: absolute;
  top: 50%;
  background-color: lightgray;
}
</style>
<template>
  <VCol
    v-for="(item, idx) in state.items.data"
    :key="idx"
  >
    <hr />
    <div style="display: flex; justify-content: space-between">
      <div style="text-align: left; color: #905dff; margin: 10px">
        {{ item?.tag }}
      </div>
      <div style="margin: 10px; margin-left: auto">
        <button
          type="button"
          @click="toggleLike(item?.id)"
          :class="{ active: isLiked(item?.id) }"
        >
          <font-awesome-icon :icon="isLiked(item?.id) ? ['fas', 'thumbs-up'] : ['far', 'thumbs-up']" />
        </button>
        {{ likestar.like[item.id] }}
      </div>
      <div style="margin: 10px; float: right">
        <button
          type="button"
          @click="toggleStar(item?.id)"
          :class="{ active: isStarred(item?.id) }"
        >
          <font-awesome-icon :icon="isStarred(item?.id) ? ['fas', 'star'] : ['far', 'star']" />
        </button>
        {{ likestar.star[item.id] }}
      </div>
    </div>
    <div
      width="100%"
      style="color: gray"
    >
      <h1>{{ item?.title }}</h1>
      <p>&nbsp; 작성자: {{ item?.user.username }} &nbsp; {{ date[idx] }} 에 최종 변경</p>
    </div>
    <br />
    <div v-html="`${item?.content}`" />
    <br /><br /><br /><br />
  </VCol>
</template>
