<template>
    <div>
		<div v-if="role==='ESTB' && profile.email!=establishment.email">
	        	Не ваш заклад!	
	     </div>
	     <div v-else>
	        <div  style="text-align:center;">
	
		        <h2 v-show="editElementId != 'name'" id="name">{{establishment.name}}</h2>
		        <input v-show="editElementId === 'name'" type="text" v-model="establishment.name">  
		        <div v-if="role===`ESTB`">
		            <button v-on:click="edit('name')">Edit</button>
		        </div>
		
		        <span>Про нас: </span>
		        <span v-show="editElementId != 'description'" id="description">{{establishment.description}}</span>
		        <input v-show="editElementId === 'description'" type="text"  v-model="establishment.description">  
		        <div v-if="role===`ESTB`">
		            <button v-on:click="edit('description')">Edit</button>
		        </div>
		
		        <span>Адреса: </span>
		        <span v-show="editElementId != 'address'" id="address">{{establishment.address}}</span>
		        <input v-show="editElementId === 'address'" type="text" v-model="establishment.address">  
		        <div v-if="role===`ESTB`">
		            <button v-on:click="edit('address')">Edit</button>
		        </div>
		
		        <span>Номер телефону: </span>
		        <span v-show="editElementId != 'phone'" id="phone">{{establishment.phone}}</span>
		        <input v-show="editElementId === 'phone'" type="text" v-model="establishment.phone">  
		        <div v-if="role===`ESTB`">
		            <button v-on:click="edit('phone')">Edit</button>
		        </div>
		        
		        <span>Графік: </span>
		        <span v-show="editElementId != 'workSchedule'" id="workSchedule">{{establishment.workSchedule}}</span>
		        <input v-show="editElementId === 'workSchedule'" type="text" v-model="establishment.workSchedule">  
		        <div v-if="role===`ESTB`">
		            <button v-on:click="edit('workSchedule')">Edit</button>
		        </div>
	        </div>
	
	        <tables-list ></tables-list>
	    </div>
    </div>
</template>

<script>
    import { mapState} from 'vuex'
    import TablesList from 'components/tables/TablesList.vue'

    export default {
        components: {
            TablesList
        },
        data () {
             return {
                editElementId: '',
                editClickCount: 0,
                editElementValue:'',
                establishment: null
             }
        },
        computed: mapState(['profile','establishments','role']),

        created: function() {
        	this.loadData()
        },
        methods: {
            edit(id) {
                if(this.editClickCount==0){
                    this.editElementValue = this.establishment.name
                    this.editElementId=id
                    this.editClickCount++
                }else if(this.editClickCount!=0 && this.editElementId != id){
                	this.editElementId=id
                }else{
                    this.editElementId=''
                    this.editClickCount=0;
                    
                    if(this.editElementValue!=this.establishment.name){
                        console.log("Збираю об'єкт і шлю на сервер")
                    }
                    this.editElementValue=''
                }
            },
            loadData(){
            	const index = this.establishments.findIndex(item => item._id === this.$route.params.id)
            	this.establishment = this.establishments[index]
            }
        }
    }
</script>

<style>

</style>
