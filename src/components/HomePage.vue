<template>
    <div class="home">
              <nav class="nav">
        <ul>
          <li><router-link to="/login">Login</router-link></li>
          <li><router-link to="register">Register</router-link></li>
          <li><router-link to="/about">About Us</router-link></li>
          <li><router-link to="/contact">Contact Us</router-link></li>
          <li><router-link to="/admin">Admin</router-link></li>

        </ul>
        
        <div class="title">
    <h1 class="show">Shri Balaji Bhavan</h1>
    
  </div>

      </nav>
      <div class="carousel">
    <div class="slides-container" :style="{ transform: `translateX(${-currentSlide * 100}%)`, transitionDuration: `${transitionDuration}ms` }">
      <div class="slide" v-for="(image, index) in images" :key="index" :class="{ 'active': index === currentSlide }">
        <img :src="image.src" alt="image.alt" :class="{ 'zoom-in': index === currentSlide }"/>
        <div class="text">{{ image.text }}</div>
      </div>
    </div>
   
  </div>
      
    </div>
  </template>
  
  <script>
export default {
  data() {
    return {
      images: [
      {
        src: require("@/assets/anna-tukhfatullina-food-photographer-stylist-Mzy-OjtCI70-unsplash.jpg"),
        alt: "Slide Image",
        text: "Delicious South Indian cuisine, made fresh daily!",
      },
      {
        src: require("@/assets/joseph-gonzalez-zcUgjyqEwe8-unsplash.jpg"),
        alt: "Slide Image",
        text: "Choose from a wide range of dosas and idlis!",
      },
      {
        src: require("@/assets/element5-digital-acrBf9BlfvE-unsplash.jpg"),
        alt: "Slide Image",
        text: "We offer both vegetarian and non-vegetarian options!",
      },
      {
        src: require("@/assets/kobby-mendez-q54Oxq44MZs-unsplash (1).jpg"),
        alt: "Slide Image",
        text: "We offer both vegetarian and non-vegetarian options!",
      },
      {
        src: require("@/assets/clarissa-carbungco-uy9DJw9e_vs-unsplash.jpg"),
        alt: "Slide Image",
        text: "We offer both vegetarian and non-vegetarian options!",
      }
      ],
      currentSlide: 0,
      transitionDuration: 500, // in milliseconds
      autoAdvanceInterval: 5000, // in milliseconds
      autoAdvanceTimer: null,
    };
  },
  methods: {
    nextSlide() {
  const prevSlide = this.currentSlide;
  this.currentSlide = (this.currentSlide + 1) % this.images.length;
  this.$nextTick(() => {
    this.$el.querySelector(`.slide:nth-child(${prevSlide + 1}) img`).classList.remove('zoom-in');
    this.$el.querySelector(`.slide:nth-child(${this.currentSlide + 1}) img`).classList.add('zoom-in');
  });
},
prevSlide() {
  const prevSlide = this.currentSlide;
  this.currentSlide = (this.currentSlide - 1 + this.images.length) % this.images.length;
  this.$nextTick(() => {
    this.$el.querySelector(`.slide:nth-child(${prevSlide + 1}) img`).classList.remove('zoom-in');
    this.$el.querySelector(`.slide:nth-child(${this.currentSlide + 1}) img`).classList.add('zoom-in');
  });
},

    startAutoAdvance() {
      this.autoAdvanceTimer = setInterval(() => {
        this.nextSlide();
      }, this.autoAdvanceInterval);
    },
    stopAutoAdvance() {
      clearInterval(this.autoAdvanceTimer);
    },
  },
  mounted() {
    this.startAutoAdvance();
  },
  beforeUnmount() {
    this.stopAutoAdvance();
  },
};
</script>
  
  
  <style scoped>
 
  h1{
    font-family: 'Lavishly Yours', cursive;
    font-size: 3rem;
  }
  body, div, ul, li, p {
font-family: "Comic Sans MS";
}
  .home {
    max-width: 1400px;
    margin: 0 auto;
    padding: 2rem;
  }
  

  .nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
}
.title h1 {
  
  margin: 0;
  font-weight: bold;
  
 
}


  
  .nav ul {
    list-style: none;
    margin: 0;
    padding: 0;
    display: flex;
    justify-content: space-between;
  }
  
  .nav li {
    margin: 0 1rem;
  }
  
  .nav a {
    text-decoration: none;
    color: #333;
    font-weight: bold;
    font-size: 1.2rem;
    padding-bottom: 0.5rem;
    border-bottom: 2px solid transparent;
    transition: border-color 0.2s;
  }
  
  .nav a:hover {
    border-color: #ff6d00;
  }
  
  .image-box {
    max-height: 500px;
    overflow: hidden;
    margin-bottom: 2rem;
  }
  
  .carousel img {
    max-width: 100%;
    height: auto;
  }
  
  
  .carousel {
  position: relative;
  width: 100%;
  height: 560px;
  overflow: hidden;
}

.slides-container {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  transition: transform 0.5s ease-out;
}

.slide {
  flex-shrink: 0;
  width: 100%;
  height: 100%;
  position:relative;
}
.slide img.zoom-in {
  animation-name: zoom-in;
  animation-duration: 1.5s;
  animation-fill-mode: both;
}
  
@keyframes zoom-in {
  0% {
    transform: scale(0.5);
  }
  100% {
    transform: scale(1);
  }
}

.text {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  background-color: rgba(0, 0, 0, 0.5);
  color: white;
  padding: 10px;
  font-size: 1.2rem;
  line-height: 1.5;
  transition: opacity 0.5s ease-out;
}

.slide img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}


  </style>
  