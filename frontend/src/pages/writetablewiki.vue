<script setup>
import { VCol, VRow, VTextField, VTextarea } from 'vuetify/lib/components/index.mjs';
import { useRouter } from "vue-router"

const router = useRouter()
const serverAddress = inject('serverAddress')
const auth = inject('auth')

const props = defineProps({
    tableId: String
})

const wiki = ref({
  title: '',
  content: '',
  category: ''
})

async function writeWiki(){

  const formData = {
    title: wiki.value.title,
    content: wiki.value.content,
    category: wiki.value.category
  }

  const response = await fetch(
      `http://${serverAddress}/api/wiki/create/${props.tableId}`,
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
//    window.location.href = '/wiki'
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
            <form @submit.prevent="writeWiki()">
            <p>Wiki 작성</p>
                <VCol
                >
                  <VTextField
                  id="title"
                  v-model="wiki.title"
                  placeholder="제목"
                  label="제목"
                  />
                </VCol>

              

                <VCol
                >
                  <VTextarea
                  id="content"
                  v-model="wiki.content"
                  placeholder="본문"
                  label="본문"
                  />
                </VCol>

                <VCol
                >
                  <VTextField
                  id="tag"
                  v-model="wiki.category"
                  placeholder="카테고리"
                  label="카테고리"
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

              <!-- <div><label>제목</label><input type="text" v-model="post.title"></input></div>
              <div><label>본문</label><input type="text" v-model="post.content"></input></div>
              <div><label>카테고리</label><input type="text" v-model="post.category"></input></div>
              <div><label>태그</label><input type="text" v-model="post.tag"></input></div>
              <div><input type="submit"></input></div> -->
            </form>
          </div>
        </VCardText>
      </VCard>
    
    </VCol>  

  </VRow>
</template>
