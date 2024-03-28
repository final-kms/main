<script setup>
import AnalyticsAward from '@/views/dashboard/AnalyticsAward.vue';
import Table from '@/views/table/TableSummary.vue';
import { useRouter } from "vue-router"

const serverAddress = inject('serverAddress')
const auth = inject('auth')
const router = useRouter()

const tables = ref([])
const invites = ref([])

async function getTables(){

  const response = await fetch(
      `http://${serverAddress}/api/tableuser/read`,
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
    tables.value = await response.json()
  }
}

function moveTable(tableId){
  router.push(`/mytables/${tableId}`)
}

async function getInvites(){
  const response = await fetch(
      `http://${serverAddress}/api/tableuser/invite`,
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
    invites.value = await response.json()
  }
}

async function acceptInvite(tableUserId){
  const response = await fetch(
      `http://${serverAddress}/api/tableuser/update/${tableUserId}`,
      {
        method: 'PUT',
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
    getInvites()
  }
}

async function rejectInvite(tableUserId){
  const response = await fetch(
      `http://${serverAddress}/api/tableuser/delete/${tableUserId}`,
      {
        method: 'DELETE',
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
    getInvites()
  }
}

getInvites()
getTables()

</script>

<template>
  <VRow>
    <VCol
      cols="12"
      md="8"
      class="mb-4"
    >

    <div style="margin-bottom: 20px; display: flex; justify-content: flex-end;">
      <VBtn 
      to="/createtable"
      >
      Create Table
      </VBtn>    
    </div>
    <!-- 초대 받은 내용들~ -->
    <div v-for="(item, index) in invites" :key="index">
      <VCard class="position-relative">
          <VCol class="mb-2">
            {{ item.table.name }} - {{ item.table.description }}
            <VBtn 
            @click="acceptInvite(item.id)"
            >
              accept
            </VBtn>
            <VBtn 
            @click="rejectInvite(item.id)"
            >
              reject
            </VBtn>
          </VCol>
      </VCard>
    </div>

    <div v-for="(item, index) in tables" :key="index">
      <Table @click="moveTable(item.table.id)" :table="item" style="margin-bottom: 20px;"/>
    </div>
    
      <!-- 조직 생성 출력 -->
      <!-- 조직 리스트 출력       -->
      <div v-for="(item, index) in posts" :key="index">
      <Post :post="item" style="margin-bottom: 20px;"/>
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
