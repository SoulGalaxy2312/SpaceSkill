package skillspace.skillspace_backend.shared.security.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import skillspace.skillspace_backend.Company.model.Company;
import skillspace.skillspace_backend.Company.service.CompanyHelper;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.User.service.UserHelper;

@Service
public class SecurityService {
    private final UserHelper userHelper;
    private final CompanyHelper companyHelper;

    public SecurityService(UserHelper userHelper, CompanyHelper companyHelper) {
        this.userHelper = userHelper;
        this.companyHelper = companyHelper;
    }

    private String getAuthenticationEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found");
        }
        return authentication.getName();
    }

    public User getCurrentUser() {
        String email = getAuthenticationEmail();
        return userHelper.getUserByEmail(email);
    }

    public Company getCurrentCompany() {
        String email = getAuthenticationEmail();
        return companyHelper.getCompany(email);
    }
}
