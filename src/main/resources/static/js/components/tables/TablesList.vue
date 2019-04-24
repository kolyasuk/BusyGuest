<template>
    <div>
        <table-row v-for="(table, index) in tablesList" :key="table._id" @updateData="loadData()" :table="table" :index="index"/>
    </div>
</template>

<script>
    import TableRow from 'components/tables/TableRow.vue'
    import { mapState} from 'vuex'
    import tablesApi from 'api/tablesApi'

    export default {
        computed: mapState(['role']),
        components: {
            TableRow
        },
        data(){
        	return{
        		tablesList: null
        	}
        },
        created: function(){
            this.loadData(this.$route.params.id)
        },
        methods:{
        	async loadData(id){
        		const result = await tablesApi.get(id)
            	const data = await result.json()
            	this.tablesList = data
            }
        }
    }
</script>

<style>

</style>