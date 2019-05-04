<template>
    <div>
        <textarea v-model="comment" placeholder="Побажання"></textarea>
		<input type="datetime-local" v-model="date"> 
		<span v-if="alreadyBooked">{{alreadyBooked}}</span>
		
		<v-btn color="primary" flat @click="book()">
			Book
		</v-btn>
    </div>
    
</template>

<script>
    import { mapState} from 'vuex'
    import bookedTablesApi from 'api/bookedTableApi'
    import EventBus from 'eventBus/event-bus.js'
    
    
    export default {
        props: ['props'],
        computed: mapState(['role','profile']),
        mounted () {
        	 EventBus.$on('dialog-action', (id) => {
        		 if(this.table._id==id)
                 this.book()
             })
       	},
        data(){
        	return{
        		...this.props,
        		comment: null,
                alreadyBooked: null,
                date: "2020-12-19T16:39",
        	}
        },
        methods: {
        	book() {
                this.$resource('/'+this.role.toLowerCase()+'/checkBookedTable/{userId}/{estbId}')
                .get({userId: this.profile.id, estbId: this.table.estb._id}).then(result =>
                result.json().then(isPresent  => {
                    if(!isPresent){
                        const bookedTable = {
                            user: this.profile, 
                            table: this.table, 
                            estb: this.table.estb, 
                            comment:this.comment, 
                            bookedOn: this.date, 
                            accepted: false,
                            done: false
                        }

                       bookedTablesApi.add(bookedTable)
                       
                       this.dialog = false
                       this.comment=null

                    }else{
                        this.alreadyBooked='Ви вже зарезервували столик!'
                    }
                }))
            },
        },
    }

</script>

<style scoped>

</style>