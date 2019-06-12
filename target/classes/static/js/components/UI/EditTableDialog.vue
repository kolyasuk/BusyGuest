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
		          <v-icon style="font-size: 20px" color="green darken-2">edit</v-icon>
		        </v-btn>
		      </v-badge>
		</template>
      <v-card>
        <v-card-title class="headline">
	        <span>Редагувати столик №{{table.tableNum}}</span>
        </v-card-title>
		
        <v-card-text>
           <div class="text-xs-center">									
				<table-form :table="table"></table-form>
			</div>
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>


          <v-btn
            color="green darken-1"
            flat="flat"
            @click="dialog = false"
          >
           Закрити
          </v-btn>
        </v-card-actions>
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
        computed: mapState(['role','profile', 'dragTables']),
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
      	methods:{
      		openTableSettings(){
        		this.dialog = true
        	}
      	}

    }
</script>

<style scoped>
.table-badge{
    top: -5px;
    right: 10px;
    position: absolute;
    z-index: 1;
}
</style>