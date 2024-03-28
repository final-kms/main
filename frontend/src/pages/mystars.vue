<script setup>
import AnalyticsAward from '@/views/dashboard/AnalyticsAward.vue';
import Post from '@/views/post/Post.vue';
import Wiki from '@/views/wiki/Wiki.vue';


const serverAddress = inject('serverAddress')
const auth = inject('auth')

const posts = ref([])
const wikis = ref([])

async function getPosts(){

  const response = await fetch(
      `http://${serverAddress}/api/poststar/read/my`,
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

async function getWikis(){

const response = await fetch(
    `http://${serverAddress}/api/wikistar/read/my`,
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
  wikis.value = await response.json()
}
}

getPosts()
getWikis()
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
      <Post :post="item.post" style="margin-bottom: 20px;"/>
    </div>

    <div v-for="(item, index) in wikis" :key="index">
      <!-- {{ item }}       -->
      <Wiki :wiki="item.wiki" style="margin-bottom: 20px;"/>
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
