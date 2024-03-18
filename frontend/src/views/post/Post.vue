<script setup>

//const post = defineProps(['post'])
const props = defineProps({
    post: Object
})


const like = ref([])
const star = ref([])

const reply = ref({
  content: ''
})

const replys = ref([])

async function checkLike(){

const response = await fetch(
    `http://localhost:8080/api/postlike/check/${props.post.id}`,
    {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'include'
    }
)

if(!response.ok) {
  alert("실패!")
} else{
  getLikes()
}
}

async function checkStar(){

const response = await fetch(
    `http://localhost:8080/api/poststar/check/${props.post.id}`,
    {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'include'
    }
)

if(!response.ok) {
  alert("실패!")
} else{
  getStars()
}
}



async function getLikes(){

  const response = await fetch(
      `http://localhost:8080/api/postlike/read/${props.post.id}`,
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
    like.value = await response.json()
  }
}

async function getStars(){

const response = await fetch(
    `http://localhost:8080/api/poststar/read/${props.post.id}`,
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
  star.value = await response.json()
}
}

async function writeReply(postId){
  const formData = {
    content: reply.value.content
  }

const response = await fetch(
    `http://localhost:8080/api/reply/create/${postId}`,
    {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(formData),
      credentials: 'include'
    }
)

if(!response.ok) {
  alert("실패!")
} else{
  getReply(postId)
}
}


async function getReply(postId){

const formData = {
  content: reply.value.content
}

const response = await fetch(
  `http://localhost:8080/api/reply/readpost/${postId}`,
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
    replys.value = await response.json()
    reply.value.content = ''
  }
}

getLikes()
getStars()
getReply(props.post.id)
</script>

<template>
  <VCard class="position-relative">
    <VCardText>
      <div class="mb-2">
        <div class="post-details">
            <div class="post-header">
                <span class="circle"></span>
                <span class="post-name">{{ post.user.username }}</span>
                <span class="post-title">{{ post.title }}</span>
                <span class="post-date">{{ post.date }}</span>
            </div>
            <hr/>
            <p class="post-content">{{ post.content }}</p>
            <hr/>
            <div class="post-interactions">
                <span @click="checkLike" class="post-interactions-item">좋아요 : {{ like }}</span>
                <span @click="checkStar" class="post-interactions-item">즐겨찾기 : {{ star }}</span>
            </div>
            <hr/>
            <div class="post-comments">
                <p>댓글</p>
                <div v-for="(item, index) in replys" :key="index">
                  {{ item.content }}
                </div>
                <div>
                  <form @submit.prevent="writeReply(post.id)">
                    <div><label>내용</label><input type="text" v-model="reply.content"></input></div>
                    <div><input type="submit"></input></div>
                  </form>
                </div>
            </div>
        </div>
    </div>
    </VCardText>
  </VCard>
</template>

<style lang="scss">
.v-card .trophy {
  position: absolute;
  inline-size: 5.188rem;
  inset-block-end: 1.25rem;
  inset-inline-end: 1.25rem;
}
.post-image {
    width: 100px;
    height: 100px;
    border-radius: 50%; /* 원형 이미지를 위한 border-radius */
    margin-bottom: 10px;
}
.post-title {
    font-weight: bold;
    margin-bottom: 5px;
}
.post-date {
    color: #666;
    margin-bottom: 10px;
}
.post-content {
    margin-bottom: 10px;
}
.post-interactions {
    display: flex;
    justify-content: space-between;
    width: 100%;
    margin-bottom: 10px;
}
.post-interactions-item {
    color: #666;
}
.post-comments {
    color: #333;
}

.circle {
    width: 20px;
    height: 20px;
    background-color: red;
    border-radius: 50%; /* 원 모양의 동그라미를 만들기 위해 반지름을 50%로 설정합니다. */

    margin-bottom: 5px;

    display: inline-block;
}
</style>
