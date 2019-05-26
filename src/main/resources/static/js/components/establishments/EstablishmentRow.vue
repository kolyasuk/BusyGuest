<template>
<!--    <div v-show="establishment.show">
        <div v-if="role=='ESTB'">
            <div v-if="profile.email===establishment.email">
            	{{this.$router.push(`/establishment/`+establishment._id)}}
            </div>
        </div>
        <div v-else>
            <router-link :to="'/establishment/'+`${establishment._id}`">
                {{establishment.name}}
            </router-link>
        </div>
    </div> -->

      <div v-show="establishment.show">
      	<div v-if="role=='ESTB'">
      		<div v-if="profile.email===establishment.email">
            	{{this.$router.push(`/establishment/`+establishment._id)}}
            </div>
      	 </div>
      	 <div v-else>
            <v-list-tile :key="establishment.name" avatar @click="goToEstb">
              <v-list-tile-avatar>
                <img :src="profile.userpic">
              </v-list-tile-avatar>
              <v-list-tile-content>
                <v-list-tile-title v-html="establishment.name"></v-list-tile-title>
                <v-tooltip bottom>
      				<template v-slot:activator="{ on }">
                		<v-list-tile-sub-title v-on="on" v-html="establishment.description"></v-list-tile-sub-title>
                    </template>
			      <span>{{establishment.description}}</span>
			    </v-tooltip>
              </v-list-tile-content>
            </v-list-tile>
            </div>
       </div>


       

    
</template>

<script>
    import { mapState} from 'vuex'
    export default {
        props: ['establishment'],
        computed: mapState(['profile','role']),
        methods:{
        	goToEstb(){
        		this.$router.push(`/establishment/`+this.establishment._id)
        	}
        }
    }
</script>

<style>

</style>