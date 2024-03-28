<script setup>
import { VCardItem, VCol, VDivider, VIcon, VTextField } from 'vuetify/lib/components/index.mjs';

const isDetails = ref(false)

const auth = inject('auth')

const props = defineProps({
    table: Object
})
</script>

<template>
  <VCard class="position-relative">
      <VCol class="mb-2">
            <VCol class="table-name">
              {{ table.table.name }}
            </VCol>
            <VDivider />
            <VCol>
              <p class="table-desc">{{ table.table.description }}</p>
            </VCol>
      </VCol>
  </VCard>
</template>

<style lang="scss">
.v-card .trophy {
  position: absolute;
  inline-size: 5.188rem;
  inset-block-end: 1.25rem;
  inset-inline-end: 1.25rem;
}
.table-image {
    width: 100px;
    height: 100px;
    border-radius: 50%; /* 원형 이미지를 위한 border-radius */
    margin-bottom: 10px;
}

.table-name {
     font-weight: bold;
//     margin-bottom: 5px;
}

.table-date {
    color: #666;
    margin-bottom: 10px;
}
.table-desc {
    margin-bottom: 10px;
}
.table-interactions {
    display: flex;
    width: 100%;
    margin-bottom: 10px;
}
.table-interactions-item {
    color: #666;
}
.table-comments {
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
