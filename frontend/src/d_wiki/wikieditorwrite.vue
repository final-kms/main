<script setup>
import MyEditor from '@/d_wiki/wikieditor.vue'
import axios from 'axios'
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()

const serverAddress = inject('serverAddress')
const auth = inject('auth')

// 토큰 브라우저에서 받아오기
let authToken = 'Bearer '
let jwtToken = auth

authToken = authToken + jwtToken

const formData = ref({
  title: '',
  content: '',
  category: '',
})

if (route.params.id) {
  axios
    .get(`http://${serverAddress}/api/wiki/read/${route.params.id}`, {
      headers: {
        Authorization: authToken,
      },
    })
    .then(res => {
      formData.value.title = res.data.title
      formData.value.content = res.data.content
      formData.value.category = res.data.category.name
    })
}

function submitForm() {
  // 헤더 정보 설정
  const headers = {
    'Content-Type': 'application/json',
    Authorization: authToken,
  }

  // 서버로 보낼 데이터
  const data = JSON.stringify(formData.value)

  console.log(data)
  // POST 요청 보내기
  axios
    .post(`http://${serverAddress}/api/wiki/create`, data, { headers })
    .then(response => {
      console.log('서버로부터의 응답:', response.data)
      // 서버로부터의 응답에 따라 적절한 동작을 수행할 수 있음.
      // ex) 성공 메시지를 표시하거나 페이지를 리디렉션할 수 있음.

      // const router = useRouter()  -- 라우터로 리다이렉트 하기 위한 코드. 잘 안됨. 보류.
      // router.push(route.query.redirect || '/dashboard');
      router.push({ path: '/wiki' })
    })
    .catch(error => {
      console.error('에러 발생:', error)
      // 에러 처리 로직을 추가할 수 있습니다.
    })
}
const props = defineProps({
  posts: Array,
})

const cancelForm = () => {
  // 이전 페이지로 이동
  router.go(-1)
}
</script>

<template>
  <div class="container mx-auto max-w-4xl my-8">
    <form
      @submit.prevent="submitForm"
      class="space-y-8"
    >
      <div>
        <input
          type="text"
          class="w-full border border-gray-400 p-2"
          v-model="formData.title"
          placeholder="title"
          style="padding-left: 10px; width: 100%; height: 40px"
        />
      </div>
      <br />

      <div>
        <MyEditor v-model="formData.content" />
      </div>

      <br />

      <div>
        <input
          type="text"
          class="w-full border border-gray-400 p-2"
          v-model="formData.category"
          placeholder="category"
          style="padding-left: 10px; width: 100%; height: 40px"
        />
      </div>
      <br />

      <div>
        <VBtn
          type="submit"
          style="margin-right: 10px"
        >
          Create Wiki
        </VBtn>
        <VBtn
          type="button"
          @click="cancelForm"
        >
          Cancel
        </VBtn>
      </div>
    </form>
  </div>
</template>
