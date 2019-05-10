import Vue from 'vue'


const tables = Vue.resource('/estb/establishment{/estbId}/table{/id}')

export default {
	getTablesByEstablishment: estbId => tables.get({estbId}),
	get: id => tables.get({id}),
	add: table => tables.save({}, table),
    update: table => tables.update({id: table._id}, table),
    remove: id => tables.remove({id}),
}