// src/components/SimpleListModal.jsx
export default function SimpleListModal({ isOpen, onClose, title, items }) {
  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 z-40 bg-black bg-opacity-70 flex justify-center items-center">
      <div className="bg-gray-800 p-6 rounded-lg w-96 max-h-[80vh] overflow-y-auto">
        <div className="flex justify-between items-center mb-4">
          <h2 className="text-lg font-semibold text-white">{title}</h2>
          <button onClick={onClose} className="text-gray-400 hover:text-white text-sm">Close</button>
        </div>
        <ul className="space-y-2">
          {items.map((item) => (
            <li key={item.id} className="text-white border-b border-gray-600 pb-2">
              <p className="font-medium">{item.profileName}</p>
              <p className="text-sm text-gray-400">{item.role}</p>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}
