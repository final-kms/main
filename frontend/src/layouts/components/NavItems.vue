<script setup>
import VerticalNavSectionTitle from '@/@layouts/components/VerticalNavSectionTitle.vue';
import VerticalNavGroup from '@layouts/components/VerticalNavGroup.vue';
import VerticalNavLink from '@layouts/components/VerticalNavLink.vue';

</script>

<template>
    <VerticalNavLink
    :item=" {
      title: 'My Page',
      to: '/mypage',
      icon: 'ri-home-line'      
    }">    
  </VerticalNavLink>

  <VerticalNavLink
    :item=" {
      title: 'Post',
      to: '/dashboard',
      icon: 'ri-home-line'      
    }">    
  </VerticalNavLink>

  <VerticalNavLink
      :item="{
        title: 'Wiki',
        to: '/wiki',
        icon:'ri-book-line'
      }"
  />

  <VerticalNavLink
    :item="{
      title: 'Account Settings',
      icon: 'ri-user-settings-line',
      to: '/account-settings',
    }"
  />

  <VerticalNavLink
    :item="{
      title: 'Login',
      icon: 'ri-login-box-line',
      to: '/login',
    }"
  />
  <VerticalNavLink
    :item="{
      title: 'Logout',
      icon: 'ri-login-box-line',
      to: '/logout',
    }"
  />

  <VerticalNavLink
    :item="{
      title: 'Register',
      icon: 'ri-user-add-line',
      to: '/register',
    }"
  />

  <VerticalNavLink
    :item="{
      title: 'Table',
      icon: 'ri-group-line',
      to: '/table',
    }"
  /> 

  <VerticalNavLink
    :item="{
      title: 'Chat',
      icon: 'ri-chat-1-line',
      to: '/chat',
    }"
  /> 

  <VerticalNavLink
    :item="{
      title: 'Follow',
      icon: 'ri-chat-1-line',
      to: '/follow',
    }"
  /> 


</template>
