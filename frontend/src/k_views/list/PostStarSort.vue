<script setup>
import { onMounted, ref } from 'vue';
import PostStar from '@/k_views/star/PostStar.vue';

const serverAddress = inject('serverAddress')
const auth = inject('auth')

const posts = ref([])

async function getPosts() {
  try {
    const response = await fetch(
      `http://${serverAddress}/api/post/read`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${auth}`
        },
        credentials: 'include'
      }
    );
    if (!response.ok) {
      alert("실패!")
    }
    const beforePosts = await response.json()
    
    posts.value = await Promise.all(beforePosts.map(async (post) => {
      const stars = await getStars(post.id)
      return { ...post, stars: stars }
    }))

    posts.value.sort((a, b) => b.stars - a.stars)

  } catch (error) {
    console.error(error)
    alert("실패!")
  }
}

onMounted(getPosts)

async function getStars(postId) {
  try {
    const response = await fetch(`http://${serverAddress}/api/poststar/read/${postId}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${auth}`
      },
      credentials: 'include'
    })

    if (!response.ok) {
    //   alert("실패!")
    }
    const stars = await response.json()
    return stars
  } catch (error) {
    console.error(error)
    // alert("실패!")
  }
}

</script>

<template>
  <VTable>  
    <thead>
      <tr>
        <th class="text-center">
          Title
        </th>
        <th class="text-uppercase text-center">
          Star
        </th>
      </tr>
    </thead>

    <tbody>
      <tr v-for="(item, index) in posts.slice(0,5)" :key="index">
        <td class="text-center">
          {{ item.title }}
        </td>
        <td class="text-center">
          <PostStar :poststar="item" />
        </td>
      </tr>
    </tbody>
  </VTable>

</template>
