import { Briefcase, MapPin, Clock } from "lucide-react";
import { formatDistanceToNow } from "date-fns";

export default function JobCard({ job }) {
  const {
    id,
    title,
    location,
    type, // e.g., Full-time, Part-time
    createdAt,
    description,
  } = job;

  return (
    <div className="bg-gray-800 hover:bg-gray-700 transition rounded-xl p-4 shadow-md space-y-2 border border-gray-700">
      <div className="flex items-center justify-between">
        <h2 className="text-lg font-semibold text-white">{title}</h2>
        <span className="text-sm text-gray-400">
          {formatDistanceToNow(new Date(createdAt), { addSuffix: true })}
        </span>
      </div>

      <div className="flex items-center text-sm text-gray-400 gap-4">
        <span className="flex items-center gap-1">
          <MapPin size={14} /> {location}
        </span>
        <span className="flex items-center gap-1">
          <Clock size={14} /> {type}
        </span>
      </div>

      <p className="text-sm text-gray-300 line-clamp-3">
        {description}
      </p>

      <div className="pt-2">
        <button
          className="text-indigo-400 hover:text-indigo-300 text-sm font-medium"
          onClick={() => {
            // You can navigate to `/jobs/${id}` or open modal
            console.log("View job details:", id);
          }}
        >
          View Details →
        </button>
      </div>
    </div>
  );
}
