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
        tableIndexToShow: -1,
    },
    mutations: {
        addPhoneMutation(state, profile) {
            state.profile = profile
        },
        changeTableIndex(state, index){
            state.tableIndexToShow=index
        },
        getUserBookedTablesMutation(state, userBookedTables){
        	state.userBookedTables = userBookedTables
        }
    },
    actions: {
    	async addPhoneAction({commit}, profile) {
        	const result = await profileApi.update(profile)
        	const data = await result.json()
            commit('addPhoneMutation', data)
        },
        async getUserBookedTablesAction({commit}, userId){
        	const result = await bookedTablesApi.getByUser(userId)
        	const data = await result.json()
        	commit('getUserBookedTablesMutation', data);
        }
    }
})