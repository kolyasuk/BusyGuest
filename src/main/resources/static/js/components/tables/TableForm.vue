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
    	props:['props'],
        data(){
            return {
            	 ...this.props,
            	id: null,
                tableNum: 1,
                seats: 1
            }
        },
        created: function(){
            if(this.props){
                this.id = this.table._id 
            	this.tableNum = this.table.tableNum
                this.seats = this.table.seats
            }
        },
        mounted () {
       	 EventBus.$on('dialog-action', (id) => {
       		 if(this.table._id==id)
                this.saveTable()
            })
      	},
        methods: {
        	async saveTable() {
	            var table = {
	            	_id: this.id,
	            	tableNum : this.tableNum,
	            	seats: this.seats,
	            	estb: this.table.estb 
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
            }
        }
    }
</script>

<style>

</style>