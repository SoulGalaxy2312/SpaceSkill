import { useState, useEffect } from "react";
import { Dialog } from "@headlessui/react";
import { X } from "lucide-react";
import { editProfile } from "../api/baseUserApi";

export default function EditCompanyModal({ isOpen, onClose, company, onUpdate }) {
  const [formData, setFormData] = useState({
    profileName: company.profileName,
    location: company.location,
    about: company.about,
  });

  useEffect(() => {
    setFormData({
      profileName: company.profileName,
      location: company.location,
      about: company.about,
    });
  }, [company]);

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");

    try {
      await editProfile(formData);
      onUpdate();
      onClose();

    } catch (err) {
      setError(err.response?.data?.message || "Update failed.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <Dialog open={isOpen} onClose={onClose} className="fixed z-50 inset-0 overflow-y-auto">
      {/* Overlay */}
      <div className="fixed inset-0 bg-black bg-opacity-50" aria-hidden="true" />
      <div className="flex items-center justify-center min-h-screen px-4">
        <Dialog.Panel className="bg-gray-900 text-white max-w-lg w-full p-6 rounded-lg z-10 relative space-y-4">
          <div className="flex justify-between items-center">
            <Dialog.Title className="text-xl font-semibold">Edit Company Profile</Dialog.Title>
            <button onClick={onClose} className="text-gray-400 hover:text-red-400 transition">
              <X />
            </button>
          </div>

          {error && <p className="text-red-400 text-sm">{error}</p>}

          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <label className="block text-sm mb-1">Company Name</label>
              <input
                type="text"
                name="profileName"
                value={formData.profileName}
                onChange={handleChange}
                required
                className="w-full bg-gray-800 text-white px-4 py-2 rounded border border-gray-700 focus:outline-none focus:ring-2 focus:ring-indigo-500"
              />
            </div>

            <div>
              <label className="block text-sm mb-1">Location</label>
              <input
                type="text"
                name="location"
                value={formData.location}
                onChange={handleChange}
                required
                className="w-full bg-gray-800 text-white px-4 py-2 rounded border border-gray-700 focus:outline-none focus:ring-2 focus:ring-indigo-500"
              />
            </div>

            <div>
              <label className="block text-sm mb-1">About</label>
              <textarea
                name="about"
                value={formData.about}
                onChange={handleChange}
                rows="4"
                className="w-full bg-gray-800 text-white px-4 py-2 rounded border border-gray-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 resize-none"
              ></textarea>
            </div>

            <button
              type="submit"
              disabled={loading}
              className="bg-indigo-600 hover:bg-indigo-700 text-white px-6 py-2 rounded transition w-full"
            >
              {loading ? "Saving..." : "Save Changes"}
            </button>
          </form>
        </Dialog.Panel>
      </div>
    </Dialog>
  );
}