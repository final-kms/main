<script setup>
import { VCardItem, VCol, VDivider, VIcon, VTextField } from 'vuetify/lib/components/index.mjs';

const serverAddress = inject('serverAddress')

const auth = inject('auth')

const isDetails = ref(false)
//const wiki = defineProps(['wiki'])
const props = defineProps({
    wiki: Object
})


const like = ref([])
const star = ref([])

async function checkLike(){

const response = await fetch(
    `http://${serverAddress}/api/wikilike/check/${props.wiki.id}`,
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
  getLikes()
}
}

async function checkStar(){

const response = await fetch(
    `http://${serverAddress}/api/wikistar/check/${props.wiki.id}`,
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
  getStars()
}
}



async function getLikes(){

  const response = await fetch(
      `http://${serverAddress}/api/wikilike/read/${props.wiki.id}`,
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
    like.value = await response.json()
  }
}

async function getStars(){

const response = await fetch(
    `http://${serverAddress}/api/wikistar/read/${props.wiki.id}`,
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
  star.value = await response.json()
}
}

getLikes()
getStars()

</script>

<template>
  <VCard class="position-relative">
    <VCardText>
      <VCol class="mb-2">
        <VCol class="wiki-details">
            <div class="wiki-header">
                <VCol class="wiki-header" >
                  <span class="circle"></span>
                  <span class="wiki-name" style="margin-left:10px; margin-right: 200px;">{{ wiki.user.username }}</span>
                  <span class="wiki-date">{{ wiki.date }}</span>
                </VCol>
              </div>
            <VCol class="wiki-title">
              <VCardTitle>{{ wiki.title }}</VCardTitle>
            </VCol>
            <VDivider />
            <VCol>
              <p class="wiki-content">{{ wiki.content }}</p>
            </VCol>
            <VCardActions>
              <VBtn @click="isDetails = !isDetails">
                Details
              </VBtn>

              <VSpacer />

              <VBtn
                icon
                size="small"
                @click="isDetails = !isDetails"
              >
                <VIcon :icon="isDetails ? 'ri-arrow-up-s-line' : 'ri-arrow-down-s-line'" />
              </VBtn>
            </VCardActions>

            <VExpandTransition>
              <div v-show="isDetails">
                <VDivider />
                <VCardText>
                  {{ wiki.content }}
                </VCardText>
              </div>
            </VExpandTransition>
            
            <VDivider />
            <VCol class="wiki-interactions">
              <IConBtn @click="checkLike"> 
                <VIcon icon="ri-heart-line"/>
              </IConBtn>
              <span class="wiki-interactions-item" style="margin-left: 10px; margin-right: 10px;">{{ like }}</span>

              <IConBtn @click="checkStar"> 
                <VIcon icon="ri-star-line"/>
              </IConBtn>
              <span class="wiki-interactions-item" style="margin-left: 10px; margin-right: 10px;">{{ star }}</span>
            </VCol>

            <VDivider />
          </VCol>
      </VCol>
    </VCardText>
  </VCard>
</template>

<style lang="scss">
.v-card .trophy {
  position: absolute;
  inline-size: 5.188rem;
  inset-block-end: 1.25rem;
  inset-inline-end: 1.25rem;
}
.wiki-image {
    width: 100px;
    height: 100px;
    border-radius: 50%; /* 원형 이미지를 위한 border-radius */
    margin-bottom: 10px;
}
// .wiki-title {
//     font-weight: bold;
//     margin-bottom: 5px;
// }
.wiki-date {
    color: #666;
    margin-bottom: 10px;
}
.wiki-content {
    margin-bottom: 10px;
}
.wiki-interactions {
    display: flex;
    width: 100%;
    margin-bottom: 10px;
}
.wiki-interactions-item {
    color: #666;
}
.wiki-comments {
    color: #333;
}

.circle {
    width: 20px;
    height: 20px;
    background-color: red;
    border-radius: 50%; /* 원 모양의 동그라미를 만들기 위해 반지름을 50%로 설정합니다. */

    margin-bottom: 5px;

    display: inline-block;
}

</style>
