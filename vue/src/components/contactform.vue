<template>
<div class ="adding-contact-container" >
<form class = "adding-contact">
    <h1>Let's add your contact!</h1>
    
    <label for ="contactName">Contact's name:</label>
    <input type="text" placeholder="Nick Smith" v-model="contact.name" class="form-control">

    <label for="contactEmail">Contact's email:</label>
    <input type="email" placeholder="nsmith2052@gmail.com" v-model="contact.email" class="form-control">

    <label for="contactAddress">Contact's address:</label>
    <input type="text" placeholder="2080 olinville ave NY" v-model="contact.address" class="form-control">

    <label for="contactLikes">Things your contact likes:</label>
    <textarea placeholder="History books, carrot cake, baseball hats from different teams" v-model="contact.likes"></textarea>

    <label for="contactDislikes">Things your contact doesnt like!:</label>
    <textarea placeholder="white chocolate, olive garden, birds" v-model="contact.dislikes"></textarea>

    <label for="contactNotes">Any notes about your contact:</label>
    <textarea placeholder="Reminder to ask about their job interview on the 6th." v-model="contact.notes"></textarea>

    <div class="date-picker">
            <label for="contactBirthDate">Contacts Birthday!:</label>
          <input type="date" id="birthDate" v-model="contact.birthDate" class = "form-control" />
          </div>

    <label for="profilePicture">Profile Picture:</label>
    <input type="file" id="profilePicture" ref="profilePicture" />

    <button id ="button" v-on:click.prevent="saveContact" type="submit">Save contact</button>
</form>
</div>
</template>

<script>
import ContactService from '../services/ContactService';

export default{
   data(){
    return{
        contact:{
            name: "",
            email: "",
            address: "",
            likes: "",
            dislikes: "",
            notes: "",
            birthDate: ""
        }
        };
    },
    methods: {
      
    async saveContact() {
      const jwtToken = localStorage.getItem("token");
      console.log("JWT Token:", jwtToken); // Debugging

      if (!this.contact.name) {
        alert("Contact name is required");
        return;
    }

      const profilePictureFile = this.$refs.profilePicture?.files?.[0];
      const formData = new FormData();

      formData.append("name", this.contact.name);
      formData.append("email", this.contact.email);
      formData.append("address", this.contact.address);
      formData.append("likes", this.contact.likes);
      formData.append("dislikes", this.contact.dislikes);
      formData.append("notes", this.contact.notes);
      formData.append("birthDate", this.contact.birthDate);
if (profilePictureFile) {
  formData.append("profilePicture", profilePictureFile);
}
   // Log form data for debugging
   formData.forEach((value, key) => {
        console.log(`${key}: ${value}`);
      });

try {
  const response = await fetch("http://localhost:9000/contacts/new-contact", {
    method: "POST",
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`
    },
    body: formData
  });

  if (response.status === 201) {
    this.contact = {
          name: "",
          email: "",
          address: "",
          likes: "",
          dislikes: "",
          notes: "",
          birthDate: "",
        };

        if (this.$refs.profilePicture) {
          this.$refs.profilePicture.value = ""; 
        }
    console.log("Contact saved successfully!"); 
    this.$router.push("/createContact");
  } else {
    const errorResponse = await response.json();
    console.error("Error saving contact:", errorResponse);
    alert(`Error: ${errorResponse.message}`); 
  }
} catch (error) {
  console.error("Error saving contact:", error);
  alert("Unexpected error occurred while saving the contact.");
}
}
}
};
</script>

<style scoped>
.adding-contact-container {
  display: grid;
  grid-template-rows: auto 1fr;
  height: 100vh; /* Full height */
}

.adding-contact {
  grid-row: 2; /* Place form in the second row */
  overflow-y: auto;
  padding: 20px;
}

.dropzone {
  border: 2px dashed #007bff;
  padding: 20px;
  margin-top: 10px;
}
</style>