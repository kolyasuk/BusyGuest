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
			    Авторизуйтесь через 
		        <a href="/login">Google</a> 
	        </div>
	        <div v-if="role=='VISITOR'">
	        	<books></books>
	        </div>
	        <v-container fluid>
		        <div v-if="role=='VISITOR' && profile.phone===''">
		            <p>Для продовження введіть всій номер телефону</p>
		            <span v-if="incorrectPhone">{{incorrectPhone}}</span>
		            <input type="tel" id="phone" name="phone" v-model="phone" pattern="[0-9]{10,13}" required>
		            <button v-on:click="inputPhone()">Go!</button>
		        </div>
		        
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
    import { mapState} from 'vuex'
    import { mapActions } from 'vuex'
    import {PhoneConstants} from 'constants/PhoneConstants'

    

    export default {
        components: {
            EstablishmentsList,
            Books
        },
        data: function () {
            return {
                incorrectPhone: null,
                phone: null
            }
        },
        computed: mapState(['profile', 'role']),
        methods:{
             ...mapActions(['addPhoneAction']),
            inputPhone(){
                if (this.phone != null && this.phone != PhoneConstants.EMPTY 
                		&& this.phone.length >= PhoneConstants.MIN_LENGTH 
                		&& this.phone.length <= PhoneConstants.MAX_LENGTH) {
                	
                    this.profile.phone=this.phone;
                    this.addPhoneAction(this.profile)
                    this.incorrectPhone=null
                }else
                    this.incorrectPhone = 'Вкажіть коректний номер телефону!';
            }
             
        }
    }
</script>

<style>

</style>
