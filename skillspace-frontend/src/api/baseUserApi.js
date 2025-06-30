import axiosInstance from "./axiosInstance";

export const editProfile = async (body) => {
    const jwt = localStorage.getItem("jwt");
    if (!jwt) throw new Error("JWT is missing");

    const res = await axiosInstance.patch(`/base-users/profile`, body, {
        headers: { Authorization: `Bearer ${jwt}` },
    });
    
    return res;
};