<script setup>
import { useRoute } from 'vue-router';
import { VCol, VRow, VTextField } from 'vuetify/lib/components/index.mjs';
import TiptapEditor from '@/k_components/TiptapEditor.vue';
import PostLikeSort from '@/k_views/list/PostLikeSort.vue';
import PostDateSort from '@/k_views/list/PostDateSort.vue';
import { useRouter } from "vue-router"

const router = useRouter()
const serverAddress = inject('serverAddress')
const auth = inject('auth')

const post = ref({
  title: '',
  content: '',
  tag: ''
})


const props = defineProps({
    id: String
})

async function getTag(postId){
  const response = await fetch(
      `http://${serverAddress}/api/tagpost/read/${postId}`,
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
    const tags = await response.json()
    console.log(tags)
  }
}
async function getPost(postId){

  const response = await fetch(
      `http://${serverAddress}/api/post/read/${postId}`,
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
    post.value = await response.json()
  }
}

const postId = props.id
getPost(postId)

async function updatePost(postId){

  const formData = {
    title: post.value.title,
    content: post.value.content,
    tag: post.value.tag
  }

  const response = await fetch(
      `http://${serverAddress}/api/post/update/${postId}`,
      {
        method: 'PUT',
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
    router.push(`/`) 
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
          <div>
            <p >Post 수정</p>
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
                <div>
                <TiptapEditor
                id="Tipcontent"
                v-model="post.content"
                />
                </div>

                  <!-- <VTextarea
                  id="content"
                  v-model="post.content"
                  placeholder="본문"
                  label="본문"
                  /> -->
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
                @click="updatePost(props.id)"
                type="submit"
                class="me-5"
                >
                  Submit
                </VBtn>

                <!-- <VBtn
                  color="secondary"
                  type="reset"
                  variant="outlined"
                >
                  Reset
                </VBtn> -->
              </VCol>
          </div>
        </VCardText>
      </VCard>
    </VCol>
    
    <VCol
      cols="12"
      md="4"
    >
      <VCard title="추천순" style="margin-bottom: 20px">
      <PostLikeSort style="margin-bottom: 20px" />
      </VCard>    
      <VCard title="최신순" style="margin-bottom: 20px">
        <PostDateSort />
      </VCard>
      <AnalyticsAward style="margin-bottom: 20px"/>
      <AnalyticsAward style="margin-bottom: 20px"/>
    </VCol>

  </VRow>
</template>
