<template>
	<v-stepper v-model="e6" vertical>
	    <v-stepper-step :complete="e6 > 1" step="1">
	      Залогіньтесь через зручну соцмережу:
	    </v-stepper-step>
	
	    <v-stepper-content step="1">
	      <v-card color="grey lighten-1" class="mb-5" height="50px" width="300px">
	       	<a v-if="!profile" href="/login" @click="saveStep(1)">Google</a>
	       	<span v-else>{{profile.name}}, ви успішно залогінилися! Переходься то наступного кроку.</span>
	      </v-card>
	      <v-btn v-show="profile" color="primary" @click="e6 = 2">Continue</v-btn>
	      <v-btn flat @click="closeStepper()">Cancel</v-btn>
	    </v-stepper-content>
	
	    <v-stepper-step :complete="e6 > 2" step="2">Вкажіть номер телефону</v-stepper-step>
	
	    <v-stepper-content step="2">
	      <v-card color="grey lighten-1" class="mb-5" height="200px">
		      <span v-if="incorrectPhone">{{incorrectPhone}}</span>
		      <v-flex xs12 sm6 md3>
		          <v-text-field
		            label="Solo"
		            placeholder="Номер телефону..."
		            solo 
		            v-model="phone" type="tel" mask="(+380) #########"
		          ></v-text-field>
		        </v-flex>
		      <input type="tel" id="phone" name="phone"  pattern="[0-9]{10,13}" required>
	      </v-card>
	      <v-btn color="primary" @click="inputPhone()">Ввести</v-btn>
	      <v-btn flat @click="closeStepper()">Cancel</v-btn>
	    </v-stepper-content>
	  </v-stepper>
</template>

<script>
    import EstablishmentsList from 'components/establishments/EstablishmentsList.vue'
    import Books from 'components/profile/Books.vue'
    import { mapState} from 'vuex'
    import { mapActions } from 'vuex'
    import {PhoneConstants} from 'constants/PhoneConstants'
    import EventBus from 'eventBus/event-bus.js'

    

    export default {
        components: {
            EstablishmentsList,
            Books
        },
        props: ['step'],
        data: function () {
            return {
                incorrectPhone: null,
                phone: +380,
                e6: 1,
            }
        },
        created(){
        	this.e6 = this.step
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
                    
                    
                    this.closeStepper()
                    
                }else
                    this.incorrectPhone = 'Вкажіть коректний номер телефону!';
            },
            closeStepper(){
            	EventBus.$emit('close_stepper')
            }
             
        }
    }
</script>

<style>

</style>
