<template>
		<v-dialog v-model="dialog" width="500">
					      <template v-slot:activator="{ on }"> 
						      <v-btn color="red lighten-2" dark v-on="on"> 
						      	{{buttonText}} 
						      </v-btn>
					      </template>
					
					      <v-card>
					        <v-card-title class="headline grey lighten-2" primary-title>
					          <span>{{title}}</span>
					        </v-card-title>
					
					        <v-card-text>
					        	<component :is="currentComponent" :props="componentProps" ></component>
					        </v-card-text>
							
							<div v-if="showMainButton">
						        <v-divider></v-divider>
								
						        <v-card-actions>
						          <v-spacer></v-spacer>
						          <v-btn color="primary" flat @click="action">
						            {{buttonText}}
						          </v-btn>
						        </v-card-actions>
					        </div>
					        
					      </v-card>
		</v-dialog>
</template>

<script>
    import { mapState} from 'vuex'
    import bookedTablesApi from 'api/bookedTableApi'
    import EventBus from 'eventBus/event-bus.js'
    
    export default {        
    	props:['buttonText', 'title','currentComponent', 'componentProps', 'showMainButton'],
        data(){
            return {
            	dialog: false,
            }
        },
        computed: mapState(['role','profile']),
        methods: {
	        action(){
	        	EventBus.$emit('dialog-action', this.componentProps.id)
	        	this.dialog = false
	        },
        }
    }
</script>

<style>

</style>