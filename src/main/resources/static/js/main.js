import Vue from 'vue'
import 'api/resource'
import App from 'pages/App.vue'
import '@babel/polyfill'
import router from 'router/router.js'
import store from 'store/store.js'
import { connect } from './util/ws'

connect()


Vue.use(require('vue-moment'));


new Vue({
    el: '#app',
    router,
    store,
    render: a => a(App),
})
