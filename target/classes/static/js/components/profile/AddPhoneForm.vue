<template>
  <form>
     <v-text-field 
	     v-model="phone" 
	     :error-messages="phoneErrors"
	     label="Phone" 
	     name="phone" 
	     mask="(+380) #########" 
	     required
	      @input="$v.phone.$touch()"
     ></v-text-field>
     <v-btn @click="addPhone">Add phone</v-btn>
  </form>
</template>

<script>
  import { validationMixin } from 'vuelidate'
  import { required, minLength} from 'vuelidate/lib/validators'
  import { mapState} from 'vuex'
  import EventBus from 'eventBus/event-bus.js'

  export default {
    mixins: [validationMixin],

    validations: {
      phone: {required, minLength: minLength(12)}
    },
    data() {
        return {
            	phones:[],
            	phone: +380,
        }
    },
    computed: {
    	...mapState(['profile', 'role']),
        phoneErrors (){
        	const errors = []
            if (!this.$v.phone.$dirty) return errors
            !this.$v.phone.minLength && errors.push('Phone must be at most 16 characters long')
            this.phones.includes(this.phone) && errors.push('Phone is already exist!')
            !this.$v.phone.required && errors.push('Phone is required')
            return errors
        },
    },
    mounted (){
        EventBus.$on('empty-phone-list', () => {
        	this.$v.phone.$touch()
        }),
        EventBus.$on('profile-phone-added', () => {
        	this.$v.$reset()
        })
    },
    methods: {
      addPhone(){
     	  this.$v.phone.$touch()
    	  if (this.phoneErrors.length != 0) {
           return;
           }
     	  this.phones.push(this.phone)
     	  EventBus.$emit('added-phone', this.phone)
    	  this.phone = +380
    	  this.$v.$reset()
      }
    }
  }
</script>

<style>

</style>