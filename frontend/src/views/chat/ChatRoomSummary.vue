<script setup>
import { VCardItem, VCol, VDivider, VIcon, VTextField } from 'vuetify/lib/components/index.mjs';

const serverAddress = inject('serverAddress')
const auth = inject('auth')

const props = defineProps({
  chatRoom: Object
})
</script>

<template>
  <VCard class="position-relative">

    <div>
      {{ chatRoom.name }}
    </div>

  </VCard>
</template>
