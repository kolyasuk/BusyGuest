<template>
    <div>
        <!-- <table-row v-for="(table, index) in tablesList" :key="table._id" @updateData="loadData()" :table="table" :index="index"/> -->
        <div class="tablesArea">
    		<div class="box" v-dragged="onDragged">Lol</div>
    	</div>

    	<v-item-group>
    		<v-container grid-list-md>
      			<v-layout wrap>
					<v-expansion-panel>
						<table-row v-for="(table, index) in tablesList" :key="table._id"  @updateData="loadData()" :table="table" :index="index"/>
			
					</v-expansion-panel>
		 		</v-layout>
      		</v-container>
      	</v-item-group> 
		 

    </div>
</template>

<script>
    import TableRow from 'components/tables/TableRow.vue'
    import { mapState} from 'vuex'
    import tablesApi from 'api/tablesApi'
    import EventBus from 'eventBus/event-bus.js'

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
        mounted(){
            EventBus.$on('table-added', (data) => {
            	this.tablesList.push(data)
            })
            EventBus.$on('table-edited', (data) => {
            	const index = this.tablesList.findIndex(item => item._id === data._id)
            	this.tablesList.splice(index, 1, data)
            })
            EventBus.$on('table-deleted', (data) => {
            	const index = this.tablesList.findIndex(item => item._id === data._id)
            	this.tablesList.splice(index, 1)
            })


       	},
        created: function(){
            this.loadData(this.$route.params.id)
        },
        methods:{
        	async loadData(id){
        		const result = await tablesApi.getTablesByEstablishment(id)
            	const data = await result.json()
            	this.tablesList = data
            },
            onDragged({ el, deltaX, deltaY, offsetX, offsetY, clientX, clientY, first, last }) {
            	if (first) {
            		this.dragged = true
            	    return
            	}
            	if (last) {
            	    this.dragged = false
            	    return
            	}
            	var l = +window.getComputedStyle(el)['left'].slice(0, -2) || 0
            	var t = +window.getComputedStyle(el)['top'].slice(0, -2) || 0
            	
            	el.style.left = l + deltaX + 'px'
            	el.style.top = t + deltaY + 'px'
            }
        }
    }
</script>

<style>
.box{
	width: 50px;
    height: 50px;
    box-sizing: border-box;
    padding: 15px;
    background: #fff;
    display: inline-block;
    position: relative;
    user-select: none;
    cursor: pointer;
    box-shadow: 0 14px 28px rgba(0,0,0,.25), 0 10px 10px rgba(0,0,0,.22);
    transition: box-shadow .3s cubic-bezier(.25,.8,.25,1);
    line-height: 1.8;
    color: #272727;
    font-family: Consolas,Courier New,Lucida Console;
    border-radius: 4px;
}

.tablesArea{
	width: 500px;
	height: 200px;
}
</style>