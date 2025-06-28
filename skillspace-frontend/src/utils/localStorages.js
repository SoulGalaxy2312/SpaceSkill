const APP_KEYS = ["jwt", "currentUserId"]; 

export const clearAppStorage = () => {
  APP_KEYS.forEach((key) => localStorage.removeItem(key));
};

export const setAppItem = (key, value) => {
  localStorage.setItem(key, value);
};

export const getAppItem = (key) => {
  return localStorage.getItem(key);
};
