<template>

    <div class="footer">
      <div class="footer-content">
        <div class="icons">
            <a href="https://www.linkedin.com/in/v4vazquez/" class="icon icon--linkedin">
            <i class="fab fa-linkedin"></i>
         </a>
            <a href="https://github.com/v4vazquez" class="icon icon--github" > 
            <i class="fab fa-github"></i>
          </a>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {

  };
  </script>
  
  <style scoped>
  
  body, html {
    height: 100%;
    margin: 0;
    padding: 0;
  }
  
  .footer {
  
    bottom: 0;
    left: 0;
    right: 0;
    height: 25px;
    background-color: rgb(81, 149, 236);
    color: #ffffff;
    padding: 10px 0;
    text-align: center;
  }
  .footer-content {
  display: flex;
  justify-content: right;
  align-items: right;
  margin: 0 auto;
  padding: 0 20px;
}
.icons {
	display: flex;
	column-gap: 25px;
    align-items: center;
}
.icon {
	display: flex;
    justify-content: center;
    align-items: center;
	width: 30px;
	height: 30px;
	text-decoration: none;
	font-size: 26px;
	color: #fff;
	border-radius: 50%;
	outline: 2px solid #fff;
	transition-property: outline-offset, outline-color, background-color;
	transition-duration: 0.25s;
 
}
.icon:hover {
	outline-offset: 4px;
}
.icon i {
	margin: auto;
}
.icon:hover i {
	animation: shake 0.25s;
}

@keyframes shake {
	10% {
		transform: rotate(15deg);
	}
	20% {
		transform: rotate(-15deg);
	}
	30% {
		transform: rotate(15deg);
	}
	40% {
		transform: rotate(-15deg);
	}
}
  </style>