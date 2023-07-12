<template>
        <div id="about-us" class="login-form">
          <h1>Shri Balaji Bhavan 🍽️</h1>
          <h><u> About Us </u></h>
      
           
        
          
          <div class="description">
        
              <p>Welcome to our restaurant! We are a family-owned business that has been serving delicious meals to our community for over 20 years. ❤️ Our menu features a wide variety of dishes, from classic comfort food to innovative modern cuisine.</p>
              <p>Our chefs are passionate about using fresh, locally-sourced ingredients to create dishes that are both flavourful and healthy. We believe in supporting local farmers and producers, and are proud to feature many locally-grown items on our menu.</p>
              <p>In addition to our dining room, we also offer catering services for events of all sizes. Whether you're planning a wedding, corporate event, or backyard barbecue, we can provide the perfect meal to suit your needs.</p>
              <p>Thank you for choosing our restaurant. We look forward to serving you and your loved ones soon!</p>
            
          </div>
          
          <div class="footer">
            <h5 class="">All rights Reserved 2023..!</h5>
          </div>
        </div>
      </template>
      
      <script>
      export default {
        
      }
      </script>
      
      <style scoped>
      body, div, ul, li, p {
font-family: "Comic Sans MS";
}
h1{
    font-family: 'Lavishly Yours', cursive;
    font-size: 4rem;
    font-weight: bold;
  }
    
      .login-form {
display: flex;
flex-direction: column;
align-items: center;
background-image: url("@/assets/elena-koycheva-PFzy4N0_R3M-unsplash.jpg");
background-repeat: no-repeat;
background-size: cover;
height: 100vh; 
background-position: center;
justify-content:center;

 
}
      
#about-us {
  text-align: center;
  padding: 50px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
}

.description {
  margin-top: 50px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  flex-grow: 1;
}

      
      
      h1 {
        font-size: 3rem;
        margin-top: 10px;
      }
      
      p {
        font-size: 1.5rem;
        line-height: 2rem;
        margin-bottom: 10px;
      }
      
      .footer {
  position: absolute;
  left: 0;
  bottom: 0;
  width: 100%; 
  background-color:transparent;
  color: rgb(9, 8, 8);
  padding: 10px 20px;
}

      </style>
      