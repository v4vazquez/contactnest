import axios from 'axios';

const http = axios.create({
  baseURL: 'https://3.134.64.215:9000' // Set to your EC2 instance's public IP
});

export default {

  login(user) {
    return http.post('/login', user)
  },

  register(user) {
    return http.post('/register', user)
  },

  getUserDetails(token){
    return http.get('/user-details',{
      headers:{
        Authorization:`Bearer ${token}`,
      }
    })
  }

}
