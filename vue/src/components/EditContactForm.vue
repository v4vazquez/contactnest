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

//         const formData = new FormData();
  
//           // Check if `localContact` exists and has properties
//   if (!this.localContact || typeof this.localContact !== "object") {
//     console.error("Invalid contact data:", this.localContact);
//     return;
//   }

//         Object.keys(this.localContact).forEach((key) => {
//         formData.append(key, this.localContact[key]);
//       });

//       // Attach profile picture
//       const profilePictureFile = this.$refs.profilePicture?.files?.[0];
//       if (profilePictureFile) {
//         formData.append("profilePicture", profilePictureFile);
//       }

  
//          this.$emit("save-contact", {
//     contactId: this.localContact.contactId, // Ensure contactId exists
//     formData,
//   });
      },
    },
  };
  </script>
  
  <style scoped>
  .edit-contact-form {
    display: flex;
    flex-direction: column;
    gap: 10px;
  }
  .current-profile-picture {
    width: 100px;
    height: 100px;
    object-fit: cover;
    margin-top: 10px;
  }
  </style>
  