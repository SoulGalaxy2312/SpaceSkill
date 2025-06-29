import axiosInstance from "./axiosInstance";

export const fetchNotifications = async (page = 1) => {
    const jwt = localStorage.getItem("jwt");
    if (!jwt) throw new Error("JWT is missing");

    const response = await axiosInstance.get(`/notifications?page=${page}`, {
        headers: { Authorization: `Bearer ${jwt}` },
    });

    console.log(response.data)
    return response.data;
};

export const markNotificationAsRead = async (id) => {
    const jwt = localStorage.getItem("jwt");
    if (!jwt) throw new Error("JWT is missing");

    await axiosInstance.post(`/notifications/${id}/mark-read`, null, {
        headers: { Authorization: `Bearer ${jwt}` },
    });
};

export const deleteNotification = async (id) => {
    const jwt = localStorage.getItem("jwt");
    if (!jwt) throw new Error("JWT is missing");

    await axiosInstance.delete(`/notifications/${id}`, {
        headers: { Authorization: `Bearer ${jwt}` },
    });
};


