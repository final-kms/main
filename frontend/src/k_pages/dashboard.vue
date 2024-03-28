<script setup>
import AnalyticsAward from '@/views/dashboard/AnalyticsAward.vue';
import Post from '@/views/post/Post.vue';
import PostLikeSort from '@/k_views/list/PostLikeSort.vue';
import PostStarSort from '@/k_views/list/PostStarSort.vue';
import PostDateSort from '@/k_views/list/PostDateSort.vue';
import { VCard } from 'vuetify/lib/components/index.mjs';

const totalProfit = {
  title: 'Total Profit',
  color: 'secondary',
  icon: 'ri-pie-chart-2-line',
  stats: '$25.6k',
  change: 42,
  subtitle: 'Weekly Project',
}

const newProject = {
  title: 'New Project',
  color: 'primary',
  icon: 'ri-file-word-2-line',
  stats: '862',
  change: -18,
  subtitle: 'Yearly Project',
}

const posts = ref([])

async function getPosts(){

  const response = await fetch(
      `http://localhost:8080/api/post/read`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
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
      <div v-for="(item, index) in posts.slice().reverse()" :key="index">
        <Post :post="item" style="margin-bottom: 20px;"/>
      </div>
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
