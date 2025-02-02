import axios from "axios";

const API_URL = "http://localhost:8080/api/auth";

export const register = async (userData) => {
    return await axios.post(`${API_URL}/register`, userData);
};

export const login = async (credentials) => {
    const response = await axios.post(`${API_URL}/login`, credentials);
    if (response.data.token) {
        localStorage.setItem("token", response.data.token);
    }
    return response.data;
};

export const logout = () => {
    localStorage.removeItem("token");
};
