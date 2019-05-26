<template>
    <v-app>
	    <v-toolbar app>
	    	<v-toolbar-title>BusyGuest</v-toolbar-title>
	    	<v-toolbar-items class="hidden-sm-and-down">
		      <v-btn flat>
		      	<router-link to="/">Home</router-link>
		      </v-btn>
		    </v-toolbar-items>
	    	
	    	<v-spacer></v-spacer>
	        <div v-if="profile">
	        	<span>{{profile.name}}</span>
	            <a href="/logout">Вийти</a>
	        </div>
	    </v-toolbar>
	    
	    <v-content>
	    	<div v-if="!profile">
		        <v-btn color="primary" href="/login">Вхід</v-btn>
		        <v-btn color="primary" @click="createEstb">Вхід для підприємців</v-btn>
	        </div>
	        <div v-if="role=='VISITOR'">
	        	<books></books>
	        </div>
	        <v-container fluid>
	        	<sign-up-stepper v-if="profile && profile.phone=='' || showStepper"></sign-up-stepper>
		        <div v-else>
		            <div>
		                <router-view></router-view>
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
            })
       	},
        data: function () {
            return {
                showStepper: false,
                step: 0,
            }
        },
        computed: mapState(['profile', 'role','establishments']),
        methods:{
             createEstb(){
            	this.showStepper = true
             	this.$cookie.set('bussines', 'true', '1h')
             },
             checkRole(){
            	 if(this.role=='ADMIN'){
            		 this.$router.push('/admin')
            	 }else if(this.role=='ESTB'&& this.profile.phone != '') {
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
