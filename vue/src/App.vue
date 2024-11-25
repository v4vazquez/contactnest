<template>
  <div id="capstone-app">
    <div id="nav">
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>&nbsp;|&nbsp;
      <router-link v-bind:to="{ name: 'userview'}" v-if="$store.state.token !=''">UserView</router-link>&nbsp;|&nbsp;
      <router-link v-bind:to="{name: 'simpleuploadview'}" v-if="$store.state.token !=''">SimpleUploadView</router-link>
    </div>
    <router-view />
  <Footer></Footer>
  </div>
</template>
<script>
import Footer from '@/components/footer.vue'
export default{
  name:'App',
  components:{
    Footer
  }
}
</script>