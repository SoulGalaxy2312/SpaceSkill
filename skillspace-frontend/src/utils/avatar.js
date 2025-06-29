export function getDefaultAvatar(role) {
  const baseUser = "https://ui-avatars.com/api/?name=U+S&background=1E293B&color=FFFFFF&size=128&bold=true";
  const baseCompany = "https://ui-avatars.com/api/?name=C+O&background=4B5563&color=FFFFFF&size=128&bold=true";

  if (role === "COMPANY") return baseCompany;
  return baseUser;
}
