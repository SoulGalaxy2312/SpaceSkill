import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axiosInstance from "../../../api/axiosInstance";
import { getDefaultAvatar } from "../../../utils/avatar";
import EditCompanyModal from "../../../components/EditCompanyModal";
import JobCard from "../../../components/JobCard";
import { fetchCompanyProfile } from "../../../api/companyApi";
import NavBar from "../../../components/NavBar"
export default function CompanyProfilePage() {
  const { id } = useParams();
  const [company, setCompany] = useState(null);
  const [activeTab, setActiveTab] = useState("overview");
  const [editing, setEditing] = useState(false);
  const [jobs, setJobs] = useState([]);

  const fetchCompany = async () => {
    try {
      console.log("Before fetching company profile")
      const res = await fetchCompanyProfile(id);
      console.log(res.data.baseUserProfileInformation);
      setCompany(res.data.baseUserProfileInformation);
    } catch (err) {
      console.error("Failed to fetch company profile", err);
    }
  };
  
  useEffect(() => {  
    fetchCompany();
  }, [id]);

  useEffect(() => {
    if (activeTab === "jobs") {
      const fetchJobs = async () => {
        try {
          const res = await axiosInstance.get(`/companies/${id}/jobs`);
          setJobs(res.data);
        } catch (err) {
          console.error("Failed to fetch jobs", err);
        }
      };
      fetchJobs();
    }
  }, [activeTab, id]);

  const handleFollowToggle = async () => {
    try {
      const endpoint = company.isFollowedByCurrentBaseUser ? "/users/unfollow" : "/users/follow";
      await axiosInstance.post(endpoint, { targetId: company.id });
      setCompany((prev) => ({
        ...prev,
        isFollowedByCurrentBaseUser: !prev.isFollowedByCurrentBaseUser,
      }));
    } catch (err) {
      console.error("Failed to toggle follow", err);
    }
  };

  if (!company) return <div className="text-white p-6">Loading...</div>;

  return (
    <>
      <NavBar />
      <div className="min-h-screen bg-gray-900 text-white px-4 py-8">
      <div className="max-w-4xl mx-auto">
        {/* Header */}
        <div className="flex items-center gap-6 mb-6">
          <img
            src={getDefaultAvatar("COMPANY")}
            alt="Company Avatar"
            className="w-20 h-20 rounded-full object-cover"
          />
          <div>
            <h2 className="text-3xl font-bold text-indigo-400">{company.profileName}</h2>
            <p className="text-gray-400">{company.location}</p>
          </div>
          <div className="ml-auto flex gap-2">
            {company.isCurrentBaseUser ? (
              <button
                onClick={() => setEditing(true)}
                className="bg-indigo-600 hover:bg-indigo-700 text-white px-4 py-2 rounded-md"
              >
                Edit Profile
              </button>
            ) : (
              <button
                onClick={handleFollowToggle}
                className="bg-indigo-600 hover:bg-indigo-700 text-white px-4 py-2 rounded-md"
              >
                {company.isFollowedByCurrentBaseUser ? "Unfollow" : "Follow"}
              </button>
            )}
          </div>
        </div>

        {/* Tabs */}
        <div className="flex border-b border-gray-700 mb-4">
          <button
            onClick={() => setActiveTab("overview")}
            className={`px-4 py-2 font-medium transition ${
              activeTab === "overview" ? "text-indigo-400 border-b-2 border-indigo-400" : "text-gray-400 hover:text-white"
            }`}
          >
            Overview
          </button>
          <button
            onClick={() => setActiveTab("jobs")}
            className={`px-4 py-2 font-medium transition ${
              activeTab === "jobs" ? "text-indigo-400 border-b-2 border-indigo-400" : "text-gray-400 hover:text-white"
            }`}
          >
            Open Positions
          </button>
        </div>

        {/* Content */}
        <div className="mt-4">
          {activeTab === "overview" ? (
            <p className="text-gray-300 whitespace-pre-wrap">{company.about}</p>
          ) : (
            jobs.length > 0 ? (
              <div className="space-y-4">
                {jobs.map((job) => (
                  <JobCard key={job.id} job={job} />
                ))}
              </div>
            ) : (
              <p className="text-gray-400">No job openings at the moment.</p>
            )
          )}
        </div>
      </div>

      {/* Modal */}
      {company && (
        <EditCompanyModal 
          isOpen={editing}
          onClose={() => setEditing(false)} 
          company={company} 
          onUpdate={fetchCompany} />
      )}
    </div>
    </>
    
  );
}
