<template>
		<v-dialog v-model="dialog" width="500">
					      <template v-slot:activator="{ on }"> 
						      <v-btn color="primary" fab small dark v-on="on">
							    <v-icon>delete</v-icon>
							  </v-btn>
					      </template>
					
					      <v-card>
					        <v-card-title class="headline grey lighten-2" primary-title>
					          <span>Видалити столик № {{table.tableNum}}</span>
					          <v-spacer></v-spacer>
					          <v-btn color="primary" fab small dark @click="dialog = false">
					              <v-icon>close</v-icon>
					          </v-btn>
					        </v-card-title>
					
					        <v-card-text>
					        	Увага, видалення столика призведе до повного зникнення даних про нього!
					        </v-card-text>
					        
					        <v-divider></v-divider>
						        <v-card-actions>
						          <v-spacer></v-spacer>
						          <v-btn color="primary" flat @click="dialog=false">
						            Відмінити
						          </v-btn>
						          <v-btn color="primary" flat @click="remove">
						            Видалити
						           </v-btn>
						        </v-card-actions>
									        
					      </v-card>
		</v-dialog>
</template>

<script>
    import { mapState} from 'vuex'
    import tablesApi from 'api/tablesApi'
    import EventBus from 'eventBus/event-bus.js'
    
    export default {        
    	props:['table'],
        computed: mapState(['role','profile']),
        data(){
            return {
            	dialog: false,
            }
        },
        methods:{
        	async remove(){
        		const result = await tablesApi.remove(this.table._id)
		        const data = await result.json()
		        EventBus.$emit('table-deleted', data)
	        },
        }

    }
</script>

<style>

</style>