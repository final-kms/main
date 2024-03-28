<script setup>
import VerticalNavSectionTitle from '@/@layouts/components/VerticalNavSectionTitle.vue';
import VerticalNavGroup from '@layouts/components/VerticalNavGroup.vue';
import VerticalNavLink from '@layouts/components/VerticalNavLink.vue';

const isLogin = ref(true)

function getJwt() {
  if($cookies.get('jwtToken')) {

  }
}
</script>

<template>
  <VerticalNavLink
    :item=" {
      title: 'My Info',
      to: '/mypage',
      icon: 'ri-home-line'      
    }">    
  </VerticalNavLink>

  <VerticalNavLink
    :item=" {
      title: 'My Tables',
      to: '/mytables',
      icon: 'ri-home-line'      
    }">    
  </VerticalNavLink>

  <VerticalNavLink
    :item=" {
      title: 'My Posts',
      to: '/myposts',
      icon: 'ri-home-line'      
    }">    
  </VerticalNavLink>
  
  <VerticalNavLink
    :item=" {
      title: 'My Wikis',
      to: '/mywikis',
      icon: 'ri-home-line'      
    }">    
  </VerticalNavLink>

  <VerticalNavLink
    :item=" {
      title: 'My Replys',
      to: '/myreplys',
      icon: 'ri-home-line'      
    }">    
  </VerticalNavLink>

  <VerticalNavLink
    :item=" {
      title: 'My Likes',
      to: '/mylikes',
      icon: 'ri-home-line'      
    }">    
  </VerticalNavLink>

  <VerticalNavLink
    :item=" {
      title: 'My Stars',
      to: '/mystars',
      icon: 'ri-home-line'      
    }">    
  </VerticalNavLink>

</template>
