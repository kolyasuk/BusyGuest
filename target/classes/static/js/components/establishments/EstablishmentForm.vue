<template>
<v-flex
        xs12
        md4
      >
  <form>
    <v-text-field
      v-model="name"
      :error-messages="nameErrors"
      label="Name"
      required
      @input="$v.name.$touch()"
      @blur="$v.name.$touch()"
    ></v-text-field>
    <v-text-field
      v-model="address"
      :error-messages="addressErrors"
      label="Address"
      required
      @input="$v.address.$touch()"
      @blur="$v.address.$touch()"
    ></v-text-field>
    <v-textarea
    	v-model="description"
    	:error-messages="descriptionErrors"
          name="input-7-1"
          label="Description"
          hint="Hint text"
          required
          @input="$v.description.$touch()"
      	@blur="$v.description.$touch()"
     ></v-textarea>
     
     <div v-for="(phone, index) in phones" :key="`phone-${index}`">
      	<span>{{phone}}</span>
      	<v-btn @click="deletePhone(index)" icon color="lighten-2">
        	<v-icon size="20">clear</v-icon>
      	</v-btn>
      </div>
     <add-phone-form></add-phone-form>
     
     <v-checkbox
     	v-model="useProfilePhone"
          label="Use profile phone"
          @change="addProfilePhone"
     ></v-checkbox>
    <v-textarea
    	v-model="workSchedule"
    	:error-messages="workScheduleErrors"
          name="input-7-1"
          label="Work schedule"
          hint="Hint text"
          required
          @input="$v.workSchedule.$touch()"
      	  @blur="$v.workSchedule.$touch()"
     ></v-textarea>

    <v-btn @click="submit">submit</v-btn>
    <v-btn @click="clear">clear</v-btn>
  </form></v-flex>
</template>

<script>
  import { validationMixin } from 'vuelidate'
  import { required, maxLength, minLength, length} from 'vuelidate/lib/validators'
  import { mapState} from 'vuex'
  import AddPhoneForm from 'components/profile/AddPhoneForm.vue'
  import EventBus from 'eventBus/event-bus.js'

  export default {
    mixins: [validationMixin],

    validations: {
      name: { required},
      address: { required, minLength: minLength(10) },
      description: {required, minLength: minLength(50)},
      workSchedule: {required, minLength: minLength(4)},
    },
    components:{
    	AddPhoneForm,
    },
    data() {
        return {
            	name: "",
            	address: "",
            	description: "",
            	workSchedule: "",
            	phones:[],
            	phone: +380,
            	useProfilePhone: false
        }
    },
    computed: {
    	...mapState(['profile', 'role']),
      nameErrors () {
        const errors = []
        if (!this.$v.name.$dirty) return errors
        !this.$v.name.required && errors.push('Name is required.')
        return errors
      },
      descriptionErrors () {
          const errors = []
          if (!this.$v.description.$dirty) return errors
          !this.$v.description.minLength && errors.push('Description must be at most 50 characters long')
          !this.$v.description.required && errors.push('Description is required')
          return errors
        },
        addressErrors (){
        	const errors = []
            if (!this.$v.address.$dirty) return errors
            !this.$v.address.minLength && errors.push('Address must be at most 10 characters long')
            !this.$v.address.required && errors.push('Address is required')
            return errors
        },
        workScheduleErrors (){
        	const errors = []
            if (!this.$v.workSchedule.$dirty) return errors
            !this.$v.workSchedule.minLength && errors.push('Work schedule must be at most 4 characters long')
            !this.$v.workSchedule.required && errors.push('Work schedule is required')
            return errors
        }
    },
    mounted (){
        EventBus.$on('added-phone', (phone) => {
        	this.phones.push(phone)
        })
    },
    methods: {
      submit () {
    	this.$v.$touch()
    	if (this.$v.$invalid) {
           return;
        }
    	
    	if(this.phones.length == 0){
    		EventBus.$emit('empty-phone-list')
    		return;
    	}
    	const estb = { 
    		email: this.profile.email,
    		name: this.name,
            address: this.address,
            description: this.description,
            workSchedule: this.workSchedule,
            phones: this.phones
    	}
    	console.log(estb)
      },
      clear () {
        this.$v.$reset()
        this.name = ''
        this.address = ''
        this.description = ''
        this.workSchedule = ''
        this.phone = ''
      },
      addProfilePhone(){
    	if(this.useProfilePhone){
    	  this.phones.push(this.profile.phone)
    	  EventBus.$emit('profile-phone-added')
    	}else{
    		const index = this.phones.findIndex(item => item == this.profile.phone)
    		this.phones.splice(index, 1)
    		if(this.phones.length == 0){
    			EventBus.$emit('empty-phone-list')
    		}
    	  }
      },
      deletePhone(index){
    	  if(this.phones[index] == this.profile.phone)
    		  this.useProfilePhone=false
    	  this.phones.splice(index, 1)
      }
    }
  }
</script>

<style>

</style>