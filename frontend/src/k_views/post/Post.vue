<script setup>
import { useRouter } from 'vue-router';
import { VCardText, VCol, VDivider, VIcon, VRow, VTextField } from 'vuetify/lib/components/index.mjs';
import ReplyVue from '@/k_views/reply/Reply.vue';
import avatar1 from '@images/avatars/avatar-1.png';

const serverAddress = inject('serverAddress')
const auth = inject('auth')

// 날짜
const formatDate = function(value) {
    const date = new Date(value);
    const year = date.getFullYear();
    const month = (date.getMonth() + 1);
    const day = date.getDate();
    const hour = date.getHours();
    const min = date.getMinutes();
    const sec = date.getSeconds();
    
    return `${year}-${month}-${day} ${hour}:${min}:${sec}`;
}

const like = ref([])
const replylike = ref([])
const star = ref([])
const replys = ref([])
const isDetails = ref(false)
const isReplyDetails = ref(false)
const isUpdateReply = ref(false)
const checkReplyId = ref(null)

const props = defineProps({
    post: Object
})
const reply = ref({
  content: ''
})
const editReply = ref({
  content: ''
})

async function checkLike(){

  const response = await fetch(
      `http://${serverAddress}/api/postlike/check/${props.post.id}`,
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
    getLikes()
  }
}

async function getReplyLikes(replyId){
  
  const response = await fetch(
      `http://${serverAddress}/api/replylike/read/${replyId}`,
      {
        method: 'GET',
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
    replylike.value[replyId]= await response.json()
  }
}

async function checkStar(){

  const response = await fetch(
      `http://${serverAddress}/api/poststar/check/${props.post.id}`,
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
    getStars()
  }
}

async function getLikes(){
  
  const response = await fetch(
      `http://${serverAddress}/api/postlike/read/${props.post.id}`,
      {
        method: 'GET',
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
    like.value = await response.json()
  }
}

async function getStars(){

  const response = await fetch(
      `http://${serverAddress}/api/poststar/read/${props.post.id}`,
      {
        method: 'GET',
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
    star.value = await response.json()
  }
}

async function writeReply(postId){
  console.log(postId)
  const formData = {
    content: reply.value.content
  }

  const response = await fetch(
      `http://${serverAddress}/api/reply/create/${postId}`,
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${auth}`
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
    `http://${serverAddress}/api/reply/readpost/${postId}`,
    {
      method: 'GET',
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
      replys.value = await response.json()
      reply.value.content = ''
    }
}

async function toggleReply(replyId) {
  if(checkReplyId.value != replyId) {
    isUpdateReply.value = true
    checkReplyId.value = replyId;
  }
  else {
    isUpdateReply.value = !isUpdateReply.value;
    checkReplyId.value = replyId;
  }
}

async function updateReply(replyId){
  const formData = {
    content: editReply.value.content
  }

  const response = await fetch(
      `http://${serverAddress}/api/reply/update/${replyId}`,
      {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
        'Authorization': `Bearer ${auth}`,
        credentials: 'include'
      }
  )

  if(!response.ok) {
    alert("실패!")
  } else{
    getReply(props.post.id);
    editReply.value.content=''
  }
}

async function delPost(postId) {
  if(confirm("삭제하시겠습니까?")) {
    const response = await fetch(
      `http://${serverAddress}/api/post/delete/${postId}`,
      {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json'
        },
        'Authorization': `Bearer ${auth}`,
        credentials: 'include'
      }
    )
    if(!response.ok) {
      alert("삭제 실패")
    } else {
      alert("삭제되었습니다")
      window.location.reload()
    }
  }
}

async function delReply(replyId) {
  if(confirm("삭제하시겠습니까?")) {
    const response = await fetch(
      `http://${serverAddress}/api/reply/delete/${replyId}`,
      {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json'
        },
        'Authorization': `Bearer ${auth}`,
        credentials: 'include'
      }
    )
    if(!response.ok) {
      alert("삭제 실패")
    } else {
      alert("삭제되었습니다")
      getReply(props.post.id);
    }
  }
}

const router = useRouter();

function gotoupdatepost(postId) {
  router.push({ path: `/updatepost/${postId}`})
}

getLikes()
getStars()
getReply(props.post.id)

</script>

<template>
  <VCard class="position-relative">
    <VCardText>
      <VCol class="mb-2">
        <VCol class="post-details">
          <div class="post-header">
              <VCol class="post-header" >
                <span>
                  <img class="propile-img" :src="avatar1">
                </span>
                <span style="display: flex; justify-content: space-between;">
                <span class="post-name" style="font-weight: 600; margin-right: auto;">
                  {{ post.user.username }}
                </span>
                <span class="post-date" style="margin-left: auto;">
                  {{ formatDate(post.date) }}
                </span>
                </span>
              </VCol>
          </div>

          <div class="post-title">
            <VCardTitle style="font-weight: 700;">{{ post.title }}</VCardTitle>
          </div>

          <VDivider />
            <VCol class="post-content, mt-2" style="margin-left: 5px;">
              <div v-html="post.content.length  > 100 ? post.content.slice(0,100) + '...' :post.content"></div>
            </VCol>
          <VCardActions>
            <VBtn @click="isDetails = !isDetails">
              Details
            </VBtn>

            <VSpacer />

            <VBtn
              icon
              size="small"
              @click="isDetails = !isDetails"
            >
              <VIcon :icon="isDetails ? 'ri-arrow-up-s-line' : 'ri-arrow-down-s-line'" />
            </VBtn>
          </VCardActions>

          <VExpandTransition>
            <div v-show="isDetails">
              <VDivider />
              <VCardText>
                <div v-html="post.content"></div>
              </VCardText>
            </div>
          </VExpandTransition>
          
          <VDivider />
          <VCol class="post-interactions">
            <IConBtn @click="checkLike"> 
              <VIcon icon="ri-heart-line"/>
            </IConBtn>
            <span class="post-interactions-item" style="margin-left: 10px; margin-right: 10px;">{{ like }}</span>

            <IConBtn @click="checkStar"> 
              <VIcon icon="ri-star-line"/>
            </IConBtn>
            <span class="post-interactions-item" style="margin-left: 10px; margin-right: 10px;">{{ star }}</span>
          </VCol>

          <VDivider />
          <VCardActions>
            <VBtn @click="isReplyDetails = !isReplyDetails">
              Reply
            </VBtn>
            <VSpacer />
            <VBtn
              icon
              size="small"
              @click="isReplyDetails = !isReplyDetails"
            >
              <VIcon :icon="isReplyDetails ? 'ri-arrow-up-s-line' : 'ri-arrow-down-s-line'" />
            </VBtn>
          </VCardActions>
                
          <VExpandTransition>
            <div v-show="isReplyDetails">
              <VCardText v-for="(item, index) in replys" :key="index">
                <ReplyVue :replyvue="item" :postob="props.post"></ReplyVue>
                <VRow >
                  <VCol cols="auto">
                    <VIconBtn @click="toggleReply(item.id)" style="font-size: 10pt; cursor: pointer;">
                      수정
                    </VIconBtn>
                  </VCol>
                  <VCol cols="auto" >
                    <VIconBtn @click="delReply(item.id)" style="font-size: 10pt; cursor: pointer;">
                    삭제
                    </VIconBtn>
                  </VCol>
                </VROW>

                <VExpandTransition>
                  <div v-show="isUpdateReply">
                    <div v-if="checkReplyId === item.id">
                      <VRow>
                        <VCol>
                          <VTextField
                          input
                          id="updatereply"
                          v-model="editReply.content"
                          placeholder="수정"
                          label="수정"
                          >
                          </VTextField>
                        </VCol>

                        <div>
                          <VCol class="mt-1">
                            <VBtn @click="updateReply(item.id)"
                                type="button"
                                class="me-2"
                              >
                                수정
                            </VBtn>
                          </VCol>
                        </div>

                      </VRow>
                    </div>
                  </div>
                </VExpandTransition>

              </VCardText>
            </div>
          </VExpandTransition>

          <div>
            <VRow>
              <VCol>
                <VTextField
                input
                id="reply"
                v-model="reply.content"
                placeholder="댓글"
                label="댓글"
                >
                </VTextField>
              </VCol>

              <div>
                <VCol class="mt-1">
                  <VBtn @click="writeReply(post.id)"
                      type="button"
                      class="me-2"
                    >
                      작성
                  </VBtn>
                </VCol>
              </div>
            </VRow>
            
            <VRow>
              <VCol style="display: flex; justify-content: space-between;">
                <VBtn @click="gotoupdatepost(post.id)"
                  type="button"
                  style="margin-left: auto;"
                  >
                  수정
                </VBtn>
                
                <VBtn @click="delPost(post.id)"
                  type="button"
                  style="margin-left: 10px;"
                  >
                  삭제
                </VBtn>
              </VCol>
            </VRow>

          </div>
        </VCol>
      </VCol>
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
// .post-title {
//     font-weight: bold;
//     margin-bottom: 5px;
// }
.post-date {
    color: #666;
    margin-bottom: 10px;
}
.post-content {
    margin-bottom: 10px;
}
.post-interactions {
    display: flex;
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

.propile-img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}

</style>