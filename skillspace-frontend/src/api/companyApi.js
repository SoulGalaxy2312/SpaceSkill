import axiosInstance from "./axiosInstance";
import { getAppItem } from "../utils/localStorages";

export const fetchCompanyProfile = async (id) => {
    const jwt = getAppItem("jwt");
    const res = await axiosInstance.get(`/companies/${id}/profile`, {
        headers: { Authorization: `Bearer ${jwt}` },
    });
    return res;
};