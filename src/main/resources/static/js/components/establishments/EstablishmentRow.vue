<template>
   <div v-show="establishment.show">
        <div v-if="role=='ESTB'">
            <div v-if="profile.email===establishment.email">
            	{{this.$router.push(`/establishment/`+establishment._id)}}
            </div>
        </div>
        <div v-else>
            <router-link :to="'/establishment/'+`${establishment._id}`">
                {{establishment.name}}
            </router-link>
        </div>
    </div>
    
</template>

<script>
    import { mapState} from 'vuex'
    export default {
        props: ['establishment'],
        computed: mapState(['profile','role']),
    }
</script>

<style>

</style>