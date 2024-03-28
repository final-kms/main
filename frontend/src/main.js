import { createApp } from 'vue'
import App from '@/App.vue'
import { registerPlugins } from '@core/utils/plugins'
import VueCookies from 'vue-cookies'

//fort-awesome
import { library } from '@fortawesome/fontawesome-svg-core';
import { faUserSecret } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

import { faThumbsUp as farThumsUp } from '@fortawesome/free-regular-svg-icons';
import { faThumbsUp as fasThumsUp } from '@fortawesome/free-solid-svg-icons';

import { faStar as fasStar } from '@fortawesome/free-regular-svg-icons';
import { faStar as farStar } from '@fortawesome/free-solid-svg-icons';

// Styles
import '@core/scss/template/index.scss'
import '@layouts/styles/index.scss'

// Create vue app
const app = createApp(App)

app.use(VueCookies)


app.provide("app", app)

const serverAddress = "localhost:8080"
//const serverAddress = "ec2-13-209-98-152.ap-northeast-2.compute.amazonaws.com:8080"

app.provide('serverAddress', serverAddress)
registerPlugins(app)

library.add(faUserSecret, farThumsUp, fasThumsUp, farStar, fasStar)

app.component('font-awesome-icon', FontAwesomeIcon)

// Mount vue app
app.mount('#app')
