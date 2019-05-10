<template>
		<v-dialog v-model="dialog" width="500">
					      <template v-slot:activator="{ on }"> 
						      <v-btn color="primary" fab small dark v-on="on">
							    <v-icon>edit</v-icon>
							  </v-btn>
					      </template>
					
					      <v-card>
					        <v-card-title class="headline grey lighten-2" primary-title>
					          <span>Редагувати столик № {{table.tableNum}}</span>
					          <v-spacer></v-spacer>
					          <v-btn color="primary" fab small dark @click="dialog = false">
					              <v-icon>close</v-icon>
					          </v-btn>
					        </v-card-title>
					
					        <v-card-text>
					        	<table-form :table="table"></table-form>
					        </v-card-text>
									        
					      </v-card>
		</v-dialog>
</template>

<script>
    import { mapState} from 'vuex'
    import bookedTablesApi from 'api/bookedTableApi'
    import EventBus from 'eventBus/event-bus.js'
    import TableForm from 'components/tables/TableForm.vue'
    
    export default {        
    	props:['table'],
        computed: mapState(['role','profile']),
    	components:{
        	TableForm,
    	},
        data(){
            return {
            	dialog: false,
            }
        },
        mounted () {
       	 EventBus.$on('dialog-close', () => {
       		 this.dialog = false
            })
      	},

    }
</script>

<style>

</style>