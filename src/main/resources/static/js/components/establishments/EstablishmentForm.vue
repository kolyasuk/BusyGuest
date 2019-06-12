<template>
<v-flex
        xs12
        md4
      >
  <form>
    <v-text-field
      v-model="name"
      :error-messages="nameErrors"
      label="Назва"
      required
      @input="$v.name.$touch()"
      @blur="$v.name.$touch()"
    ></v-text-field>
    <v-text-field
      v-model="address"
      :error-messages="addressErrors"
      label="Адреса"
      required
      @input="$v.address.$touch()"
      @blur="$v.address.$touch()"
    ></v-text-field>
    <v-textarea
    	v-model="description"
    	:error-messages="descriptionErrors"
          name="input-7-1"
          label="Опис"
          hint="Hint text"
          required
          @input="$v.description.$touch()"
      	@blur="$v.description.$touch()"
     ></v-textarea>
     
     <v-text-field
      v-model="cuisine"
      :error-messages="cuisineErrors"
      label="Кухня"
      required
      @input="$v.cuisine.$touch()"
      @blur="$v.cuisine.$touch()"
    ></v-text-field>
    
     <v-text-field
      v-model="avgCheck"
      :error-messages="avgCheckErrors"
      label="Середній чек"
      required
      @input="$v.avgCheck.$touch()"
      @blur="$v.avgCheck.$touch()"
    ></v-text-field>
     
     <div v-for="(phone, index) in phones" :key="`phone-${index}`">
      	<span>{{phone}}</span>
      	<v-btn @click="deletePhone(index)" icon color="lighten-2">
        	<v-icon size="20">clear</v-icon>
      	</v-btn>
      </div>
     <add-phone-form></add-phone-form>
     
     <v-checkbox
     	v-model="useProfilePhone"
          label="Використати номер профілю"
          @change="addProfilePhone"
     ></v-checkbox>
    <v-textarea
    	v-model="workSchedule"
    	:error-messages="workScheduleErrors"
          name="input-7-1"
          label="Графік роботи"
          hint="Hint text"
          required
          @input="$v.workSchedule.$touch()"
      	  @blur="$v.workSchedule.$touch()"
     ></v-textarea>

    <v-btn @click="submit">Ввести</v-btn>
    <v-btn @click="clear">Очистити</v-btn>
  </form></v-flex>
</template>

<script>
  import { validationMixin } from 'vuelidate'
  import { required, maxLength, minLength, length} from 'vuelidate/lib/validators'
  import { mapState, mapMutations, } from 'vuex'
  import AddPhoneForm from 'components/profile/AddPhoneForm.vue'
  import EventBus from 'eventBus/event-bus.js'
  import establishmentApi from 'api/establishmentApi'

  export default {
    mixins: [validationMixin],

    validations: {
      name: { required, maxLength: maxLength(25)},
      address: { required, minLength: minLength(10), maxLength: maxLength(35)},
      description: {required, minLength: minLength(50), maxLength: maxLength(400)},
      workSchedule: {required, minLength: minLength(4), maxLength: maxLength(150)},
      cuisine:{required, minLength: minLength(3), maxLength: maxLength(35)},
      avgCheck: {required, minLength: minLength(2), maxLength: maxLength(10)}
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
            	cuisine: "",
            	avgCheck: "",
            	useProfilePhone: false
        }
    },
    computed: {
    	...mapState(['profile', 'role']),
      nameErrors () {
        const errors = []
        if (!this.$v.name.$dirty) return errors
        !this.$v.name.required && errors.push('Потрібно ввести назву')
        !this.$v.name.maxLength && errors.push('Name must be at least 25 characters long')
        return errors
      },
      descriptionErrors () {
          const errors = []
          if (!this.$v.description.$dirty) return errors
          !this.$v.description.minLength && errors.push('Опис має бути довше 50 символів')
          !this.$v.description.maxLength && errors.push('Description must be at least 400 characters long')
          !this.$v.description.required && errors.push('Потрібно ввести опис')
          return errors
        },
        addressErrors (){
        	const errors = []
            if (!this.$v.address.$dirty) return errors
            !this.$v.address.minLength && errors.push('Адреса має бути довше 10 символів')
            !this.$v.address.maxLength && errors.push('Address must be at least 35 characters long')
            !this.$v.address.required && errors.push('Потрібно ввести адресу')
            return errors
        },
        cuisineErrors (){
        	const errors = []
            if (!this.$v.cuisine.$dirty) return errors
            !this.$v.cuisine.minLength && errors.push('Cuisine must be at most 3 characters long')
            !this.$v.cuisine.maxLength && errors.push('Cuisine must be at least 35 characters long')
            !this.$v.cuisine.required && errors.push('Потрібно ввести кухню')
            return errors
        },
        avgCheckErrors (){
        	const errors = []
            if (!this.$v.avgCheck.$dirty) return errors
            !this.$v.avgCheck.minLength && errors.push('AvgCheck must be at most 3 characters long')
            !this.$v.avgCheck.maxLength && errors.push('AvgCheck must be at least 35 characters long')
            !this.$v.avgCheck.required && errors.push('Потрібно ввести середній чек')
            return errors
        },
        workScheduleErrors (){
        	const errors = []
            if (!this.$v.workSchedule.$dirty) return errors
            !this.$v.workSchedule.minLength && errors.push('Work schedule must be at most 4 characters long')
            !this.$v.workSchedule.maxLength && errors.push('Work schedule must be at least 150 characters long')
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
    	...mapMutations(['addEstablishmentMutation']),
      async submit () {
    	this.$v.$touch()
    	if (this.$v.$invalid || this.phones.length == 0) {
    		if(this.phones.length == 0){
            	EventBus.$emit('empty-phone-list')
            }
           return;
        }
    	const estb = { 
    		email: this.profile.email,
    		name: this.name,
            address: this.address,
            description: this.description,
            workSchedule: this.workSchedule,
            phone: this.phones,
            cuisine: this.cuisine,
            avgCheck: this.avgCheck
    	}

    	const result = await establishmentApi.add(estb)
    	const data = await result.json()
    	this.addEstablishmentMutation(data)
    	this.$router.push('/')
      },
      clear () {
        this.$v.$reset()
        this.name = ''
        this.address = ''
        this.description = ''
        this.workSchedule = ''
        this.phone = ''
        this.cuisine = ''
        this.avgCheck = ''
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