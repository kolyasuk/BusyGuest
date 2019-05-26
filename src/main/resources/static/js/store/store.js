import Vue from 'vue'
import Vuex from 'vuex'
import profileApi from 'api/profile'
import tablesApi from 'api/tablesApi'
import bookedTablesApi from 'api/bookedTableApi'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
    	establishments: frontendData.establishments,
        profile: frontendData.profile,
        role: frontendData.role,
        userBookedTables:[],
        dragTables: false,
        estbCoordinates:{
        	estbId: null,
        	width: null,
        	height: null,
        	tables: null,
        	signs: null
        },
    },
    mutations: {
        addPhoneMutation(state, profile) {
            state.profile = profile
        },
        getUserBookedTablesMutation(state, userBookedTables){
        	state.userBookedTables = userBookedTables
        },
        addEstablishmentMutation(state, establishment) {
            state.establishments.push(establishment)
        },
        changeDragTablesMutation(state, dragTables) {
            state.dragTables = !dragTables
        },
        addEstbCoordinatesMutation(state, estbCoordinates) {
        	state.estbCoordinates = estbCoordinates
        },
        setTablesCoordinates(state, [tableId, tablesCoordinatesObj]) {
        	state.estbCoordinates.tables[tableId] = tablesCoordinatesObj
        },
        setSignsCoordinates(state, [signId, signsObj]) {
        	state.estbCoordinates.signs[signId] = signsObj
        }
    },
    actions: {
    	async addPhoneAction({commit}, profile) {
    		const result = await profileApi.update(profile)
    		const data = await result
            commit('addPhoneMutation', data)
        },
        async getUserBookedTablesAction({commit}, userId){
        	const result = await bookedTablesApi.getByUser(userId)
        	const data = await result.json()
        	commit('getUserBookedTablesMutation', data);
        }
    }
})