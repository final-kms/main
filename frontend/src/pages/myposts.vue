<script setup>
import AnalyticsAward from '@/views/dashboard/AnalyticsAward.vue';
import Post from '@/views/post/Post.vue';

const serverAddress = inject('serverAddress')
const auth = inject('auth')

const posts = ref([])

async function getPosts(){

  const response = await fetch(
      `http://${serverAddress}/api/post/read/my`,
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
    posts.value = await response.json()
  }
}

getPosts()
</script>

<template>
  <VRow>
    <VCol
      cols="12"
      md="8"
      class="mb-4"
    >
    
    <div v-for="(item, index) in posts" :key="index">
      <!-- {{ item }}       -->
      <Post :post="item" style="margin-bottom: 20px;"/>
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
