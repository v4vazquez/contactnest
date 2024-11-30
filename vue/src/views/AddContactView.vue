<template>
    <div class="UserView">
 
      <div class="contact-form-container"> <ContactForm   :disabled="userRole === 'user' && contacts.length >= 4"/></div>
  
    </div>
    <p v-if="userRole === 'user' && contacts.length >= 4" class="warning-message">
      You cannot add more contacts. Please delete an existing contact to proceed.
    </p>
  </template>
  
  <script>
  import ContactForm from '@/components/contactform.vue';
  import ContactService from '../services/ContactService';
  export default {
    components:{
        ContactForm,
    },
    data(){
      return{
        contacts:[],
        isModalOpen:false,
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
    display:flex;
    justify-content: center;;
    align-items: center;
    height: 100%; /* Ensures it takes up full height */
  }
  
  .contact-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); 
    gap: 20px;
    justify-content: center;
    width: 100%;
  }
  </style>