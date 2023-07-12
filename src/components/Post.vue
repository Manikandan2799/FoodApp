<template>
  <div id="post-form" class="container">
    <div class="existing-address">
      <h3>Address</h3>
      <input type="radio" v-model="selectedAddress" value="existing">
      <label>Existing Address</label>
      <div v-if="selectedAddress === 'existing'">
        <div v-for="(address) in existingAddresses" :key="address.id">
          <input type="radio" v-model="selectedAddressId" :value="address.id">
          <label>{{ address.name }}</label>
          <br>
          {{ address.address }}<br>
          {{ address.city }}, {{ address.zip }}
          <br><br>
        </div>
      </div>
      <input type="radio" v-model="selectedAddress" value="addNew">
      <label>Add New Address</label>
    </div>

    <div v-if="selectedAddress === 'addNew' || (!selectedAddress && existingAddresses.length === 0)" class="new-address">
      <h2>Add New Address</h2>
      <form>
        <div class="form-group">
          <label for="name">Name:</label>
          <input type="text" id="name" v-model="name">
        </div>
        <div class="form-group">
          <label for="city">City:</label>
          <select id="city" v-model="city">
            <option value="">Select a city</option>
            <option v-for="(city, index) in cities" :value="city" :key="index">{{ city }}</option>
          </select>
        </div>
        <div class="form-group">
          <label for="address">Address:</label>
          <input type="text" id="address" v-model="address">
        </div>
        <div class="form-group">
          <label for="zip">Zip Code:</label>
          <input type="text" id="zip" v-model="zip">
        </div>
        <button type="button" @click="addAddress">Save Address</button>
      </form>
    </div>
  </div>
</template>




<script>
import axios from 'axios';

export default {
  name:'post-form',
  data() {
    return {
      selectedAddress: '',
      selectedAddressId: '',
      name: '',
      city: '',
      address: '',
      zip: '',
      existingAddresses: [],
      cities: [
        'New York',
        'Los Angeles',
        'Chicago'
      ]
    }
  },
  mounted() {
  const username = sessionStorage.getItem('username');
  axios.get(`/api/AddressServlet?username=${username}`)
    .then(response => {
      this.existingAddresses = response.data;
    })
    .catch(error => console.error(error));
},

  watch: {
    selectedAddress(value) {
      if (value === 'addNew') {
        this.showForm = true;
      } else if(value === 'existing') {
        this.showForm = false;
        this.selectedAddressId = '';
      }
    }
  },
  methods: {
    addAddress() {
      const username = sessionStorage.getItem('username');
  const newAddress = {
    name: this.name,
    city: this.city,
    address: this.address,
    zip: this.zip,
    username:username
  };
  axios.post('/api/address', newAddress)
    .then(response => {
      this.existingAddresses.push(response.data);
      this.name = '';
      this.city = '';
      this.address = '';
      this.zip = '';
      this.selectedAddress = response.data.id;
      this.selectedAddress = 'existing';
    })
  .catch(error => console.error(error));
}

  }
}
</script>

<style>
 body, div, ul, li, p {
font-family: "Comic Sans MS";
}
.container {
  max-width: 960px;
  margin: 0 auto;
  padding: 1rem;
}

.cart-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.cart-form {
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-gap: 2rem;
}

.existing-address {
  border: 1px solid #ddd;
  padding: 1rem;
}

.new-address {
  border: 1px solid #ddd;
  padding: 1rem;
}

.form-group {
  margin-bottom: 1rem;
}

label {
  display: block;
  margin-bottom: 0.5rem;
}

input[type="text"],
select {
  width: 100%;
  padding: 0.5rem;
  border-radius: 4px;
  border: 1px solid #ddd;
}

button {
  background-color: #007bff;
  color: #fff;
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #0069d9;
}
</style>
