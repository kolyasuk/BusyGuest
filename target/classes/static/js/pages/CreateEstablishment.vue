<template>
    <v-app >
	    <div v-if="role=='ESTB'">
	    	<div v-if="profileEstb == null">
			    <h2><b>Заповніть дані про заклад за допомогою даної форми, та надішліть заявку на розгляд адміністріції.</b></h2>
			    
			    <establishment-form></establishment-form>
		    </div>
		    <div v-else>
				Ви вже створили заклад!
			</div>
		</div>
		<div v-else>
			Ви не можете створювати заклад!
		</div>
    </v-app>
</template>

<script>
	import EstablishmentForm from 'components/establishments/EstablishmentForm.vue'
	import { mapState} from 'vuex'
    export default {
	    components:{
	    	EstablishmentForm,
	    },
	    data() {
	        return {
	        	profileEstb: null
	        }
	    },
	    created(){
	    	this.profileEstb = this.establishments.find(item => item.email==this.profile.email)
	    },
	    computed: {
	    	...mapState(['profile', 'role', 'establishments'])
	    }
    }
</script>

<style>

</style>
