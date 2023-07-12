<template>
  <div id="selected-items">
    <div class="welcome-message" v-if="username">
  Welcome, <span style="color: red">{{ username }}</span>..!
</div>
    <table class="table-wrapper">
      <thead>
        <tr>
          <th>Item Name</th>
          <th>Price</th>
          <th>Quantity</th>
          <th>Total Price</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in localSelectedItems" :key="item.id">
          <td>{{ item.item_name }}</td>
          <td>Rs. {{ item.price_rupee }}</td>
          <td>
            <div class="quantity">
              <button @click="decrement(item.id)">-</button>
              <input type="text" :id="'quantity_'+item.id" :value="item.quantity" disabled />
              <button @click="increment(item.id)">+</button>
            </div>
          </td>
          <td>
            <div class="total-price">
            <input type="text" :id="'total_'+item.id" :value=" 'Rs.'+getTotalPrice(item)"  disabled class="total-price" />
            <button class="remove-button" @click="removeItem(item.id)">&#x2715;</button>
          </div>
          </td>
        </tr>
      </tbody>
    </table>
    <div class="grand-total">
      <span>Grand Total:</span>
      <input type="text" id="grand_total" :value="'Rs.'+getGrandTotal()" disabled />
    </div>
 <div class="checkout-button-wrapper">
    <button class="proceed-to-checkout" @click="proceedToCheckout"><router-link to="/post">Order Now</router-link></button>
  </div>
    <footer class="footer">
  <p>All rights reserved &copy; 2023</p>
</footer>
  </div>

</template>



<script>
// import { mapGetters } from 'vuex'
import axios from 'axios';
export default {
  name: 'selected-items',
 
  
  data() {
    return {
      localSelectedItems: [],
      username:''
    };
  },
//   computed: {
// ...mapGetters(['getSelectedItems', 'getUsername']),
// },
  
// const username = this.getUsername;
// const selectedItems = this.getSelectedItems;


// if (!username || !selectedItems) {
//   alert('UnAuthorized Action..! Please Login First');
//   this.$router.push('/login');
// } else try {
//   this.localSelectedItems = selectedItems.map((item) => ({
//     ...item,
//     quantity: 1,
//   }));
mounted() {
this.username = sessionStorage.getItem('username');
    const selectedItemsString = sessionStorage.getItem('selectedItems');
    if (!this.username || !selectedItemsString) {
      alert('UnAuthorized Action..! Please Login First');
      this.$router.push('/login');
    } else {
      try {
        const selectedItems = JSON.parse(selectedItemsString);
        this.localSelectedItems = selectedItems.map((item) => ({
          ...item,
          quantity: 1,
        }));
        
} catch (e) {
  console.error(e);
}}},
  methods: {
    increment(itemId) {
      const item = this.localSelectedItems.find(item => item.id === itemId);
      item.quantity++;
      this.calculateGrandTotal();
    },
    removeItem(itemId) {
    const index = this.localSelectedItems.findIndex(item => item.id === itemId);
    if (index !== -1) {
      this.localSelectedItems.splice(index, 1);
      this.calculateGrandTotal();
    }
  },
    decrement(itemId) {
      const item = this.localSelectedItems.find(item => item.id === itemId);
      if (item.quantity > 1) {
        item.quantity--;
        this.calculateGrandTotal();
      }
    },
    getTotalPrice(item) {
      return item.price_rupee * item.quantity;
    },
    getGrandTotal() {
      return this.localSelectedItems.reduce((acc, item) => acc + this.getTotalPrice(item), 0);
    },
    calculateGrandTotal() {
      var grandTotal = this.getGrandTotal();
      document.getElementById("grand_total").value = grandTotal;
    },
    proceedToCheckout() {
      const url = '/api/getOrdersData'; 
      const data = {
    username: this.username,
    selectedItems: this.localSelectedItems
  };
      sessionStorage.setItem('selectedItems', JSON.stringify(this.localSelectedItems));
      axios.post(url, data)
    .then(response => {
      
      console.log(response.data);
    })
    .catch(error => {
    
      console.log(error);
    });

      this.$router.push('/post');
    },

  }
};
</script>


<style scoped>
 body, div, ul, li, p {
font-family: "Comic Sans MS";
}
.total-price button:last-child {
  font-size: 0.8em;
}
.multiply-button {
  color: red;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 10px;
  text-align: left;
  border-bottom: 1px solid #000000;
  border-right: 1px solid #000000;
}

th {
  background-color: #f6bf2999;
  
}
th:last-child, td:last-child {
  border-right: none;
}

.quantity {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 110px;
}

.quantity button {
  background-color: #0a0809;
  color: rgb(255, 255, 255);
  border: none;
  /* cursor: pointer; */
  width: 30px;
  height: 30px;
  font-size: 14px;
}

.quantity input {
  text-align: center;
  width: 40px;
}

.total-price {
  font-weight: bold;
}

.grand-total {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  margin-top: 10px;
  font-weight: bold;
  font-size: 16px;
}
.grand-total span {
  white-space: nowrap;
}

#grand_total {
  flex-shrink: 0;
  background-color: white;
  font-weight: bold;
}
.total-price input {
  border: none;
}


.footer {
  position: absolute;
  bottom: 0;
  width: 100%;

  text-align: center;
  font-size: 14px;
  font-weight: bold;
  color: #000000;
  padding: 10px 20px;

 
}

#selected-items {
  background-image: url("@/assets/hermes-rivera-Ww8eQWjMJWk-unsplash.jpg");
  background-size: cover;
  background-repeat: no-repeat;
  background-attachment: fixed;
  backdrop-filter: blur(10px);
  display: flex;
  flex-direction: column;
   justify-content: center; 
  align-items: center;
  min-height: calc(100vh - 0px);
  padding: 20px 40px;
}
.checkout-button-wrapper button {
  background-color: #1d191a;
  color: #ffffff;
  border: none;
  cursor: pointer;
  padding: 10px 20px;
  font-size: 16px;
  font-weight: bold;
}
.table-wrapper {
  margin-bottom: 20px;
  background-color: rgba(255, 255, 255, 0.8);
  /* padding: 20px;
  border-radius: 10px; */
}
.welcome-message {
  
  font-weight: bold;
  
  
}

</style>
