package skillspace.skillspace_backend.User.service.following;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import skillspace.skillspace_backend.Company.model.Company;
import skillspace.skillspace_backend.Company.repository.CompanyRepository;
import skillspace.skillspace_backend.Company.service.CompanyHelper;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.User.repository.UserRepository;
import skillspace.skillspace_backend.shared.enums.UserRole;
import skillspace.skillspace_backend.shared.exception.NotFoundException;
import skillspace.skillspace_backend.shared.response.StatusResponseDTO;

@Service
public class CompanyFollowingMechanism implements IFollowingMechanism {
    
    private final CompanyHelper companyHelper;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    
    public CompanyFollowingMechanism(CompanyHelper companyHelper, UserRepository userRepository, CompanyRepository companyRepository) {
        this.companyHelper = companyHelper;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public StatusResponseDTO follow(User follower, UUID targetId) {
        Company targetCompany = companyHelper.getCompany(targetId);
        if (targetCompany == null) {
            throw new NotFoundException("Company not found with ID: " + targetId);
        }

        List<Company> followedCompanies = follower.getFollowingCompanies();
        if (followedCompanies.contains(targetCompany)) {
            throw new IllegalArgumentException("You are already following this company");
        }       

        followedCompanies.add(targetCompany);
        targetCompany.getFollowers().add(follower);
        userRepository.save(follower);
        companyRepository.save(targetCompany);

        return new StatusResponseDTO(true, "Followed company successfully");
    }

    @Override
    public StatusResponseDTO unfollow(User follower, UUID targetId) {
        Company targetCompany = companyHelper.getCompany(targetId);
        if (targetCompany == null) {
            throw new NotFoundException("Company not found with ID: " + targetId);
        }
        List<Company> followedCompanies = follower.getFollowingCompanies();
        if (!followedCompanies.contains(targetCompany)) {
            throw new IllegalArgumentException("You have not followed this company");
        }
        followedCompanies.remove(targetCompany);
        targetCompany.getFollowers().remove(follower);
        userRepository.save(follower);
        companyRepository.save(targetCompany);

        return new StatusResponseDTO(true, "Unfollowed company successfully");
    }

    @Override
    public UserRole getTargetRole() {
        return UserRole.COMPANY;
    }
}
