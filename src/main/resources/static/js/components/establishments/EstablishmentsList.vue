<template>
    <div>
        <establishment-row v-for="establishment in establishments" :key="establishment._id" :establishment="establishment"/>
    </div>
</template>

<script>
    import EstablishmentRow from 'components/establishments/EstablishmentRow.vue'
    import { mapState} from 'vuex'

    export default {
        components: {
            EstablishmentRow
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
