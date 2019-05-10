<template>
<div>
	<div v-if="Array.isArray(bookedTables) && bookedTables.length">
		<booked-table-row v-for="bookedTable in bookedTables"
			:key="bookedTable._id" :bookedTable="bookedTable" />
	</div>
	<div v-else>
		Замовлень немає
	</div>
</div>
</template>

<script>
    import BookedTableRow from 'components/tables/bookedTables/BookedTableRow.vue'
    import bookedTablesApi from 'api/bookedTableApi'
    import { mapState} from 'vuex'

    export default {
    	props: ['table','bookedTables'],
        computed: mapState(['role']),
        components: {
            BookedTableRow
        }, 
    }
</script>

<style>
</style>