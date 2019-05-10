<template>
    <v-app>
	    Привіт! Створи його зараз же!
	    
	    <establishment-form></establishment-form>
    </v-app>
</template>

<script>
	import EstablishmentForm from 'components/establishments/EstablishmentForm.vue'
	
    export default {
	    components:{
	    	EstablishmentForm,
	    },
    }
</script>

<style>

</style>
