<!-- <script setup>
import { hexToRgb } from '@layouts/utils';
import axios from "axios";
import { useTheme } from 'vuetify';

const vuetifyTheme = useTheme()

const options = computed(() => {
  const currentTheme = ref(vuetifyTheme.current.value.colors)
  const variableTheme = ref(vuetifyTheme.current.value.variables)
  const disabledColor = `rgba(${ hexToRgb(currentTheme.value['on-surface']) },${ variableTheme.value['disabled-opacity'] })`
  const borderColor = `rgba(${ hexToRgb(String(variableTheme.value['border-color'])) },${ variableTheme.value['border-opacity'] })`
  
  return {
    chart: {
      offsetY: -10,
      offsetX: -15,
      parentHeightOffset: 0,
      toolbar: { show: false },
    },
    plotOptions: {
      bar: {
        borderRadius: 6,
        distributed: true,
        columnWidth: '30%',
      },
    },
    stroke: {
      width: 2,
      colors: [currentTheme.value.surface],
    },
    legend: { show: false },
    grid: {
      borderColor,
      strokeDashArray: 7,
      xaxis: { lines: { show: false } },
    },
    dataLabels: { enabled: false },
    colors: [
      currentTheme.value['track-bg'],
      currentTheme.value['track-bg'],
      currentTheme.value['track-bg'],
      'rgba(var(--v-theme-primary),1)',
      currentTheme.value['track-bg'],
      currentTheme.value['track-bg'],
    ],
    states: {
      hover: { filter: { type: 'none' } },
      active: { filter: { type: 'none' } },
    },
    xaxis: {
      categories: [
        'Sun',
        'Mon',
        'Tue',
        'Wed',
        'Thu',
        'Fri',
        'Sat',
      ],
      tickPlacement: 'on',
      labels: { show: false },
      crosshairs: { opacity: 0 },
      axisTicks: { show: false },
      axisBorder: { show: false },
    },
    yaxis: {
      show: true,
      tickAmount: 4,
      labels: {
        style: {
          colors: disabledColor,
          fontSize: '13px',
        },
        formatter: value => `${ value > 999 ? `${ (value / 1000).toFixed(0) }` : value }k`,
      },
    },
    responsive: [
      {
        breakpoint: 1560,
        options: { plotOptions: { bar: { columnWidth: '35%' } } },
      },
      {
        breakpoint: 1380,
        options: { plotOptions: { bar: { columnWidth: '45%' } } },
      },
    ],
  }
})

const series = [{
  data: [
    37,
    57,
    45,
    75,
    57,
    40,
    65,
  ],
}]

const state = reactive({
  items: []
})
axios.get("http://localhost:8080/api/wiki/readall", {
  headers: {
    'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiYXV0aCI6IlJPTEVfQURNSU4sUk9MRV9VU0VSIiwiZXhwIjoxNzk2MjY1NTM1fQ.1_UIoRPlOCIlwjt5YZCDpz0-tOMQ-9UfB4ghW6MRlGmCBzqkJJTGDdW6pO1rSDsCw6lOtq1l3T266iccihzOJA'
  }
}).then((res) => {
    console.log(res.data[0])
    state.items = res;
    console.log(state.items.data[0])
})

</script>

<style>
    .button {
      position: absolute;
      top:50%;
      background-color: lightgray;
    }
</style>

<template>
  <VCol v-for="i in state.items.data?.length" :key="i">
    <div style="margin-left:20px">{{ state.items.data[i-1]?.user.username }} &nbsp; {{ state.items.data[i-1]?.date.substring(0,10) }}
        {{ state.items.data[i-1]?.date.substring(12,19) }}</div>
    <div align="right" margin-right="20px" float="left">
      <button style="background-color: #905DFF; 
        border: none;
        color: white;
        padding: 7px 15px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        border-style: solid;
        font-size: 15px;" type="button">like</button> &nbsp; 

      <button style="background-color: #905DFF; 
        border: none;
        color: white;
        padding: 7px 15px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        border-style: solid;
        font-size: 15px;" type="button">star</button>
    </div>
    <br/>
    <div>
      <table border="1" width="100%">
        <tr align="center">
          <td>{{ state.items.data[i-1]?.title }}</td>
        </tr>
        <tr align="center" height="500">
          <td>{{ state.items.data[i-1]?.content }}</td>
        </tr>
        <tr align="center">
          <td>{{ state.items.data[i-1]?.tag }}</td>
        </tr>
      </table>
    </div>
  </VCol>
</template> -->