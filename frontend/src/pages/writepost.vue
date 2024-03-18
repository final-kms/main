<script setup>

const post = ref({
  title: '',
  content: '',
  category: '',
  tag: ''
})

async function writePost(){

  const formData = {
    title: post.value.title,
    content: post.value.content,
    category: post.value.category,
    tag: post.value.tag
  }

  const response = await fetch(
      `http://localhost:8080/api/post/create`,
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
    window.location.href = 'http://localhost:5173/'
  }
}

</script>

<template>
  <VRow>
    <VCol
      cols="12"
      md="8"
      class="mb-4"
    >
    <VCard class="position-relative">
    <VCardText>
      <div class="mb-2">
        <form @submit.prevent="writePost()">
            <div><label>제목</label><input type="text" v-model="post.title"></input></div>
            <div><label>본문</label><input type="text" v-model="post.content"></input></div>
            <div><label>카테고리</label><input type="text" v-model="post.category"></input></div>
            <div><label>태그</label><input type="text" v-model="post.tag"></input></div>
            <div><input type="submit"></input></div>
        </form>      
      </div>
    </VCardText>
  </VCard>
    
    </VCol>  

  </VRow>
</template>
