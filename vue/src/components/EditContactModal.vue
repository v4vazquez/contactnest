<template>
  <BaseModal @close="closeModal">
    <template #title>Edit Contact</template>
    <template #body>
      <EditContactForm
        :contact="contact"
        @save-contact="submitEdit"
      />
    </template>
  </BaseModal>
</template>

  <script>
  import BaseModal from "@/components/BaseModal.vue";
  import EditContactForm from "@/components/EditContactForm.vue";
  import ContactService from "../services/ContactService";
  
  export default {
    name: "EditContactModal",
    components: { BaseModal, EditContactForm },
    props: {
      contact: {
        type: Object,
        required: true, 
      },
    },
    emits: ["update-contact", "close"],
    methods: {
      closeModal() {
        this.$emit("close"); 
      },
      async submitEdit({ contactId, formData }) {
        if (!contactId || !formData) {
    console.error("Invalid contactId or formData:", contactId, formData);
    return;
  }
      try {
        const response = await ContactService.updateContact(contactId, formData);
        if (response.status === 200) {
          const updatedContact = response.data;
          this.$emit("update-contact", updatedContact); // Emit updated contact
          this.closeModal();
        } else {
          console.error("Failed to update contact:", response.statusText);
        }
      } catch (error) {
        console.error("Error updating contact:", error);
      }
    },
    
  },
};
  </script>
  