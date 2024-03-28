<script setup>
import AnalyticsAward from '@/views/dashboard/AnalyticsAward.vue';
import Post from '@/views/post/Post.vue';

const auth = inject('auth')
const serverAddress = inject('serverAddress')

const posts = ref([])

async function getPosts(){

  const response = await fetch(
      `http://${serverAddress}/api/reply/read/my`,
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
    posts.value = Array.from(await response.json())
    console.log(posts.value)
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
      
      <Post :post="item.post" style="margin-bottom: 20px;"/>
      
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
