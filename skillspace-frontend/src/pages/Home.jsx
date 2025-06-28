import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { LogOut, User } from "lucide-react"; // dùng icon đẹp từ lucide-react
import { clearAppStorage } from "../utils/localStorages";

export default function Home() {
  const navigate = useNavigate();

  useEffect(() => {
    const jwt = localStorage.getItem("jwt");
    if (!jwt) {
      navigate("/login");
    }
  }, [navigate]);

  const [searchType, setSearchType] = useState("user");
  const [query, setQuery] = useState("");
  const [location, setLocation] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!query.trim()) return;

    let url = `/search-results?type=${searchType}&query=${encodeURIComponent(query)}&page=1`;
    if (searchType === "job" && location.trim()) {
      url += `&location=${encodeURIComponent(location)}`;
    }

    navigate(url);
  };

  const handleLogout = () => {
    clearAppStorage();
    navigate("/");
  };

  return (
    <div className="min-h-screen bg-gray-900 text-white relative">
      {/* 🔝 Navigation Icons */}
      <div className="absolute top-4 right-6 flex items-center gap-4">
        <button
          onClick={() => navigate("/profile")}
          className="hover:text-indigo-400 transition"
          title="Profile"
        >
          <User size={24} />
        </button>
        <button
          onClick={handleLogout}
          className="hover:text-red-400 transition"
          title="Logout"
        >
          <LogOut size={24} />
        </button>
      </div>

      {/* 🔍 Main Search Section */}
      <div className="flex items-center justify-center px-4 min-h-screen">
        <div className="w-full max-w-2xl space-y-8 text-center">
          <h1 className="text-4xl md:text-5xl font-bold text-indigo-400 mb-2">Welcome to SkillSpace</h1>
          <p className="text-gray-400 text-lg">
            Find jobs, connect with companies and professionals in tech
          </p>

          <form onSubmit={handleSubmit} className="flex flex-col gap-4 mt-8">
            <div className="flex flex-col md:flex-row items-center gap-4">
              <select
                value={searchType}
                onChange={(e) => setSearchType(e.target.value)}
                className="bg-gray-800 text-white border border-gray-600 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-indigo-500"
              >
                <option value="user">User</option>
                <option value="company">Company</option>
                <option value="job">Job</option>
              </select>

              <input
                type="text"
                value={query}
                onChange={(e) => setQuery(e.target.value)}
                placeholder={`Search ${searchType}...`}
                className="flex-1 bg-gray-800 text-white border border-gray-600 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-indigo-500"
              />
            </div>

            {searchType === "job" && (
              <input
                type="text"
                value={location}
                onChange={(e) => setLocation(e.target.value)}
                placeholder="Location (optional)"
                className="w-full bg-gray-800 text-white border border-gray-600 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-indigo-500"
              />
            )}

            <button
              type="submit"
              className="bg-indigo-600 hover:bg-indigo-700 text-white px-6 py-2 rounded-lg transition"
            >
              Search
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}
