<template>
<div style="text-align: right; display: inline;">
	<p>Ваші замовлення:</p>
	<div v-for="(bookedTable, index) in userBookedTables" :key="index">
		<router-link :to="`/bookedTable/`+`${bookedTable._id}`">
		<span @click="reloadBookedTableComponent(bookedTable._id)">{{bookedTable.estb.name}}
			{{bookedTable.bookedOn | moment("MM/DD/YYYY HH:mm ")}}</span> </router-link>

            <v-btn flat icon color="red" @click="deleteBook(bookedTable, index)">
              <v-icon color="red lighten-2">delete</v-icon>
            </v-btn>
		
	</div>
</div>
</template>

<script>
  import bookedTablesApi from 'api/bookedTableApi'
  import { mapState, mapActions} from 'vuex'
  import EventBus from 'eventBus/event-bus.js'
  import {addHandler} from 'util/ws.js'

    export default {
        data: function () {
            return {
                userBookedTables:[]
            }
        }, 
        computed: mapState(['profile','role' ,'userBookedTables']),
        created(){
            this.loadBoockedTables(this.profile.id)
        },
        methods:{
         	...mapActions(['getUserBookedTablesAction']),
            deleteBook(bookedTable, index){
            	const currentRoute = this.$route.path
            	if(currentRoute.includes('bookedTable'))
            		this.$router.push(`/establishment/`+bookedTable.estb._id);
            	
                bookedTablesApi.remove(bookedTable._id)
                EventBus.$emit('user-book-deleted')
            },
            loadBoockedTables(userId){
 				this.getUserBookedTablesAction(userId)
                this.$resource('/'+this.role.toLowerCase()+'{/userId}/userBookedTable').get({userId:this.profile.id}).then(result =>
                result.json().then(data => this.userBookedTables = data))

                addHandler(data=>{
                if (data.objectType === 'BOOKEDTABLE') {
                    const index = this.userBookedTables.findIndex(item => item._id === data.body._id)
                     switch (data.eventType) {
                         case 'CREATE':
                            if(data.body.user.id==this.profile.id)
                            this.userBookedTables.push(data.body)
                        break
                         case 'UPDATE':
                            if(data.body.user.id==this.profile.id){
                            if (index > -1) {
                                this.userBookedTables.splice(index, 1, data.body)
                            } else {
                                this.userBookedTables.push(data.body)
                            }
                        }
                         break
                         case 'REMOVE':
                           this.userBookedTables.splice(index, 1)
                            break
                        default:
                            console.error(`Looks like the event type is unknown "${data.eventType}"`)
                     }

                }else {
                    console.error(`Looks like the object type is unknown "${data.objectType}"`)
                }
            })  
            },
            reloadBookedTableComponent(id){
                EventBus.$emit('reload-booked-table-component', id)
            }
        }

    }
</script>

<style>
</style>