<script setup>
import AnalyticsAward from '@/views/dashboard/AnalyticsAward.vue';
import Post from '@/views/post/Post.vue';
import Wiki from '@/views/wiki/Wiki.vue';
import { useRouter } from "vue-router"

const serverAddress = inject('serverAddress')
const auth = inject('auth')
const router = useRouter()

const props = defineProps({
    tableId: String
})

const table = ref([])
const tableUsers = ref([])
const inviteUsers = ref([{ email: '' }])
const adminUser = ref({
  email: ''
})

async function getTable(){

  const response = await fetch(
      `http://${serverAddress}/api/table/read/${props.tableId}`,
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
    table.value = await response.json()
  }
}

async function getTableUsers(){

  const response = await fetch(
      `http://${serverAddress}/api/tableuser/readuser/${props.tableId}`,
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
    tableUsers.value = await response.json()
  }
}

const addUser = () => {
  inviteUsers.value.push({ email: '' })
}

// 사용자 정보를 서버로 전송하는 함수
async function addTableUsers(){
  const formData = { email : [] }
  inviteUsers.value.forEach(function(user) {
    formData.email.push(user.email)
  })

  const response = await fetch(
    `http://${serverAddress}/api/tableuser/create/${props.tableId}`,
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
    getTableUsers()
  }
}

async function addAdminUser(){
  const response = await fetch(
    `http://${serverAddress}/api/tableuser/admin/add/${props.tableId}`,
    {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${auth}`,          
      },
      body: JSON.stringify(adminUser.value),
      credentials: 'include'
    }
  )

  if(!response.ok) {
    alert("실패!")
  } else{
    getTableUsers()
  }
}

async function removeAdminUser(){
  const response = await fetch(
    `http://${serverAddress}/api/tableuser/admin/remove/${props.tableId}`,
    {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${auth}`,          
      },
      body: JSON.stringify(adminUser.value),
      credentials: 'include'
    }
  )

  if(!response.ok) {
    alert("실패!")
  } else{
    getTableUsers()
  }
}



getTable()
getTableUsers()

const userManageDialog = ref(false)
function handleUserManageDialog(){
  if(userManageDialog.value == false){
    inviteUsers.value = [{ email: '' }]
    userManageDialog.value = true
  } else {
    userManageDialog.value = false
  }
}

const tableDialog = ref(false)
function handleTableDialog(){
  if(tableDialog.value == false){
    tableDialog.value = true
  } else {
    tableDialog.value = false
  }
}


async function updateTable(){
  const response = await fetch(
    `http://${serverAddress}/api/table/update/${props.tableId}`,
    {
      method: 'PUT',
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
    getTable()
  }
}

function createTablePost(){
  router.push(`/writepost/${props.tableId}`)
}

function createTableWiki(){
  router.push(`/writewiki/${props.tableId}`)
}



const posts = ref([])

async function getPosts(){

const response = await fetch(
    `http://${serverAddress}/api/post/read/table/${props.tableId}`,
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
  posts.value = await response.json()
}
}

const wikis = ref([])

async function getWikis(){

const response = await fetch(
    `http://${serverAddress}/api/wiki/read/table/${props.tableId}`,
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




getPosts()
getWikis()

</script>

<template>
  <VRow>
    <VCol
      cols="12"
      md="8"
      class="mb-4"
    >
    
      <!-- 정보 출력 / 추후 업데이트 가능하게 -->
      <VCard class="position-relative" style="margin-bottom: 20px;">
        <VCol class="mb-2">
          <VCol>
            {{ table.name }}
            <p class="table-desc">{{ table.description }}</p>
          </VCol>
          <VCol>
              <VBtn
                @click="handleTableDialog"
                style="display: flex; justify-content: space-between;"
                >
                Table Edit
                </VBtn>
          </VCol>
        </VCol>
      </VCard>

      <!-- 멤버 출력 / 버튼 같은거 클릭하면 초대하기 기능 팝업으로 -->
      <VCard class="position-relative" style="margin-bottom: 20px;">
        <VCol class="mb-2">
          <div style="margin-bottom: 20px; display: flex; justify-content: flex-end;">
            <VBtn 
            @click="handleUserManageDialog"
            >
            User Manage
            </VBtn>
          </div>
          <!-- 멤버 리스트 -->
          <div v-for="(item, index) in tableUsers" :key="index" >
            {{ item.user.username }}
          </div>
        </VCol>
      </VCard>      

      <VRow>
        <VBtn @click="createTablePost">
          Post+
        </VBtn>
        <VBtn @click="createTableWiki">
          Wiki+
        </VBtn>
      </VRow>





      <!-- 조직 위키 / 포스트 싹 다 출력 -->
      <div v-for="(item, index) in posts" :key="index">
        <Post :post="item" style="margin-bottom: 20px;"/>
      </div>
      <div v-for="(item, index) in wikis" :key="index">
        <Wiki :wiki="item" style="margin-bottom: 20px;"/>
      </div>


    </VCol>  
    <VCol
      cols="12"
      md="4"
    >
        <AnalyticsAward />
    </VCol>  

  </VRow>

            <VDialog v-model="tableDialog" max-width="50%">
              <VCard>
                <VCardTitle>Table Edit</VCardTitle>
                <VCardText>
                  <form @submit="updateTable">
                    <VCol
                    >
                      <VTextField
                      id="name"
                      v-model="table.name"
                      placeholder="이름"
                      label="이름"
                      />
                    </VCol>
                    <VCol
                    >
                      <VTextField
                      id="description"
                      v-model="table.description"
                      placeholder="설명"
                      label="설명"
                      />
                    </VCol>
                    <VRadioGroup
                    v-model="table.isPublic" 
                    inline>
                      <VRadio
                      label="public"
                      :value=true>
                      </VRadio>
                      <VRadio
                      label="private"
                      :value=false>
                      </VRadio>
                    </VRadioGroup>
                    <VBtn
                      type="submit"
                      class="me-5"
                    >
                      Submit
                    </VBtn>
                  </form>               
                </VCardText>
                <VCardActions>
                  <!-- 팝업을 닫는 버튼 -->
                  <VBtn color="primary" @click="tableDialog = false">Close</VBtn>
                </VCardActions>
              </VCard>
            </VDialog>



            <VDialog v-model="userManageDialog" max-width="50%">
              <VCard>
                <VCardTitle>Invite</VCardTitle>
                <VCardText>
                  초대
                  <form @submit="addTableUsers">
                    <!-- 여러 유저의 정보를 입력할 수 있는 입력란 -->
                    <div v-for="(user, index) in inviteUsers" :key="index">
                      <label>이메일:</label>
                      <input type="email" v-model="user.email"> <!-- required> -->
                    </div>
                    <!-- 새로운 사용자 추가 버튼 -->
                    <button type="button" @click="addUser">새로운 사용자 추가</button><br/>
                    <!-- 정보를 서버로 전송하는 버튼 -->
                    <button type="submit">정보 제출</button>
                  </form>               
                </VCardText>
                <VCardTitle>Add Admin</VCardTitle>
                <VCardText>
                  권한 추가
                  <form @submit="addAdminUser">
                    <label>이메일:</label>
                    <input type="email" v-model="adminUser.email"> <!-- required> -->
                    <button type="submit">정보 제출</button>
                  </form>               
                </VCardText>
                <VCardTitle>Remove Admin</VCardTitle>
                <VCardText>
                  권한 삭제
                  <form @submit="removeAdminUser">
                    <label>이메일:</label>
                    <input type="email" v-model="adminUser.email"> <!-- required> -->
                    <button type="submit">정보 제출</button>
                  </form>               
                </VCardText>
                <VCardActions>
                  <!-- 팝업을 닫는 버튼 -->
                  <VBtn color="primary" @click="userManageDialog = false">Close</VBtn>
                </VCardActions>
              </VCard>
            </VDialog>

</template>
