<template>
    <div>
      <div class="login-form" v-if="!isLogged">
        <h2>Admin Login</h2>
        <form @submit.prevent="SignIn">
          <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" id="username" v-model="username" />
          </div>
          <div class="password-input">
  <input v-model="password" :type="showPassword ? 'text' : 'password'" placeholder="Enter Password" :class="{ 'show-icon': showPassword }" />
  <i :class="['fa', showPassword ? 'fa-eye-slash' : 'fa-eye']" @click="togglePasswordVisibility"></i>
</div>
          <p v-if="statusmessage" class="popup">{{ statusmessage }}</p>      
          <div >
            <button id="subbtn" type="submit"  >Login</button>
          </div>
        </form>
      </div>
      <div  v-else>
       <ul>
          <li><router-link to="/chart">Chart</router-link></li></ul>
        <button type="button" @click="showAddForm">Add Item</button>
  <table>
    <thead>
      <tr>
        <th>ID</th>
        <th>Image</th>
        <th>Name</th>
        <th>Category</th>
        <th>Price</th>
        <th>Availability</th>
        <th>Actions</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(item,id) in items" :key="id">
        <td>{{ item.id }}</td>
        <td :class="'small-image'"><img :src="item.Image" /></td>        <td>{{ item.item_name }}</td>
        <td>{{ item.category }}<span v-if="item.category === 'Veg'">🟢</span>
      <span v-else-if="item.category === 'Non-Veg'">🔴</span></td>
        <td>Rs. {{ item.price_rupee }}</td>
        <td>{{ item.availability }}</td>
        <td>
          <button @click="editItem(item.id)">Edit</button>
          <button @click="deleteItem(item.id)">Delete</button>
        </td>
      </tr>
    </tbody>                                            
      <modal v-if="isEditing" >
        <h2>Edit Item</h2>
  <form @submit.prevent="saveItem">
    <div class="form-group">
      <label for="name">Name:</label>
      <input type="text" id="name" v-model="editingItemData.item_name" />
    </div>
    <div class="form-group">
      <label for="category">Category:</label>
      <input type="text" id="category" v-model="editingItemData.category" />
    </div>
    <div class="form-group">
      <label for="price">Price:</label>
      <input type="text" id="price" v-model="editingItemData.price_rupee" />
    </div>
    <div class="form-group">
      <label for="availability">Availability:</label>
      <input type="text" id="availability" v-model="editingItemData.availability" />
    </div>
    <button type="submit" @submit="saveItem">Save</button>
    <button type="button" @click="cancelEdit">Cancel</button>
  </form>
</modal>
  </table>
</div>
    </div>
      <modal v-if="showingAddForm">
  <h2>Add New Item</h2>
  <form >
    <div class="form-group">
      <label for="id">Id:</label>
      <input type="text" id="id" v-model="newItemData.id" />
    </div>
    <div class="form-group">
      <label for="name">Name:</label>
      <input type="text" id="name" v-model="newItemData.name" />
    </div>
    <div class="form-group">
      <label for="category">Category:</label>
      <input type="text" id="category" v-model="newItemData.category" />
    </div>
    <div class="form-group">
      <label for="price">Price:</label>
      <input type="text" id="price" v-model="newItemData.price" />
    </div>
    <div class="form-group">
      <label for="availability">Availability:</label>
      <input type="text" id="availability" v-model="newItemData.availability" />
    </div>
    <div class="form-group">
      <label for="availability">Image</label>
      <input type="text" id="Image" v-model="newItemData.Image" />
    </div>
    <button type="button" @click="addItem">Save</button>
    <button type="button" @click="cancelAdd">Cancel</button>
  </form>
</modal>
  </template>
  
  
  <script>
  import axios from 'axios';
  export default {
  
    data() {
      return {
        username: '',
        password: '',
        showPassword: false,
        isLoggedIn: false,
        items: [],
        statusmessage: '',
        editingItemId: null,
    editingItemData: {},
    isEditing: false,
    showingAddForm:false,
    newItemData: {id: null,name:'', category: '', price: null, availability: '',Image:''},
    };
  },
    computed: {
  isLogged() {
    return this.isLoggedIn;
  },
},
    methods: {
      SignIn() {
        const data = {
          username: this.username,
          password: this.password,
        };
        const url = '/api/AdminServlet?username=' + data.username + '&password=' + data.password;
        axios
          .get(url, data, {
            headers: {
              'Content-Type': 'application/json',
            },
          })
          .then((response) => {
            if (response.data.status === 'Failure') {
              this.statusmessage = 'Invalid credentials';
            } else {
              this.statusmessage = 'Login successful';
              this.isLoggedIn = true;
              this.showItems();
            }
            this.showPopup();
          })
          .catch((error) => {
            console.log(error);
          });
      },
      togglePasswordVisibility() {
      this.showPassword = !this.showPassword;
    },
      showPopup() {
      const popup = document.querySelector('.popup');
      if (popup) {
       popup.classList.add('show');
       setTimeout(() => {
       popup.classList.remove('show');
       this.statusmessage = '';
    }, 3000);
  }
},
cancelEdit() {
  this.isEditing = false;
},
showAddForm() {
  this.showingAddForm = true;
},
cancelAdd() {
  this.showingAddForm = false;
},
editItem(itemId) {
  const item = this.items.find(item => item.id === itemId);
  this.editingItemId = itemId;
  this.editingItemData = { ...item };
  this.isEditing = true;
},
saveItem() {
  const url = '/api/ItemUpdate';
  const data = { ...this.editingItemData };
  data.action = 'updateItem';
  data.id = this.editingItemId;
  axios.post(url, data)
    .then(() => {
      this.isEditing = false;
      this.showItems();
    })
    .catch(error => {
      console.error(error);
      this.statusmessage = 'Error saving item';
    });
},
addItem() {
  const url = '/api/AddNewItem';
  const data = {
  id: this.newItemData.id,
  name: this.newItemData.name,
  category: this.newItemData.category,
  price: this.newItemData.price,
  availability: this.newItemData.availability,
  Image: this.newItemData.Image,
};
  axios.post(url, data)
    .then(() => {
      this.newItemData = { id: null,name:'', category: '', price: null, availability: '',Image:'' };
      this.showItems();
      console.log(this.newItemData);
    })
    .catch(error => {
      console.error(error);
      this.statusmessage = 'Error adding item';
    });
},
mounted() {
  this.showItems();
},
showItems() {
  console.log('showItems() called');
  axios.get('api/ItemServlet?action=/listItem')
    .then(response => {
      
        this.items = response.data;
        this.newItemData = {};
        this.statusmessage = 'Loaded successfully';
        
        console.log(response.data);
     })
     .catch(error => {
       console.error(error);
       this.statusmessage = 'Error loading items';        
     });
    }
    },
  };

</script>


<style scoped>


body, div, ul, li, p {
  font-family: "Comic Sans MS";
}
.small-image img {
    width: 50px;
    height: 50px;
  }


.login-form {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 2rem;
  border-radius: 10px;
  box-shadow: 0px 0px 5px rgba(170, 237, 93, 0.3);
  background-image: url("@/assets/brooke-lark-29h2n639YDA-unsplash.jpg");
  background-repeat: no-repeat;
  background-size: cover;
  height: 100vh; 
  justify-content: center;
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
  padding: 7px 5px;
  background-color: #e3e2dd;
  color: rgb(15, 10, 10);
  border: none;
  border-radius: 5px;
  box-shadow: 0px 0px 3px rgba(232, 237, 226, 0.2);
  cursor: pointer;
  transition: all 0.2s ease;
}

#reset {
  color: black;
  margin-left: 20px;
}

#subbtn {
  width: 80px;
}

button:hover {
  background-color: #1c1f204c;
}

button:focus {
  outline: none;
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



  table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 20px;
  }
  
  th, td {
    border: 1px solid #100f0f;
    padding: 8px;
    text-align: left;
  }
  
  th {
    background-color: #fff5f5;
    color: #e91e63;
  font-weight: bold;
  text-transform: uppercase;
  letter-spacing: 1px;

  }
  td {
    background-color: #fff5f52b;
  color: #333;
  font-weight: bold;  }
  
  button {
    background-color: #171416;
    color: white;
    border: none;
    border-radius: 4px;
    padding: 8px 16px;
    margin-right: 5px;
    cursor: pointer;
  }
  
  button:hover {
    background-color: #505350ee;
  }



  </style>
  