import Vue from 'vue'


const establishments = Vue.resource('/visitor/establishment{/id}')

export default {
	get: id => establishments.get({id}),
    add: bookedTable => bookedTables.save({}, bookedTable),
    update: bookedTable => bookedTables.update({id: bookedTable._id}, bookedTable),
    remove: id => bookedTables.remove({id}),
}