import Vue from 'vue'
import { mapState} from 'vuex'

const bookedTables = Vue.resource('/visitor{/tableId}/bookedTable{/id}')
mapState(['role'])

export default {
	
	getByTable: tableId => bookedTables.get({tableId}),
	getByUser: userId => bookedTables.get({userId}),
	get: id => bookedTables.get({id}),
    add: bookedTable => bookedTables.save({}, bookedTable),
    update: bookedTable => bookedTables.update({id: bookedTable._id}, bookedTable),
    remove: id => bookedTables.remove({id}),
}