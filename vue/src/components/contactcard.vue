<template>
    <div class="contact-card">
        
      <img :src="contact.profilePictureUrl" alt="Profile Picture" class="profile-picture" />
      <div class="contact-info">
        <h3>{{ contact.name }}</h3>
        <p><strong>Email:</strong> {{ contact.email }}</p>
        <p><strong>Address:</strong> {{ contact.address }}</p>
        <p><strong>Likes:</strong> {{ contact.likes }}</p>
        <p><strong>Dislikes:</strong> {{ contact.dislikes }}</p>
        <p><strong>Notes:</strong> {{ contact.notes }}</p>
        <p><strong>Birthdate:</strong> {{ contact.birthDate }}</p>
        <button class="delete-button" @click="deleteContact(contact.contactId)">delete contact</button>
        <button class="edit-button" @click="editContact">edit contact</button>
      </div>
    </div>
  </template>
  
  <script>
  import ContactService from '../services/ContactService';
  export default {
    props: {
      contact: {
        type: Object,
        required: true,
      },
    },
    methods:{
        async deleteContact(contactId) {
        try {
            await ContactService.deleteContact(contactId);
            this.$emit("contact-deleted", contactId);
            // this.contacts = this.contacts.filter(contact => contact.contactId !== contactId);
        } catch (error) {
            console.error("Error deleting contact:", error);
        }
    },
},
  };
  </script>
  
  <style scoped>
  .contact-card {
    width: 300px;
    border: 1px solid #ccc;
    border-radius: 10px;
    overflow: hidden;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    margin: 10px;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  
  .profile-picture {
    width: 100%;
    height: 400px;
    object-fit: cover;
    border-bottom: 1px solid #ccc;
  }
  
  .contact-info {
    padding: 10px;
    text-align: left;
    width: 100%;
  }
  
  .contact-info h3 {
    margin-bottom: 10px;
    font-size: 1.5rem;
    color: #333;
  }
  
  .contact-info p {
    margin: 5px 0;
    font-size: 1rem;
    color: #555;
  }
  
  .delete-button:hover{
  background-color: red;
}
  </style>
  