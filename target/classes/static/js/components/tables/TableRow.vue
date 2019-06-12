<template>

				<vue-draggable-resizable v-if="renderComponent" 
					:draggable="dragTables" 
					:resizable="dragTables" 
					:disableUserSelect="true" 
					:grid=[5,5] 
					:x="x" :y="y" 
					:w="width" :h="height" 
					:maxWidth="50"
					:maxHeight="50"
					:minWidth="25"
					:minHeight="25"
					@dragging="onDrag" 
					@resizing="onResize" 
					:parent="true"
					:active.sync="active"
					class="table"
					>				

				    <table-details-dialog :table="table"></table-details-dialog>
				    <edit-table-dialog :table="table"></edit-table-dialog>
				    <delete-table-dialog :table="table"></delete-table-dialog>
				</vue-draggable-resizable>
				

</template>

<script>
	import EditTableDialog from 'components/UI/EditTableDialog.vue'
	import DeleteTableDialog from 'components/UI/DeleteTableDialog.vue'
	import TableDetailsDialog from 'components/UI/TableDetailsDialog.vue'
    import {mapState, mapActions, mapMutations} from 'vuex'
    import EventBus from 'eventBus/event-bus.js'
    import bookedTablesApi from 'api/bookedTableApi'

    export default {
        components: {
            EditTableDialog,
            DeleteTableDialog,
            TableDetailsDialog,
        },
        props: ['table','index'],
        data() {
            return {
            	dialog: false,
            	renderComponent: true,
                alreadyBooked: null,
                tableBooked: 'color: black',
                width: 48,
                height: 48,
                x: 0,
                y: 0,
                active: false
            }
        },
        computed: mapState(['profile','role', 'dragTables', 'estbCoordinates']),
        mounted () {
            EventBus.$on('user-book-deleted', (index) => {
                this.alreadyBooked=null
            }),
            EventBus.$on('found-new-book', (bookedTable) => {
            	if(this.table._id == bookedTable.table._id)
            		this.tableBooked = 'color: red'
            }),
            EventBus.$on('tableAreaChanged', () => {
            	this.renderComponent = false;
                this.$nextTick(() => {
                  this.renderComponent = true;
                });
            })

       	},
        methods: {
        	...mapMutations(['changeDragTablesMutation', 'setTablesCoordinates']),
            loadCoordinates(){
            	if(!Object.keys(this.estbCoordinates.tables).length == 0){
            		var map = new Map(Object.entries(this.estbCoordinates.tables))
                	var currentObj = map.get(this.table._id)
                	if(currentObj != null){
                       	this.x = currentObj.x
                    	this.y = currentObj.y
                    	this.width = currentObj.width
                    	this.height = currentObj.height
                	}
                	
            	}
            },
            onResize: function (x, y, width, height) {
                this.x = x
                this.y = y
                this.width = width
                this.height = height
                var tablesCoordinatesObj = {
                   x: this.x,
                   y: this.y,
                   width:this.width,
                   height:this.height
                }
                this.setTablesCoordinates([this.table._id, tablesCoordinatesObj])
              },
              onDrag: function (x, y) {
                  this.x = x
                  this.y = y
                  var tablesCoordinatesObj = {
                		  x: this.x,
                          y: this.y,
                          width:this.width,
                          height:this.height
                  }     
                  this.setTablesCoordinates([this.table._id, tablesCoordinatesObj])
                }
        },
        updated(){
        	this.loadCoordinates()
         	if(this.active && this.dragTables){
        		EventBus.$emit('tablePositionChanged')
        	} 
        },
        created(){
        	this.loadCoordinates()
            
        }
        
    }

</script>

<style scoped>

.table-button{
	width: 40px;
	position: absolute;
	left:50px;
}

.button-edit{
	top:0px;
}

.button-delete{
	top:15px;
}

.table{
	text-align: center; 
	border-style: solid; 
	width: 50px; 
	margin: auto;
	margin-left:45px;
	position: relative;
	border: 1px solid red;
}

.box{
	width: 48px;
    height: 48px;
    box-sizing: border-box;
    padding: 15px;
    background: yellow;
    display: inline-block;
    position: absolute;
    user-select: none;
    cursor: pointer;
    line-height: 1.8;
    color: #272727;
    font-family: Consolas,Courier New,Lucida Console;
    border-radius: 4px;
}

.dragging{
	background-color: blue;
}

</style>