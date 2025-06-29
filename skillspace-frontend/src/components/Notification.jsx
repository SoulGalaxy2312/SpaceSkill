import { Trash2 } from "lucide-react";
import { formatDistanceToNow } from "date-fns";
import { getDefaultAvatar } from "../utils/avatar";
import { useNavigate } from "react-router-dom";

export default function Notification({ data, onRead, onDelete }) {
    const {
        id,
        title,
        message,
        isRead,
        createdAt,
        sender: { id: senderId, profileName, role },
    } = data;

    const avatarUrl = getDefaultAvatar(role);
    const navigate = useNavigate();

    const handleGoToProfile = () => {
        const path = role === "COMPANY"
        ? `/companies/profile/${senderId}`
        : `/users/profile/${senderId}`

        navigate(path);
    }

    return (
        <div className="flex items-start gap-3 px-4 py-3 border-t border-gray-700">
            {/* Avatar - tách biệt hover */}
            <div
            onClick={handleGoToProfile}
            className="shrink-0 hover:scale-105 transition cursor-pointer"
            title="View sender profile"
            >
            <img
                src={avatarUrl}
                alt="Avatar"
                className="w-10 h-10 rounded-full object-cover"
            />
            </div>

            {/* Info + actions - phần hover chính */}
            <div
            className={`flex-1 p-2 rounded-md transition cursor-pointer ${
                isRead ? "bg-gray-800" : "bg-gray-700"
            } hover:bg-gray-600`}
            onClick={() => onRead(id)}
            >
            <div className="text-sm font-semibold text-white mb-0.5">{title}</div>
            <div className="text-sm text-gray-300 line-clamp-2">{message}</div>
            <div className="text-xs text-gray-500 mt-1 flex items-center justify-between">
                <span>
                From: <strong>{profileName}</strong> ({role})
                </span>
                <span>{formatDistanceToNow(new Date(createdAt), { addSuffix: true })}</span>
            </div>
            </div>

            {/* Delete icon */}
            <button
            onClick={(e) => {
                e.stopPropagation(); // prevent triggering read
                onDelete(id);
            }}
            className="text-gray-500 hover:text-red-400 transition ml-2 mt-1"
            title="Delete"
            >
            <Trash2 size={16} />
            </button>
        </div>
    );

}
