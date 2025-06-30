import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { fetchNotifications } from "../api/notificationApi";
import { Rocket, LogOut, User, Bell } from "lucide-react";
import { 
  clearAppStorage,
  getAppItem } from "../utils/localStorages";
import Notification from "./Notification";
import { 
  markNotificationAsRead,
  deleteNotification } from "../api/notificationApi";

export default function NavBar() {
  const navigate = useNavigate();
  const [notifications, setNotifications] = useState([]);
  const [showDropdown, setShowDropdown] = useState(false);
  const [page, setPage] = useState(1);
  const [hasNext, setHasNext] = useState(false);

  const handleLogout = () => {
    clearAppStorage();
    navigate("/");
  };

  const handleNotificationsClick = async () => {
    // Nếu đang mở dropdown → đóng lại
    if (showDropdown) {
      setShowDropdown(false);
      return;
    }

    // Nếu đang đóng dropdown → fetch và mở lên
    try {
      const res = await fetchNotifications(1); // reset page = 1
      setNotifications(res.content || []);
      setHasNext(res.hasNext);
      setPage(1);
      setShowDropdown(true);
    } catch (err) {
      console.error("Failed to fetch notifications:", err);
    }
  };

  const loadMoreNotifications = async () => {
    try {
      const nextPage = page + 1;
      const res = await fetchNotifications(nextPage);
      setNotifications((prev) => [...prev, ...(res.content || [])]);
      setHasNext(res.hasNext);
      setPage(nextPage);
    } catch (err) {
      console.error("Failed to load more notifications:", err);
    }
  };

  const handleMarkAsRead = async (id) => {
    try {
      await markNotificationAsRead(id);
      setNotifications((prev) =>
        prev.map((n) => (n.id === id ? {...n, isRead: true} : n))
      );
    } catch (error) {
      console.error("Failed to mark notification as read", err);
    }
  };

  const handleDelete = async (id) => {
    try {
      await deleteNotification(id);
      setNotifications((prev) => prev.filter((n) => n.id !== id));
    } catch (error) {
      console.error("Failed to delete notification", err);
    }
    
  };

  const handleNavigateProfile = () => {
    const role = getAppItem("role")
    const id = getAppItem("currentUserId")

    const path = role === "COMPANY" 
    ? `/companies/${id}/profile`
    : `/users/${id}/profile`

    console.log(`Navigate to ${path}`)
    navigate(path);
  }

  return (
    <>
      <div className="fixed top-0 h-16 left-0 w-full flex items-center justify-between px-6 z-50 bg-gray-900 bg-opacity-95">
        {/* Logo at far left */}
        <button
          onClick={() => navigate("/home")}
          className="flex items-center gap-2 px-2 py-1 rounded-lg bg-gradient-to-r from-indigo-500 via-purple-500 to-pink-500 hover:from-indigo-600 hover:to-pink-600 transition shadow-lg"
          title="Go to Home"
        >
          <Rocket size={16} className="text-white drop-shadow" />
          <span className="font-extrabold text-sm text-white tracking-wide drop-shadow">SkillSpace</span>
        </button>

        {/* Icons at far right */}
        <div className="flex items-center gap-4">
          <button
            onClick={() => handleNavigateProfile()}
            className="hover:text-indigo-400 transition"
            title="Profile"
          >
            <User size={24} className="text-white"/>
          </button>

          <div className="relative">
            <button
              onClick={handleNotificationsClick}
              className="hover:text-yellow-400 transition"
              title="Notifications"
            >
              <Bell size={24} className="text-white"/>
            </button>
            {/* Dropdown */}
            {showDropdown && (
              <div className="absolute right-0 mt-2 w-80 bg-gray-800 border border-gray-700 rounded-lg shadow-lg z-50">
                <div className="p-3 text-sm font-semibold text-indigo-400">
                  Notifications
                </div>
                <ul className="max-h-64 overflow-y-auto divide-y divide-gray-700">
                  {notifications.length > 0 ? (
                    notifications.map((notif) => (
                      <Notification
                        key={notif.id}
                        data={notif}
                        onRead={handleMarkAsRead}
                        onDelete={handleDelete}
                      />
                    ))
                  ) : (
                    <li className="px-4 py-2 text-sm text-gray-400">
                      No notifications
                    </li>
                  )}
                </ul>
                {hasNext && (
                  <button
                    onClick={loadMoreNotifications}
                    className="w-full text-center text-indigo-400 hover:text-indigo-300 text-sm py-2 border-t border-gray-700"
                  >
                    Show More
                  </button>
                )}
              </div>
            )}
          </div>

          <button
            onClick={handleLogout}
            className="hover:text-red-400 transition"
            title="Logout"
          >
            <LogOut size={24} className="text-white" />
          </button>
        </div>
      </div>
      {/* Spacer to push content below the NavBar */}
      <div className="h-16" />
    </>
  );
}
