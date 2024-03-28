<script setup>
import AnalyticsAward from '@/views/dashboard/AnalyticsAward.vue';
import ChatRoomSummary from '@/views/chat/ChatRoomSummary.vue';
import socketModule from '/test/index.js'
import { useRouter } from 'vue-router';

const serverAddress = inject('serverAddress')

const auth = inject('auth')

const router = useRouter()

//socketModule.connectionString = `ws://${serverAddress}/ws`

// socketModule.setConnectionString(`ws://${serverAddress}/ws`)
// socketModule.connect()

//socketModule.socket.value.push
//socketModule.socket.value.close();

const chatRooms = ref([])

async function getChatRooms(){

  const response = await fetch(
    `http://${serverAddress}/api/chatroomuser/read`,
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
    chatRooms.value = await response.json()
  }
}


const chatRoom = ref({
  name: ''
})

async function createChatRoom(){

  const formData = {
    name: chatRoom.value.name
  }

  const response = await fetch(
      `http://${serverAddress}/api/chatroom/create`,
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
    getChatRooms()
    //router.push('/')  
  }
}


async function moveChatRoom(chatRoomId){
  
  router.push({
      path: `/chatroom/${chatRoomId}`
  })
  
}

getChatRooms()
</script>

<template>
  <VRow>
    <VCol
      cols="12"
      md="8"
      class="mb-4"
    >
    <div>
      <form @submit.prevent="createChatRoom()">        
        {{ chatRoom.name }}
        <input type="text" v-model="chatRoom.name"></input>
        <input type="submit">정보 제출</input>
      </form>
    </div>
    <hr/>
    <div v-for="(item, index) in chatRooms" :key="index">
      <div>    
        <ChatRoomSummary :chatRoom="item.chatRoom" style="margin-bottom: 20px;" @click="moveChatRoom(item.chatRoom.id)" />
      </div>
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
