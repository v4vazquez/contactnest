<template>
  <div id="capstone-app">
    <div id="nav">
      <div class="navbar">
        <div class="navbar-left">
          <span class="brand">ContactNest</span>
        </div>
        <div class="navbar-right">
      <router-link v-bind:to="{ name: 'home' }"  v-if="$store.state.token != ''">Home</router-link>&nbsp;&nbsp;
      <router-link v-bind:to="{ name: 'userview'}" v-if="$store.state.token !=''">UserView</router-link>&nbsp;&nbsp;
      <router-link v-bind:to="{ name: 'addcontact'}" v-if="$store.state.token !=''">Add a Contact</router-link>
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>&nbsp;&nbsp;
    </div>
  </div>
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
<style>
html, body {
  margin: 0;
  padding: 0;
  height: 100%;
  overflow-x: hidden; /* Prevent horizontal scroll */
  font-family: 'Roboto', sans-serif;
}

#capstone-app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;

}
#nav {
  background-color: rgb(81, 149, 236);
  padding: 10px 20px;
  color: white;
}
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.navbar-left .brand {
  font-size: 1.5rem;
  font-weight: bold;
  color: white;
}
.navbar-right {
  display: flex;
  gap: 20px;
}
.navbar-right a {
  text-decoration: none;
  color: white;
  font-size: 1rem;
}

.navbar-right a:hover {
  text-decoration: underline;
}

.content {
  flex: 1; /* Allows the content to take the remaining space */
  overflow-y: auto; /* Enable scrolling only for the content */
  margin-bottom: 50px; /* Avoid content overlapping with the fixed footer */
}
footer {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  height: 50px; /* Set fixed height for the footer */
  
}
</style>