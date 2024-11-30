<template>
    <form class="edit-contact-form" @submit.prevent="submitEdit">
      <label>
        Name:
        <input v-model="localContact.name" type="text" required />
      </label>
      <label>
        Email:
        <input v-model="localContact.email" type="email" />
      </label>
      <label>
        Address:
        <input v-model="localContact.address" type="text" />
      </label>
      <label>
        Likes:
        <textarea v-model="localContact.likes"></textarea>
      </label>
      <label>
        Dislikes:
        <textarea v-model="localContact.dislikes"></textarea>
      </label>
      <label>
        Notes:
        <textarea v-model="localContact.notes"></textarea>
      </label>
      <label>
        Birthdate:
        <input v-model="localContact.birthDate" type="date" />
      </label>
  
      <!-- Profile Picture -->
      <label>
        Profile Picture:
        <input type="file" ref="profilePicture" />
        <img
          v-if="localContact.profilePictureUrl"
          :src="localContact.profilePictureUrl"
          alt="Current Profile Picture"
          class="current-profile-picture"
        />
      </label>
  
      <button type="submit">Save Changes</button>
    </form>
  </template>
  
  <script>
  export default {
    props: {
      contact: {
        type: Object,
        required: true,
      },
    },
    data() {
      return {
        localContact: { ...this.contact }, // Local copy of contact data
      };
    },
    emits: ["save-contact"],
    methods: {
      submitEdit() {
        const formData = new FormData();

    formData.append("contactId", this.localContact.contactId);
    // formData.append("userId", this.localContact.userId);
    formData.append("name", this.localContact.name);
    formData.append("notes", this.localContact.notes);
    formData.append("birthDate", this.localContact.birthDate);
    formData.append("email", this.localContact.email);
    formData.append("address", this.localContact.address);
    formData.append("likes", this.localContact.likes);
    formData.append("dislikes", this.localContact.dislikes);
 
   // Attach profile picture
   const profilePictureFile = this.$refs.profilePicture?.files?.[0];
    if (profilePictureFile) {
      formData.append("profilePicture", profilePictureFile);
    }

    console.log("Submitting FormData:");
    for (let [key, value] of formData.entries()) {
      console.log(`${key}:`, value);
    }

    // Emit the FormData to the parent component
    this.$emit("save-contact", { contactId: this.localContact.contactId, formData });

      },
    },
  };
  </script>
  
  <style scoped>
.edit-contact-form {
  display: flex;
  flex-direction: column;
  gap: 10px; /* Smaller spacing between elements */
  padding: 10px; /* Reduced padding */
  border-radius: 8px; /* Slightly rounded corners */
  background-color: #ffffff; /* White background */
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* Subtle shadow */
  max-width: 300px; /* Half the width */
  margin: 0 auto; /* Center the form within its container */
}

label {
  display: flex;
  flex-direction: column;
  font-weight: bold;
  color: #555;
  font-size: 0.9rem; /* Slightly smaller font size */
}

input,
textarea {
  padding: 8px; /* Reduced padding for inputs */
  border: 1px solid #ddd;
  border-radius: 5px;
  width: 100%;
  box-sizing: border-box;
}

textarea {
  resize: vertical; /* Allow vertical resizing only */
  height: 80px; /* Reduced default height */
}

input:focus,
textarea:focus {
  border-color: #007bff;
  outline: none;
}

.current-profile-picture {
  width: 80px; /* Smaller profile picture preview */
  height: 80px;
  object-fit: cover;
  margin-top: 8px;
  border: 1px solid #ddd;
  border-radius: 5px;
}

button.save-button {
  background-color: #007bff;
  color: white;
  padding: 8px 16px; /* Smaller button padding */
  border: none;
  border-radius: 5px;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

button.save-button:hover {
  background-color: #0056b3;
}
</style>