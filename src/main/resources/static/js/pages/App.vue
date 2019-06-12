<template>
    <v-app>
	    <v-toolbar app>
	    	<v-toolbar-title>BusyGuest</v-toolbar-title>
	    	<v-toolbar-items class="hidden-sm-and-down">
		      <v-btn flat>
		      	<router-link to="/">Додому</router-link>
		      </v-btn>
		    </v-toolbar-items>
	    	
	    	<v-spacer></v-spacer>
	    	<v-toolbar-items class="hidden-sm-and-down">
	    	<div style="width: 500px; margin-left: 75%;">
			        <v-alert
				      v-model="alert"
				      dismissible
				      type="success"
				    >
				      Столик замовлено!
				    </v-alert>
			  </div>
			  </v-toolbar-items>
	        <div v-if="profile">
	        	<span>{{profile.name}}</span>
	            <a href="/logout">Вийти</a>
	        </div>
	        	    	
	    </v-toolbar>
	    
	    
	    <v-content>
	        
	        <div v-if="role=='VISITOR'">
	        	<books></books>
	        </div>
	        <v-container fluid>
	        	<sign-up-stepper v-if="profile && profile.phone=='' || showStepper"></sign-up-stepper>
		        <div v-else>
		            <div v-if="profile">
		                <router-view></router-view>
		            </div>
		            <div v-else style="text-align: center;">
		            <h2>Для користування сервісом потрібно увійти в систему. </h2>
				        <v-btn color="primary" href="/login">Вхід</v-btn>
				        <v-btn color="primary" @click="createEstb">Вхід для підприємців</v-btn>
		            </div>
		        </div>
			</v-container>
			
	    </v-content>

    </v-app>
</template>

<script>
    import EstablishmentsList from 'components/establishments/EstablishmentsList.vue'
    import Books from 'components/profile/Books.vue'
    import SignUpStepper from 'components/UI/SignUpStepper.vue'
    import { mapState} from 'vuex'
    import { mapActions } from 'vuex'
    import {PhoneConstants} from 'constants/PhoneConstants'
    import EventBus from 'eventBus/event-bus.js'

    

    export default {
        components: {
            EstablishmentsList,
            Books,
            SignUpStepper
        },
        data: function () {
            return {
                showStepper: false,
                step: 0,
                alert: false
            }
        },
        created(){
        	if(this.profile && this.profile.phone==''){
        		showStepper: true
        	}
        	this.checkRole()
        },
        watch:{
            $route (to, from){
                this.checkRole()
            }
        },
        mounted () {
            EventBus.$on('close_stepper', () => {
            	this.showStepper=false
            	this.checkRole()
            }),
            EventBus.$on('booked', () => {
				this.alert = true
            })
       	},
        computed: mapState(['profile', 'role','establishments']),
        methods:{
             createEstb(){
            	this.showStepper = true
             	this.$cookie.set('bussines', 'true', '1h')
             },
             checkRole(){
            	 /* if(this.role=='ADMIN'){
            		 this.$router.push('/admin')
            	 }else  */if(this.role=='ESTB'&& this.profile.phone != '') {
              		const estb = this.establishments.find(element => element.email==this.profile.email)
              		if(estb==null){
              			this.$router.push('/establishment')
              		}
              	}
             }
             
        }
    }
</script>

<style>

</style>
