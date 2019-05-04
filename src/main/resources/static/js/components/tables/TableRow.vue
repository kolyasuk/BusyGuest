<template>
			<div>
				<v-expansion-panel-content>

		            <template v-slot:header >
		              <div>Стіл №{{table.tableNum}}</div>
		            </template>
		            
		            <v-card max-width="350">
		              <v-card-text >
		              	<booked-tables-list :bookedTables="bookedTables" :table="table"></booked-tables-list>
		              	
							<book-dialog v-if="role=='VISITOR'" :title="`Замовити столик №`+table.tableNum" :buttonText="'Замовити'" 
							:currentComponent="bookingTableForm" :componentProps="bookingTableFormProps"></book-dialog>
							
							<book-dialog v-if="role=='ESTB'" :title="`Редагувати столик №`+table.tableNum" :buttonText="'Редагувати'" 
							:currentComponent="tableForm"  :componentProps="tableFormProps"></book-dialog>
							
				            <div v-if="role=='ESTB'" class="text-xs-center">
						            <v-btn color="primary" fab small dark>
						              <v-icon>edit</v-icon>
						            </v-btn>
						          	<v-btn fab dark small color="red">
								      <v-icon dark>delete</v-icon>
								    </v-btn>
				             </div>

		              </v-card-text>
		            </v-card>
	          </v-expansion-panel-content>

	       </div>
</template>

<script>
	import BookDialog from 'components/UI/BookDialog.vue'
    import BookedTablesList from 'components/tables/bookedTables/BookedTablesList.vue'
    import {mapState, mapActions} from 'vuex'
    import EventBus from 'eventBus/event-bus.js'
    import bookedTablesApi from 'api/bookedTableApi'
    import {addHandler} from 'util/ws.js'
    import BookingTableForm from 'components/tables/bookedTables/BookingTableForm.vue'
    import TableForm from 'components/tables/TableForm.vue'

    export default {
        components: {
            BookedTablesList,
            BookDialog,
            BookingTableForm,
            TableForm,
        },
        props: ['table','index'],
        data() {
            return {
                alreadyBooked: null,
                bookedTables: null,
                bookingTableForm: BookingTableForm,
                bookingTableFormProps:{
                	id: this.table._id,
                	table: this.table,
                },
                tableForm: TableForm,
                tableFormProps:{
                	table: this.table,
                },
            }
        },
        computed: mapState(['profile','role']),
        mounted () {
            EventBus.$on('user-book-deleted', (index) => {
                this.alreadyBooked=null
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
                         if(data.body.table._id==this.table._id)
                            this.bookedTables.push(data.body)
                            break
                         case 'UPDATE':
                         if(data.body.table._id==this.table._id){
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