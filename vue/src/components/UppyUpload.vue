<template>
    <div>
      <Dashboard
        v-if="uppy"
        :uppy="uppy"
        inline
        width="100%"
        height="300px"
        note="Images only, up to 2 MB"
      />
    </div>
  </template>
  
  <script>
  import {Dashboard} from '@uppy/vue';
  import Uppy from '@uppy/core';
  import AwsS3 from '@uppy/aws-s3';
  
  import '@uppy/core/dist/style.css';
  import '@uppy/dashboard/dist/style.css';
  
  export default {
    name: 'UppyUpload',
    components:{
        Dashboard,
    },
    data() {
      return {
        uppy: null,
      };
    },
    mounted() {
        this.uppy = new Uppy({
      autoProceed: true,
    }).use(AwsS3, {
      async getUploadParameters(file) {
        console.log('Requesting presigned URL for:', file.name, file.type);

        // Request presigned URL from the backend
        const response = await fetch('http://localhost:9000/s3/sign-s3-upload', {
          
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${localStorage.getItem('jwt')}`,
          },
          body: JSON.stringify({
            filename: file.name,
            contentType: file.type,
          }),
        });
        console.log('Presigned URL response:', response);
            if (!response.ok) {
              throw new Error('Could not get presigned URL');
            }
  
            const data = await response.json();
            console.log('Presigned URL response:', data);
            return {
              method: 'PUT',
              url: data.url,
              headers: {
                'Content-Type': file.type,
                'x-amz-acl': 'public-read',
              },
            };
          },
        });
  
      // Handle successful upload and emit the URL
      this.uppy.on('upload-success', (file, response) => {
        console.log('File uploaded:', response.uploadURL);
        this.$emit('file-uploaded', response.uploadURL);
      });
  
      // Handle errors
      this.uppy.on('error', (error) => {
        console.error('Uppy error:', error);
      });
    },
    beforeUnmount() {
      if (this.uppy) this.uppy.close();
    },
  };
  </script>
  
  <style scoped>
  .uppy-Dashboard {
    border: 2px dashed #007bff;
    padding: 10px;
  }
  </style>
  