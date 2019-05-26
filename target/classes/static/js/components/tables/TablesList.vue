<template>
    <div>
    	<v-item-group>
     		<v-container grid-list-xl> 
      			<v-layout wrap> 
      						<div v-if="role != 'VISITOR'">
	      						<v-btn color="red lighten-2" @click="changeTablePosition" dark>
									<span>Редагувати</span>
								</v-btn>
								<div v-show="dragTables" class="tableAreaSettings">
									<v-btn color="red lighten-2" @click="createToilet" dark>
										<span>+WC</span>
									</v-btn>
									<v-btn color="red lighten-2" @click="signs.set()" dark>
										<span>+EXIT</span>
									</v-btn>
									<v-checkbox v-model="editTableArea" :label="'Змінити розмір планування'"></v-checkbox>
									<table-form ></table-form>

								</div>
							</div>
							
							<div id="areaParrent" class="areaParrent">
							    <vue-draggable-resizable 
								    :disableUserSelect="true" 
								    id="tablesArea" 
								    class="tablesArea"
								    :active="editTableArea" 
								    :draggable="false" 
								    :resizable="editTableArea" 
								    :w="estbCoordinates.width" 
								    :h="estbCoordinates.height"
								    @resizing="onResize" 
								    :minWidth="minWidth"
								    :minHeight="minHeight"
								    :parent="true"
								    @deactivated="onDeactivate">
							    	<table-row v-for="(table, index) in tablesList" :key="table._id"  @updateData="loadData()" :table="table" :index="index"/>
							    	<sign v-for="(obj, index) in Object.keys(signs).length" :key="index"></sign>
							    </vue-draggable-resizable>
							 </div>
		 		 </v-layout>
       		</v-container> 
      	</v-item-group> 
		 

    </div>
</template>

<script>
    import TableRow from 'components/tables/TableRow.vue'
    import Sign from 'components/tables/Sign.vue'
    import { mapState, mapMutations} from 'vuex'
    import tablesApi from 'api/tablesApi'
    import EventBus from 'eventBus/event-bus.js'
    import VueDraggableResizable from 'vue-draggable-resizable'
    import TableForm from 'components/tables/TableForm.vue'

    export default {
        computed: mapState(['role','dragTables', 'estbCoordinates']),
        components: {
            TableRow,
            VueDraggableResizable,
            Sign,
            TableForm
        },
        data(){
        	return{
        		tablesList: null,
        		exitList: [],
        		wcList: [],
        		editTableArea: false,
                width: 0,
                height: 0,
                x: 0,
                y: 0,
                minWidth: 0,
                minHeight: 0,
                signs: {}
        	}
        },
        mounted(){
            EventBus.$on('table-added', (data) => {
            	this.tablesList.push(data)
            	if(this.dragTables==false)
            		this.changeDragTablesMutation()
            }),
            EventBus.$on('table-edited', (data) => {
            	const index = this.tablesList.findIndex(item => item._id === data._id)
            	this.tablesList.splice(index, 1, data)
            }),
            EventBus.$on('table-deleted', (data) => {
            	const index = this.tablesList.findIndex(item => item._id === data._id)
            	this.tablesList.splice(index, 1)
            }),
            EventBus.$on('tablePositionChanged', () => {
            	this.minWidth = 0
            	this.minHeight = 0
            	this.defineMinAreaSize()
            	this.editTableArea = false
            })
       	},
       	updated(){
			this.loadCoordinates()
			if(this.dragTables == false)
				this.editTableArea = false
       	},
        created: function(){
            this.loadData(this.$route.params.id)
        },
        methods:{
        	...mapMutations(['changeDragTablesMutation','addEstbCoordinatesMutation','setSignsCoordinates']),
        	loadCoordinates(){
    			this.x = this.estbCoordinates.x - this.estbCoordinates.width
    			this.defineMinAreaSize()
        	},
        	defineMinAreaSize(){
        		if(!Object.keys(this.estbCoordinates.tables).length == 0){
            		var map = new Map(Object.entries(this.estbCoordinates.tables))
            		map.forEach(this.compareSizeMap)
            	}
        	},
        	loadSingsCoordinates(){
        		if(!Object.keys(this.estbCoordinates.signs).length == 0){
            		var map = new Map(Object.entries(this.estbCoordinates.signs))
            		map.forEach(this.compareSignsMap)
            	}
        	},
        	compareSizeMap(value, key, map) {
        		var x = value.x+value.width
            	var y = value.y+value.height
        		if(this.minWidth < x)
        			this.minWidth = x
        		if(this.minHeight < y)
            		this.minHeight = y
        	},
        	compareSignsMap(value, key, map) {
        		
        	},
        	createToilet(){
        		var sign = {
        			x: 0,
        			y: 0
        		}
        		this.setSignsCoordinates([this.sign.id, sign])
        	},
        	async loadData(id){
        		const result = await tablesApi.getTablesByEstablishment(id)
            	const data = await result.json()
            	this.tablesList = data
            },
            changeTablePosition(){
            	if(this.dragTables){
            		this.editTableArea = false
                	this.$resource('/estb/establishment{/id}/coordinates').save({id:this.$route.params.id}, this.estbCoordinates)
            	}

            	this.changeDragTablesMutation(this.dragTables)
            },
            onResize: function (x, y, width, height) {
                this.x = x
                this.y = y
                this.width = width
                this.height = height

              },
              onDeactivate(){
            	  EventBus.$emit('tableAreaChanged')
                  var tempEstbCoordinates = {
                  	...this.estbCoordinates
                  }
                  tempEstbCoordinates.width = this.width
                  tempEstbCoordinates.height = this.height
                  tempEstbCoordinates.x = this.x
                  tempEstbCoordinates.y = this.y
                  this.addEstbCoordinatesMutation(tempEstbCoordinates)
              }
            
        }
    }
</script>

<style>

.areaParrent{
	height: 700px; 
	width: 100%; 
	border: 1px solid red; 
	position: relative;
	margin: 0 auto;
}


.box{
	width: 24px;
    height: 24px;
    box-sizing: border-box;
    padding: 15px;
    background: #fff;
    display: inline-block;
    position: absolute;
    user-select: none;
    cursor: pointer;
    line-height: 1.8;
    color: #272727;
    font-family: Consolas,Courier New,Lucida Console;
    border-radius: 4px;
    left: 0px;
    top: 0px;
}

.tablesArea{
	position: relative !important;
 	margin: 0 auto !important;
}

.dragging{
	background-color: blue;
}
</style>