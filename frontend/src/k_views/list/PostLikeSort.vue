<script setup>
import { onMounted, ref } from 'vue';
import PostLike from '@/k_views/like/PostLike.vue';

const serverAddress = inject('serverAddress')
const auth = inject('auth')

const posts = ref([])
const ispostmodal = ref(false)
const selectedPostId = ref(null)

async function getPosts() {
  try {
    const response = await fetch(
      `http://${serverAddress}/api/post/read/public`,
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
      const likes = await getLikes(post.id)
      return { ...post, likes: likes }
    }))

    posts.value.sort((a, b) => b.likes - a.likes)

  } catch (error) {
    console.error(error)
    alert("실패!")
  }
}

onMounted(getPosts)

async function getLikes(postId) {
  try {
    const response = await fetch(`http://${serverAddress}/api/postlike/read/${postId}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${auth}`
      },
      credentials: 'include'
    })

    if (!response.ok) {
      alert("실패!")
    }
    const likes = await response.json()
    return likes
  } catch (error) {
    console.error(error)
    alert("실패!")
  }
}

async function checkLike(postId){

const response = await fetch(
    `http://${serverAddress}/api/postlike/check/${postId}`,
    {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${auth}`
      },
      credentials: 'include'
    }
)

if(!response.ok) {
  alert("실패!")
} else{
  getLikes(postId)
}
}

function clickpostmodal(postId) {
  ispostmodal.value = !ispostmodal.value
  selectedPostId.value = postId
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
          Like
        </th>
      </tr>
    </thead>

    <tbody>
      <tr v-for="(item, index) in posts.slice(0,5)" :key="index">
        <td class="text-center">
          <VIconBtn @click="clickpostmodal(item.id)" style="cursor: pointer;">
            {{ item.title }}
          </VIconBtn>
        </td>
        <td class="text-center">
          <VIconBtn @click="checkLike(item.id)" style="cursor: pointer;">
            <PostLike :postlike="item" />
          </VIconBtn>
        </td>

      </tr>
    </tbody>
  </VTable>

  <div class="modal-wrap" v-show="ispostmodal">
    <div class="modal-container">
      <div>
        <!-- <SelectedPost :post="item" style="margin-bottom: 20px;"/> -->
      </div>
      <div class="modal-btn">
        <button @click="ispostmodal=!ispostmodal"> 확인 </button>
      </div>
    </div>
  </div>

</template>

<style lang="scss">
/* dimmed */
.modal-wrap {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.4);
}
/* modal or popup */
.modal-container {
  position: relative;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 550px;
  background: #fff;
  border-radius: 10px;
  padding: 20px;
  box-sizing: border-box;
}
</style>