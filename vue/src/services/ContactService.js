import axios from "axios";

const http = axios.create( {
       baseURL: 'https://3.137.41.111:9000'
    // baseURL: 'http://localhost:9000'
})

export default{

    createContact(contact,profilePicture){
        const formData = new FormData();

          // Attach contact fields directly (your backend uses @ModelAttribute Contacts)
    Object.keys(contact).forEach((key) => {
        formData.append(key, contact[key]);
      });
  
      // Attach profile picture if provided
      if (profilePicture) {
        formData.append("profilePicture", profilePicture);
      }
      return axios.post("/contacts/new-contact", formData, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      });
    },
    getContactsForCurrentUser(userId){
        return http.get(`/contacts/contacts-list/${userId}`,{
            headers:{
                Authorization: `Bearer ${localStorage.getItem("token")}`
            }
        });
    },
    updateContact(contactId, formData) {
    
        // Send PUT request
        return http.put(`/contacts/update-contact/${contactId}`, formData, {
          headers: {
            "Content-Type": "multipart/form-data",
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        });
      },
    deleteContact(contactId){
        return http.delete(`/contacts/delete-contact/${contactId}`, {
            headers: {
              Authorization: `Bearer ${localStorage.getItem("token")}`,
             },
        })
    }
}
