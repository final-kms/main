<script setup>
import { VCol, VRow, VTextField, VTextarea } from 'vuetify/lib/components/index.mjs';
import { useRouter } from "vue-router"

const auth = inject('auth')
const serverAddress = inject('serverAddress')

const router = useRouter()

const table = ref({
  name: '',
  description: ''
})

async function createTable(){

  const response = await fetch(
      `http://${serverAddress}/api/table/create`,
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${auth}`,          
        },
        body: JSON.stringify(table.value),
        credentials: 'include'
      }
  )

  if(!response.ok) {
    alert("실패!")
  } else{
    router.push({ path: '/mytables' })
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
            <form @submit.prevent="createTable()">
            <p>Table 생성</p>
                <VCol
                >
                  <VTextField
                  id="name"
                  v-model="table.name"
                  placeholder="조직 이름"
                  label="조직 이름"
                  />
                </VCol>
                
                <VCol
                >
                  <VTextarea
                  id="desc"
                  v-model="table.description"
                  placeholder="설명"
                  label="설명"
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
