<template>
  
  <div class="title">
  <h1>Shri Balaji Bhavan</h1>
  <div class="search-box">
  <input type="text" v-model="searchInput" placeholder="Search items...">

</div>
<nav>
    <ul>
      <li><router-link to="/">Home</router-link></li>
      <li><router-link to="/orders">My Orders</router-link></li>
    </ul>
  </nav>
</div>

  <div id="item-list" >
    <div class="welcome-message" v-if="username">
  Welcome, <span style="color: red">{{ username }}</span>..!
</div>
    <table class="table-design">
      <tr v-for="(row, index) in menuRows" :key="index">
        <td v-for="item in row" :key="item.id">
          <img class="img-design" :src="item.Image">
         <span> {{ item.item_name }}
           Rs. {{ item.price_rupee }}</span>
          <input class="" type="checkbox" :name="'chk'+item.id" :value="item.id" v-model="selectedItems">
        </td>
      </tr>
   </table>
   <div v-if="menuRows.length === 0" class="no-results">No results found</div>
  <button class="checkout-btn" @click="onCheckout">Checkout</button>
  </div>

</template>

<script>
import axios from 'axios';
//import { mapActions,mapGetters } from 'vuex';
export default {
  name: 'item-list',
  
  data() {
    return {
      menuItems: [],
      selectedItems: [],
      username:'',
      searchInput:''
     
    };
  },

mounted() {
  const username = sessionStorage.getItem('username');
    this.username = username;
 
    if(username===null || sessionStorage===null || username===''){
      alert('UnAuthorized Action..! Please Login First')
              this.$router.push('/login');
          
    }
    
  
},

  created() {
    axios.get('api/ItemServlet?action=/listItem').then((response) => {
      const time = new Date().getHours();
      const filteredItems = response.data.filter((item) => {
        if (time >= 6 && time < 12 && item.availability === 'BREAKFAST') {
          return true;
        } else if (time >= 12 && time < 18 && item.availability === 'LUNCH') {
          return true;
        } else if (time >= 18 && time < 24 && item.availability === 'DINNER') {
          return true;
        }
        return false;
      });

      const uniqueIds = new Set();
      filteredItems.forEach((item) => {
        if (!uniqueIds.has(item.id)) {
          uniqueIds.add(item.id);
          this.menuItems.push(item);
        }
      });

      console.log(this.menuItems);
    });
  },
  computed: {

    // ...mapGetters(['getUsername']),
  menuRows() {
    const rows = [];
    let currentRow = [];
    const filteredItems = this.menuItems.filter(item =>
      item.item_name.toLowerCase().includes(this.searchInput.toLowerCase())
    );
    

    filteredItems.forEach((item, index) => {
      currentRow.push(item);

      if ((index + 1) % 4 === 0 || index === filteredItems.length - 1) {
        rows.push(currentRow);
        currentRow = [];
      }
    });

    return rows;
  }
},

  methods: {
    // ...mapActions(['saveCheckoutData']),

    getSelectedItems() {
      const selectedItems = [];
      this.menuItems.forEach((item) => {
        if (this.selectedItems.includes(item.id)) {
          selectedItems.push(item);
        }
      });
      return selectedItems;
    },
    // onCheckout() {
    //   const username = this.getUsername;
    //   const selectedItems = this.getSelectedItems();

    //   if (!username) {
    //     alert('UnAuthorized Action..! Please Login First');
    //     this.$router.push('/login');
    //   } else if (selectedItems.length === 0) {
    //     alert('Please select at least one item to checkout');
    //   } else {
    //     this.saveCheckoutData({ username, selectedItems });
    //     this.$router.push({ name: 'selected-items' });
    //   }
    // }
  
    onCheckout() {
  const selectedItems = this.getSelectedItems();
  sessionStorage.setItem('selectedItems', JSON.stringify(selectedItems));
  sessionStorage.setItem('username', this.username);
  this.$router.push({ name: 'selected-items' });
}
  }
};
</script>

<style scoped>
   div, ul, li, p {
font-family: "Comic Sans MS";
}
h1{
    font-family: 'Lavishly Yours', cursive;
   
  }

.img-design {
  background-color: transparent;
  border-radius: 20px;
  margin: 50px;
  padding: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  width: 240px;
  height:240px;
}

.img-design:hover { 
  background-color: #ffffff98;
  transition: 1s;
} 
/* .container {
  background-image: url("@/assets/oxana-melis-BeU4pUH2quQ-unsplash.jpg");
  background-repeat: no-repeat;
  background-size: cover;
  background-attachment: fixed;
  height: calc(100vh - 80px);
} */
.table-design {
  background-color: transparent;
background-image: url("@/assets/oxana-melis-BeU4pUH2quQ-unsplash.jpg");
  background-repeat: no-repeat;
  background-size: cover;
  background-attachment: fixed;
  border-collapse: separate;
  border-spacing: 0;
  border-radius: 0px;
  margin: 0px;
  padding: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  height: calc(100vh - 80px);
}
.table-design {
    border-collapse: separate;
    border-spacing: 0;
    border-radius: 0px;
    margin: 0px;
    padding: 10px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
    height: calc(100vh - 80px);
  }
.table-design th,
.table-design td {
  padding: 10px;
  text-align: center;
  
}



.table-design td:hover {

  /* transition: 3s;  */
  border: none;
  text-shadow: 2px 2px 4px #010101;
}
.table-design td:hover span {
  color: #ece6e6df;
  font-weight: bold;
}


.checkout-btn {
  background-color: transparent;
  border: 2px solid rgb(0, 0, 0);
  color: rgb(0, 0, 0);
  font-size: 16px;
  display: block;
  margin: 0 auto;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  transition: all 0.3s ease-in-out;
  mix-blend-mode: multiply;
}

.checkout-btn:hover {
  background-color: rgba(188, 173, 13, 0.637);
  color: black;
  mix-blend-mode: normal; 
}


.welcome-message {
  float: left;
  font-weight: bold;
  
  
}
.title {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: rgb(0, 0, 0);
  padding: 10px;
}

.title h1 {
  font-size: 3rem;
  margin: 0;
  text-shadow: 2px 2px 4px #f2ff04ac;
  color: #fefefe;
}

.search-box {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  width: 300px;
  height: 30px;
  background-color: #ffffffd4;
  border-radius: 20px;
  
  
}

.search-box input[type="text"] {
  width: 100%;
  height: 100%;
  padding: 6px;
  font-size: 14px;
  border: none;
  outline: none;
  background-color: transparent;
}

.no-results {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  font-size: 1.5rem;
  color: red;
}


</style>