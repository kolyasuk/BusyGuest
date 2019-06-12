<template>
	<v-app>
		<div style="text-align: center;">
			<span><b>Заклад:</b> {{bookedTable.estb.name}}</span> <br>
			<span><b>Номер столика: </b>{{bookedTable.table.tableNum}}</span> <br>
			<span><b>Замовник:</b> {{bookedTable.user.name}}</span> <br>
			<span><b>Телефон:</b> {{bookedTable.user.phone}}</span> <br>
			<div v-if="this.bookedTable.comment">
			<b>Коментар:</b>
				<span id="date" v-show="!showEdit"> {{bookedTable.comment}} </span>
				<textarea v-show="showEdit" style="border: 2px solid #87CEFA;" v-model="bookedTable.comment" placeholder="Коментар"></textarea>
			</div>
			<div v-else v-show="!showEdit">Коментар відсутній.</div>

			<div v-show="showEdit">
				<b>Дата:</b> <input style="border: 2px solid #87CEFA;" type="datetime-local" v-model="bookedTable.bookedOn">
			</div>
			<span v-show="!showEdit" id="date"> <b>Дата:</b> {{bookedTable.bookedOn | moment("MM/DD/YYYY HH:mm ") }}</span> <br>
			<span><b>Замовлення {{bookedTable.accepted ? '':'не'}} підтверджено.</b> </span>
			<div v-if="role===`VISITOR`">
				<v-btn color="info" @click="edit()">{{showEdit ? 'Зберегти':'Редагувати'}}</v-btn>
			</div>
			<div v-if="role===`ESTB`">
				<div v-if="this.bookedTable.user.reputation.name =='UNRELIABLE' "><b>Увага! Репутація замовника низька!</b></div>
				<v-btn color="success" @click="changeBookStatus()">{{bookedTable.accepted ? 'Відмінити' : 'Підтвердити'}} замовлення</v-btn>
			</div>
		</div>
	</v-app>
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