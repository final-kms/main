<script setup>
import AnalyticsAward from '@/views/dashboard/AnalyticsAward.vue';
import Post from '@/views/post/Post.vue';
import Wiki from '@/views/wiki/Wiki.vue';
const auth = inject('auth')
const serverAddress = inject('serverAddress')

const switchContent = ref(true)

const posts = ref([])

async function getPosts(){

const response = await fetch(
    `http://${serverAddress}/api/post/read/table`,
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

const wikis = ref([])

async function getWikis(){

const response = await fetch(
    `http://${serverAddress}/api/wiki/read/table`,
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
    <VBtn
    @click="switchContent=true"
    >
      Post
    </VBtn>
    <VBtn
    @click="switchContent=false"
    >
      Wiki
    </VBtn>

    <div v-if="switchContent" v-for="(item, index) in posts" :key="index">
      <Post :post="item" style="margin-bottom: 20px;"/>
    </div>
    <div v-if="!switchContent" v-for="(item, index) in wikis" :key="index">
      <Wiki :wiki="item" style="margin-bottom: 20px;"/>
    </div>
        <!-- <wiki style="margin-bottom: 20px;"/>
        <wiki style="margin-bottom: 20px;"/>
        <wiki style="margin-bottom: 20px;"/> -->
    </VCol>  
    <VCol
      cols="12"
      md="4"
    >
        <AnalyticsAward />
    </VCol>  

  </VRow>
</template>
