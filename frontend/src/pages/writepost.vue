<script setup>
import { VCol, VRow, VTextField, VTextarea } from 'vuetify/lib/components/index.mjs';
import { useRouter } from "vue-router"

const serverAddress = inject('serverAddress')
const router = useRouter()

const auth = inject('auth')

const post = ref({
  title: '',
  content: '',
  tag: ''
})

async function writePost(){

  const formData = {
    title: post.value.title,
    content: post.value.content,
    tag: post.value.tag
  }

  const response = await fetch(
      `http://${serverAddress}/api/post/create`,
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${auth}`,          
        },
        body: JSON.stringify(formData),
        credentials: 'include'
      }
  )

  if(!response.ok) {
    alert("실패!")
  } else{
    router.push('/')  
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
          <div >
            <form @submit.prevent="writePost()">
            <p >Post 작성</p>
                <VCol
                >
                  <VTextField
                  id="title"
                  v-model="post.title"
                  placeholder="제목"
                  label="제목"
                  />
                </VCol>

              

                <VCol
                >
                  <VTextarea
                  id="content"
                  v-model="post.content"
                  placeholder="본문"
                  label="본문"
                  />
                </VCol>

                <VCol
                >
                  <VTextField
                  id="tag"
                  v-model="post.tag"
                  placeholder="태그"
                  label="태그"
                  />
                </VCol>


              <VCol cols="12">
                <VBtn
                  type="submit"
                  class="me-5"
                >
                  Submit
                </VBtn>

                <VBtn
                  color="secondary"
                  type="reset"
                  variant="outlined"
                >
                  Reset
                </VBtn>
              </VCol>
            </form>
          </div>
        </VCardText>
      </VCard>
    
    </VCol>  

  </VRow>
</template>
