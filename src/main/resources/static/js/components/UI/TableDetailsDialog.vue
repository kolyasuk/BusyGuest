<template>
		<v-dialog
	      v-model="dialog"
	      max-width="290"
	    >
	    <template v-slot:activator="{ on }"> 
			<v-btn depressed color="brown darken-1" large class="tableButton" @click="openTableDetails">{{table.tableNum}}</v-btn>
		</template>
      <v-card>
        <v-card-title class="headline">Деталі столика</v-card-title>

        <v-card-text>
        <booked-tables-list :bookedTables="bookedTables" :table="table"></booked-tables-list>
          
        <booking-table-dialog v-if="role=='VISITOR'" :table="table"></booking-table-dialog>
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn
            color="green darken-1"
            flat="flat"
            @click="dialog = false"
          >
            Disagree
          </v-btn>

          <v-btn
            color="green darken-1"
            flat="flat"
            @click="dialog = false"
          >
            Agree
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
</template>

<script>
    import { mapState} from 'vuex'
    import EventBus from 'eventBus/event-bus.js'
	import BookingTableDialog from 'components/UI/BookingTableDialog.vue'
    import BookedTablesList from 'components/tables/bookedTables/BookedTablesList.vue'
    import bookedTablesApi from 'api/bookedTableApi'
    import {addHandler} from 'util/ws.js'
    
    export default {        
    	props:['table'],
        computed: mapState(['role','profile', 'dragTables']),
    	components:{
    		BookedTablesList,
    		BookingTableDialog
    	},
        data(){
            return {
                bookedTables: null,
            	dialog: false,

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
        methods: {
        	openTableDetails(){
        		if(this.dragTables == false)
        			this.dialog = true
        	},
            async loadBoockedTables(tableId){
	        	const result = await bookedTablesApi.getByTable(tableId)
	        	const data = await result.json()
	        	this.bookedTables = data
        	}
      	}


    }
</script>

<style scoped>
.tableButton{
    height: 100%;
    width: 100%;
    padding: 0px;
    margin: 0px;
    min-width: 0;
}
</style>