<template>
    <div style="width: 200px; margin-left: 60px;">
        <b>Особливі побажання: </b><br><textarea style="border: 2px solid #87CEFA;" v-model="comment" placeholder="Побажання"></textarea><br>
		<b>Дата: </b><br><input style="border: 2px solid #87CEFA;" type="date" min="currentDate" v-model="date"><br>
		<b>Час: </b><br><input style="border: 2px solid #87CEFA;" type="time" min="currentTime" v-model="time"><br>
		<span v-if="alreadyBooked">{{alreadyBooked}}</span>
		<span v-show="test == true">Можна замовити не пізніше пів години!</span>
		<v-btn color="success" @click="book()">Замовити</v-btn>
		

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
                currentDate: new Date().toJSON().slice(0,10),
                currentTime: new Date(new Date().getTime()+10800000).toJSON().slice(11,16),
                date: new Date().toJSON().slice(0,10),
                time: new Date(new Date().getTime()+10800000).toJSON().slice(11,16),
                test: false
        	}
        },
        created(){
            this.$resource('/'+this.role.toLowerCase()+'/checkBookedTable/{tableId}')
            .get({tableId: this.table._id}).then(result => result.json().then(data =>  {
                data.forEach(function(item){
                	  console.log(data.bookedOn)
                })
            }))
        },
        methods: {
        	book() {
        		if(new Date(this.date)>= new Date(this.currentDate) && this.time.replace(':','') - this.currentTime.replace(':','') >= 50){


        			
                    this.$resource('/'+this.role.toLowerCase()+'/checkUserBookedTable/{userId}/{estbId}')
                    .get({userId: this.profile.id, estbId: this.table.estb._id}).then(result =>
                    result.json().then(isPresent  => {
                        if(!isPresent){
                            const bookedTable = {
                                user: this.profile, 
                                table: this.table, 
                                estb: this.table.estb, 
                                comment:this.comment, 
                                bookedOn: this.date+'T'+this.time, 
                                accepted: false,
                                done: false
                            }

                           bookedTablesApi.add(bookedTable)
                           
                           EventBus.$emit('dialog-close')
                           EventBus.$emit('booked')
                           this.comment=null

                        }else{
                            this.alreadyBooked='Ви вже зарезервували столик!'
                        }
                    }))
        		}else{
        			this.test = true
        		}
   
            }
        },
    }

</script>

<style scoped>

</style>