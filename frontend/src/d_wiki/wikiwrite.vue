<script>
import axios from 'axios'

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
console.log('토큰:', authToken)

const route = useRoute()

export default {
  data() {
    return {
      formData: {
        title: '',
        category: '',
        content: '',
        tag: '',
      },
    }
  },
  methods: {
    submitForm() {
      // 헤더 정보 설정
      const headers = {
        'Content-Type': 'application/json',
        Authorization: authToken,
      }

      // 서버로 보낼 데이터
      const data = JSON.stringify(this.formData)

      // POST 요청 보내기
      axios
        .post('http://localhost:8080/api/wiki/create', data, { headers })
        .then(response => {
          console.log('서버로부터의 응답:', response.data)
          // 서버로부터의 응답에 따라 적절한 동작을 수행할 수 있음.
          // ex) 성공 메시지를 표시하거나 페이지를 리디렉션할 수 있음.

          // const router = useRouter()  -- 라우터로 리다이렉트 하기 위한 코드. 잘 안됨. 보류.
          // router.push(route.query.redirect || '/dashboard');
          window.location.href = 'http://localhost:5173/dashboard'
        })
        .catch(error => {
          console.error('에러 발생:', error)
          // 에러 처리 로직을 추가할 수 있습니다.
        })
    },
  },
}
</script>

<style scoped>
table {
  border-collapse: collapse;
  border-radius: 10px;
  border-style: hidden;
  box-shadow: 0 0 0 1px #905dff;
}

td,
th {
  border: 1px solid #905dff; /* 테두리 스타일 및 색상을 설정합니다. */
}
</style>
<template>
  <h2>Write Wiki</h2>
  <br />
  <form @submit.prevent="submitForm">
    <table
      border="1"
      width="90%"
      height="700"
    >
      <tr>
        <td>
          <input
            type="text"
            v-model="formData.title"
            placeholder="title"
            style="padding-left: 20px; padding-top: 10px; padding-bottom: 10px; width: 100%"
          />
        </td>
      </tr>
      <tr>
        <td>
          <input
            type="text"
            v-model="formData.category"
            placeholder="category"
            style="padding-left: 20px; padding-top: 10px; padding-bottom: 10px"
          />
        </td>
      </tr>
      <tr>
        <td>
          <textarea
            placeholder="content"
            v-model="formData.content"
            rows="25"
            cols="130"
            style="padding-left: 20px; padding-top: 10px; padding-bottom: 10px"
          ></textarea>
        </td>
      </tr>
      <tr>
        <td>
          <input
            type="text"
            v-model="formData.tag"
            placeholder="tag"
            style="padding-left: 20px; padding-top: 10px; padding-bottom: 10px; width: 100%"
          />
        </td>
      </tr>
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
            @click="location.href = 'this.$router.go(-1)'"
          >
            취소
          </button>
        </td>
      </tr>
    </table>
  </form>
</template>
