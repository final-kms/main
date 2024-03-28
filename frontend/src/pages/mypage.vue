<script setup>
const serverAddress = inject('serverAddress')
const auth = inject('auth')
import { useRouter } from "vue-router"

const router = useRouter()

const info = ref({
  username: '',
  email: '',
  password: ''
})

async function getInfo(){

  const response = await fetch(
    `http://${serverAddress}/api/getinfo`,
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
    const res = await response.json()
    info.value.username = res[0]
    info.value.email = res[1]
    
  }
}

async function updateUsername(){

const response = await fetch(
  `http://${serverAddress}/api/update/username`,
  {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${auth}`,          
    },
    body: JSON.stringify(info.value.username),
    credentials: 'include'
  }  
)

if(!response.ok) {
  alert("실패!")
} else{
  alert('성공')
  router.push('/logout')
}
}

async function updateEmail(){

const response = await fetch(
  `http://${serverAddress}/api/update/email`,
  {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${auth}`,          
    },
    body: JSON.stringify(info.value.email),
    credentials: 'include'
  }  
)

if(!response.ok) {
  alert("실패!")
} else{
  alert('성공')
  router.push('/logout')
}
}

async function updatePassword(){

const response = await fetch(
  `http://${serverAddress}/api/update/password`,
  {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${auth}`,          
    },
    body: JSON.stringify(info.value.password),
    credentials: 'include'
  }  
)

if(!response.ok) {
  alert("실패!")
} else{
  alert('성공')
  router.push('/logout')

}
}


async function deleteAccount(){

const response = await fetch(
  `http://${serverAddress}/api/delete`,
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
  alert("못가 히히")
} else{
  alert('빠이!')
  router.push('/logout')
}
}


getInfo()
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
            
            <p >My Info</p>
                <VCol
                >
                  <VTextField
                  id="username"
                  v-model="info.username"
                  placeholder="이름"
                  label="이름"
                  />
                  <VBtn
                  color="secondary"
                  type="submit"
                  variant="outlined"
                  @click="updateUsername"
                >
                  update
                </VBtn>
                </VCol>
              

                <VCol
                >
                  <VTextField
                  id="email"
                  v-model="info.email"
                  placeholder="메일"
                  label="메일"
                  />
                  <VBtn
                  color="secondary"
                  type="submit"
                  variant="outlined"
                  @click="updateEmail"
                >
                  update
                </VBtn>
                </VCol>

                <VCol
                >
                  <VTextField
                  id="password"
                  v-model="info.password"
                  placeholder="비밀번호"
                  label="비밀번호"
                  />
                  <VBtn
                  color="secondary"
                  type="submit"
                  variant="outlined"
                  @click="updatePassword"
                >
                  update
                </VBtn>
                </VCol>

                <VCol
                >
                  <VBtn
                  color="secondary"
                  type="submit"
                  variant="outlined"
                  @click="deleteAccount"
                >
                  탈...퇴...
                </VBtn>
                </VCol>
        
          </div>
        </VCardText>
      </VCard>
    
    </VCol>  

  </VRow>
</template>
