package skillspace.skillspace_backend.User.service;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.BaseUser.mapper.BaseUserMapper;
import skillspace.skillspace_backend.BaseUser.response.BaseUserBrief;
import skillspace.skillspace_backend.Company.model.Company;
import skillspace.skillspace_backend.User.exception.UserNotFoundException;
import skillspace.skillspace_backend.User.mapper.UserMapper;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.User.repository.UserRepository;
import skillspace.skillspace_backend.User.response.UserProfileDTO;
import skillspace.skillspace_backend.User.specification.UserSpecification;
import skillspace.skillspace_backend.shared.response.PagingDTO;
import skillspace.skillspace_backend.shared.security.service.SecurityService;

@Service
@Slf4j
public class UserReadServiceImpl implements UserReadService {
    private final UserProfileDTOLoadAndCacheService cacheService;
    private final SecurityService securityService;
    private final UserRepository userRepository;

    public UserReadServiceImpl(UserProfileDTOLoadAndCacheService cacheService, SecurityService securityService, UserRepository userRepository) {
        this.cacheService = cacheService;
        this.securityService = securityService;
        this.userRepository = userRepository;
    }

    public UserProfileDTO getUserProfile(UUID targetId) throws UserNotFoundException, JsonProcessingException {
        Supplier<UserProfileDTO> dataSupplier = () -> {
            try {
                User target = userRepository.getUserByIdOrThrow(targetId);
                UUID currentUserId = securityService.getCurrentBaseUserId();

                boolean isCurrentBaseUser = target.getId().equals(currentUserId);
                boolean isFollowedByCurrentUser = (!isCurrentBaseUser) && userRepository.isUserFollowedByCurrentBaseUser(targetId, currentUserId);
                return UserMapper.toUserProfileDTO(target, isCurrentBaseUser, isFollowedByCurrentUser);
            } catch (UserNotFoundException ex) {
                throw ex;
            }
        };

        UserProfileDTO profile = cacheService.loadAndCacheUserProfileDTO(targetId, dataSupplier);
        return profile;
    }

    public PagingDTO<BaseUserBrief> getFollowingCompanies(int page, int size) {
        UUID currentUserId = securityService.getCurrentBaseUserId();
        Pageable pageable = PageRequest.of(page, size);
        List<Company> followingCompanies = userRepository.findFollowingCompanies(currentUserId, pageable);

        if (followingCompanies.isEmpty()) {
            log.info("No following companies found for user with id: {}", currentUserId);
        } else {
            log.info("Found {} following companies for user with id: {}", followingCompanies.size(), currentUserId);
        }

        return new PagingDTO<>(
            followingCompanies.stream()
                .map(BaseUserMapper::toBaseUserBrief)
                .toList(),
            page,
            size,
            followingCompanies.size(),
            (int) Math.ceil((double) followingCompanies.size() / size)
        );
    }

    public PagingDTO<BaseUserBrief> getConnections(int page, int size) {
        UUID currentUserId = securityService.getCurrentBaseUserId();
        Pageable pageable = PageRequest.of(page, size);
        List<User> connections = userRepository.findConnections(currentUserId, pageable);

        if (connections.isEmpty()) {
            log.info("No connections found for user with id: {}", currentUserId);
        } else {
            log.info("Found {} connections for user with id: {}", connections.size(), currentUserId);
        }

        return new PagingDTO<>(
            connections.stream()
                .map(BaseUserMapper::toBaseUserBrief)
                .toList(),
            page,
            size,
            connections.size(),
            (int) Math.ceil((double) connections.size() / size)
        );
    }

    public PagingDTO<BaseUserBrief> searchUsers(String profileName, int page, int size) {
        profileName = safeTrim(profileName);
        if (profileName == null || profileName.isEmpty()) {
            log.info("Search profile name is empty or null, returning empty list.");
            return new PagingDTO<>(List.of(), page, size, 0, 0);
        }
        log.info("Searching users with profile name: {}", profileName);
        Specification<User> spec = UserSpecification.fullTextSearchProfileName(profileName);
        Pageable pageable = PageRequest.of(page, size);
        List<User> users = userRepository.findAll(spec, pageable).getContent();

        if (users.isEmpty()) {
            log.info("No users found with profile name: {}", profileName);
        } else {
            log.info("Found {} users with profile name: {}", users.size(), profileName);
        }

        return new PagingDTO<>(
            users.stream()
                .map(BaseUserMapper::toBaseUserBrief)
                .toList(),
            page,
            size,
            users.size(),
            (int) Math.ceil((double) users.size() / size)
        );
    }

    private String safeTrim(String str) {
        return str.trim().isEmpty() ? null : str.trim();
    }
}
