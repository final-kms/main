<script setup>
import Post from '@/k_views/post/Post.vue';
import PostLikeSort from '@/k_views/list/PostLikeSort.vue';
import PostStarSort from '@/k_views/list/PostStarSort.vue';
import PostDateSort from '@/k_views/list/PostDateSort.vue';

// import post from '@/views/'
import { watchEffect } from 'vue';

const serverAddress = inject('serverAddress')
const auth = inject('auth')

const props = defineProps({
    keyword: String
})

const posts = ref([])

watchEffect(() => {
  if(props.keyword == null){
    getPosts()
  } else{
    searchPost()
  }
});


async function getPosts(){

  const response = await fetch(
      `http://${serverAddress}/api/post/read/public`,
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

async function searchPost(){
  const response = await fetch(
      `http://${serverAddress}/api/post/search/${props.keyword}`,
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


if(props.keyword == null){
  getPosts()
} 
</script>

<template>
  <VRow>
    <VCol
      cols="12"
      md="8"
      class="mb-4"
    >
    
    <div v-for="(item, index) in posts.slice().reverse()" :key="index">

      <Post :post="item" style="margin-bottom: 20px;"/>
    </div>
        <!-- <Post style="margin-bottom: 20px;"/>
        <Post style="margin-bottom: 20px;"/>
        <Post style="margin-bottom: 20px;"/> -->
    </VCol>  
    <VCol
      cols="12"
      md="4"
    >
      <VCard title="추천순" style="margin-bottom: 20px">
        <PostLikeSort style="margin-bottom: 20px" />
      </VCard>
      <VCard title="즐겨찾기순" style="margin-bottom: 20px">
        <PostStarSort style="margin-bottom: 20px" />
      </VCard>
      <VCard title="최신순" style="margin-bottom: 20px">
        <PostDateSort style="margin-bottom: 20px" />
      </VCard>
    </VCol>  

  </VRow>
</template>
