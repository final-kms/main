<script setup>
import Footer from '@/layouts/components/Footer.vue'
import NavItems from '@/layouts/components/NavItems.vue'
import NavbarThemeSwitcher from '@/layouts/components/NavbarThemeSwitcher.vue'
import UserProfile from '@/layouts/components/UserProfile.vue'
import logo from '@images/logo.svg?raw'
import VerticalNavLayout from '@layouts/components/VerticalNavLayout.vue'

import { useRouter } from 'vue-router';

//import router from '@/plugins/router/routes.js'

const router = useRouter();

const posts = ref([])
const searchKeyword = ref({
  keyword: '',
  option: ''
})

async function search(){
  if(searchKeyword.value.option == 'Wiki' | searchKeyword.value.option == 'Post'){
    if(searchKeyword.value.keyword.length != 0){
      router.push({
        path: `/search/${searchKeyword.value.keyword}/${searchKeyword.value.option}`
      })
    } else {
      alert('검색어를 입력해주세요')
    }
  } else{
    alert('option을 선택해주세요')
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
          <!-- 👉 Search Trigger button -->
          <div class="search-group">
            <form @submit.prevent="search()"> 
              
                <!-- <span class="d-none d-md-flex align-center text-disabled"> -->
                  <!-- <select class="option-key" aria-label="Default select example" v-model="searchKeyword.option">
                    <option style="display: none;" disabled value="">Option</option>
                    <option value="Post">Post</option>
                    <option value="Wiki">Wiki</option>
                  </select> -->
                <!-- </span> -->
              <div class="input-group">
                <img src="C:\Users\Playdata\Desktop\hwfinal\main\frontend\src\assets\images\logos\search.png"
                class="icons"/>

                <input type="text" v-model="searchKeyword.keyword" placeholder="Search"
                style="display:flex; height:20px; width:450px;" class="search-bar">
                </input>

                <select class="option-key" aria-label="Default select example" v-model="searchKeyword.option">
                    <option style="display: none;" disabled value="" class="list">Option</option>
                    <option value="Post" class="list">Post</option>
                    <option value="Wiki" class="list">Wiki</option>
                </select>

              </div>

                <!-- <IconBtn @click.prevent="search(searchKeyword.keyword)" class="search-btn">
                  <VIcon icon="ri-search-line" />
                </IconBtn> -->
              
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
      outline: none;
      // border: thin solid rgba(var(--v-border-color), var(--v-border-opacity)) ;
      border-radius: 6px ;
      block-size: 1.5625rem;
      line-height: 1.3125rem;
      padding-block: 0.125rem;
      padding-inline: 0.25rem;
      display: inline-block;
      text-align: center;


      // position: absolute;
      // // right: -1px;
      // left: 390px;
      // width: 200px;
      // list-style: none;
      // background: #FFFFFF;
      // border: 1px solid #C4C4C4;
      // box-sizing: border-box;
      // box-shadow: 4px 4px 14px rgba(0, 0, 0, 0.15);
      // border-radius: 10px;
      // margin-top: 9px;

    }

    // .list{
    //   border: none;
    //   background-color: #FFFFFF;
    //   font-family: 'Roboto';
    //   font-style: normal;
    //   font-weight: 400;
    //   font-size: 14px;
    //   line-height: 16px;
    //   padding: 7px 10px;
    //   margin: 5px 7px;
    //   box-sizing: border-box;
    // }


  }


  // .option-key {
  //     border: thin solid rgba(var(--v-border-color), var(--v-border-opacity));
  //     border-radius: 6px;
  //     block-size: 1.5625rem;
  //     line-height: 1.3125rem;
  //     padding-block: 0.125rem;
  //     padding-inline: 0.25rem;
  //     display: inline-block;
  //   }

  // .search-box {
  //   width: 500px;
  //   display: flex;
  //   flex-direction: column;
  //   align-items: center;
  //   justify-content: center;
  // }

  // .search-box {
  //   position: relative;
  //   width: 100%;
  //   margin-top: 20px;

  //   .search {
  //     width: 100%;
  //     padding: 13px;
  //     padding-left: 45px;
  //     padding-right: 60px;
  //     border-radius: 30px;
  //   }
  // }



}

</style>
