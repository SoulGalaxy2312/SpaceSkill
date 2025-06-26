package skillspace.skillspace_backend.shared.security.service;

import java.util.UUID;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import skillspace.skillspace_backend.BaseUser.model.BaseUser;
import skillspace.skillspace_backend.BaseUser.repository.BaseUserRepository;
import skillspace.skillspace_backend.Company.model.Company;
import skillspace.skillspace_backend.Company.repository.CompanyRepository;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.User.repository.UserRepository;

@Service
public class SecurityService {
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final BaseUserRepository baseUserRepository;

    public SecurityService(
        UserRepository userRepository, 
        CompanyRepository companyRepository,
        BaseUserRepository baseUserRepository) {

        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.baseUserRepository = baseUserRepository;
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
        return userRepository.getUserByEmailOrThrow(email);
    }

    public Company getCurrentCompany() {
        String email = getAuthenticationEmail();
        return companyRepository.getCompanyByEmailOrThrow(email);
    }

    public BaseUser getCurrentBaseUser() {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken)
            return null;

        String email = getAuthenticationEmail();
        return baseUserRepository.findByEmailOrThrow(email);
    }

    public UUID getCurrentBaseUserId() {
        BaseUser currentBaseUser = getCurrentBaseUser();
        return (currentBaseUser == null) ? null : currentBaseUser.getId();
    }

    public boolean assertCurrentUserMatches(UUID id) {
        BaseUser currentBaseUser = getCurrentBaseUser();
        return (currentBaseUser != null) && id.equals(currentBaseUser.getId());
    }    
}
