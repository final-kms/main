<script setup>
import {ref} from 'vue'
import PostLike from '@/k_views/like/PostLike.vue'

const props = defineProps({
    replylikevue: Object,
    postoj: Object
})

const editReply = ref({
  content: ''
})

const replylike = ref([])
const isUpdateReply = ref(false)
const checkReplyId = ref(null)

async function checkReplyLike(){

    const response = await fetch(
        `http://localhost:8080/api/replylike/check/${props.replylikevue.id}`,
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

async function getReplyLikes(){
  
  const response = await fetch(
      `http://localhost:8080/api/replylike/read/${props.replylikevue.id}`,
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
    getReply(props.post.id);
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
    if(response.ok) {
      alert("삭제되었습니다")
    } else {
      alert("삭제 실패")
    }
    getReply(props.post.id);
  }
}

getReplyLikes()

</script>

<template>
<VDivider class="mb-2"/>
<div class="mb-2" style="display: flex; justify-content: space-between;">
</div>

<VRow class="justify-end">
<VCol>
    <VIconBtn @click="checkReplyLike()" style="font-size: 10pt; cursor: pointer">
    추천
    {{ replylike }}
    </VIconBtn>
</VCol>
<VCol cols="auto">
    <VIconBtn @click="toggleReply(props.reply.id)" style="font-size: 10pt; cursor: pointer;">
    수정
    </VIconBtn>
</VCol>
<VCol cols="auto">
    <VIconBtn @click="delReply(props.reply.id)" style="font-size: 10pt; cursor: pointer;">
    삭제
    </VIconBtn>
</VCol>
</VRow>

<!-- <VExpandTransition>
<div v-show="isUpdateReply">
    <div v-if="checkReplyId === props.reply.id">
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
            <VBtn @click="updateReply(props.reply.id)"
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