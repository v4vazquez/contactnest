<template>
<div class ="adding-contact-container" >
<form class = "adding-contact">
    <h1>Let's add your contact!</h1>
    
    <label for ="contactName" >Contact's name:</label>
    <input type="text" v-model="contact.name" class="form-control">

    <label for="contactEmail">Contact's email:</label>
    <input type="email"  v-model="contact.email" class="form-control">

    <label for="contactAddress">Contact's address:</label>
    <input type="text"  v-model="contact.address" class="form-control">

    <label for="contactLikes">Things your contact likes:<ToolTip content = "Good reminder for birthday gifts/holiday gifts"></ToolTip></label>
    <textarea placeholder="History books, carrot cake, baseball hats from different teams" v-model="contact.likes"></textarea>

    <label for="contactDislikes">Things your contact doesnt like!:</label>
    <textarea placeholder="white chocolate, olive garden, birds" v-model="contact.dislikes"></textarea>

    <label for="contactNotes">Any notes about your contact:<ToolTip content = "This could be used for talking points with your contact! Other examples: How did their audition go? How was their vacation to Chile?"></ToolTip></label>
    <textarea placeholder="Reminder to ask about their job interview on the 6th." v-model="contact.notes"></textarea>

    <div class="date-picker">
            <label for="contactBirthDate">Contacts Birthday!:</label>
          <input type="date" id="birthDate" v-model="contact.birthDate" class = "form-control" :max="currentDate" />
          <p v-if="birthDateError" class="error">{{ birthDateError }}</p>
          </div>

    <label for="profilePicture">Profile Picture:</label>
    <input type="file" id="profilePicture" ref="profilePicture" />

    <button id ="button" v-on:click.prevent="saveContact" type="submit">Save contact</button>
</form>
</div>
</template>

<script>
import ContactService from '../services/ContactService';
import ToolTip from './ToolTip.vue';

export default{
  components:{
    ToolTip,
  },
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
        },
        currentDate: (() => {
  const today = new Date();
  const year = today.getFullYear();
  const month = String(today.getMonth() + 1).padStart(2, "0"); // Months are 0-indexed
  const day = String(today.getDate()).padStart(2, "0");
  return `${year}-${month}-${day}`;
})(),
        birthDateError: ""
        };
    },
    methods: {
      
    async saveContact() {

      this.birthDateError = "";

// Validate birthdate
const selectedDate = new Date(this.contact.birthDate);
const today = new Date();

if (selectedDate > today) {
  this.birthDateError =
    "You can't be born in the future... at least I don't think you can.";
  return; // Stop form submission
}
      const formData = new FormData();

      formData.append("name", this.contact.name);
      formData.append("email", this.contact.email);
      formData.append("address", this.contact.address);
      formData.append("likes", this.contact.likes);
      formData.append("dislikes", this.contact.dislikes);
      formData.append("notes", this.contact.notes);
      formData.append("birthDate", this.contact.birthDate);

      const profilePictureFile = this.$refs.profilePicture?.files?.[0];
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
    this.$emit("contact-added"); 

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
  display: flex;
  justify-content: center;
  align-items: center; /* Align form at the top of the viewport */
  min-height: calc(100vh - 50px); /* Adjust height to subtract the fixed footer height */
  padding: 20px;
  background-color: #f7f8fa;
  box-sizing: border-box; /* Ensure padding doesn’t affect layout */
}

.adding-contact {
  display: grid;
  grid-template-columns: 1fr; /* Single column layout */
  gap: 20px; /* Space between form elements */
  background-color: #ffffff; /* White background for the form */
  padding: 30px; /* Inner padding for the form */
  border-radius: 10px; /* Rounded corners */
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* Subtle shadow */
  max-width: 800px; /* Limit form width */
  width: 100%; /* Make form responsive */

}


.adding-contact h1 {
  font-size: 1.8rem;
  margin-bottom: 20px;
  color: #333;
  text-align: center;
}

.adding-contact label {
  font-weight: bold;
  margin-bottom: 5px;
  display: block; /* Ensures proper spacing for labels */
  color: #555;
}

.adding-contact input,
.adding-contact textarea,
.adding-contact button {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  box-sizing: border-box;
}

.adding-contact input:focus,
.adding-contact textarea:focus {
  outline: none;
  border-color: #007bff; /* Highlight on focus */
}

.adding-contact textarea {
  resize: vertical; /* Allow vertical resizing only */
  height: 100px; /* Default height */
}

.date-picker {
  margin-top: 10px;
}

.error {
  color: red;
  font-size: 0.9rem;
  margin-top: 5px;
}

button[type="submit"] {
  background-color: #007bff;
  color: #fff;
  border: none;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s;
}

button[type="submit"]:hover {
  background-color: #0056b3;
}

@media (max-width:1200px) {
  .adding-contact {
   max-width: 1000px; /* Adjust padding for smaller screens */
  }
}
</style>