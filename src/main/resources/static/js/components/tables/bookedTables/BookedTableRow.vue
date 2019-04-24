<template>
    <div>
        <div v-if="role==='ESTB'">
            <router-link :to="`/bookedTable/`+`${bookedTable._id}`">
                {{bookedTable.bookedOn | moment("MM/DD/YYYY HH:mm ")}}
            </router-link>
        </div>
        <div v-else>
            {{bookedTable.bookedOn | moment("MM/DD/YYYY HH:mm ")}}
        </div>
    </div>
    
</template>

<script>
    import { mapState} from 'vuex'
    export default {
        props: ['bookedTable'],
        computed: mapState(['role','profile'])
    }

</script>

<style scoped>

</style>