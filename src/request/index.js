import axios from 'axios';

const service = axios.create({
    baseURL: 'http://sugarblack.top:8081',
    timeout: 6000,
});
service.interceptors.request.use(config => {
    return config;
}, error => {
    return Promise.reject(error);
});
service.interceptors.response.use(response => {
    return response.data;
}, error => {
    return Promise.reject(error);
});
export default service;
