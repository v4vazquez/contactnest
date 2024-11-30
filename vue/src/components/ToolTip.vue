<template>
    <div class="tooltip-wrapper">
      <div
        class="tooltip-icon"
        @mouseover="showTooltip = true"
        @mouseleave="showTooltip = false"
      >
        i
      </div>
      <div v-if="showTooltip" class="tooltip">
        {{ content }}
      </div>
    </div>
  </template>
  
  <script>
  export default {
    props: {
      content: {
        type: String,
        required: true,
      },
    },
    data() {
      return {
        showTooltip: false, // Controls visibility of the tooltip
      };
    },
  };
  </script>
  
  <style scoped>
  .tooltip-wrapper {
    position: relative;
    display: inline-block;
  }
  
  .tooltip-icon {
    display: inline-flex;
    justify-content: center;
    align-items: center;
    width: 12px;
    height: 12px;
    font-size: 0.9rem;
    font-weight: bold;
    color: white;
    background-color: #007bff;
    border-radius: 50%;
    cursor: pointer;
    user-select: none; /* Prevent text selection */
  }
  
  .tooltip {
    position: absolute;
    bottom: 120%;
    left: 50%;
    transform: translateX(-50%);
    background-color: #333;
    color: #fff;
    padding: 5px 10px;
    border-radius: 5px;
    font-size: 0.8rem;
    white-space: nowrap;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    z-index: 1000;
    opacity: 0;
    visibility: hidden;
    transition: opacity 0.2s ease, visibility 0.2s ease;
  }
  
  .tooltip-wrapper:hover .tooltip {
    opacity: 1;
    visibility: visible;
  }
  
  .tooltip::after {
    content: "";
    position: absolute;
    top: 100%;
    left: 50%;
    transform: translateX(-50%);
    border-width: 5px;
    border-style: solid;
    border-color: #333 transparent transparent transparent;
  }
  </style>
  