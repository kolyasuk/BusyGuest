import Vue from 'vue'
import VueRouter from 'vue-router'
import Establishment from 'pages/Establishment.vue'
import CreateEstablishment from 'pages/CreateEstablishment.vue'
import EstablishmentsList from 'components/establishments/EstablishmentsList.vue'
import TablesList from 'components/tables/TablesList.vue'
import BookedTable from 'pages/BookedTable.vue'

Vue.use(VueRouter)


 const routes = [
   {path: '/establishment/:id', component: Establishment, props: true},
   {path: '/establishment', component: CreateEstablishment, props: true},
   {path: '/', component: EstablishmentsList, props: true},
   {path: '/bookedTable/:id', component: BookedTable, props: true},



    { path: '*', component: EstablishmentsList}

 ]

export default new VueRouter({
    mode: 'history',
    routes
})