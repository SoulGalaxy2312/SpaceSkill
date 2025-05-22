package skillspace.skillspace_backend.shared.security.service;

import java.util.UUID;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import skillspace.skillspace_backend.Company.model.Company;
import skillspace.skillspace_backend.Company.service.CompanyHelper;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.User.service.UserHelper;
import skillspace.skillspace_backend.shared.model.BaseUser;

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

    public BaseUser getCurrentBaseUser() {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken)
            return null;

        String email = getAuthenticationEmail();
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_COMPANY"))) {
            return companyHelper.getCompany(email); // returns Company, which extends BaseUser
        } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
            return userHelper.getUserByEmail(email); // returns User, which extends BaseUser
        }
        return null;
    }

    public boolean assertCurrentUserMatches(UUID id) {
        BaseUser currentBaseUser = getCurrentBaseUser();
        return (currentBaseUser != null) && id.equals(currentBaseUser.getId());
    }
}
