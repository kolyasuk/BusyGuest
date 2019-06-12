<template>
    <div>
        <div v-if="role==='ESTB'">
            <router-link :to="`/bookedTable/`+`${bookedTable._id}`">
                <span :style="bookedTable.accepted ? 'color:green;': 'color:red'">{{bookedTable.bookedOn | moment("MM/DD/YYYY HH:mm ")}}</span>
            </router-link>
        </div>
        <div v-else>
            <span :style="bookedTable.accepted ? 'color:green;': 'color:red'">{{bookedTable.bookedOn | moment("MM/DD/YYYY HH:mm ")}}</span>
        </div>
    </div>
    
</template>

<script>
    import { mapState} from 'vuex'
    import EventBus from 'eventBus/event-bus.js'
    export default {
        props: ['bookedTable'],
        computed: mapState(['role','profile']),
        created: function(){
        	if(!this.bookedTable.accepted && this.role == 'ESTB'){
        		EventBus.$emit('found-new-book', this.bookedTable)
        	}
        }
    }

</script>

<style scoped>

</style>