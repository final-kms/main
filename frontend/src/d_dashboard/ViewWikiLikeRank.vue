<!-- eslint-disable no-restricted-imports -->
<script setup>
import { hexToRgb } from '@layouts/utils'
import axios from 'axios'
import { useTheme } from 'vuetify'
import { useRouter } from 'vue-router'

const router = useRouter()

// 토큰 브라우저에서 받아오기
const serverAddress = inject('serverAddress')
const auth = inject('auth')

// 토큰 브라우저에서 받아오기
let authToken = 'Bearer '
let jwtToken = auth

authToken = authToken + auth

const vuetifyTheme = useTheme()

const series = [
  {
    data: [0, 20, 5, 30, 15, 45],
  },
]

const chartOptions = computed(() => {
  const currentTheme = vuetifyTheme.current.value.colors
  const variableTheme = vuetifyTheme.current.value.variables

  return {
    chart: {
      parentHeightOffset: 0,
      toolbar: { show: false },
    },
    tooltip: { enabled: false },
    grid: {
      borderColor: `rgba(${hexToRgb(String(variableTheme['border-color']))},${variableTheme['border-opacity']})`,
      strokeDashArray: 6,
      xaxis: { lines: { show: true } },
      yaxis: { lines: { show: false } },
      padding: {
        top: -10,
        left: -7,
        right: 5,
        bottom: 5,
      },
    },
    stroke: {
      width: 3,
      lineCap: 'butt',
      curve: 'straight',
    },
    colors: [currentTheme.primary],
    markers: {
      size: 6,
      offsetY: 4,
      offsetX: -2,
      strokeWidth: 3,
      colors: ['transparent'],
      strokeColors: 'transparent',
      discrete: [
        {
          size: 5.5,
          seriesIndex: 0,
          strokeColor: currentTheme.primary,
          fillColor: currentTheme.surface,
          dataPointIndex: series[0].data.length - 1,
        },
      ],
      hover: { size: 7 },
    },
    xaxis: {
      labels: { show: false },
      axisTicks: { show: false },
      axisBorder: { show: false },
    },
    yaxis: { labels: { show: false } },
  }
})

const wiki = reactive([])

axios
  .get(`http://${serverAddress}/api/wiki/read/public`, {
    headers: {
      Authorization: authToken,
    },
  })
  .then(res => {
    wiki.value = res.data
    for (let i = 0; i < res.data.length; i++) {
      getLikes(wiki.value[i]?.id)
    }
  })

const likestar = reactive({
  like: [],
  star: [],
})

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


const rankedItems = computed(() => {
  if (!wiki.value) return [] // wiki.value가 비어 있는 경우 빈 배열 반환

  // 좋아요 개수를 기준으로 wiki.value를 정렬
  const sortedItems = wiki.value.slice().sort((a, b) => {
    return likestar.like[b.id] - likestar.like[a.id]
  })

  return sortedItems.slice(0, 5)
})


function readWiki(id){
  router.push({
    path: `/readwiki/${id}`
  })
}

</script>

<template>
  <VCard>
    <VCardTitle class="text-center">Wiki 좋아요 순위</VCardTitle>
    <VCardText>
      <div
        style="justify-content: space-between; display: flex; margin: 5px"
        v-for="(item, idx) in rankedItems"
      >
        <div>{{ idx + 1 }}</div>
        <div>
          <a @click="readWiki(item.id)">{{ item.title }}</a>
        </div>
        <div>{{ likestar.like[item.id] }}</div>
      </div>
    </VCardText>
  </VCard>
</template>
