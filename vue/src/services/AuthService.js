import axios from 'axios';

export default {

  login(user) {
    return axios.post('/login', user)
  },

  register(user) {
    return axios.post('/register', user)
  },

  getUserDetails(token){
    return axios.get('/user-details',{
      headers:{
        Authorization:`Bearer ${token}`,
      }
    })
  }

}
