<script setup>
import AnalyticsAward from '@/views/dashboard/AnalyticsAward.vue';
import wiki from '@/views/wiki/Wiki.vue';

const auth = inject('auth')
const serverAddress = inject('serverAddress')

const wikis = ref([])

async function getWikis(){

  const response = await fetch(
      `http://${serverAddress}/api/wiki/read/my`,
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
    wikis.value = await response.json()
  }
}

getWikis()

</script>

<template>
  <VRow>
    <VCol
      cols="12"
      md="8"
      class="mb-4"
    >
    
    <div v-for="(item, index) in wikis" :key="index">
      <!-- {{ item }}       -->
      <wiki :wiki="item" style="margin-bottom: 20px;"/>
    </div>
    </VCol>  
    <VCol
      cols="12"
      md="4"
    >
        <AnalyticsAward />
    </VCol>  

  </VRow>
</template>
