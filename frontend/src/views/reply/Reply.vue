<script setup>
import { VCardItem, VCol, VDivider, VIcon, VTextField } from 'vuetify/lib/components/index.mjs';

const serverAddress = inject('serverAddress')

const auth = inject('auth')

const props = defineProps({
    reply: Object
})

const likes = ref()

async function getReplyLikes(){

const response = await fetch(
    `http://${serverAddress}/api/replylike/read/${props.reply.id}`,
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
    likes.value = await response.json()
  }
}


async function checkReplyLike(){

const response = await fetch(
    `http://${serverAddress}/api/replylike/check/${props.reply.id}`,
    {
      method: 'POST',
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
  getReplyLikes()
}
}
getReplyLikes()

</script>

<template>

    {{ reply.content }} - {{ likes }}
    <IConBtn @click="checkReplyLike"> 
        <VIcon icon="ri-heart-line"/>
    </IConBtn>
</template>

<style lang="scss">

</style>
