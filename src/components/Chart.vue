<template>
  <div id="chart-order">
    <Calendar v-model="selectedDates" @click:day="updateChartData" />  
      <canvas ref="chart" width="800" height="800"></canvas>
      </div>
</template>
<script>
import Chart  from 'chart.js/auto'
import axios from 'axios'
import { Calendar } from 'vue-simple-calendar';



export default {
  name:'chart-order',
  components: {
  Calendar,
},

  data() {
    return {
      orders: [],
      chartData: {
        labels: [],
        datasets: [
          {
            backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#008000', '#FF8C00', '#9400D3', '#008080','#09F99E','#FB0058','#00FBF4'],
            data: []
          },
        ]
      },
      chartOptions: {
        responsive: true,
        maintainAspectRatio: true,
        aspectRatio:1,
        legend: {
          position: 'right',
        },
        layout: {
         padding: {
           top: 20,
           bottom: 20,
           left: 20,
           right: 20,
    },
  },
  elements: {
    circumference: 1,
    arc: {
      borderWidth: 0,
      hoverOffset: 20,
      // hoverBackgroundColor: '#FF6384',
      hoverBorderColor: 'white',
      hoverBorderWidth: 2
    }
  },
  plugins: { // Add the 'legend' plugin to display tooltips on hover
    legend: {
      display: true,
      labels: {
        fontColor: 'black',
        fontSize: 14
      }
    },
  }
  
      },
      chart: null,
      selectedDates: [],
      startDate: null,
      endDate: null,
    }
  },
  async mounted() {
    try {
    await this.fetchOrders();
    this.$nextTick(this.renderChart);
  } catch (error) {
    console.error('Error mounting component:', error);
  }
  },

  
  watch: {
    selectedDates() {
      this.startDate = this.selectedDates[0]?.toISOString();
    this.endDate = this.selectedDates[this.selectedDates.length - 1]?.toISOString();
    this.fetchOrders();
    },
    orders: {
      handler() {
        this.updateChartData();
      },
      deep: true,
    },
  },
  methods: {
   async fetchOrders() {
    try {
      const response = await axios.get(`/api/getOrdersData?startDate=${this.selectedDates[0]}&endDate=${this.selectedDates[this.selectedDates.length - 1]}`);
  this.orders = response.data.orders;
  } catch (error) {
    console.error('Error fetching orders:', error);
    this.orders = [];
      }
    },
    updateChartData() {
      if (!Array.isArray(this.orders)) {
        console.warn('Orders data is not an array:', this.orders);
        return;
   
  }
     
      const itemQuantities = new Map();
      for (const order of this.orders) {
        if(order.item){
        if (itemQuantities.has(order.item)) {
          itemQuantities.set(order.item, itemQuantities.get(order.item) + order.quantity);
        } else {
          itemQuantities.set(order.item, order.quantity);
        }
      }
    }
    this.chartData.datasets[0].data = Array.from(itemQuantities.values());
      this.chartData.labels = Array.from(itemQuantities.keys()).map(
        (item, i) => `${item} (${this.chartData.datasets[0].data[i]})`
      );
      this.chartData.datasets[0].backgroundColor = Array.from(
        itemQuantities.keys(),
        (item) => {
          const index = this.chartData.labels.indexOf(
            `${item} (${itemQuantities.get(item)})`
          );
          return this.chartOptions.backgroundColor[
            index % this.chartOptions.backgroundColor.length
          ];
        }
      );
      if (this.chart && this.chart.data.labels.length > 0) {
    this.chart.update();
  }
},
    renderChart() {
      Chart.defaults.defaultFontFamily = "'Helvetica Neue', 'Helvetica', 'Arial', sans-serif";
      Chart.defaults.defaultFontSize = 24;
      if (this.$data.chart) {
  this.$data.chart.data = this.chartData;
  this.$data.chart.update();
} else {
  this.$data.chart = new Chart(this.$refs.chart, {
    type: 'doughnut',
    data: this.chartData,
    options: this.chartOptions,
    plugins: []
  });
}

    },
  }
};
</script>


<style scoped>
#chart-order canvas {
  max-width: 100%;
  max-height: 100%;
}


</style>