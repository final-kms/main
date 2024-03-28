<script setup>
import AnalyticsAward from '@/views/dashboard/AnalyticsAward.vue';
import wiki from '@/views/wiki/Wiki.vue';

import { watchEffect } from 'vue';

const auth = inject('auth')
const serverAddress = inject('serverAddress')

const props = defineProps({
    keyword: String
})

const wikis = ref([])

watchEffect(() => {
  if(props.keyword == null){
    getWikis()
  } else{
    searchWiki()
  }
});


async function getWikis(){

  const response = await fetch(
      `http://${serverAddress}/api/wiki/read`,
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

async function searchWiki(){
  const response = await fetch(
      `http://${serverAddress}/api/wiki/search/${props.keyword}`,
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


if(props.keyword == null){
  getWikis()
} 
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
        <!-- <wiki style="margin-bottom: 20px;"/>
        <wiki style="margin-bottom: 20px;"/>
        <wiki style="margin-bottom: 20px;"/> -->
    </VCol>  
    <VCol
      cols="12"
      md="4"
    >
        <AnalyticsAward />
    </VCol>  

  </VRow>
</template>
