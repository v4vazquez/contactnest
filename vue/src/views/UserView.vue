<template>
    <div class="UserView">
      <h1>Your contacts</h1>
      <!-- <div class="contact-form-container"><ContactForm/></div> -->
      <div class="contact-grid">
        <ContactCard v-for="contact in contacts" :key="contact.contactId" :contact="contact" @contact-deleted="refreshContacts"/>
      </div>
    </div>
  
  </template>
  
  <script>
  import ContactForm from '@/components/contactform.vue';
  import ContactCard from '../components/contactcard.vue';
  import ContactService from '../services/ContactService';
  export default {
    components:{
        // ContactForm,
        ContactCard
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
  openModal() {
      this.isModalOpen = true; // Open the modal
    },
    closeModal() {
      this.isModalOpen = false; // Close the modal
    },
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
  async updateContact(updatedContact) {
      try {
        await ContactService.updateContact(updatedContact.contactId, updatedContact);
        this.contacts = this.contacts.map((contact) =>
          contact.contactId === updatedContact.contactId ? updatedContact : contact
        );
        await this.fetchContacts();
      } catch (error) {
        console.error("Error updating contact:", error);
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
  padding-bottom: 100px; /* Leave enough space for the footer */
}
  
  .contact-form-container {
    width: 100%;
    max-width: 800px;
    margin-bottom: 20px;
  }
  
  .contact-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); /* Responsive grid */
  gap: 20px; /* Space between cards */
  justify-content: center; /* Center the grid itself */
  justify-items: center; /* Center items within their grid cells */
  width: 100%;
  max-width: 1200px; /* Optional: Limit grid width */
  padding-bottom: 40px; /* Prevent footer from overlapping second-row items */
  margin: 0 auto; /* Ensure the grid itself is centered */
}
  </style>