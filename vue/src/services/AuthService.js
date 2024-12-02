import axios from 'axios';

const http = axios.create({
  baseURL: 'http://3.137.41.111:9000' // Set to your EC2 instance's public IP
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
