<template>
<div class="px-0 estb-info">

	<b>Про нас: </b><button v-if="role==`ESTB`" v-on:click="edit('description')"><v-icon color="green">edit</v-icon></button>
	<div v-show="editElementId != 'description'" id="description">{{establishment.description}}</div>
	<textarea style="width: 100%; height: 120px; border: 2px solid #87CEFA;" v-show="editElementId === 'description'"  v-model="establishment.description"></textarea>

	<b>Кухня: </b><button v-if="role==`ESTB`" v-on:click="edit('cuisine')"><v-icon color="green">edit</v-icon></button>
	<div v-show="editElementId != 'cuisine'" id="cuisine">{{establishment.cuisine}}</div>
	<div v-show="editElementId === 'cuisine'">
		<input style="border: 2px solid #87CEFA;" type="text" v-model="establishment.cuisine">
	</div>
	
	<b>Середній чек: </b><button v-if="role==`ESTB`" v-on:click="edit('avgCheck')"><v-icon color="green">edit</v-icon></button>
	<div v-show="editElementId != 'avgCheck'" id="avgCheck">{{establishment.avgCheck}}</div>
	<div v-show="editElementId === 'avgCheck'">
		<input style="border: 2px solid #87CEFA;" type="text" v-model="establishment.avgCheck">
	</div>
	

 	<b>Адреса: </b><button v-if="role==`ESTB`" v-on:click="edit('address')"><v-icon color="green">edit</v-icon></button>
	<div v-show="editElementId != 'address'" id="address">{{establishment.address}}</div>
	<div v-show="editElementId === 'address'">
		<input style="border: 2px solid #87CEFA;" type="text" v-model="establishment.address">
	</div>

	
	<b>Номер телефону: </b><button v-if="role==`ESTB`" v-on:click="edit('phone')"><v-icon color="green">edit</v-icon></button>
	<div v-show="editElementId != 'phone'" id="phone"><span v-for="(phone, index) in establishment.phone" :key="phone">{{phone+((establishment.phone.length -1) == index ? '':',') }}</span>
	</div>
	<div v-show="editElementId === 'phone'">
		<input style="border: 2px solid #87CEFA;" type="text" v-model="establishment.phone">
	</div>

	<b>Графік: </b><button v-if="role==`ESTB`" @click="edit('workSchedule')"><v-icon color="green">edit</v-icon></button>
	<div v-show="editElementId != 'workSchedule'" id="workSchedule">{{establishment.workSchedule}}</div>
	<div v-show="editElementId === 'workSchedule'">
		<textarea style="border: 2px solid #87CEFA;" v-model="establishment.workSchedule"></textarea>
	</div>
 

</div>
</template>
<script>
    import { mapState} from 'vuex'
    import establishmentApi from 'api/establishmentApi'

    export default {
    	props: ['establishment'],
    	data () {
             return {
                editElementId: '',
                editClickCount: 0,
                editElementValue:'',
             }
        },
        computed: mapState(['profile','establishments','role']),
        methods:{
        	 edit(id) {
                 if(this.editClickCount==0){
                     this.editElementValue = this.establishment[id]
                     this.editElementId=id
                     this.editClickCount++
                 }else if(this.editClickCount!=0 && this.editElementId != id){
                     this.editElementValue = this.establishment[id]
                 	 this.editElementId=id
                 }else{
                     this.editElementId=''
                     this.editClickCount=0;
                     
                     if(this.editElementValue!=this.establishment[id]){
                    	console.log(2131312312)
                    	establishmentApi.update(this.establishment)
                     }
                     this.editElementValue=''
                 }
             },
        }
    }
</script>

<style scoped>

.estb-info{
	font-size: 18px;
}
</style>
