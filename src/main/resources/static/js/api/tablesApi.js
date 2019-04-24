import Vue from 'vue'


const tables = Vue.resource('/visitor/establishment{/id}/table')

export default {
	get: id => tables.get({id})
/*    add: bookedTable => bookedTables.save({}, bookedTable),
    update: bookedTable => bookedTables.update({id: bookedTable._id}, bookedTable),
    remove: id => bookedTables.remove({id}),*/
}