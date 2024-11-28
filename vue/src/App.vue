<template>
  <div id="capstone-app">
    <div id="nav">
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;&nbsp;
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>&nbsp;&nbsp;
      <router-link v-bind:to="{ name: 'userview'}" v-if="$store.state.token !=''">UserView</router-link>&nbsp;&nbsp;
      <router-link v-bind:to="{ name: 'addcontact'}" v-if="$store.state.token !=''">Add a Contact</router-link>
    </div>
    <router-view />

  </div>
  <Footer></Footer>
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
<style>
html, body {
  margin: 0;
  padding: 0;
  height: 100%;
  overflow-x: hidden; /* Prevent horizontal scroll */
}

#capstone-app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.content {
  flex: 1; /* Allows the content to take the remaining space */
  overflow-y: auto; /* Enable scrolling only for the content */
  margin-bottom: 35px; /* Avoid content overlapping with the fixed footer */
}
footer {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
}
</style>