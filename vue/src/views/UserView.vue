<template>
    <div class="UserView">
      <h1>Your contacts</h1>
      <div class="contact-form-container"><ContactForm/></div>
      <div class="contact-grid">
        <Contactcard v-for="contact in contacts" :key="contact.contactId" :contact="contact" @contact-deleted="refreshContacts"/>
      </div>
    </div>
  
  </template>
  
  <script>
  import ContactForm from '@/components/contactform.vue';

import Contactcard from '../components/contactcard.vue';
import ContactService from '../services/ContactService';
  export default {
    components:{
        ContactForm,
        Contactcard
    },
    data(){
      return{
        contacts:[],
      };
    },
    async created(){
      await this.fetchContacts();
    },
 methods: {
  async fetchContacts(){
    const user = JSON.parse(localStorage.getItem("user"));

      if(user){
        try{
          const response = await ContactService.getContactsForCurrentUser(user.id);
       
          this.contacts=response.data;
        }catch(error){
          console.error("Error fetching contacts:", error);
        }
      }
  },
    removeContact(contactId) {
      this.contacts = this.contacts.filter((contact) => contact.contactId !== contactId);
    },
    async refreshContacts() {
      await this.fetchContacts(); // Refresh the contacts list after deletion
    },
  },

  };
  </script>
  <style scoped>
  .user-view {
    padding: 20px;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  
  .contact-form-container {
    width: 100%;
    max-width: 800px;
    margin-bottom: 20px;
  }
  
  .contact-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); 
    gap: 20px;
    justify-content: center;
    width: 100%;
  }
  </style>