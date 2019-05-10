<template>
    <div>
		Номер: <input type="number" min=1 placeholder="number.." v-model="tableNum" /><br>
        Кількість місць: <input type="number" min=1 placeholder="seats.." v-model="seats" />
        <input type="button" value="Save" @click="saveTable()" />
    </div>
</template>

<script>
    import { mapState} from 'vuex'
    import tablesApi from 'api/tablesApi'
    import EventBus from 'eventBus/event-bus.js'
    
    export default {
    	props:['table','establishment'],
        data(){
            return {
            	id: null,
                tableNum: 1,
                seats: 1,
                estb: null
            }
        },
        created: function(){
        	if(this.table){
	        	this.id = this.table._id 
	            this.tableNum = this.table.tableNum
	            this.seats = this.table.seats
	            this.estb = this.table.estb
        	}else{        		
	            this.estb = this.establishment
        	}
        },
        methods: {
        	async saveTable() {
	            var table = {
	            	_id: this.id,
	            	tableNum : this.tableNum,
	            	seats: this.seats,
	            	estb: this.estb 
	            }
	            
	            if(this.id){
	            	const result = await tablesApi.update(table)
		        	const data = await result.json()
		        	EventBus.$emit('table-edited', data)
		        	
	            }else{
		            const result = await tablesApi.add(table)
		        	const data = await result.json()
		        	EventBus.$emit('table-added', data)
		            ++this.tableNum
	            }
	            EventBus.$emit('dialog-close')
            }
        }
    }
</script>

<style>

</style>