<template>
		
		<v-dialog
	      v-model="dialog"
	      max-width="290"
	    >
	    <template v-slot:activator="{ on }"> 
			<v-badge v-if="role != 'VISITOR'" v-show="dragTables==false"
			        color="transparent"
			        class="table-badge"
		     	>
		        <v-btn slot="badge"
		               flat
		               icon
		               dark
		               small
		               :ripple="false"
		               @click="dialog = true"
		               >
		          <v-icon style="font-size: 20px" color="red lighten-2">delete</v-icon>
		        </v-btn>
		      </v-badge>
		</template>
      <v-card>
        <v-card-title class="headline grey lighten-2" primary-title>
			<span>Видалити столик №{{table.tableNum}}</span>
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
        computed: mapState(['role','profile', 'dragTables']),
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
      	methods:{
      		async remove(){
        		const result = await tablesApi.remove(this.table._id)
		        const data = await result.json()
		        EventBus.$emit('table-deleted', data)
	        },
      	}

    }
</script>

<style scoped>
.table-badge{
    bottom: 10px;
    right: 10px;
    position: absolute;
    z-index: 1;
}
</style>