<template>
    <div class="simple-upload-view">
      <h2>Simple File Upload</h2>
      <SimpleUpload @file-uploaded="handleFileUploaded" />
    </div>
  </template>
  
  <script>
  import SimpleUpload from '@/components/simpleupload.vue'; // Adjust the import path if needed
  
  export default {
    name: 'SimpleUploadView',
    components: {
      SimpleUpload,
    },
    methods: {
      handleFileUploaded(url) {
        console.log('File uploaded successfully. URL:', url);
      },
    },
  };
  </script>
  
  <style scoped>
  .simple-upload-view {
    max-width: 600px;
    margin: 0 auto;
    text-align: center;
  }
  </style>
  