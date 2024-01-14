import axios from "axios";

const service = axios.create({
    baseURL: "http://localhost:8081",
    timeout: 5000,
});

service.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token')
        if (token) {
            config.headers.Authorization = token
        }
        return config;
    },
    error => Promise.reject(error)
);


service.interceptors.response.use(
    response => response,
    error => Promise.reject(error)
);

export default service;
