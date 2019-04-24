<template>
    <div>
        <div v-if="!profile">
            <div >Авторизуйтесь через 
                <a href="/login">Google</a> 
            </div>
        </div>
        <div v-if="profile">
            <div>{{profile.name}}&nbsp;
                <a href="/logout">Вийти</a>
            </div>
        </div>
        <div v-if="role=='VISITOR'">
        	<books></books>
        </div>
        <div v-if="role=='VISITOR' && profile.phone===''">
            <p>Для продовження введіть всій номер телефону</p>

            <span v-if="incorrectPhone">{{incorrectPhone}}</span>
            <input type="tel" id="phone" name="phone" v-model="phone" pattern="[0-9]{10,13}" required>
            <button v-on:click="inputPhone()">Go!</button>
        </div>
        <div v-else>
            Ваши права: {{role}}
            <div>
                <router-link to="/">Home</router-link>
                <router-view></router-view>
            </div>
        </div>
    </div>
</template>

<script>
    import EstablishmentsList from 'components/establishments/EstablishmentsList.vue'
    import Books from 'components/profile/Books.vue'
    import { mapState} from 'vuex'
    import { mapActions } from 'vuex'
    import {PhoneConstants} from 'constants/PhoneConstants'
    

    export default {
        components: {
            EstablishmentsList,
            Books
        },
        data: function () {
            return {
                incorrectPhone: null,
                phone: null
            }
        },
        computed: mapState(['profile', 'role']),
        methods:{
             ...mapActions(['addPhoneAction']),
            inputPhone(){
                if (this.phone != null && this.phone != PhoneConstants.EMPTY 
                		&& this.phone.length >= PhoneConstants.MIN_LENGTH 
                		&& this.phone.length <= PhoneConstants.MAX_LENGTH) {
                	
                    this.profile.phone=this.phone;
                    this.addPhoneAction(this.profile)
                    this.incorrectPhone=null
                }else
                    this.incorrectPhone = 'Вкажіть коректний номер телефону!';
            }
        }

    }
</script>

<style>

</style>
