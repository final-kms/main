<script setup>
import Footer from '@/layouts/components/Footer.vue'
import NavItems from '@/layouts/components/NavItems.vue'
import NavbarThemeSwitcher from '@/layouts/components/NavbarThemeSwitcher.vue'
import UserProfile from '@/layouts/components/UserProfile.vue'
import logo from '@images/logo.svg?raw'
import VerticalNavLayout from '@layouts/components/VerticalNavLayout.vue'

import { useRouter } from 'vue-router';

// axios 패키지
import axios from 'axios'
// input 박스 클릭시 block 노출
import { ref } from 'vue'
const setFocus = ref(false);
function handleFocus(){
  setFocus.value = true
  console.log(setFocus.value)

  const searchTerm = searchKeyword.value.keyword.replace(/\s/g, '').toLowerCase(); // 검색 키워드를 소문자로 변환
  let filteredData = null;

  if(searchKeyword.value.keyword != null && searchKeyword.value.keyword != '' && searchKeyword.value.keyword.trim() !== ''){
    filteredData = responseData.value.filter(item => item.replace(/\s/g, '').toLowerCase().includes(searchTerm)); // 걸러진 데이터 필터링
  } else {
    // filteredData = '데이터가 없습니다'
    // 추후 이 자리에 이전 검색 기록 넣기
    filteredData = ['검색 데이터가 없습니다']
  }

  if (filteredData.length > 0) { // 걸러진 데이터가 있는 경우
    filterKeywords.value = filteredData; // filterKeyword에 걸러진 데이터 저장
  } else {
    filterKeywords.value = ['검색 데이터가 없습니다']
  }

}

// input 박스 외부 클릭시 block 사라짐
function handleBlur(){
  setFocus.value = false
  console.log(setFocus.value)
}

// 키보드 입력에 따른 목록 변환
const filterKeywords = ref();
function keyHandle() {
  const searchTerm = searchKeyword.value.keyword.replace(/\s/g, '').toLowerCase(); // 검색 키워드를 소문자로 변환
  const searchTermRegex = makeRegexByCho(searchTerm);
  let filteredData = null;

  if(searchKeyword.value.keyword != null && searchKeyword.value.keyword != '' && searchKeyword.value.keyword.trim() !== ''){
    filteredData = responseData.value.filter(item => item.replace(/\s/g, '').toLowerCase().match(searchTermRegex)); // 걸러진 데이터 필터링
  } else {
    // filteredData = '데이터가 없습니다'
    // 추후 이 자리에 이전 검색 기록 넣기
    filteredData = ['검색 데이터가 없습니다']
  }

  if (filteredData.length > 0) { // 걸러진 데이터가 있는 경우
    filterKeywords.value = filteredData; // filterKeyword에 걸러진 데이터 저장
  } else {
    filterKeywords.value = ['검색 데이터가 없습니다']
  }
}


// 한글 즉시 인식
function changeKeyword(event) {
  searchKeyword.value.keyword = event.target.value
}

// 초성 검색 기능
// 1. 초성배열
const CHO_HANGUL = [
  'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ',
  'ㄹ', 'ㅁ', 'ㅂ','ㅃ', 'ㅅ',
  'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ',
  'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ',
];

// 2. 한글 시작 유니코드
const HANGUL_START_CHARCODE = "가".charCodeAt();

// 3. 초성, 종성 주기
const CHO_PERIOD = Math.floor("까".charCodeAt() - "가".charCodeAt());
const JUNG_PERIOD = Math.floor("개".charCodeAt() - "가".charCodeAt());

// 4. 한글 결합 함수
function combine(cho, jung, jong) {
  return String.fromCharCode(
    HANGUL_START_CHARCODE + cho * CHO_PERIOD + jung * JUNG_PERIOD + jong
  );
}

// 5. 초성검색
function makeRegexByCho(search = "") {
  const regex = CHO_HANGUL.reduce(
    (acc, cho, index) =>
      acc.replace(
        new RegExp(cho, "g"),
        `[${combine(index, 0, 0)}-${combine(index + 1, 0, -1)}]` // [시작-끝] -> [가-깋]
      ),
    search
  );

  return new RegExp(`(${regex})`, "g");
}

// 변수와 일치하는 단어 강조하기(하이라이트)



// 검색창 block 선택하기
function selectKeyword(filterKeyword){
  console.log(filterKeyword)
  searchKeyword.value.keyword = filterKeyword
  search()
}

// 검색창 block 요소 마우스 커서 하이라이트 
const highlightedKeyword = ref(null);
function mouseenterHandler(filterKeyword) {
  console.log(filterKeyword)
  highlightedKeyword.value = filterKeyword;
}
function mouseleaveHandler(filterKeyword) {
  console.log(filterKeyword)
  highlightedKeyword.value = null;
}

// 검색창 block 요소 방향키 하이라이트
function keyboardHandler(event){
  if (event.key === 'ArrowUp') {
    // 위쪽 화살표 키를 눌렀을 때 수행할 동작
    console.log(event.key)
    // event.preventDefault();]
  } else if (event.key === 'ArrowDown') {
    // 아래쪽 화살표 키를 눌렀을 때 수행할 동작
    console.log(event.key)
    console.log(searchKeyword.value.keyword)

  } else if (event.key === 'ArrowLeft') {
    // 왼쪽 화살표 키를 눌렀을 때 수행할 동작
    console.log(event.key)
  } else if (event.key === 'ArrowRight') {
    // 오른쪽 화살표 키를 눌렀을 때 수행할 동작
    console.log(event.key)
  }
}


//////////////////////////////////////////////////////////////

// axios
const responseData = ref(null); // responseData를 ref()로 래핑
onMounted(() => {
  getData(); // 페이지가 실행될 때 getData() 메소드 실행
});

async function getData() {
  try {
    const response = await axios.get(`http://localhost:8080/api/post/search/all`, {
      headers: {
        'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiYXV0aCI6IlJPTEVfQURNSU4sUk9MRV9VU0VSIiwiZXhwIjoxNzk2MTgyOTE4fQ.ef_Rm9mtylWcmJk3h-FqB2r4pXDOa17D4xidKsyQmHMZe8cik9X8zLro9rZI-7HjjNAZ3Lb3XcQyGidfaphO6A'
      }
    });
    responseData.value = response.data;
    // console.log(response.data);
  } catch (error) {
    console.error(error);
  }
}


const router = useRouter();

const searchKeyword = ref({
  keyword: '',
  option: ''
})

// Option에 다른 검색 방향성
async function search(){ 
  if(searchKeyword.value.option == 'Wiki'){
    if(searchKeyword.value.keyword.length != 0){
      router.push({
        path: `/search/${searchKeyword.value.keyword}/${searchKeyword.value.option}`
      })
    } else {
      // 검색어 없을시 option에 맞는 리스트 전체 출력
      router.push({
        path: `/read`
      })
    }
  } else{
    if(searchKeyword.value.option == ''){searchKeyword.value.option = 'Post'}
    if(searchKeyword.value.keyword.length != 0){
      router.push({
        path: `/search/${searchKeyword.value.keyword}/${searchKeyword.value.option}`
      })
    } else {
      // 검색어 없을시 option에 맞는 리스트 전체 출력
      router.push({
        path: `/read`
      })
    }
  }
}



</script>


<template>
  <VerticalNavLayout>
    <!-- 👉 navbar -->
    <template #navbar="{ toggleVerticalOverlayNavActive }">
      <div class="d-flex h-100 align-center">
        <!-- 👉 Vertical nav toggle in overlay mode -->
        <IconBtn
          class="ms-n3 d-lg-none"
          @click="toggleVerticalOverlayNavActive(true)"
        >
          <VIcon icon="ri-menu-line" />
        </IconBtn>
        <VSpacer /> <!-- 수정 -->
        <!-- 👉 Search -->
        <div
          class="d-flex align-center cursor-pointer"
          style="user-select: none;"
        >
          <!-- 👉 Search Trigger button  수정한 부분-->
          <div class="search-group">
            <form @submit.prevent="search()" @keydown.tab="KeydownTab"> 
              
              <div class="input-group">
                <img src="C:\Users\Playdata\Desktop\hwfinal\main\frontend\src\assets\images\logos\search.png"
                class="icons"/>

                <section>
                  <input type="text" @input="changeKeyword" placeholder="Search"
                  style="display:flex; height:20px; width:450px;" class="search-bar"
                  @focus="handleFocus()" @blur="handleBlur()"
                  @keyup="keyHandle()"
                  @keydown="keyboardHandler"
                  v-model="searchKeyword.keyword"
                  >
                  </input>

                  <div class="wrapper" >
                    <div class="block" v-if="setFocus">
                      <ul v-for="filterKeyword in filterKeywords"
                      @mousedown="selectKeyword(filterKeyword)"
                      @mouseenter="mouseenterHandler(filterKeyword)"
                      @mouseleave="mouseleaveHandler(filterKeyword)"
                      @keydown="keyboardHandler(filterKeyword)"
                      :class="{ 'highlighted': highlightedKeyword === filterKeyword }"
                      > {{ filterKeyword }}</ul>
                    </div>
                  </div>

                </section>

                <select class="option-key" aria-label="Default select example" v-model="searchKeyword.option">
                    <option style="display: none;" disabled value="" class="list">Post</option>
                    <option value="Post" class="list">Post</option>
                    <option value="Wiki" class="list">Wiki</option>
                </select>

              </div>
            </form>
          </div>
          <!-- 👉 Search Trigger button end -->
        
        </div>

        <VSpacer />

        <router-link to="/writepost">Post+</router-link>
        <router-link to="/login">Wiki+</router-link>

        <IconBtn
          class="me-2"
          href="https://github.com/final-kms/main"
          target="_blank"
          rel="noopener noreferrer"
        >
          <VIcon icon="ri-github-fill" />
        </IconBtn>

        <IconBtn class="me-2">
          <VIcon icon="ri-notification-line" />
        </IconBtn>

        <NavbarThemeSwitcher class="me-2" />

        <UserProfile />
      </div>
    </template>

    <template #vertical-nav-header="{ toggleIsOverlayNavActive }">
      <RouterLink
        to="/"
        class="app-logo app-title-wrapper"
      >
        <div
          class="d-flex"
          v-html="logo"
        />

        <h1 class="font-weight-medium leading-normal text-xl text-uppercase">
          Materio
        </h1>
      </RouterLink>

      <IconBtn
        class="d-block d-lg-none"
        @click="toggleIsOverlayNavActive(false)"
      >
        <VIcon icon="ri-close-line" />
      </IconBtn>
    </template>

    <template #vertical-nav-content>
      <NavItems />
    </template>

    <!-- 👉 Pages -->
    <slot />

    <!-- 👉 Footer -->
    <template #footer>
      <Footer />
    </template>
  </VerticalNavLayout>
</template>

<style lang="scss" scoped>

.app-logo {
  display: flex;
  align-items: center;
  column-gap: 0.75rem;

  .app-logo-title {
    font-size: 1.25rem;
    font-weight: 500;
    line-height: 1.75rem;
    text-transform: uppercase;
  }
}


.search-group {
  position: relative;
  width: 100%;
  margin-top: 20px;
  display: flex;
  justify-content: center;
  align-items: center;

  .input-group {
    position: relative;
    display: flex;
    align-items: center;
    width: 100%;

    .search-bar {
    width: 100%;
    padding: 13px;
    padding-left: 45px;
    padding-right: 60px;
    border-radius: 30px;
    border: 1px solid #a738ed;
    outline: none;
    font-size: 16px;
    display: flex;
    justify-content: center;
    }

    .search-bar {
    width: 100%;
    padding: 13px;
    padding-left: 45px;
    padding-right: 60px;
    border-radius: 30px;
    border: 1px solid #a738ed;
    outline: none;
    font-size: 16px;
    display: flex;
    justify-content: center;
    }

    .wrapper {
      position: relative;
    
      .block {
      position: absolute;
      // width: 100%;
      // height: 30px;
      width: 100%; /* 변경된 부분 */
      height: auto; /* 변경된 부분 */
      max-width: 100%; /* 변경된 부분 */
      background: white;
      box-shadow: 0px 0.25rem 0.5rem;
      border-radius: 0.5rem;

        .highlighted {
          background-color: rgb(228, 213, 236);
        }

      }
    }


    .icons {
      position: absolute;
      left: -4px;
      top: 1;
      width: 12%;
      display: flex;
      padding: 12px 20px;
      pointer-events: none;
    }

    .option-key {
      position: absolute;
      // right: -1px;
      left: 390px;
      width: 58px;
      outline: none;
      // border: thin solid rgba(var(--v-border-color), var(--v-border-opacity)) ;
      border-radius: 6px ;
      block-size: 1.5625rem;
      line-height: 1.3125rem;
      padding-block: 0.125rem;
      padding-inline: 0.25rem;
      display: inline-block;
      text-align: center;
      // text-align: right;
    }

  }



}

</style>