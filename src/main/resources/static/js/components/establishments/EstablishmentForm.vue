<template>
    <div>
        <input type="text" placeholder="email.." v-model="email" />
        <input type="text" placeholder="name.." v-model="name" />
        <input type="text" placeholder="address.." v-model="address" />
        <input type="text" placeholder="phone.." v-model="phone" />
        <input type="text" placeholder="workSchedule.." v-model="workSchedule" />
        <input type="text" placeholder="description.." v-model="description" />
        <input type="button" value="Save" @click="save" />
    </div>
</template>

<script>
    import { mapState} from 'vuex'
    export default {

        computed: mapState(['establishments']),
        data(){
            return {
                email: '',
                name: '',
                address: '',
                phone: '',
                workSchedule: '',
                description: ''
            }
        },
        methods: {
            save(){
            	var estb = {
                    email : this.email,
                    name: this.name,
                    address: this.address,
                    phone: this.phone,
                    workSchedule: this.workSchedule,
                    description: this.description
                };

            this.$resource('/estb/').save({}, estb).then(result => 
            result.json().then(data => {
                this.estbs.push(data);
                this.email = ''
                this.name = ''
                this.address = ''
                this.phone = ''
                this.workSchedule = ''
                this.description = ''
            }))

            }
        }
    }
</script>

<style>

</style>