<template>
    <div id="thank-form">
      <p class="thank-message">Thank you for using our services!</p>
      <div class="delivery-message">
        <marquee behaviour="scroll" class="blink" direction="right" scrollamount="18">
          Your order will be delivered to you within 10 minutes...
        </marquee>
      </div>
      <p class="rights-message">All rights reserved &copy; 2023</p>
      <button class="rate-app-btn" @click="showRatingPopup">Rate our app</button>
      <div class="rating-popup" v-if="showPopup">
        <h2 class="popup-header">Rate our app</h2>
        <div class="star-rating">
          <span class="star" v-for="n in 5" :key="n" @click="rateApp(n)">
            <i class="fas fa-star" :class="{ 'filled': n <= rating }"></i>
          </span>
        </div>
        <p class="popup-message" v-if="rating > 0">
          Thank you for rating our app {{ rating }} stars!
        </p>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'thank-form',
    data() {
      return {
        showPopup: false,
        rating: 0,
      };
    },
    methods: {
      showRatingPopup() {
        this.showPopup = true;
      },
      rateApp(stars) {
        this.rating = stars;
      },
    },
  };
  </script>
  
  <style scoped>
  #thank-form {
    text-align: center;
    margin-top: 50px;
  }
  
  .thank-message {
    font-size: 24px;
    font-weight: bold;
    margin-bottom: 20px;
  }
  
  .delivery-message {
    margin-bottom: 30px;
  }
  
  .rights-message {
    margin-top: 30px;
  }
  
  .rate-app-btn {
    padding: 10px 20px;
    background-color: #ff6600;
    color: #fff;
    border: none;
    border-radius: 5px;
    margin-top: 30px;
    cursor: pointer;
  }
  
  .rating-popup {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    align-items: center;
    justify-content: center;
  }
  
  .popup-header {
    font-size: 24px;
    font-weight: bold;
    color: #fff;
    margin-bottom: 30px;
  }
  
  .star-rating {
    display: inline-block;
    font-size: 0;
    margin-bottom: 30px;
  }
  
  .star-rating .star {
    display: inline-block;
    position: relative;
    width: 0;
    height: 0;
    margin-right: 5px;
    margin-left: 5px;
    border-right: 10px solid transparent;
    border-bottom: 7px solid #f2b600;
    border-left: 10px solid transparent;
    transform: rotate(35deg);
  }
  
  .star-rating .star:before,
  .star-rating .star:after {
    content: "";
    position: absolute;
    width: 0;
    height: 0;
    border-right: 10px solid transparent;
    border-bottom: 7px solid #f2b600;
    border-left: 10px solid transparent;
    transform: rotate(-35deg);
}

.star-rating .star:before {
  top: -5px;
  left: -12px;
}

.star-rating .star:after {
  top: -5px;
  left: 12px;
}
  </style>  