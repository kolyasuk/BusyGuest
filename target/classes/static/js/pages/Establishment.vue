<template>
    <v-app>
    	<div v-if="role=='ADMIN'">
    		<h2><b>Заклад {{establishment.accepted ? '' : 'не'}} опубліковано! </b></h2>
    		<v-btn color="red lighten-2" @click="acceptEstablishment" dark> {{establishment.accepted ? 'Відмінити підтвердження' : 'Підтвердити'}}</v-btn>
    	</div>
    	<div v-if="establishment.show || role!='VISITOR'">
			<div v-if="role==='ESTB' && profile.email!=establishment.email">
		        	<b>Не ваш заклад!</b>	
		    </div>
		     <div v-else>
		     	<div v-if="role==='ESTB' && establishment.accepted">
		     		
			     	<div v-if="!establishment.show">
	    				<h3><b>Заклад не оприлюднено! Опублікуйте після заповнення інформації!</b></h3>
	    				<v-btn color="red lighten-2" @click="changeShowStatus()" dark>Опублікувати</v-btn>
	    			</div>
	    			<div v-else>
	    				<h3><b>Заклад оприлюднено.</b></h3>
	    				<v-btn color="red lighten-2" @click="changeShowStatus()" dark>Заховати</v-btn>
	    			</div>
    			</div>
    			
    			<div v-if="role==='ESTB' && !establishment.accepted">
    				<h3><b>Заклад знаходиться на розгляді в адміністрації!</b></h3>
    			</div>
		        <div>
				<div style="text-align: center;">
					<h1 style="display: inline;" v-show="editElementId != 'name'" id="name">
						{{establishment.name}}
					</h1>
					<span v-show="editElementId === 'name'">
						<input style="border: 2px solid #87CEFA; font-size: 30px;" type="text" v-model="establishment.name">
					</span>
					<button v-if="role==`ESTB`" v-on:click="edit('name')"><v-icon color="green">edit</v-icon></button>
				</div>
				
						<v-container fluid grid-list-md >
						    <v-layout align-start justify-space-around fill-height wrap>
						      <v-flex xs6 order-lg2>
						        <v-carousel>
								    <v-carousel-item
								      v-for="(item,i) in items"
								      :key="i"
								      :src="item.src"
								    ></v-carousel-item>
								  </v-carousel>
						      </v-flex>
						      <v-flex xs6 order-lg2>
						        <establishment-info :establishment="establishment"></establishment-info>
						      </v-flex>
						    </v-layout >
						  </v-container>
			
		        	</div>
				<h2 style="text-align: center;"><b>Замовити столик:</b></h2>

		        <tables-list :establishment="establishment"></tables-list>
		    </div>
	    </div>
	    
	    <div v-else>
	    	Заклад не оприлюднено!
	    </div>
    </v-app>
</template>

<script>
    import { mapState, mapMutations} from 'vuex'
    import TablesList from 'components/tables/TablesList.vue'
    import EstablishmentInfo from 'components/establishments/EstablishmentInfo.vue'
    import establishmentApi from 'api/establishmentApi'

    export default {
        components: {
            TablesList,
            EstablishmentInfo
        },
        data () {
             return {
                 editElementId: '',
                 editClickCount: 0,
                 editElementValue:'',
                establishment: null,
                items: [
                    {
                      src: 'https://media-cdn.tripadvisor.com/media/photo-o/0c/9f/ce/c5/photo0jpg.jpg'
                    },
                    {
                      src: 'https://media-cdn.tripadvisor.com/media/photo-o/07/b8/1a/21/extraordinary-pizza-atmosphere.jpg'
                    },
                    {
                      src: 'https://media-cdn.tripadvisor.com/media/photo-o/07/b8/1a/35/extraordinary-pizza-atmosphere.jpg'
                    }
                  ]
             }
        },
        computed: mapState(['profile','establishments','role']),

        created: function() {
        	this.loadData()
        	this.loadCoordinates()
        	
        },
        methods: {
        	...mapMutations(['changeDragTablesMutation', 'addEstbCoordinatesMutation']),
            loadCoordinates(){
            	var estbCoordinates
                
            	this.$http.get('/estb/establishment/'+this.establishment._id+'/coordinates').then((response) => {
        			this.addEstbCoordinatesMutation(response.data)
                }, (response) => {
                	estbCoordinates = {
  		                   estbId: this.establishment._id,
  		                   width: 1000,
  		                   height: 500,
  		                   tables: {},
  		                   signs: {}
  	                   }
                	this.addEstbCoordinatesMutation(estbCoordinates)
            	})
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
            },
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
                        establishmentApi.update(this.establishment)
                    }
                    this.editElementValue=''
                }
            },
            acceptEstablishment(){
            	const estb = {
            		...this.establishment,
            		accepted: !this.establishment.accepted
            	}
            	 this.$resource('/estb/establishment/{id}').update({id: estb._id}, estb).then(result =>
                 result.json().then(data => this.establishment = data))
            }
        }
    }
</script>

<style>

</style>
