import Vue from 'vue'
import 'api/resource'
import App from 'pages/App.vue'
import '@babel/polyfill'
import router from 'router/router.js'
import store from 'store/store.js'
import { connect } from './util/ws'
import VDragged from 'v-dragged'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
var VueCookie = require('vue-cookie');
import Vuelidate from 'vuelidate'

Vue.use(Vuelidate)

Vue.use(VueCookie);

Vue.use(Vuetify)

Vue.use(VDragged)

connect()


Vue.use(require('vue-moment'));


new Vue({
    el: '#app',
    router,
    store,
    render: a => a(App),
})
