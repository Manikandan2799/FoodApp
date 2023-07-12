<template>
    <div class="register-page">
      <div class ="register-box">
      <h1>Register</h1>
      <form @submit.prevent="SignUp">
        <div class="form-group">
        <label for="firstname">First Name</label>
        <input type="text"  v-model="firstname">
        </div>
        <div class="form-group">
        <label for="lastname">Last Name</label>
        <input type="text"  v-model="lastname">
      </div>
      <div class="form-group">
        <label for="email">Email</label>
        <input type="email"  v-model="email">
      </div>
      <div class="form-group">
        <label for="username">Username</label>
        <input type="text"  v-model="username">
      </div>
      <div class="form-group">
        <label for="password">Password</label>
        <input type="password" v-model="password">
        <p v-if="statusmessage" class="popup">{{ statusmessage }}</p>
      </div>
        <button type="submit">Register</button>
      </form>
      </div>
    </div>
</template>
<script>
import axios from 'axios';
  
  export default {
    data() {
      return {
        firstname:'',
        lastname:'',
        email: '',
        username: '',
        password: '',
        statusmessage:''
      };
    },
    methods: {
      SignUp() {
  const url = '/api/AuthenticationServlet'; 
  const data = {
    firstname:this.firstname,
    lastname:this.lastname,
    email: this.email,
    username: this.username,
    password: this.password
  };
  axios.post(url, data, {
    headers: {
      'Content-Type': 'application/json'
    }
  })
  .then(response => {
    if(response.data.status==='Failure') {
      this.statusmessage = 'Invalid credentials'; 
    }
    else{
      this.statusmessage = 'Successfully Registered'; 
      this.showPopup(); 
      setTimeout(() => {
        this.$router.push('/login');
      }, 3000); 
    }
  })
  .catch(error => {
    console.log(error);
  });
},

      showPopup() {
     const popup = document.querySelector('.popup');
popup.classList.add('show');
setTimeout(() => popup.classList.remove('show'), 3000);
}
    }
  };
</script>

<style scoped>
 body, div, ul, li, p {
font-family: "Comic Sans MS";
}
.register-page {
  height: 100vh;
  background-size: cover;
  display: flex;
  justify-content: center;
  align-items: center;
  background-image: url("@/assets/katherine-chase-4MMK78S7eyk-unsplash.jpg");

}

.register-box {
  background-color: rgba(255, 255, 255, 0);
  padding: 60px;
  border-radius: 50px;
  box-shadow: 0px 0px 10px rgba(255, 151, 151, 0.1);
  max-width: 800px;
}

h2 {
  text-align: center;
  margin-bottom: 20px;
}

form {
  display: flex;
  flex-direction: column;
}

.form-group {
  display: flex;
  flex-direction: column;
  margin-bottom: 20px;
}
.form-group label {
  color: #333;
}
.form-group input {
  font-size: 16px;
  border-radius: 10px;
    border: 1px solid #43433c;
    font-size: 16px;
}


button {
  padding: 10px;
  background-color: #000000;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
}
.popup {
position: fixed;
bottom: 2rem;
left: 50%;
transform: translateX(-50%);
padding: 1rem;
border-radius: 5px;
background-color: #333;
color: #fff;
font-size: 1.2rem;
opacity: 0;
visibility: hidden;
transition: all 0.3s ease;
}

.popup.show {
opacity: 1;
visibility: visible;
}
</style>