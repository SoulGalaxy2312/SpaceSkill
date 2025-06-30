// src/pages/LoginPage.jsx

import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { loginUser } from "../../api/authApi";
import { setAppItem } from "../../utils/localStorages";

export default function LoginPage() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    email: "",
    password: "",
    showPassword: false,
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    setLoading(true);

    try {
      const res = await loginUser({
        email: formData.email,
        password: formData.password,
      });

      console.log("JWT: " + res.jwtToken);
      console.log("CurrentUserId: " + res.currentUserId);
      console.log("ROLE: " + res.role);

      Object.entries({
        jwt: res.jwtToken,
        currentUserId: res.currentUserId,
        role: res.role
      }).forEach(([key, value]) => setAppItem(key, value));

      navigate("/home"); // điều hướng về trang chủ
    } catch (err) {
      setError(err.response?.data?.message || "Invalid email or password.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gray-900 text-white flex items-center justify-center p-4">
      <div className="w-full max-w-md space-y-6 bg-gray-800 p-8 rounded-2xl shadow-lg">
        <h2 className="text-3xl font-semibold text-center">Welcome Back</h2>
        <p className="text-sm text-gray-400 text-center">Login to your SkillSpace account</p>

        <form onSubmit={handleSubmit} className="space-y-4">
          {/* Email */}
          <div>
            <label htmlFor="email" className="block mb-1 text-sm font-medium">
              Email
            </label>
            <input
              type="email"
              name="email"
              required
              value={formData.email}
              onChange={handleChange}
              className="w-full px-4 py-2 rounded-md bg-gray-700 text-white focus:outline-none focus:ring-2 focus:ring-indigo-500"
            />
          </div>

          {/* Password */}
          <div>
            <label htmlFor="password" className="block mb-1 text-sm font-medium">
              Password
            </label>
            <div className="relative">
              <input
                type={formData.showPassword ? "text" : "password"}
                name="password"
                required
                value={formData.password}
                onChange={handleChange}
                className="w-full px-4 py-2 rounded-md bg-gray-700 text-white focus:outline-none focus:ring-2 focus:ring-indigo-500"
              />
              <button
                type="button"
                onClick={() =>
                  setFormData((prev) => ({
                    ...prev,
                    showPassword: !prev.showPassword,
                  }))
                }
                className="absolute inset-y-0 right-3 text-gray-400 hover:text-indigo-400 text-sm"
              >
                {formData.showPassword ? "Hide" : "Show"}
              </button>
            </div>
          </div>

          {/* Submit */}
          <button
            type="submit"
            disabled={loading}
            className={`w-full py-2 mt-2 rounded-md bg-indigo-600 hover:bg-indigo-700 transition ${
              loading ? "opacity-50 cursor-not-allowed" : ""
            }`}
          >
            {loading ? "Logging in..." : "Login"}
          </button>
        </form>

        {/* Error */}
        {error && <p className="text-red-400 text-sm text-center mt-2">{error}</p>}

        {/* Switch to Register */}
        <p className="text-sm text-center mt-4">
          Don’t have an account?{" "}
          <a href="/register" className="text-indigo-400 hover:underline">
            Register here
          </a>
        </p>
      </div>
    </div>
  );
}
