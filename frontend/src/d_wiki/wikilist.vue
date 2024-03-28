<script setup>
import axios from 'axios'
import { ref, watch } from 'vue'
import { useRouter } from 'vue-router'
import ViewRecentWiki from '@/d_dashboard/ViewRecentWiki.vue'
import ViewWikiLikeRank from '@/d_dashboard/ViewWikiLikeRank.vue'
import ViewWikiStarRank from '@/d_dashboard/ViewWikiStarRank.vue'


const serverAddress = inject('serverAddress')
const auth = inject('auth')

// 토큰 브라우저에서 받아오기
let authToken = 'Bearer '
let jwtToken = auth

authToken = authToken + jwtToken

const router = useRouter()

const list = ref([]) //보여지는 리스트
const cache = ref([]) //리스트 전체

const listCunt = ref('5') // 한 페이지에 노출될 게시글 개수

let currentPage = ref(0) //현재 페이지
let pageNum = 10 //페이징 갯수
const pageList = ref([]) // 보여지는 페이지 리스트
let totalPage = ref(0) //페이지 숫자

let isBtnFirst = ref(true)
let isBtnPrev = ref(true)
let isBtnNext = ref(true)
let isBtnLast = ref(true)

const currentPageListStart = () => {
  return Math.floor(currentPage.value / pageNum) * pageNum
}
// 페이징
const paging = () => {
  //보여지는 페이지 리셋
  pageList.value = []

  //몇페이지 까지 있는지 확인
  if (cache.value.length % listCunt.value == 0) {
    totalPage.value = cache.value.length / listCunt.value - 1
  } else {
    totalPage.value = Math.ceil(cache.value.length / listCunt.value) - 1
  }

  //현재페이지 기준으로 페이징 숫자 넣기
  let pageListStart = currentPageListStart()
  for (let i = 0; i < pageNum; i++) {
    if (totalPage.value >= pageListStart) {
      pageList.value.push(pageListStart)
      pageListStart++
    }
  }
}

const getList = () => {
  axios
    .get(`http://${serverAddress}/api/wiki/read/public`, {
      headers: {
        Authorization: authToken,
      },
    })
    .then(res => {
      cache.value = res.data
      list.value = [] //보여지는 게시물 리셋

      let listIdx = listCunt.value * currentPage.value // 보여질 게시물 index
      for (let i = 0; i < listCunt.value; i++) {
        //게시글 수 만큼 루프
        if (cache.value.length > listIdx) {
          //
          list.value.push(cache.value[listIdx])
          listIdx++
        }
      }
      paging()
      pageBtnCheck()
    })
}

getList()

//페이지 번호 클릭시
const page = e => {
  currentPage.value = e
  getList()
}

//리스트 갯수 수정시
watch(listCunt, (after, before) => {
  currentPage.value = 0
  getList()
})

const pageBtnCheck = () => {
  isBtnFirst.value = currentPage.value == 0 ? true : false
  isBtnPrev.value = currentPage.value == 0 ? true : false

  isBtnNext.value = currentPage.value == totalPage.value ? true : false
  isBtnLast.value = currentPage.value == totalPage.value ? true : false
}

watch(currentPage, (after, before) => {
  pageBtnCheck()
})

//페이지 처음/끝/이전/다음 버튼 클릭시
const pageArrow = e => {
  let movePage = parseInt(currentPage.value)
  if (e == 'first') {
    //처음으로
    movePage = 0
  } else if (e == 'last') {
    //마지막
    movePage = totalPage.value
  } else if (e == 'prev') {
    //이전
    movePage = currentPageListStart() - 1
    movePage < 0 ? (movePage = 0) : ''
  } else {
    //다음
    movePage = currentPageListStart() + 10
    movePage >= totalPage.value ? (movePage = totalPage.value) : ''
  }
  page(movePage)
}

function sendId(id) {
  router.push({ name: 'readwiki', params: { id } })
}
</script>

<template>
    <VRow>
    <VCol
      cols="12"
      md="8"
      class="mb-4"
    >
  <div style="margin-bottom: 20px"><VCardTitle color>Wiki List</VCardTitle></div>

  <select v-model="listCunt">
    <option
      value="5"
      selected
    >
      5개씩 보기
    </option>
    <option value="10">10개씩 보기</option>
    <option value="20">20개씩 보기</option>
  </select>
  <br />
  <br />
  <hr />
  <table
    class="wikititle"
    border="0"
    style="border-spacing: 5px; width: 100%; height: 50%"
  >
    <template
      v-for="(item, idx) in list"
      :key="idx"
    >
      <tr>
        <td width="80%">
          <button
            type="button"
            @click="sendId(item.id)"
            style="color: #905dff"
          >
            {{ item.title }}
          </button>
        </td>
        <td
          width="10%"
          align="center"
        >
          {{ item.user.username }}
        </td>
        <td
          width="10%"
          align="center"
        >
          {{ item.date.substring(0, 10) }}
        </td>
      </tr>
      <tr>
        <td colspan="3"><hr /></td>
      </tr>
    </template>
  </table>
  <br />

  <div style="display: flex; gap: 5px; justify-content: center">
    <ul class="pagination">
      <li
        class="page-item"
        :class="{ disabled: isBtnFirst }"
      >
        <a
          class="page-link"
          href="#"
          @click.prevent="pageArrow('first')"
          >First</a
        >
      </li>
      <li
        class="page-item"
        :class="{ disabled: isBtnPrev }"
      >
        <a
          class="page-link"
          href="#"
          @click.prevent="pageArrow('prev')"
          >Previous</a
        >
      </li>
      <template
        v-for="(item, index) in pageList"
        :key="`list-${index}`"
      >
        <li
          class="page-item"
          :class="{ active: item == currentPage, bold: item == currentPage }"
        >
          <a
            class="page-link"
            href="#"
            @click.prevent="page(item)"
            >{{ item + 1 }}</a
          >
        </li>
      </template>
      <li
        class="page-item"
        :class="{ disabled: isBtnNext }"
      >
        <a
          class="page-link"
          href="#"
          @click.prevent="pageArrow('next')"
          >Next</a
        >
      </li>
      <li
        class="page-item"
        :class="{ disabled: isBtnLast }"
      >
        <a
          class="page-link"
          href="#"
          @click.prevent="pageArrow('last')"
          >Last</a
        >
      </li>
    </ul>
  </div>
  <!-- <Post style="margin-bottom: 20px;"/>
  <Post style="margin-bottom: 20px;"/>
  <Post style="margin-bottom: 20px;"/> -->
</VCol>  
<VCol
cols="12"
md="4"
>
  <ViewRecentWiki style="margin-bottom: 20px;"/>
  <ViewWikiLikeRank style="margin-bottom: 20px;"/>
  <ViewWikiStarRank style="margin-bottom: 20px;"/>
</VCol>  

</VRow>

</template>

<style scoped>
.bold {
  font-weight: bold;
}

li {
  margin: 10px;
  float: left;
}

ul {
  list-style: none;
}

select {
  -webkit-appearance: none; /* Safari 및 Chrome에서의 appearance 속성 제거 */
  -moz-appearance: none; /* Firefox에서의 appearance 속성 제거 */
  appearance: none; /* 기본 스타일 제거 */
  border: 2px solid;
  padding-right: 20px; /* 오른쪽 여백 추가하여 화살표를 표시할 공간 확보 */
  background-image: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="black"><path d="M10 14a1 1 0 01-.707-.293l-6-6a1 1 0 111.414-1.414L10 11.586l5.293-5.293a1 1 0 111.414 1.414l-6 6A1 1 0 0110 14z"/></svg>'); /* 화살표 SVG 이미지를 배경 이미지로 사용 */
  background-repeat: no-repeat; /* 이미지 반복 설정 */
  background-position: right center; /* 이미지 위치 설정 */
  cursor: pointer;
}
/* 선택된 상태의 select 태그 스타일 */
select:active,
select:focus {
  background-image: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="black"><path d="M10 6a1 1 0 01.707.293l6 6a1 1 0 11-1.414 1.414L10 8.414 4.707 13.707a1 1 0 01-1.414-1.414l6-6A1 1 0 0110 6z"/></svg>'); /* 위쪽 화살표 SVG 이미지를 배경 이미지로 사용 */
}
</style>
