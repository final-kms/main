<script setup>
import { onMounted, ref } from 'vue';

const props = defineProps({
    poststar: Object
})

const serverAddress = inject('serverAddress')
const auth = inject('auth')

const star = ref([])

async function getStars(){
  
  const response = await fetch(
    `http://${serverAddress}/api/poststar/read/${props.poststar.id}`,
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

onMounted(getStars)

</script>

<template>
    <span>
        {{ star }}
    </span>
</template>
  