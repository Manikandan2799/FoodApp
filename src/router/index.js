import RegisterForm from '@/components/RegisterForm'
import LoginForm from '@/components/LoginForm'
import HomePage from '@/components/HomePage'
import AboutUs from '@/components/AboutUs.vue'
import ContactUs from '@/components/ContactUs'
import AdminLogin from '@/components/AdminLogin.vue'
import Items from '@/components/Items'
import SelectedItems from '@/components/SelectedItems'
import Post from '@/components/Post'
import Chart from '@/components/Chart'
import Thank from '@/components/Thank'
import Orders from '@/components/Orders'
import { createRouter,  createWebHistory } from 'vue-router';
const routes=[
{
    path:'/',
    component :HomePage 
},
{
    path:'/contact',
    component :ContactUs 
},
{
    path:'/login',
    component :LoginForm 
},
{
    path:'/register',
    component :RegisterForm 
},
{
    path:'/about',
    component :AboutUs 
},
{
    path:'/admin',
    component :AdminLogin 
},
{
    path:'/item',
    component :Items 
},
{
    path:'/selected-items',
    name: 'selected-items',
    component:SelectedItems,
    
},
{
    path:'/post',
    component :Post 
},
{
    path:'/thank',
    component :Thank 
},
{
    path:'/chart',
    component :Chart 
},
{
    path:'/orders',
    component :Orders 
},


];
const router =createRouter({
   
    history:createWebHistory(process.env.BASE_URL),

    routes

});

export default router;