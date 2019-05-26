<template>
    <div>
        <textarea v-model="comment" placeholder="Побажання"></textarea>
		<input type="datetime-local" min="currentDate" v-model="date"> 
		<span v-if="alreadyBooked">{{alreadyBooked}}</span>
		
		<v-btn color="primary" flat @click="book()">
			Book{{date}}
		</v-btn>
    </div>
    
</template>

<script>
    import { mapState} from 'vuex'
    import bookedTablesApi from 'api/bookedTableApi'
    import EventBus from 'eventBus/event-bus.js'
    
    
    export default {
        props: ['table'],
        computed: {
        	  ...mapState(['role','profile']),
        },
        data(){
        	return{
        		comment: null,
                alreadyBooked: null,
                currentDate:new Date().toJSON().slice(0,16),
                date: new Date().toJSON().slice(0,16)
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
                       
                       EventBus.$emit('dialog-close')
                       this.comment=null

                    }else{
                        this.alreadyBooked='Ви вже зарезервували столик!'
                    }
                }))
            }
        },
    }

</script>

<style scoped>

</style>