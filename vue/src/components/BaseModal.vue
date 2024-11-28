<template>
    <Teleport to="body">
      <div class="modal" @click.self="closeModal">
        <div class="modal-content">
          <div class="modal-header">
            <slot name="title"></slot>
            <button class="close-button" @click="closeModal">X</button>
          </div>
          <div class="modal-body">
            <slot name="body"></slot>
          </div>
        </div>
      </div>
    </Teleport>
  </template>
  
  <script>
  export default {
    name: "BaseModal",
    emits: ["close"],
    methods: {
      closeModal() {
        this.$emit("close");
      },
    },
  };
  </script>
  
  <style scoped>
  .modal {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 1000;
  }
  .modal-content {
  background: white;
  border-radius: 8px;
  width: 90%;
  max-width: 400px; /* Adjust modal width */
  padding: 10px;
}
  .close-button {
    position: absolute;
    top: 10px;
    right: 10px;
    background: none;
    border: none;
    font-size: 16px;
    cursor: pointer;
  }
  </style>
  