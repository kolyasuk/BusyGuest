import Vue from 'vue'


const establishments = Vue.resource('/estb/establishment{/id}')

export default {
	get: id => establishments.get({id}),
    add: establishment => establishments.save({}, establishment),
    update: establishment => establishments.update({id: establishment._id}, establishment),
    remove: id => establishments.remove({id}),
}