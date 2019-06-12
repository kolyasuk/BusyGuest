<template>
   

	<v-flex xs12 sm6 offset-sm3>
		<h2>Список закладів</h2>
		<v-list two-line>
			<template v-for="establishment in establishments" >
			     <establishment-row  :establishment="establishment" :key="establishment._id"/>
		    </template>
		
		</v-list>
	</v-flex>
</template>

<script>
    import EstablishmentRow from 'components/establishments/EstablishmentRow.vue'
    import { mapState} from 'vuex'

    export default {
        components: {
            EstablishmentRow
        },
        computed: mapState(['establishments','role', 'profile']),	
        data(){
            return{
                establishment: null
            }
        }
    }
</script>

<style>

</style>
