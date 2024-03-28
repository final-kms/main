<script setup>
import Footer from '@/layouts/components/Footer.vue'
import NavItems from '@/layouts/components/NavItems.vue'
import NavbarThemeSwitcher from '@/layouts/components/NavbarThemeSwitcher.vue'
import UserProfile from '@/layouts/components/UserProfile.vue'
import logo from '@images/logo.svg?raw'
import VerticalNavLayout from '@layouts/components/VerticalNavLayout.vue'
import Search from '@/layouts/components/Search.vue'
import { useRouter } from 'vue-router';

//import router from '@/plugins/router/routes.js'

const router = useRouter();

const posts = ref([])
const searchKeyword = ref({
  keyword: ''
})
async function searchPost(){
  if(searchKeyword.value.keyword.length != 0){
    router.push({
      path: `/search/${searchKeyword.value.keyword}`
    })
  } else {
    alert('검색어를 입력해주세요!')
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

const props = defineProps({
  modelValue: Number
})
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

        <!-- 👉 Search -->
          <Search/>

        <VSpacer />

        <IconBtn
        size="height:20px;"
        type="submit"
        to="/writepost"
        >
        Post+
        </IconBtn>

        <IconBtn
        size="height:20px;"
        type="submit"
        to="/writewiki"
        >
        Wiki+
        </IconBtn>
        <!--
        <router-link to="/writepost">Post+</router-link>
        <router-link to="/login">Wiki+</router-link>
        -->

        <IconBtn
          class="me-2"
          href="https://github.com/themeselection/materio-vuetify-vuejs-admin-template-free"
          target="_blank"
          rel="noopener noreferrer"
        >
          <VIcon icon="ri-github-fill" />
        </IconBtn>

        <IconBtn class="me-2">
          <VIcon @click="openModal()" icon="ri-notification-line" />
          <div class="notification-badge">{{ props.modelValue }}</div>
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
.meta-key {
  border: thin solid rgba(var(--v-border-color), var(--v-border-opacity));
  border-radius: 6px;
  block-size: 1.5625rem;
  line-height: 1.3125rem;
  padding-block: 0.125rem;
  padding-inline: 0.25rem;
}

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

</style>
