<template>
    <div>
        <div v-if="role=='ESTB'">
        	<establishment-form/> 
        </div>
        <establishment-row v-for="establishment in establishments" :key="establishment._id" :establishment="establishment"/>
    </div>
</template>

<script>
    import EstablishmentRow from 'components/establishments/EstablishmentRow.vue'
    import EstablishmentForm from 'components/establishments/EstablishmentForm.vue'
    import { mapState} from 'vuex'

    export default {
        components: {
            EstablishmentRow,
            EstablishmentForm
        },
        computed: mapState(['establishments','role']),
        data(){
            return{
                establishment: null
            }
        }
    }
</script>

<style>

</style>
