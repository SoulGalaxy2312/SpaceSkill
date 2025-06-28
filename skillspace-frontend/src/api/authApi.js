// src/api/authApi.js
import axiosInstance from "./axiosInstance";

export const registerUser = async (payload) => {
  const response = await axiosInstance.post("/auth/register", payload);
  return response.data;
};

export const loginUser = async (payload) => {
  const response = await axiosInstance.post("/auth/login", payload);
  return response.data;
};