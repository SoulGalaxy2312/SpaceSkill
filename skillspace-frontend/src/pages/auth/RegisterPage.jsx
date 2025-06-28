// src/pages/RegisterPage.jsx

import { useState } from "react";
import { registerUser } from "../../api/authApi";

export default function RegisterPage() {
  const [formData, setFormData] = useState({
    email: "",
    password: "",
    confirmPassword: "",
    profileName: "",
    location: "",
    isCompany: false,
  });

  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState(null);
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
    setMessage(null);
    setError(null);

    if (formData.password !== formData.confirmPassword) {
      setError("Passwords do not match.");
      return;
    }

    setLoading(true);

    try {
        const resData = await registerUser({
            email: formData.email,
            password: formData.password,
            profileName: formData.profileName,
            location: formData.location,
            isCompany: formData.isCompany,
        });

        setMessage(resData.message || "Registered successfully!");
    } catch (err) {
        setError(err.response?.data?.message || "Something went wrong.");
    } finally {
        setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gray-900 text-white flex items-center justify-center p-4">
      <div className="w-full max-w-md space-y-6 bg-gray-800 p-8 rounded-2xl shadow-lg">
        <h2 className="text-3xl font-semibold text-center">Create an account</h2>

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
            <input
              type="password"
              name="password"
              required
              value={formData.password}
              onChange={handleChange}
              className="w-full px-4 py-2 rounded-md bg-gray-700 text-white focus:outline-none focus:ring-2 focus:ring-indigo-500"
            />
          </div>

          {/* Confirm Password */}
          <div>
            <label htmlFor="confirmPassword" className="block mb-1 text-sm font-medium">
              Confirm Password
            </label>
            <input
              type="password"
              name="confirmPassword"
              required
              value={formData.confirmPassword}
              onChange={handleChange}
              className="w-full px-4 py-2 rounded-md bg-gray-700 text-white focus:outline-none focus:ring-2 focus:ring-indigo-500"
            />
            {formData.password && formData.confirmPassword && formData.password !== formData.confirmPassword && (
              <p className="text-red-400 text-sm mt-1">Passwords do not match</p>
            )}
          </div>

          {/* Profile Name */}
          <div>
            <label htmlFor="profileName" className="block mb-1 text-sm font-medium">
              Profile Name
            </label>
            <input
              type="text"
              name="profileName"
              required
              value={formData.profileName}
              onChange={handleChange}
              className="w-full px-4 py-2 rounded-md bg-gray-700 text-white focus:outline-none focus:ring-2 focus:ring-indigo-500"
            />
          </div>

          {/* Location */}
          <div>
            <label htmlFor="location" className="block mb-1 text-sm font-medium">
              Location
            </label>
            <input
              type="text"
              name="location"
              required
              value={formData.location}
              onChange={handleChange}
              className="w-full px-4 py-2 rounded-md bg-gray-700 text-white focus:outline-none focus:ring-2 focus:ring-indigo-500"
            />
          </div>

          {/* Checkbox */}
          <div className="flex items-center">
            <input
              type="checkbox"
              id="isCompany"
              name="isCompany"
              checked={formData.isCompany}
              onChange={handleChange}
              className="h-4 w-4 text-indigo-600 bg-gray-700 border-gray-600 focus:ring-indigo-500"
            />
            <label htmlFor="isCompany" className="ml-2 text-sm cursor-pointer">
              I’m a Company
            </label>
          </div>

          {/* Submit */}
          <button
            type="submit"
            disabled={loading || formData.password !== formData.confirmPassword}
            className={`w-full py-2 mt-2 rounded-md bg-indigo-600 hover:bg-indigo-700 transition ${
              loading || formData.password !== formData.confirmPassword ? "opacity-50 cursor-not-allowed" : ""
            }`}
          >
            {loading ? "Registering..." : "Register"}
          </button>
        </form>

        {/* Message */}
        {message && <p className="text-green-400 text-sm mt-2 text-center">{message}</p>}
        {error && <p className="text-red-400 text-sm mt-2 text-center">{error}</p>}

        <p className="text-sm text-center mt-4">
          Already have an account?{" "}
          <a href="/login" className="text-indigo-400 hover:underline">
            Login here
          </a>
        </p>
      </div>
    </div>
  );
}
