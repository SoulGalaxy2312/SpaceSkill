import { useState } from "react";
import axiosInstance from "../api/axiosInstance";
import { getAppItem } from "../utils/localStorages";

const statuses = ["PENDING", "ACCEPTED", "REJECTED"];

export default function ReviewApplicationModal({ application, onClose }) {
  const [status, setStatus] = useState(application.status);
  const [note, setNote] = useState(application.reviewNote || "");

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (status === "REJECTED") {
      const confirmed = window.confirm("Are you sure you want to reject this application?");
      if (!confirmed) return;
    }

    try {
      const jwt = getAppItem("jwt");
      console.log(note);
      await axiosInstance.patch(`/applications/${application.id}`, {
        status,
        reviewerNote: note,
      }, {
        headers: { Authorization: `Bearer ${jwt}` },
      });

      window.toast?.success("Application reviewed successfully");
      onClose();
    } catch (err) {
      console.error("Review failed", err);
      window.toast?.error("Failed to review application");
    }
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-60 z-50 flex justify-center items-center">
      <div className="bg-gray-900 p-6 rounded-lg w-full max-w-md">
        <div className="flex justify-between items-center mb-4">
          <h2 className="text-white font-semibold">Review Application</h2>
          <button onClick={onClose} className="text-gray-400 hover:text-white">✕</button>
        </div>

        <form onSubmit={handleSubmit} className="space-y-3">
          <label className="text-gray-300 text-sm block">
            Status
            <select
              className="w-full mt-1 bg-gray-800 text-white p-2 rounded border border-gray-700"
              value={status}
              onChange={(e) => setStatus(e.target.value)}
            >
              {statuses.map((s) => (
                <option key={s} value={s}>{s}</option>
              ))}
            </select>
          </label>

          <label className="text-gray-300 text-sm block">
            Review Note
            <textarea
              className="w-full mt-1 bg-gray-800 text-white p-2 rounded border border-gray-700"
              rows={4}
              value={note}
              onChange={(e) => setNote(e.target.value)}
            />
          </label>

          <button
            type="submit"
            className="w-full bg-indigo-600 hover:bg-indigo-700 text-white py-2 rounded-md"
          >
            Submit
          </button>
        </form>
      </div>
    </div>
  );
}
