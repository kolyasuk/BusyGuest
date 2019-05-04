<template>
    <div>
    	
    	<div v-if="establishment.show || role==='ESTB'">
			<div v-if="role==='ESTB' && profile.email!=establishment.email">
		        	Не ваш заклад!	
		    </div>
		     <div v-else>
		     
		     	<div v-if="role==='ESTB'">
			     	<div v-if="!establishment.show">
	    				<h3>Заклад не оприлюднено! Після заповнення всієї інформації, не забудьте опублікувати!</h3>
	    				<input type="button" @click="changeShowStatus()" value="Опублікувати">
	    			</div>
	    			<div v-else>
	    				<h3>Заклад оприлюднено ^ ^</h3>
	    				<input type="button" @click="changeShowStatus()" value="Заховати">
	    			</div>
    			</div>
    			
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
			            <button @click="edit('workSchedule')">Edit</button>
			        </div>
		        </div>
				<h3>Столики: </h3>
				<div v-if="role=='ESTB'">
					<button  @click="showForm=!showForm" >+</button>
				</div>
				<table-form  :establishment=establishment v-show="showForm"></table-form>
		        <tables-list></tables-list>
		    </div>
	    </div>
	    
	    <div v-else>
	    	Заклад не оприлюднено!
	    </div>
    </div>
</template>

<script>
    import { mapState} from 'vuex'
    import TablesList from 'components/tables/TablesList.vue'
    import TableForm from 'components/tables/TableForm.vue'
    import establishmentApi from 'api/establishmentApi'

    export default {
        components: {
            TablesList,
            TableForm
        },
        data () {
             return {
                editElementId: '',
                editClickCount: 0,
                editElementValue:'',
                establishment: null,
                showForm:false
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
            },
            async changeShowStatus(){
            	const establishment = {...this.establishment}
            	establishment.show = !establishment.show
            	const result = await establishmentApi.update(establishment)
	        	const data = await result.json()
	        	this.establishment = data
            }
        }
    }
</script>

<style>

</style>
