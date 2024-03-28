<script setup>
import DefaultLayoutWithVerticalNav from './components/DefaultLayoutWithVerticalNav.vue'
import socketModule from '/test/index.js'

const serverAddress = inject('serverAddress')
const auth = inject('auth')


const myAlarms = ref([])

function getAlarmsFromSession(){
  myAlarms.value = JSON.parse(sessionStorage.getItem("alarms"))
  getAlarmCnt()
}


socketModule.setConnectionString(`ws://${serverAddress}/ws/notifier`)
socketModule.connect()

socketModule.onOpen(() => {
  sessionStorage.removeItem('alarms')
  const formData = { auth: auth }
  socketModule.socket.value.send(JSON.stringify(formData))

  getAlarms()
})

socketModule.onMessage(message => {
    const jsonData = JSON.parse(message.data)
    var alarms = JSON.parse(sessionStorage.getItem("alarms"));
    if(alarms == null) alarms = [];
    alarms.push(jsonData);
    sessionStorage.setItem("alarms", JSON.stringify(alarms));

    getAlarmsFromSession()
})

socketModule.onClose(() => {
  
  socketModule.connect()
})

async function getAlarms(){

  const postAlarmRes = await fetch(
      `http://${serverAddress}/api/alarm/read/post`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${auth}`,          
        },
        credentials: 'include'
      }
  )

  const wikiAlarmRes = await fetch(
    `http://${serverAddress}/api/alarm/read/wiki`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${auth}`,          
        },
        credentials: 'include'
      }
  )

  const replyAlarmRes = await fetch(
    `http://${serverAddress}/api/alarm/read/reply`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${auth}`,          
        },
        credentials: 'include'
      }
  )

  if(!postAlarmRes.ok || !wikiAlarmRes.ok || !replyAlarmRes.ok) {
    alert("실패!")
  } else{
    var alarms = JSON.parse(sessionStorage.getItem("alarms"));
    if(alarms == null) alarms = [];

    var postAlarms = await postAlarmRes.json()
    var wikiAlarms = await wikiAlarmRes.json()
    var replyAlarms = await replyAlarmRes.json()

    postAlarms.forEach(function(postAlarm){
      alarms.push(postAlarm)
    })

    wikiAlarms.forEach(function(wikiAlarm){
      alarms.push(wikiAlarm)
    })

    replyAlarms.forEach(function(replyAlarm){
      alarms.push(replyAlarm)
    })

    sessionStorage.setItem("alarms", JSON.stringify(alarms));

    getAlarmsFromSession()
  }
}

const alarmCnt = ref()

function getAlarmCnt(){
  alarmCnt.value = JSON.parse(sessionStorage.getItem("alarms")).length
}


</script>

<template>

  <!-- modal -->
  <div id="myModal" class="modal">
    <div class="modal-content">
      <span class="close">&times;</span>
      <span>새 알림 !</span>
      <div v-for="(item, index) in myAlarms" :key="index">
        <span v-if="item.name == 'reply'">댓글 : {{ item.content }}</span>
        <span v-else-if="item.name == 'post'">post : {{ item.postTitle }}</span>
        <span v-else-if="item.name == 'wiki'">wiki : {{ item.wikiTitle }}</span>
      </div>
    </div>
  </div>
  
  <DefaultLayoutWithVerticalNav v-model:modelValue="alarmCnt">
    <RouterView />
  </DefaultLayoutWithVerticalNav>
</template>

<style>

.modal {
  display: none;
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(0,0,0,0.4);
}

.modal-content {
  background-color: #fefefe;
  margin: 15% auto;
  padding: 20px;
  border: 1px solid #888;
  width: 80%;
}

.close {
  color: #aaaaaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: #000;
  text-decoration: none;
  cursor: pointer;
}
</style>
<style lang="scss">
// As we are using `layouts` plugin we need its styles to be imported
@use "@layouts/styles/default-layout";
</style>
