<template>
<div style="display: inline-flex;">
	<div
		style="text-align: center; border-style: solid; width: 50px; margin: auto;"
		@click="showDetails(index)">
		<div>Стіл №{{table.tableNum}}</div>
	</div>

	<div v-show="tableIndexToShow == index && clicked==true"
		style="text-align: center; border-style: solid; width: 200px; margin: auto;">

		<booked-tables-list :bookedTables="bookedTables" :table="table"></booked-tables-list>

		<div v-if="role!='ESTB'">
			<textarea v-model="comment" placeholder="Побажання"></textarea>
			<input type="datetime-local" v-model="date"> <input
				type="button" value="Book" @click="book" /> <span
				v-if="alreadyBooked">{{alreadyBooked}}</span>
		</div>
	</div>
</div>

</template>

<script>
    import BookedTablesList from 'components/tables/bookedTables/BookedTablesList.vue'
    import {mapState, mapActions, mapMutations} from 'vuex'
    import EventBus from 'eventBus/event-bus.js'
    import bookedTablesApi from 'api/bookedTableApi'
    import {addHandler} from 'util/ws.js'

    export default {
        props: ['table','index'],
        data() {
            return {
                date: "2020-12-19T16:39",
                clicked: false,
                comment: null,
                alreadyBooked: null,
                bookedTableIndex: -1,
                bookedTables: null
            }
        },
        computed: mapState(['profile','role','tableIndexToShow']),
        components: {
            BookedTablesList
        },
        mounted () {
            EventBus.$on('user-book-deleted', (index) => {
                this.alreadyBooked=null
            })
       },
        methods: {
            ...mapMutations(['changeTableIndex']),
            book() {
                this.$resource('/'+this.role.toLowerCase()+'/checkBookedTable/{userId}/{estbId}')
                .get({userId: this.profile.id, estbId: this.$props.table.estb._id}).then(result =>
                result.json().then(isPresent  => {
                    if(!isPresent){
                        const bookedTable = {
                            user: this.profile, 
                            table: this.table, 
                            estb:this.$props.table.estb, 
                            comment:this.comment, 
                            bookedOn: this.date, 
                            done: false
                        }

                       bookedTablesApi.add(bookedTable)
                       
                       this.comment=null
                    }else{
                        this.alreadyBooked='Ви вже зарезервували столик!'
                    }
                }))
            },
            showDetails(index){
                if(this.tableIndexToShow == index && this.clicked)
                    this.clicked=false;
                else this.clicked = true

                this.changeTableIndex(index)
            },
            async loadBoockedTables(tableId){
	        	const result = await bookedTablesApi.getByTable(tableId)
	        	const data = await result.json()
	        	this.bookedTables = data
        	}
        },
        created(){
        	this.loadBoockedTables(this.table._id)
        	
            addHandler(data=>{
                if (data.objectType === 'BOOKEDTABLE') {
                    const index = this.bookedTables.findIndex(item => item.id === data.body.id)
                     switch (data.eventType) {
                         case 'CREATE':
                         if(data.body.table._id==this.table._id)
                            this.bookedTables.push(data.body)
                            break
                         case 'UPDATE':
                         if(data.body.table._id==this.table._id){
                            if (index > -1) {
                                this.bookedTables.splice(index, 1, data.body)
                            } else {
                                this.bookedTables.push(data.body)
                            }
                         }
                            break
                         case 'REMOVE':
                           this.bookedTables.splice(index, 1)
                            break
                        default:
                            console.error(`Looks like the event type is unknown "${data.eventType}"`)
                     }
                }else {
                    console.error(`Looks like the object type is unknown "${data.objectType}"`)
                }
            })
        },
        
    }

</script>

<style scoped>
</style>