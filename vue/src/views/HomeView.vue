<template>
  <div class="home">
    <h2>What started it</h2>
  <p>
    The idea behind it: for people (like myself) who struggle to find gifts for others, forget about important events for someone, or forget a person's birthday. A contact manager to help you with all those features.
  </p>
  
  <h2>Upcoming features:</h2>
  <ul>
    <li>Text message reminders to reach out to the contact if you haven't done so in a while.</li>
    <li>Email notifications when a birthday is coming up, including their likes and dislikes.</li>
    <li>Image uploads via an API feature like Uppy for drag-and-drop functionality.</li>
    <li>A page to restore deleted contacts and permanently delete them.</li>
    <li>Additional user interface improvements for better visuals.</li>
  </ul>
</div>

</template>

<script>
export default {
  components:{
  }
};
</script>
<style>
.info-section {
  font-family: 'Roboto', sans-serif; /* Use a clean, modern font */
  line-height: 1.6; /* Improve readability with generous line spacing */
  color: #333; /* Neutral color for text */
  padding: 20px; /* Add some space around the content */
  background-color: #f9f9f9; /* Light background for contrast */
  border-radius: 8px; /* Slightly rounded corners */
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* Subtle shadow for depth */
}

.info-section h2 {
  font-size: 1.8rem; /* Distinct size for headings */
  color: #007bff; /* Accent color for headings */
  margin-bottom: 10px; /* Space below headings */
}

.info-section p {
  font-size: 1rem; /* Regular text size */
  margin-bottom: 15px; /* Space between paragraphs */
}

.info-section ul {
  margin-top: 10px;
  margin-left: 20px; /* Indent the list */
  list-style-type: disc; /* Use bullet points */
}

.info-section li {
  font-size: 1rem; /* Match text size */
  margin-bottom: 10px; /* Space between list items */
}
</style>