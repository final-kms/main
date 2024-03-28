<script setup>
import {ref} from 'vue'

const props = defineProps({
    replyvue: Object,
    postob: Object
})

const editReply = ref({
  content: ''
})

const replys = ref([])
const reply = ref({
  content: ''
})
const isUpdateReply = ref(false)
const checkReplyId = ref(null)

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

async function checkReplyLike(){

    const response = await fetch(
        `http://localhost:8080/api/replylike/check/${props.replyvue.id}`,
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
    getReplyLikes()
    }
}

const replylike = ref([])

async function getReplyLikes(){
  
  const response = await fetch(
      `http://localhost:8080/api/replylike/read/${props.replyvue.id}`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
        credentials: 'include'
      }
  )

  if(!response.ok) {
    
  } else{
    replylike.value= await response.json()
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
      `http://localhost:8080/api/reply/update/${replyId}`,
      {
        method: 'PUT',
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
    // getReply(props.postoj.id)
    window.location.reload()
  }
}

async function delReply(replyId) {
  if(confirm("삭제하시겠습니까?")) {
    const response = await fetch(
      `http://localhost:8080/api/reply/delete/${replyId}`,
      {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json'
        },
        credentials: 'include'
      }
    )
    if(!response.ok) {
      alert("삭제 실패")
    } else {
      alert("삭제되었습니다")
      // getReply(props.postoj.id);
      window.location.reload()
    }
  }
}

getReplyLikes()

</script>

<template>
<VDivider class="mb-2"/>
<div class="mb-2" style="display: flex; justify-content: space-between;">
  <span style="font-weight: 600;"> {{ props.replyvue.user.username }} </span>
  <span> {{ formatDate(props.replyvue.date) }}</span>
</div>
<div class="mb-2"> {{ props.replyvue.content }}</div>

<VRow class="justify-end">
  <VCol>
    <VIconBtn @click="checkReplyLike(props.replyvue.id)" style="font-size: 10pt; cursor: pointer">
      추천 {{ replylike }}
    </VIconBtn>
  </VCol>
  <!-- <VCol cols="auto">
    <VIconBtn @click="toggleReply(props.replyvue.id)" style="font-size: 10pt; cursor: pointer;">
      수정
    </VIconBtn>
  </VCol>
  <VCol cols="auto">
    <VIconBtn @click="delReply(props.replyvue.id)" style="font-size: 10pt; cursor: pointer;">
      삭제
    </VIconBtn>
  </VCol> -->
</VRow>

<!-- <VExpandTransition>
  <div v-show="isUpdateReply">
    <div v-if="checkReplyId === props.replyvue.id">
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
            <VBtn @click="updateReply(props.replyvue.id)"
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
</VExpandTransition> -->

</template>