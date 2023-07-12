<template>
  <div id="login-form" class="login-form">
    <h2>Login</h2>
    <form v-if="!showForgotPassword && !showEnterOTP && !showCreateNewPassword" @submit.prevent="SignIn">
      <div class="form-group">
        <label>Username</label>
        <input type="text" v-model="username" />
      </div>
      <div class="form-group">
        <label>Password</label>
        <div class="password-input">
  <input v-model="password" :type="showPassword ? 'text' : 'password'" placeholder="Enter Password" :class="{ 'show-icon': showPassword }" />
  <i :class="['fa', showPassword ? 'fa-eye-slash' : 'fa-eye']" @click="togglePasswordVisibility"></i>
</div>

        <p v-if="statusmessage" class="popup">{{ statusmessage }}</p>
      </div>
      <button id="subbtn" type="submit">Login</button>
      <button id="reset" @click="showForgotPassword = true">Forgot Password?</button>
    </form>

    <form v-if="showForgotPassword && !showEnterOTP && !showCreateNewPassword" @submit.prevent="sendPasswordResetEmail">
      <div class="form-group">
        <label>Enter your email address</label>
        <input type="email" v-model="email" />
      </div>
      <p v-if="statusmessage" class="popup">{{ statusmessage }}</p>

      <button type="submit" >Reset Password</button>
    </form>

    <form v-if="showEnterOTP && !showCreateNewPassword" @submit.prevent="submitOTP">
      <div class="form-group">
        <label>Enter OTP sent to {{ email }}</label>
        <input type="text" v-model="otp" />
      </div>
      <p v-if="statusmessage" class="popup">{{ statusmessage }}</p>

      <button type="submit" >Submit OTP</button>
    </form>

    <form  v-if="showCreateNewPassword" @submit.prevent="createNewPassword">
      <div class="form-group">
        <label>New Password</label>
        <input type="text" v-model="password" />
      </div>
      <div class="form-group">
        <label> Confirm New Password</label>
        <div class="password-input">
          <input v-model="newPassword" :type="showPassword ? 'text' : 'password'" placeholder="New Password" :class="{ 'show-icon': showPassword }" />
          <i class="fa fa-eye" :class="{ 'fa-eye': !showPassword, 'fa fa-eye-slash': showPassword }" @click="togglePasswordVisibility"></i>
        </div>
        <p v-if="statusmessage" class="popup">{{ statusmessage }}</p>

      </div>
      <button type="submit">Create New Password</button>
    </form>
    
  </div>
</template>




<script>
import axios from 'axios';

export default {
  name: 'login-form',
  data() {
    return {
      username: '',
      password: '',
      email:'',
      statusmessage: '',
      showPassword: false,
      showForgotPassword:false,
      showCreateNewPassword:false,
      showEnterOTP: false,
      otp:'',
      newPassword:'',
    };
  },
  methods: {
    SignIn() {
      const data = {
        username: this.username,
        password: this.password,
      };
      const url = '/api/AuthenticationServlet?username=' + data.username + '&password=' + data.password;

      axios
        .get(url, data, {
          headers: {
            'Content-Type': 'application/json',
          },
        })
        .then((response) => {
          if (response.data.status === 'Failure') {
            this.statusmessage = 'Invalid credentials';
            if (this.showForgotPassword) {
              return;
            }
          
            this.showForgotPassword = false;
          
          } else {
            sessionStorage.setItem('username', this.username);
            this.statusmessage = 'Login Successful';
            this.showPopup();
            setTimeout(() => {
              this.$router.push('/item');
            }, 3000);
          }
         this.showPopup();
        })
        .catch((error) => {
      console.log(error);
    })
    
},
showPopup() {
  const popup = document.querySelector('.popup');
  if (popup) {
    popup.classList.add('show');
    setTimeout(() => {
      popup.classList.remove('show');
    }, 3000);
  }
},
    togglePasswordVisibility() {
      this.showPassword = !this.showPassword;
    },
    sendPasswordResetEmail() {
      const emailRegex = /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/;
      if (!emailRegex.test(this.email)) {
      this.statusmessage = 'Please enter a valid email address';
      this.showPopup();
      return;
  }
  const data = { email: this.email,
  };
  
  const url = '/api/ForgotPasswordServlet?email=' + data.email;
  axios
    .get(url)
    .then((response) => {
      if (response.data.status === 'Failure') {
        this.statusmessage = 'Invalid email ID';
        this.showPopup();
      } else {
        this.statusmessage = 'Password reset email sent';
      
      this.showForgotPassword = false;
        this.showEnterOTP = true;
        this.showPopup();
      }
    })
    .catch((error) => {
      console.log(error);
    });
},
submitOTP() {
    axios
      .post('/api/VerifyOtp', {
        email: this.email,
        otp: this.otp,
      }, {
        withCredentials: true, 
      })
      .then((response) => {
        if (response.data.status === 'Success') {
          this.showCreateNewPassword = true;
          this.statusmessage='';
          this.showPopup();
        } else {
          this.statusmessage = response.data.message || 'OTP doesn\'t match. Please try again.';
          this.showPopup();
        }
      })
      .catch((error) => {
        console.log(error);
        this.statusmessage = 'An error occurred while verifying OTP.';
      });
  },


createNewPassword() {
      const data = {
        email: this.email,
        newPassword: this.newPassword,
      };
      const url = '/api/UpdatePassword';

      axios
        .post(url, data, {
          headers: {
            'Content-Type': 'application/json',
          },
        })
        .then((response) => {
          if (response.data.status === 'Failure') {
            this.statusmessage = 'Failed to create new password';
            
          } else {
            localStorage.setItem('username', this.username);
            this.statusmessage = 'New password created Successfully ';
            this.showForgotPassword = false;
            this.showCreateNewPassword = false;
            this.showEnterOTP = false;
          }
          this.showPopup();
        })
        .catch((error) => {
          console.log(error);
          this.statusmessage = 'An error occurred while creating new password.';
        });
    },
  

  },
};
</script>



<style scoped>
body, div, ul, li, p {
font-family: "Comic Sans MS";
}
.login-form {
display: flex;
flex-direction: column;
align-items: center;
padding: 2rem;
border-radius: 10px;
box-shadow: 0px 0px 5px rgba(170, 237, 93, 0.3);
background-image: url("@/assets/brooke-lark-08bOYnH_r_E-unsplash.jpg");
background-repeat: no-repeat;
background-size: cover;
height: 100vh; 
justify-content:center;

 
}

h2 {
margin: 0;
margin-bottom: 1rem;
}

.form-group {
display: flex;
flex-direction: column;
margin-bottom: 1rem;
}

label {
font-weight: bold;
}

input {
padding: 0.5rem;
border: none;
border-radius: 5px;
box-shadow: 0px 0px 3px rgba(0, 0, 0, 0.2);
}

button {
margin-top: 1rem;
padding:7px 5px;
background-color: #e3e2dd;
color: rgb(15, 10, 10);
border: none;
border-radius: 5px;
box-shadow: 0px 0px 3px rgba(232, 237, 226, 0.2);
cursor: pointer;
transition: all 0.2s ease;

}
#reset{
  color:black;
  margin-left:20px;
}
#subbtn{
  width:80px;
}

button:hover {
background-color: #1c1f204c;
}

button:focus {
outline: none;
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
transition: all 0.2s ease;
}

.popup.show {
opacity: 1;
visibility: visible;
}
.password-input {
  position: relative;
}
.password-input .fa-eye {
  position: absolute;
  top: 50%;
  right: 10px;
  transform: translateY(-50%);
  font-size: 1.2em;
  color: #888;
  cursor: pointer;
}
.password-input .fa-eye-slash {
  position: absolute;
  top: 50%;
  right: 10px;
  transform: translateY(-50%);
  font-size: 1.2em;
  color: #888;
  cursor: pointer;
}

.show-icon + .password-input .fa-eye {
  display: block;
}
.show-icon + .password-input .fa-eye-slash{
  display: block;
}
</style>