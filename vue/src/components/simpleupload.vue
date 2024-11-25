<template>
    <div>
      <h3>Simple File Upload Test</h3>
      <form @submit.prevent="uploadFile">
        <input type="file" ref="fileInput" required />
        <button type="submit">Upload File</button>
      </form>
    </div>
  </template>
  
  <script>
  export default {
    methods: {
      async uploadFile() {
        const file = this.$refs.fileInput.files[0];
        if (!file) {
          console.error("No file selected.");
          return;
        }
  
        console.log("Selected file:", file);
  
        // Step 1: Fetch the Presigned URL from the Backend
        const response = await fetch("http://localhost:9000/s3/sign-s3-upload", {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify({
            filename: file.name,
            contentType: file.type,
          }),
        });
  
        if (!response.ok) {
          console.error("Failed to get presigned URL from backend.");
          return;
        }
  
        const data = await response.json();
        console.log("Received presigned URL:", data.url);
  
        // Step 2: Upload the File to S3 Using the Presigned URL
        try {
          const s3Response = await fetch(data.url, {
            method: "PUT",
            headers: {
              "Content-Type": file.type,
              "x-amz-acl": "public-read",
            },
            body: file,
          });
  
          if (s3Response.ok) {
            console.log("File uploaded successfully to S3.");
          } else {
            console.error("Failed to upload file to S3:", s3Response);
          }
        } catch (error) {
          console.error("Error during file upload:", error);
        }
      },
    },
  };
  </script>
  