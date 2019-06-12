<template>
    <div>
    	<span style="font-size: 18px;"><b>Додати столик:</b></span><br>
		Номер: <input style="border: 2px solid #87CEFA; width: 40px;" type="number" min=1 placeholder="number.." v-model="tableNum" /><br>
        Кількість місць: <input style="border: 2px solid #87CEFA; width: 40px;" type="number" min=1 placeholder="seats.." v-model="seats" />
        <v-btn color="success" @click="saveTable()">Зберегти</v-btn>
    </div>
</template>

<script>
    import { mapState, mapMutations} from 'vuex'
    import tablesApi from 'api/tablesApi'
    import EventBus from 'eventBus/event-bus.js'
    
    export default {
    	props:['table','establishment'],
        data(){
            return {
            	_id: null,
                tableNum: 1,
                seats: 1,
                estb: null
            }
        },
        created: function(){
        	if(this.table){
	        	this._id = this.table._id 
	            this.tableNum = this.table.tableNum
	            this.seats = this.table.seats
	            this.estb = this.table.estb
        	}else{        		
	            this.estb = this.establishment
        	}
        },
        computed: mapState(['estbCoordinates']),
        methods: {
        	...mapMutations(['setTablesCoordinates']),
        	async saveTable() {
	            var table = {
	            	_id: this.id,
	            	tableNum : this.tableNum,
	            	seats: this.seats,
	            	estb: this.estb 
	            }
	            
	            if(this._id){
	            	const result = await tablesApi.update(table)
		        	const data = await result.json()
		        	
		        	EventBus.$emit('table-edited', data)
		        	
	            }else{
		            const result = await tablesApi.add(table)
		        	const data = await result.json()
		        	EventBus.$emit('table-added', data)
		            ++this.tableNum
		            
		            var tablesCoordinatesObj = {
	                		  x: 0,
	                          y: 0,
	                          width:48,
	                          height:48
	                  }     
	                this.setTablesCoordinates([table._id, tablesCoordinatesObj])
	                this.$resource('/estb/establishment{/id}/coordinates').save({id:this.$route.params.id}, this.estbCoordinates)
	            }
	            EventBus.$emit('dialog-close')
            }
        }
    }
</script>

<style>

</style>