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
        <button class="edit-button" @click="openEditModal">edit contact</button>
      </div>
      <EditContactModal
      v-if="isEditModalOpen"
      :contact="contact"
      @update-contact="updateContact"
      @close="isEditModalOpen = false"
    />
    </div>
  </template>
  
  <script>
  import ContactService from '../services/ContactService';
  import EditContactModal from './EditContactModal.vue';

  export default {
    props: {
      contact: {
        type: Object,
        required: true,
      },
    },
    components: { EditContactModal },
  data() {
    return {
      isEditModalOpen: false,
    };
  },
    methods:{
        async deleteContact(contactId) {
          const isConfirmed = window.confirm("Are you sure you want to delete this contact?");
          if(!isConfirmed){
            return;
          }
        try {
            await ContactService.deleteContact(contactId);
            this.$emit("contact-deleted", contactId);
            // this.contacts = this.contacts.filter(contact => contact.contactId !== contactId);
        } catch (error) {
            console.error("Error deleting contact:", error);
        }
    },
    openEditModal() {
      this.isEditModalOpen = true;
    },
    updateContact(updatedContact) {
      this.$emit("update-contact", updatedContact); // Emit to parent
    },
},
  };
  </script>
  
  <style scoped>
  .contact-card {
    width: 300px;
    border: 1px solid #ccc;
    border-radius: 10px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    margin: 10px;
    display: flex;
    flex-direction: column;
    align-items: center;
    font-family: 'Roboto', sans-serif;
    overflow: visible; /* Ensure content isn't clipped */
    padding: 10px 20px; /* Add padding inside the card */
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
 
    overflow: visible; /* Ensure content isn’t clipped */
    white-space: normal; /* Ensure proper text wrapping */
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
    white-space: normal; /* Ensure wrapping */
  word-wrap: break-word; /* For long unbroken words */
  overflow-wrap: break-word; /* Modern equivalent */
  overflow: visible; /* Ensure no clipping */
  }

  .delete-button,
.edit-button {
  display: inline-block; /* Ensures buttons are treated as inline elements */
  padding: 10px 10px; /* Add space inside the buttons */
  font-size: 1rem; /* Modern font size */
  font-weight: bold; /* Slightly thicker text */
  color: white; /* White text for better contrast */
  background-color: rgb(81, 149, 236); /* Base color */
  border: none; /* Removes default borders */
  border-radius: 5px; /* Rounded corners for a softer look */
  cursor: pointer; /* Indicates interactivity */
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1); /* Subtle shadow for depth */
  transition: background-color 0.3s ease, transform 0.2s ease; /* Smooth hover effects */
}

.delete-button:hover {
  background-color: rgb(255, 69, 58); /* Modern red shade for danger */
  transform: scale(1.05); /* Slight zoom effect on hover */
}

.edit-button:hover {
  background-color: rgb(60, 120, 200); /* Slightly darker blue for hover */
  transform: scale(1.05); /* Slight zoom effect */
}

.delete-button:active,
.edit-button:active {
  transform: scale(0.95); /* Subtle "pressed" effect */
}

.edit-button {
  margin-left: 20px;
}
  </style>
  