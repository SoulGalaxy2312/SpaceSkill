import { Briefcase, MapPin, Clock, BadgeCheck } from "lucide-react";
import { formatDistanceToNow } from "date-fns";
import { useNavigate } from "react-router-dom";

export default function JobCard({ job }) {
  const {
    id,
    title,
    location,
    type,
    createdAt,
    description,
    requiredSkills,
    company,
  } = job;

  const navigate = useNavigate();

  return (
    <div className="bg-gray-800 hover:bg-gray-700 transition rounded-xl p-4 shadow-md space-y-3 border border-gray-700">
      {/* Header */}
      <div className="flex items-center justify-between">
        <h2 className="text-lg font-semibold text-white">{title}</h2>
        <span className="text-sm text-gray-400">
          {formatDistanceToNow(new Date(createdAt), { addSuffix: true })}
        </span>
      </div>

      {/* Company */}
      {company?.profileName && (
        <div
          onClick={() => {
            if (window.location.pathname === `/companies/${company.id}/profile`) {
              navigate(0); // reloads the page
            } else {
              navigate(`/companies/${company.id}/profile`);
            }
          }}
          className="flex items-center text-sm text-indigo-300 gap-1 cursor-pointer hover:underline hover:text-indigo-400"
        >
          <Briefcase size={14} />
          <span>{company.profileName}</span>
        </div>
      )}

      {/* Location & Type */}
      <div className="flex items-center text-sm text-gray-400 gap-4">
        <span className="flex items-center gap-1">
          <MapPin size={14} /> {location}
        </span>
        <span className="flex items-center gap-1">
          <Clock size={14} /> {type}
        </span>
      </div>

      {/* Required Skills */}
      {requiredSkills?.length > 0 && (
        <div className="flex flex-wrap gap-2 pt-1">
          {requiredSkills.map((skill, index) => (
            <span
              key={index}
              className="bg-gray-700 text-gray-300 text-xs px-2 py-1 rounded-full"
            >
              {skill}
            </span>
          ))}
        </div>
      )}

      {/* Description */}
      <p className="text-sm text-gray-300 line-clamp-3">{description}</p>

      {/* Action */}
      <div className="pt-2">
        <button
          className="text-indigo-400 hover:text-indigo-300 text-sm font-medium"
          onClick={() => {
            // You can navigate to /jobs/${id} or open modal
            console.log("View job details:", id);
          }}
        >
          Apply →
        </button>
      </div>
    </div>
  );
}
