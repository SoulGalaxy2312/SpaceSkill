// src/components/ApplyJobModal.jsx
import { useState } from "react";
import axiosInstance from "../api/axiosInstance";
import { getAppItem } from "../utils/localStorages";

export default function ApplyJobModal({ isOpen, onClose, job }) {
  const [personalStatement, setPersonalStatement] = useState("");
  const [error, setError] = useState(null);
  const [responseMessage, setResponseMessage] = useState("");

  if (!isOpen || !job) return null;

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    setResponseMessage("");

    if (!personalStatement.trim()) {
      setError("Personal statement is required.");
      return;
    }

    try {
      const jwt = getAppItem("jwt");
      const res = await axiosInstance.post(
        `/applications/${job.id}`,
        { personalStatement },
        { headers: { Authorization: `Bearer ${jwt}` } }
      );
      setResponseMessage(res.data.message);
      setPersonalStatement("");
    } catch (err) {
      setError("Something went wrong while applying.");
      console.error(err);
    }
  };

  return (
    <div className="fixed inset-0 z-50 bg-black bg-opacity-70 flex justify-center items-center">
      <div className="bg-gray-900 p-6 rounded-lg w-full max-w-lg">
        <div className="flex justify-between items-center mb-4">
          <h2 className="text-lg font-semibold text-white">Apply for Job</h2>
          <button onClick={onClose} className="text-gray-400 hover:text-white">✕</button>
        </div>

        <div className="text-gray-300 mb-4">
          <p><span className="font-semibold text-indigo-400">Job:</span> {job.title}</p>
          <p><span className="font-semibold text-indigo-400">Company:</span> {job.company?.profileName}</p>
        </div>

        <form onSubmit={handleSubmit} className="space-y-3">
          <textarea
            className="w-full p-3 rounded bg-gray-800 text-white border border-gray-600"
            rows={5}
            placeholder="Write your personal statement here..."
            maxLength={1000}
            value={personalStatement}
            onChange={(e) => setPersonalStatement(e.target.value)}
            required
          />
          <div className="text-sm text-gray-400 text-right">
            {personalStatement.length}/1000
          </div>

          {error && <div className="text-red-400 text-sm">{error}</div>}
          {responseMessage && <div className="text-green-400 text-sm">{responseMessage}</div>}

          <button
            type="submit"
            className="w-full bg-indigo-600 hover:bg-indigo-700 text-white py-2 rounded-md"
          >
            Submit Application
          </button>
        </form>
      </div>
    </div>
  );
}
