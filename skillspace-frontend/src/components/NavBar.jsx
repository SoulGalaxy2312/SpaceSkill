// src/components/NavBar/NavBar.jsx
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Menu, X } from "lucide-react"; // modern icons
import avatarPlaceholder from "../assets/avatar.jpg"; // Placeholder avatar image

export default function NavBar() {
  const [menuOpen, setMenuOpen] = useState(false);
  const [searchType, setSearchType] = useState("user");
  const [query, setQuery] = useState("");
  const navigate = useNavigate();

  const isLoggedIn = false; // Replace with real auth state

  const handleSearch = (e) => {
    e.preventDefault();
    if (!query.trim()) return;
    navigate(`/search-results?type=${searchType}&query=${encodeURIComponent(query)}`);
  };

  return (
    <nav className="bg-gray-900 shadow sticky top-0 z-50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center h-16">
          {/* Logo */}
          <div className="flex-shrink-0">
            <Link to="/" className="text-xl font-bold text-white">SkillSpace</Link>
          </div>

          {/* Desktop Search */}
          <form
            onSubmit={handleSearch}
            className="hidden md:flex flex-1 mx-6 items-center space-x-2"
          >
            <select
              value={searchType}
              onChange={(e) => setSearchType(e.target.value)}
              className="bg-gray-800 text-white border border-gray-700 rounded px-3 py-2 text-sm focus:outline-none"
            >
              <option value="user">User</option>
              <option value="company">Company</option>
              <option value="job">Job</option>
            </select>
            <input
              type="text"
              placeholder={`Search ${searchType}...`}
              value={query}
              onChange={(e) => setQuery(e.target.value)}
              className="flex-grow border rounded px-4 py-2 text-sm focus:outline-none"
            />
            <button
              type="submit"
              className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition"
            >
              Search
            </button>
          </form>

          {/* Auth / Menu */}
          <div className="hidden md:flex items-center space-x-4">
            {isLoggedIn ? (
              <>
                <img src={avatarPlaceholder} alt="Avatar" className="w-8 h-8 rounded-full" />
                <button className="text-sm text-gray-600 hover:text-red-600">Logout</button>
              </>
            ) : (
              <>
                <Link to="/login" className="text-sm text-gray-300 hover:text-white">Login</Link>
                <Link to="/register" className="text-sm font-medium text-blue-600 hover:underline">Register</Link>
              </>
            )}
          </div>

          {/* Mobile menu toggle */}
          <button
            onClick={() => setMenuOpen(!menuOpen)}
            className="md:hidden focus:outline-none text-gray-600"
          >
            {menuOpen ? <X size={24} /> : <Menu size={24} />}
          </button>
        </div>
      </div>

      {/* Mobile dropdown menu */}
      {menuOpen && (
        <div className="md:hidden px-4 pb-4 space-y-4">
          <form onSubmit={handleSearch} className="flex flex-col space-y-2">
            <select
              value={searchType}
              onChange={(e) => setSearchType(e.target.value)}
              className="border rounded px-3 py-2 text-sm"
            >
              <option value="user">User</option>
              <option value="company">Company</option>
              <option value="job">Job</option>
            </select>
            <input
              type="text"
              placeholder={`Search ${searchType}...`}
              value={query}
              onChange={(e) => setQuery(e.target.value)}
              className="border rounded px-4 py-2 text-sm"
            />
            <button
              type="submit"
              className="bg-blue-600 text-white py-2 rounded hover:bg-blue-700"
            >
              Search
            </button>
          </form>

          <div className="flex flex-col items-start space-y-2">
            {isLoggedIn ? (
              <>
                <img src={avatarPlaceholder} alt="Avatar" className="w-8 h-8 rounded-full" />
                <button className="text-sm text-gray-600 hover:text-red-600">Logout</button>
              </>
            ) : (
              <>
                <Link to="/login" className="text-sm hover:text-blue-600">Login</Link>
                <Link to="/register" className="text-sm font-medium text-blue-600 hover:underline">Register</Link>
              </>
            )}
          </div>
        </div>
      )}
    </nav>
  );
}
