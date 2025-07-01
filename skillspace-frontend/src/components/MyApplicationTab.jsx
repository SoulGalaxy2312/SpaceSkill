import { useEffect, useState } from "react";
import axiosInstance from "../api/axiosInstance";
import { getAppItem } from "../utils/localStorages";
import ReviewApplicationModal from "./ReviewApplicationModal"; // sẽ tạo sau

export default function MyApplicationsTab({ companyId }) {
  const [applications, setApplications] = useState([]);
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [selectedApplication, setSelectedApplication] = useState(null);

  const fetchApplications = async () => {
    try {
      const jwt = getAppItem("jwt");
      const res = await axiosInstance.get(`/applications`, {
        headers: { Authorization: `Bearer ${jwt}` },
        params: { page },
      });
      console.log(res);

      setApplications(res.data.content);
      setTotalPages(res.data.totalPages);
    } catch (err) {
      console.error("Failed to fetch applications", err);
      window.toast?.error("Failed to load applications");
    }
  };

  useEffect(() => {
    fetchApplications();
  }, [companyId, page]);

  const openReviewModal = (application) => {
    setSelectedApplication(application);
  };

  const closeReviewModal = () => {
    setSelectedApplication(null);
    fetchApplications(); // reload data after review
  };

  return (
    <div className="space-y-4">
      {applications.map((app) => (
        <div
          key={app.id}
          className="bg-gray-800 rounded-md p-4 border border-gray-700"
        >
          <div className="flex justify-between items-center mb-2">
            <div>
              <h3 className="text-white font-semibold">{app.user.profileName}</h3>
              <p className="text-gray-400 text-sm">Role: {app.user.role}</p>
            </div>

            <div className="text-sm text-gray-300 text-right">
              <p><span className="text-indigo-400">Job:</span> {app.job.title}</p>
              <p><span className="text-gray-400">Applied at:</span> {app.appliedAt}</p>
            </div>
          </div>

          <div className="text-gray-300 text-sm mb-2">
            <p><span className="text-gray-400">Status:</span> {app.status}</p>
            <p><span className="text-gray-400">Review Note:</span> {app.reviewerNote || "—"}</p>
          </div>

          <button
            onClick={() => openReviewModal(app)}
            className="text-sm text-indigo-400 hover:text-indigo-300"
          >
            Review →
          </button>
        </div>
      ))}

      {/* Paging */}
      <div className="flex justify-between mt-4 text-sm text-gray-300">
        <button
          disabled={page <= 1}
          onClick={() => setPage((p) => p - 1)}
          className="disabled:opacity-50 hover:text-indigo-400"
        >
          ← Previous
        </button>
        <span>
          Page {page} of {totalPages}
        </span>
        <button
          disabled={page + 1 >= totalPages}
          onClick={() => setPage((p) => p + 1)}
          className="disabled:opacity-50 hover:text-indigo-400"
        >
          Next →
        </button>
      </div>

      {/* Modal */}
      {selectedApplication && (
        <ReviewApplicationModal
          application={selectedApplication}
          onClose={closeReviewModal}
        />
      )}
    </div>
  );
}
