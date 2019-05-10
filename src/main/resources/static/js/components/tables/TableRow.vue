<template>
			<div>
				<v-expansion-panel-content :style="tableBooked">

		            <template v-slot:header>
		              <div>Стіл №{{table.tableNum}}</div>
		            </template>
		            
		            <v-card max-width="350">
		              <v-card-text >
		              	<booked-tables-list :bookedTables="bookedTables" :table="table"></booked-tables-list>
		              	
							<booking-table-dialog v-if="role=='VISITOR'" :table="table"></booking-table-dialog>

				            <div v-if="role=='ESTB'" class="text-xs-center">									
								<edit-table-dialog :table="table"></edit-table-dialog>
								<delete-table-dialog :table="table"></delete-table-dialog>
							</div>
		              </v-card-text>
		            </v-card>
	          </v-expansion-panel-content>

	       </div>
</template>

<script>
	import BookingTableDialog from 'components/UI/BookingTableDialog.vue'
    import BookedTablesList from 'components/tables/bookedTables/BookedTablesList.vue'
	import EditTableDialog from 'components/UI/EditTableDialog.vue'
	import DeleteTableDialog from 'components/UI/DeleteTableDialog.vue'
    import {mapState, mapActions} from 'vuex'
    import EventBus from 'eventBus/event-bus.js'
    import bookedTablesApi from 'api/bookedTableApi'
    import {addHandler} from 'util/ws.js'

    export default {
        components: {
            BookedTablesList,
            BookingTableDialog,
            EditTableDialog,
            DeleteTableDialog,
        },
        props: ['table','index'],
        data() {
            return {
                alreadyBooked: null,
                bookedTables: null,
                tableBooked: 'color: black'
            }
        },
        computed: mapState(['profile','role']),
        mounted () {
            EventBus.$on('user-book-deleted', (index) => {
                this.alreadyBooked=null
            }),
            EventBus.$on('found-new-book', (bookedTable) => {
            	if(this.table._id == bookedTable.table._id)
            		this.tableBooked = 'color: red'
            })
            
       	},
        methods: {
            async loadBoockedTables(tableId){
	        	const result = await bookedTablesApi.getByTable(tableId)
	        	const data = await result.json()
	        	this.bookedTables = data
        	}
        },
        created(){
        	this.loadBoockedTables(this.table._id)
        	
            addHandler(data=>{
                if (data.objectType === 'BOOKEDTABLE') {
                    const index = this.bookedTables.findIndex(item => item._id === data.body._id)
                     switch (data.eventType) {
                         case 'CREATE':
                         if(data.body.table._id==this.table._id && this.role =='ESTB')
                        	this.tableBooked =  'color: red'
                            this.bookedTables.push(data.body)
                            break
                         case 'UPDATE':
                         if(data.body.table._id==this.table._id){
                        	 if(data.body.accepted)
                        		 this.tableBooked =  'color: black'
                            if (index > -1) {
                                this.bookedTables.splice(index, 1, data.body)
                            } else {
                                this.bookedTables.push(data.body)
                            }
                         }
                            break
                         case 'REMOVE':
                           this.bookedTables.splice(index, 1)
                            break
                        default:
                            console.error(`Looks like the event type is unknown "${data.eventType}"`)
                     }
                }else {
                    console.error(`Looks like the object type is unknown "${data.objectType}"`)
                }
            })
        },
        
    }

</script>

<style scoped>
.table-button{
	width: 40px;
	position: absolute;
	left:50px;
}

.button-edit{
	top:0px;
}

.button-delete{
	top:15px;
}

.table{
	text-align: center; 
	border-style: solid; 
	width: 50px; 
	margin: auto;
	margin-left:45px;
	position: relative;
}

</style>