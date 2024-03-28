<script setup>
import axios from 'axios'
import { reactive } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// 토큰 브라우저에서 받아오기
const serverAddress = inject('serverAddress')
const auth = inject('auth')

// 토큰 브라우저에서 받아오기
let authToken = 'Bearer '
let jwtToken = auth

authToken = authToken + auth
const wiki = reactive([])

axios
  .get(`http://${serverAddress}/api/wiki/read/public`, {
    headers: {
      Authorization: authToken,
    },
  })
  .then(res => {
    wiki.value = res.data
  })

const rankedItems = computed(() => {
  if (!wiki.value) return [] // wiki.value가 비어 있는 경우 빈 배열 반환

  // date 속성을 기준으로 내림차순으로 wiki.value를 정렬
  const sortedItems = wiki.value.slice().sort((a, b) => {
    return new Date(b.date) - new Date(a.date)
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
    <VCardTitle class="text-center">최신 Wiki 게시글</VCardTitle>
    <VCardText>
      <div
        style="justify-content: space-between; display: flex; margin: 5px"
        v-for="(item, idx) in rankedItems"
      >
        <div>{{ idx + 1 }}</div>
        <div style="text-align: center; flex: 1">
          <a @click="readWiki(item.id)">{{ item.title }}</a>
        </div>
      </div>
    </VCardText>
  </VCard>
</template>
