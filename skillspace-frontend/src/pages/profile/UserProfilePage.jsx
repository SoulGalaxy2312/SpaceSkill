// src/pages/user/UserProfilePage.jsx
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axiosInstance from "../../api/axiosInstance"
import { getDefaultAvatar } from "../../utils/avatar"
import EditProfileModal from "../../components/EditProfileModal"
import NavBar from "../../components/NavBar"
import { getAppItem } from "../../utils/localStorages";
import SimpleListModal from "../../components/SimpleListModal";

export default function UserProfilePage() {
  const { id } = useParams();
  const [profile, setProfile] = useState(null);
  const [editing, setEditing] = useState(false);
  const [showConnections, setShowConnections] = useState(false);
  const [showFollowingCompanies, setShowFollowingCompanies] = useState(false);
  const [connections, setConnections] = useState([]);
  const [followingCompanies, setFollowingCompanies] = useState([]);

  const fetchUserProfile = async () => {
    try {
        const jwt = localStorage.getItem("jwt");
        const headers = jwt
        ? { Authorization: `Bearer ${jwt}` }
        : {};

        const res = await axiosInstance.get(`/users/${id}/profile`, { headers });
        console.log(res.data)
        setProfile(res.data);
    } catch (err) {
      console.error("Failed to fetch user profile", err);
    }
  };

  useEffect(() => {
    fetchUserProfile();
  }, [id]);

  const handleFollowToggle = async () => {
    try {
      const endpoint = profile.baseUserProfileInformation.isFollowedByCurrentBaseUser
        ? "/users/unfollow"
        : "/users/follow";
      await axiosInstance.post(endpoint, { targetId: profile.baseUserProfileInformation.id });
      setProfile((prev) => ({
        ...prev,
        baseUserProfileInformation: {
          ...prev.baseUserProfileInformation,
          isFollowedByCurrentBaseUser:
            !prev.baseUserProfileInformation.isFollowedByCurrentBaseUser,
        },
      }));
    } catch (err) {
      console.error("Failed to toggle follow", err);
    }
  };

  const fetchConnections = async () => {
    try {
      const res = await axiosInstance.get("/users/connections", {
        headers: { Authorization: `Bearer ${getAppItem("jwt")}` },
      });
      setConnections(res.data);
      setShowConnections(true);
    } catch (err) {
      console.error("Failed to fetch connections", err);
    }
  };

  const fetchFollowingCompanies = async () => {
    try {
      const res = await axiosInstance.get("/users/following-companies", {
        headers: { Authorization: `Bearer ${getAppItem("jwt")}` },
      });
      setFollowingCompanies(res.data);
      setShowFollowingCompanies(true);
    } catch (err) {
      console.error("Failed to fetch following companies", err);
    }
  };

  if (!profile) return <div className="text-white p-6">Loading...</div>;

  const { baseUserProfileInformation, skills, experiences, educations } = profile;

  return (
    <>
      <NavBar />
      <div className="min-h-screen bg-gray-900 text-white px-4 py-8">
        <div className="max-w-4xl mx-auto">
          {/* Header */}
          <div className="flex items-center gap-6 mb-6">
            <img
              src={getDefaultAvatar("USER")}
              alt="User Avatar"
              className="w-20 h-20 rounded-full object-cover"
            />
            <div>
              <h2 className="text-3xl font-bold text-indigo-400">{baseUserProfileInformation.profileName}</h2>
              <p className="text-gray-400">{baseUserProfileInformation.location}</p>
              <div className="flex gap-4 mt-2 text-indigo-300 text-sm">
                <button onClick={fetchConnections} className="hover:underline">Connections</button>
                <button onClick={fetchFollowingCompanies} className="hover:underline">Following Companies</button>
              </div>
            </div>
            <div className="ml-auto">
              {baseUserProfileInformation.isCurrentBaseUser ? (
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
                  {baseUserProfileInformation.isFollowedByCurrentBaseUser ? "Unfollow" : "Follow"}
                </button>
              )}
            </div>
          </div>

          {/* Overview */}
          <div>
            <h3 className="text-xl text-indigo-400 font-semibold mb-2">About</h3>
            <p className="text-gray-300 whitespace-pre-wrap mb-6">{baseUserProfileInformation.about}</p>

            <h3 className="text-xl text-indigo-400 font-semibold mb-2">Skills</h3>
            <div className="flex flex-wrap gap-2 mb-6">
              {skills.map((skill, index) => (
                <span key={index} className="bg-indigo-600 text-sm px-3 py-1 rounded-full">
                  {skill}
                </span>
              ))}
            </div>

            <h3 className="text-xl text-indigo-400 font-semibold mb-2">Experience</h3>
            <div className="space-y-3 mb-6">
              {experiences.map((exp) => (
                <div key={exp.id} className="bg-gray-800 p-4 rounded-lg">
                  <p className="font-semibold">{exp.title}</p>
                  <p className="text-sm text-gray-400">{exp.company}</p>
                  <p className="text-xs text-gray-500">
                    {exp.startDate} - {exp.endDate}
                  </p>
                </div>
              ))}
            </div>

            <h3 className="text-xl text-indigo-400 font-semibold mb-2">Education</h3>
            <div className="space-y-3">
              {educations.map((edu) => (
                <div key={edu.id} className="bg-gray-800 p-4 rounded-lg">
                  <p className="font-semibold">{edu.title}</p>
                  <p className="text-sm text-gray-400">{edu.university}</p>
                  <p className="text-xs text-gray-500">
                    {edu.startDate} - {edu.endDate} | {edu.degree}
                  </p>
                </div>
              ))}
            </div>
          </div>

          {/* Modal */}
          {baseUserProfileInformation.isCurrentBaseUser && (
            <EditProfileModal
              isOpen={editing}
              onClose={() => setEditing(false)}
              company={baseUserProfileInformation}
              onUpdate={fetchUserProfile}
            />
          )}
        </div>

        {/* Connections Modal */}
        <SimpleListModal
          isOpen={showConnections}
          onClose={() => setShowConnections(false)}
          title="Connections"
          items={connections}
        />

        {/* Following Companies Modal */}
        <SimpleListModal
          isOpen={showFollowingCompanies}
          onClose={() => setShowFollowingCompanies(false)}
          title="Following Companies"
          items={followingCompanies}
        />
      </div>
    </>
  );
}
