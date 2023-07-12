<template>
    <div>
      <h2>Contact Us</h2>
      <div class="contact-details">
        <div class="contact-info">
          <i class="fa fa-map-marker"></i>
          <p>NH.45 High Road</p>
          <p>Opposite To State Bank Of India</p>
          <p>Peravurani..</p>
        </div>
        <div class="contact-info">
          <i class="fa fa-envelope-square"></i>
          <p>E-mail : shribalajibhavan@gmail.com</p>
        </div>
        <div class="contact-info">
          <i class="fa fa-phone-square"></i>
          <p>Telephone : 04373-4000 5000</p>
        </div>
      </div>
      
      <div class="map-container">
        <iframe
          src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3875.456375791705!2d79.3941313153061!3d10.341747192630733!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3ba91b854c596d63%3A0x4a4a4f4bdcf14c4c!2sShri%20Balaji%20Bhavan%20Hotel!5e0!3m2!1sen!2sus!4v1645038086547!5m2!1sen!2sus"
          width="100%"
          height="450"
          style="border:0;"
          allowfullscreen=""
          loading="lazy"
        ></iframe>
      </div>
    </div>
  </template>
  <script>
  export default {};
  </script>
  <style scoped>
   body, div, ul, li, p {
font-family: "Comic Sans MS";
}
  .contact-details {
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    margin-bottom: 2rem;
  }
  
  .contact-info {
    text-align: center;
  }
  
  .contact-info i {
    font-size: 2rem;
  }
  
  </style>