<script setup>
import AnalyticsAward from '@/views/dashboard/AnalyticsAward.vue';
import socketModule from '/test/index.js'


const serverAddress = inject('serverAddress')
const auth = inject('auth')

// websocket

socketModule.setConnectionString(`ws://${serverAddress}/ws`)
socketModule.connect()

socketModule.onOpen(() => {
  const formData = { auth: auth }
  socketModule.socket.value.send(JSON.stringify(formData))
})

socketModule.onMessage(message => {
    const jsonData = JSON.parse(message.data)
    jsonData.date = new Date(jsonData.date)
    chats.value.push(jsonData)
})

socketModule.onClose(() => {
  socketModule.connect()
})
//socketModule.socket.value.push
//socketModule.socket.value.close();


///////


const props = defineProps({
    roomId: String
})

// 초기에 빈 배열로 시작
const inviteUsers = ref([{ email: '' }])
const chatRoomUsers = ref([])

const chats = ref([])

// 사용자 정보를 서버로 전송하는 함수
async function addChatRoomUsers(){
  const formData = { email : [] }
  inviteUsers.value.forEach(function(user) {
    formData.email.push(user.email)
  })

  inviteUsers.value = [{ email : ''}]

  const response = await fetch(
    `http://${serverAddress}/api/chatroomuser/create/${props.roomId}`,
    {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${auth}`,          
      },
      body: JSON.stringify(formData),
      credentials: 'include'
    }
  )

  if(!response.ok) {
    alert("실패!")
  } else{
    getChatRoomUsers()
  }
}

async function getChatRoomUsers(){
  const response = await fetch(
    `http://${serverAddress}/api/chatroomuser/read/${props.roomId}`,
    {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${auth}`,          
      },
      credentials: 'include'
    }
  )

  if(!response.ok) {
    alert("실패!")
  } else{
    chatRoomUsers.value = await response.json()
  }
}

// 새로운 사용자 추가하는 함수
const addUser = () => {
  inviteUsers.value.push({ email: '' })
}

// socket.value.send(text.value)

const message = ref('')
async function sendMessage(){
  const formData = {
    content: message.value
  }
  const response = await fetch(
    `http://${serverAddress}/api/chat/create/${props.roomId}`,
    {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${auth}`,          
      },
      credentials: 'include',
      body: JSON.stringify(formData)
    }
  )

  if(!response.ok) {
    alert("실패!")
  } else{
    message.value = ''
    getMessages()
  }}

async function getMessages(){
  const response = await fetch(
    `http://${serverAddress}/api/chat/read/${props.roomId}`,
    {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${auth}`,          
      },
      credentials: 'include'
    }
  )

  if(!response.ok) {
    alert("실패!")
  } else{
    chats.value = await response.json()
  }
}



getMessages()
getChatRoomUsers()  
</script>

<template>
  <VRow>
    <VCol
      cols="12"
      md="8"
      class="mb-4"
    >
      <hr/>
      <div>
        <!-- 사용자 입력을 위한 폼 -->
        <form @submit.prevent="addChatRoomUsers">
          <!-- 여러 유저의 정보를 입력할 수 있는 입력란 -->
          <div v-for="(user, index) in inviteUsers" :key="index">
            <label>이메일:</label>
            <input type="email" v-model="user.email"> <!-- required> -->
          </div>
          <!-- 새로운 사용자 추가 버튼 -->
          <button type="button" @click="addUser">새로운 사용자 추가</button><br/>
          <!-- 정보를 서버로 전송하는 버튼 -->
          <button type="submit">정보 제출</button>
        </form>
      </div>    
      <hr/>
      <div>
        <div v-for="(chatRoomUser, index) in chatRoomUsers" :key="index">

          {{ chatRoomUser.user.email }}<br/>
        </div>
      </div>
      <hr/>
      <div>
        <div v-for="(chat, index) in chats" :key="index">
          {{ chat.user.username }} - {{ chat.content }} - {{ chat.date }}
        </div>
      </div>
      <hr/>
      <div>
        <input type="text" v-model="message"></input>
        <button @click="sendMessage">전송</button>
      </div>
    </VCol>  
    <VCol
      cols="12"
      md="4"
    >
      <AnalyticsAward />
    </VCol>  
  </VRow>
</template>
