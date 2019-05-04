<template>
<div>
	Заклад: {{this.bookedTable.estb.name}}<br> 
	Столик №{{this.bookedTable.table.tableNum}}<br> 
	Замовник: {{this.bookedTable.user.name}}<br>
	Телефон: {{this.bookedTable.user.phone}}<br>
	<div v-if="this.bookedTable.comment">
		<span id="date">Коментар: {{this.bookedTable.comment}} </span>
	</div>
	<div v-else v-show="!showEdit">Коментар відсутній.</div>
	<textarea v-show="showEdit" v-model="bookedTable.comment" placeholder="Побажання"></textarea> <br> 
	<input v-show="showEdit" type="datetime-local" v-model="bookedTable.bookedOn"> 
	<span v-show="!showEdit" id="date"> Дата: {{this.bookedTable.bookedOn | moment("MM/DD/YYYY HH:mm ") }}</span> <br>
	Підтверджено: {{this.bookedTable.accepted}}
	<div v-if="this.bookedTable.user.reputation.name =='UNRELIABLE' ">Увага! Репутація замовника низька!</div>
	<div v-if="role===`VISITOR`">
		<button v-on:click="edit()">Edit</button>
	</div>
	<div v-if="role===`ESTB`">
		<button v-on:click="changeBookStatus()">{{this.bookedTable.accepted ? 'Cancel' : 'Accept'}}</button>
	</div>
</div>
</template>
<script>
    import EstablishmentsList from 'components/establishments/EstablishmentsList.vue'
    import { mapState} from 'vuex'
    import EventBus from 'eventBus/event-bus.js'
    import bookedTablesApi from 'api/bookedTableApi'
    
    export default {
        computed: mapState(['role']),
        data(){
            return{
                bookedTable:null,
                oldBookedTable:null,
                showEdit:false  
            }
        },
        components: {
            EstablishmentsList
        },
        methods:{
        	async loadbookedTable(id){
            	const result = await bookedTablesApi.get(id)
	        	const data = await result.json()
	        	this.bookedTable = data
            },
            edit() {
            	if(!this.showEdit ) 
            		this.oldBookedTable = {...this.bookedTable}
            	
                if(this.showEdit && JSON.stringify(this.oldBookedTable) != JSON.stringify(this.bookedTable))
                    bookedTablesApi.update(this.bookedTable)
                 
                this.showEdit=!this.showEdit
            },
            changeBookStatus(){
            	this.bookedTable.accepted = !this.bookedTable.accepted
            	bookedTablesApi.update(this.bookedTable)
            	this.$router.push(`establishment/`+this.bookedTable.estb._id)
            }
           
        },
        created: function(){
            this.loadbookedTable(this.$route.params.id)
        },
        mounted () {
            EventBus.$on('reload-booked-table-component', (id) => {
                this.loadbookedTable(id)
            })
        }

    }
</script>
<style>
</style>