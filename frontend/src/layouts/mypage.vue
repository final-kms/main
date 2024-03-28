<script setup>
import DefaultLayoutWithVerticalNav from './components/MyPageLayoutWithVerticalNav.vue'
import socketModule from '/test/index.js'

const serverAddress = inject('serverAddress')
const auth = inject('auth')

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

  }
}

function openModal() {
  document.getElementById('myModal').style.display = 'block';
}

// 모달 닫기
function closeModal() {
  document.getElementById('myModal').style.display = 'none';
}


onMounted(() => {

  // 닫기 버튼에 클릭 이벤트 추가
  if (document.querySelector('.close')) {
      document.querySelector('.close').addEventListener('click', closeModal);
  }

})


window.addEventListener('beforeunload', function() {
    // 웹 소켓 연결을 닫음
    socketModule.socket.close();
});


</script>

<template>

  <div id="myModal" class="modal">
    <div class="modal-content">
      <span class="close">&times;</span>
      <p>알람 메시지를 여기에 작성합니다.</p>
    </div>
  </div>

  
  <DefaultLayoutWithVerticalNav>
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
