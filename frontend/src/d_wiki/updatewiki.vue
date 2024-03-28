<script setup>
import MyEditor from '@/d_wiki/wikieditor.vue'
import { onMounted } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

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

const router = useRouter()
const route = useRoute()

const wiki = reactive({
  title: '',
  category: '',
  content: '',
})

async function readWiki() {
  try {
    const res = await axios.get(`http://localhost:8080/api/wiki/read/${route.params.id}`, {
      headers: {
        Authorization: authToken,
      },
    })
    const data = res.data
    wiki.title = data.title
    wiki.category = data.category
    wiki.content = data.content
  } catch (error) {
    console.log('에러 발생: ', error)
  }
}

const submitForm = async () => {
  const formData = {
    title: wiki.title,
    category: wiki.category,
    content: wiki.content,
    tag: wiki.tag,
  }
  const headers = {
    'Content-Type': 'application/json',
    Authorization: authToken,
  }
  // 서버로 보낼 데이터
  const data = JSON.stringify(formData)

  axios.put(`http://localhost:8080/api/wiki/update/${route.params.id}`, data, {
    headers,
  })

  try {
  } catch (error) {
    console.error('에러 발생: ', error)
  }
}

const cancelForm = () => {
  // 이전 페이지로 이동
  router.go(-1)
}

readWiki(route.params.id)
</script>

<style scoped>
table {
  border-collapse: collapse;
  border-radius: 10px;
  border-style: hidden;
  box-shadow: 0 0 0 1px #000;
}

td,
th {
  border: 3px solid #ccc; /* 테두리 스타일 및 색상을 설정합니다. */
}
</style>
<template>
  <div class="container mx-auto max-w-4xl my-8">
    <form @submit.prevent="submitForm">
      <div>
        <input
          type="text"
          class="w-full border border-gray-400 p-2"
          v-model="wiki.title"
          placeholder="title"
          style="padding-left: 10px; width: 100%; height: 40px"
        />
      </div>
      <div>
        <MyEditor
          :modelValue="wiki.content"
          contentType="html"
        />
      </div>
      <div>
        <input
          class="w-full border border-gray-400 p-2"
          type="text"
          v-model="wiki.category"
          placeholder="category"
          style="padding-left: 10px; width: 100%; height: 40px"
        />
      </div>

      <!-- <textarea
            placeholder="content"
            v-model="formData.content"
            rows="25"
            cols="130"
            style="padding-left: 20px; padding-top: 10px; padding-bottom: 10px"
          ></textarea> -->

      <tr align="center">
        <td>
          <button
            style="
              background-color: #905dff;
              border: none;
              color: white;
              padding: 7px 15px;
              text-align: center;
              text-decoration: none;
              display: inline-block;
              border-style: solid;
              font-size: 15px;
            "
            type="submit"
          >
            작성
          </button>
          <button
            style="
              background-color: #905dff;
              border: none;
              color: white;
              padding: 7px 15px;
              text-align: center;
              text-decoration: none;
              display: inline-block;
              border-style: solid;
              font-size: 15px;
            "
            type="button"
            @click="cancelForm"
          >
            취소
          </button>
        </td>
      </tr>
    </form>
  </div>
</template>
