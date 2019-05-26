<template>
    <vue-draggable-resizable 
					:draggable="dragTables" 
					:disableUserSelect="true" 
					:x="x" :y="y" 
					:w="15" :h="10" 
					:parent="true"
					@dragging="onDrag"
					>
					
	</vue-draggable-resizable>
</template>

<script>
    import { mapState, mapMutations } from 'vuex'
    import EventBus from 'eventBus/event-bus.js'
    export default {
        props: ['sign'],
        computed: mapState(['role','profile', 'dragTables']),
        data() {
            return {
                x: 0,
                y: 0,
            }
        },
        created: function(){
        	if(!this.bookedTable.accepted && this.role == 'ESTB'){
        		EventBus.$emit('found-new-book', this.bookedTable)
        	}
        },
        methods: {
        	...mapMutations(['setSignsCoordinates']),
        	 onDrag: function (x, y) {
                 this.x = x
                 this.y = y
                 var signObj = {
               		 x: this.x,
                     y: this.y,
                 }     
                 this.setSignsCoordinates([this.sign.id, signObj])
               }
        }
    }

</script>

<style scoped>

</style>