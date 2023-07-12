<template>
    <div id="my-orders">
        <div class="title">
    <h1 >Shri Balaji Bhavan</h1>
    
  </div>
        <nav>
      <div>
        <span>Logged in as {{ username }}</span>
        <button @click="goBack">Back</button>
      </div>
    </nav>
      <h2>My Orders</h2>
      <table>
        <thead>
          <tr>
            <th>Order ID</th>
            <th>Item</th>
            <th>Quantity</th>
            <th>Total Price</th>
            <th>Ordered Date</th>
          </tr>
        </thead>
        <tbody v-if="orders.length > 0">
          <tr v-for="(order, index) in orders" :key="index">
            <td>{{ order.orderId }}</td>
            <td>{{ order.item }}</td>
            <td>{{ order.quantity }} Nos</td>
            <td>Rs.{{ order.totalPrice }}</td>
            <td>{{ order.orderDate }}</td>
          </tr>
        </tbody>
        <tbody v-else>
        <tr>
          <td colspan="4">No orders found.</td>
        </tr>
      </tbody>
      </table>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    name: 'my-orders',
    data() {
      return {
        orders: [],
        username: sessionStorage.getItem('username') || ''
      };
    },
    mounted() {
  const username = sessionStorage.getItem('username');
  console.log(`username: ${username}`);
  console.log(`Logged in as ${this.username}`);

  axios.get(`api/myOrdersData?username=${username}`)
    .then(response => {
      if (response.data.success) {
        console.log(response.data);
        const orders = response.data.orders.map(order => ({
            orderId: order.orderId,
            item: order.item,
            quantity: order.quantity,
            totalPrice: order.totalPrice,
            orderDate:order.orderDate
        }));
        this.orders = orders;
      }
    })
    .catch(error => {
      console.error(error);
    });
},
methods: {
    goBack() {
      window.history.back();
    }
  }

  };
  </script>
  
  <style scoped>
  
  .title {
    opacity: 1;
  }

  .title h1 {
    color: rgb(0, 0, 0);
    margin: 0;
    font-weight: bold;

  }




  
  table {
    border-collapse: collapse;
    width: 100%;
  }

  th, td {
    text-align: left;
    padding: 8px;
    border-bottom: 1px solid #ddd;
  }

  tr:hover {
    background-color: #f5f5f5;
  }

  th {
    background-color: #4CAF50;
    color: white;
  }


  </style>