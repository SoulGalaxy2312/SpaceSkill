import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { fetchNotifications } from "../api/notificationApi";
import { LogOut, User, Bell } from "lucide-react";
import { clearAppStorage } from "../utils/localStorages";
import Notification from "./Notification"; // 👉 Import component mới

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

  const handleMarkAsRead = (readId) => {
    setNotifications((prev) =>
      prev.map((n) => (n.id === readId ? { ...n, isRead: true } : n))
    );
  };

  const handleDelete = (deletedId) => {
    setNotifications((prev) => prev.filter((n) => n.id !== deletedId));
  };

  return (
    <div className="absolute top-4 right-6 flex items-center gap-4 z-50">
      <button
        onClick={() => navigate("/profile")}
        className="hover:text-indigo-400 transition"
        title="Profile"
      >
        <User size={24} />
      </button>

      {/* Notifications */}
      <div className="relative">
        <button
          onClick={handleNotificationsClick}
          className="hover:text-yellow-400 transition"
          title="Notifications"
        >
          <Bell size={24} />
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

            {/* Show More */}
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

      {/* Logout */}
      <button
        onClick={handleLogout}
        className="hover:text-red-400 transition"
        title="Logout"
      >
        <LogOut size={24} />
      </button>
    </div>
  );
}
