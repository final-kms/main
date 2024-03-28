<script setup>

const serverAddress = inject('serverAddress')
const auth = inject('auth')

// 년-월-일
const formatDate = function(value) {
    const date = new Date(value);
    const year = date.getFullYear();
    const month = (date.getMonth() + 1);
    const day = date.getDate();
    const hour = date.getHours();
    const min = date.getMinutes();
    const sec = date.getSeconds();
    
    return `${year}-${month}-${day}`;
}

const props = defineProps({
    post: Object
})

const like = ref([])
const star = ref([])
const posts = ref([])

async function getPosts(){

  const response = await fetch(
      `http://${serverAddress}/api/post/read/public`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
        credentials: 'include',
        'Authorization': `Bearer ${auth}`
      }
  )

  if(!response.ok) {
    alert("실패!")
  } else{
    posts.value = await response.json()
  }
}

getPosts()

function sortedDate(value) {
  const sortedValue = value.slice().sort(function(a,b) {
    return new Date(b.date).getTime() - new Date(a.date).getTime()
  })
  return sortedValue
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
          Date
        </th>
      </tr>
    </thead>

    <tbody>
      <tr
        v-for="(item, index) in sortedDate(posts).slice(0,5)" 
        :key="index"
      >
        <td class="text-center">
          {{ item.title }}
        </td>
        <td class="text-center">
          {{ formatDate(item.date) }}
        </td>
      </tr>
    </tbody>
  </VTable>

  </template>
  