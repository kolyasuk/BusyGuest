<template>
    <div >
    	<div v-if="role=='ADMIN'">
	    	Адмін панель
	    	<div v-for="establishment in establishments" :key="establishment._id">
	    		<router-link :to="'/establishment/'+`${establishment._id}`">
                {{establishment.name}}
            </router-link>
	    	</div>
	    </div>
    </div>
</template>

<script>
    import { mapState} from 'vuex'
    import establishmentApi from 'api/establishmentApi'

    export default {
        data: function () {
            return {
            	establishments: []
            }
        },
        created(){
        	this.loadData()
        },
        computed: mapState(['profile', 'role']),
        methods:{
        	async loadData(){
        		const result = await establishmentApi.getAll()
            	const data = await result.json()
            	const notAcceptedEstablishments = []
            	data.map(function(item) {
            		  if(!item.accepted)
            			  notAcceptedEstablishments.push(item)
            	});
        		this.establishments = notAcceptedEstablishments
        		

        	}
        }
    }
        
</script>

<style>

</style>
