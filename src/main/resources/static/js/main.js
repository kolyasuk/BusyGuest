var clientApi = Vue.resource('/client/estb');
var estbApi = Vue.resource('/estb/');

Vue.component('estb-form',{
  props:['estbs'],
  data: function(){
      return {
        email: '',
        name: '',
        address: '',
        phone: '',
        workSchedule: '',
        description: ''
      }
  },
  template: '<div>'+
              '<input type="text" placeholder="email.." v-model="email" />'+
              '<input type="text" placeholder="name.." v-model="name" />'+
              '<input type="text" placeholder="address.." v-model="address" />'+
              '<input type="text" placeholder="phone.." v-model="phone" />'+
              '<input type="text" placeholder="workSchedule.." v-model="workSchedule" />'+
              '<input type="text" placeholder="description.." v-model="description" />'+
              '<input type="button" value="Save" @click="save" />'
            +'</div>',
  methods: {
    save: function(){
      var estb = {
            email : this.email,
            name: this.name,
            address: this.address,
            phone: this.phone,
            workSchedule: this.workSchedule,
            description: this.description
        };

      estbApi.save({}, estb).then(result => 
        result.json().then(data => {
          this.estbs.push(data);
          this.email = '';
          this.name = '';
          this.address = '';
          this.phone = '';
          this.workSchedule = '';
          this.description = '';
      }))

    }
  }
});

Vue.component('estb-row',{
  props: ['estb'],
  template: '<div>' +
                '{{estb.name}}, <a :href="`${estb.email}`">{{estb.email}}</a>' 
            +'</div>',

});

Vue.component('estbs-list',{
  props:['estbs'],
  data: function(){
      return{
          estb: null
      }
  },

  template:'<div>'+
            '<estb-form :estbs="estbs" />'+
            '<estb-row v-for="estb in estbs" :key="estb.id" :estb="estb"/>'+
            '</div>'

});

var app = new Vue({
  el: '#app',
  template: 
  '<div>'+
    '<div v-if="!profile">Авторизуйтесь через <a href="/login">Google</a> </div>'+
    '<div v-else>' +
        '<div>{{profile.name}}&nbsp;<a href="/logout">Вийти</a></div>' +
        '<estbs-list :estbs="estbs"/>'+
     '</div>' +
  '</div>',
  data: {
    estbs: frontendData.estbs,
    profile: frontendData.profile
  },
    created: function(){
    // clientApi.get().then(result => 
    //   result.json().then(data => 
    //     data.forEach(estb => this.estbs.push(estb))
    //   )
    // )
  }
})

