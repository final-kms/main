<script setup>
import AnalyticsAward from '@/views/dashboard/AnalyticsAward.vue';

const serverAddress = inject('serverAddress')
const auth = inject('auth')

const emailKeyword = ref('')
const emails = ref([])

async function searchEmail(){

  const response = await fetch(
      `http://${serverAddress}/api/search/email/${emailKeyword.value}`,
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
    emails.value = await response.json()
  }
}

async function checkFollow(email){

  const response = await fetch(
      `http://${serverAddress}/api/follow/check/${email}`,
      {
        method: 'POST',
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
    getFollowers()
    getFollowees()
  }
}

const followers = ref([])
const followees = ref([])


async function getFollowers(){

  const response = await fetch(
      `http://${serverAddress}/api/follow/read/follower`,
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
    followers.value = await response.json()
  }
}

async function getFollowees(){

  const response = await fetch(
      `http://${serverAddress}/api/follow/read/followee`,
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
    followees.value = await response.json()
  }
}

const switchContent = ref(true)

function isFollowed(email){
 
  if(followers.value.includes(email)){
    return 'Follow'
  }
  else{
    return 'Follow'
  }
}

getFollowees()
getFollowers()
</script>

<template>
  <VRow>
    <VCol
      cols="12"
      md="8"
      class="mb-4"
    >

      <VBtn
      @click="switchContent=true"
      >
        Follower
      </VBtn>
      <VBtn
      @click="switchContent=false"
      >
        Followee
      </VBtn>

      <!-- 검색 -->
      <VCard class="position-relative">
        <VCardText>
          <VCol class="mb-2">
              <VCol
                >
                <form @submit.prevent="searchEmail()">
                  <VTextField
                  id="title"
                  v-model="emailKeyword"
                  placeholder="제목"
                  label="제목"
                  />
                  <IconBtn
                  type="submit"
                  >
                    <VIcon icon="ri-search-line" />
                  </IconBtn>
                </form>
              </VCol>
            <VDivider />
            <div v-for="(item, index) in emails" :key="index">
              {{ item }}
              <VBtn
              @click="checkFollow(item)">
                {{ isFollowed(item) }}
              </VBtn>
            </div>
          </VCol>
        </VCardText>
      </VCard>
 
      <!-- 내용 -->
      <VCard class="position-relative">
        <VCardText>
          <VCol class="mb-2">
            <div v-if="switchContent" v-for="(item, index) in followers" :key="index">
              {{ item.follower.email }}
              <VBtn
              @click="checkFollow(item.follower.email)">
                {{ isFollowed(item) }}
              </VBtn>
              <VDivider />
            </div>
            <div v-if="!switchContent" v-for="(item, index) in followees" :key="index">
              {{ item.followee.email }}
              <VDivider />
            </div>
            <VDivider />
          </VCol>
        </VCardText>
      </VCard>
    </VCol>  
    <VCol
      cols="12"
      md="4"
    >
        <AnalyticsAward />
    </VCol>  

  </VRow>
</template>
