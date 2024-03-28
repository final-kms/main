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

const logs = reactive([])
let date = reactive([])

axios
  .get(`http://${serverAddress}/api/wikilog/readall/${route.params.id}`, {
    headers: {
      Authorization: authToken,
    },
    withCredentials: true,
  })
  .then(res => {
    logs.value = res.data

    for (let i = 0; i < logs.value.length; i++) {
      date[i] = new Date(logs.value[i].date)

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
    }
  })

const cancelForm = () => {
  // 이전 페이지로 이동
  router.go(-1)
}
</script>

<template>
  <h2>{{ route.params.id }}번 게시글 변경 이력</h2>
  <br />
  <table
    border="1"
    style="width: 50%"
    height=""
  >
    <tr align="center">
      <td>Log ID</td>
      <td>Date</td>
      <td>title</td>
      <td>content</td>
    </tr>
    <tr
      align="center"
      v-for="(item, idx) in logs.value"
    >
      <td>{{ item.id }}</td>
      <td>{{ date[idx] }}</td>
      <td>{{ item.title }}</td>
      <td v-html="`${item.content}`"></td>
    </tr>
  </table>
  <br />
  <VBtn @click="cancelForm">뒤로가기</VBtn>
</template>
