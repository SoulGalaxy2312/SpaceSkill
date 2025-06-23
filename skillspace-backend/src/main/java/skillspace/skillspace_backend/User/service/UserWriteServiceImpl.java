package skillspace.skillspace_backend.User.service;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.User.exception.DuplicateSkillException;
import skillspace.skillspace_backend.User.mapper.UserMapper;
import skillspace.skillspace_backend.User.model.Education;
import skillspace.skillspace_backend.User.model.Experience;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.User.repository.UserRepository;
import skillspace.skillspace_backend.User.request.AddEducationDTO;
import skillspace.skillspace_backend.User.request.AddExperienceDTO;
import skillspace.skillspace_backend.User.request.FollowRequestDTO;
import skillspace.skillspace_backend.User.response.UserProfileDTO;
import skillspace.skillspace_backend.User.service.following.FollowingStrategyFactory;
import skillspace.skillspace_backend.User.service.following.IFollowingMechanism;
import skillspace.skillspace_backend.shared.response.StatusResponseDTO;
import skillspace.skillspace_backend.shared.security.service.SecurityService;

@Service
@Slf4j
public class UserWriteServiceImpl implements UserWriteService {


    private final UserRepository userRepository;
    private final UserProfileDTOLoadAndCacheService cacheService;
    private final SecurityService securityService;
    private final FollowingStrategyFactory followingStrategyFactory;

    public UserWriteServiceImpl(
        UserRepository userRepository,
        UserProfileDTOLoadAndCacheService cacheService,
        SecurityService securityService,
        FollowingStrategyFactory followingStrategyFactory
        ) {
        
        this.userRepository = userRepository;
        this.cacheService = cacheService;
        this.securityService = securityService;
        this.followingStrategyFactory = followingStrategyFactory;
    }

    /**
     * Experience section
     */
    @Transactional
    public UserProfileDTO addExperience(UUID userId, AddExperienceDTO dto) throws JsonProcessingException, AccessDeniedException {
        boolean isCurrentUser = securityService.assertCurrentUserMatches(userId);
        if (!isCurrentUser) {
            log.warn("Current user is not authorized to add experience for user {}", userId);
            throw new AccessDeniedException("You are not authorized to add experience for this user");
        };

        User user = userRepository.getUserByIdOrThrow(userId);
        Experience entity = new Experience();
        entity.setStartDate(dto.startDate());
        entity.setEndDate(dto.endDate());
        entity.setCompany(dto.company());
        entity.setTitle(dto.title());
        entity.setUser(user);

        user.getExperiences().add(entity);
        UserProfileDTO profile = UserMapper.toUserProfileDTO(userRepository.save(user), true, false);
        cacheService.writeHash(userId, profile);
        return profile;
    }

    @Transactional
    public UserProfileDTO deleteExperience(UUID userId, UUID experienceId) throws JsonProcessingException, AccessDeniedException {
        boolean isCurrentUser = securityService.assertCurrentUserMatches(userId);
        if (!isCurrentUser) {
            log.warn("Current user is not authorized to delete experience for user {}", userId);
            throw new AccessDeniedException("You are not authorized to delete experience for this user");
        };

        User user = userRepository.getUserByIdOrThrow(userId);
        List<Experience> experiences = user.getExperiences();
        experiences.removeIf((experience) -> {
            return experience.getId().equals(experienceId);
        });
        UserProfileDTO profile = UserMapper.toUserProfileDTO(userRepository.save(user), true, false);
        cacheService.writeHash(userId, profile);
        return profile;
    }

    /**
     * Education section
     */
    @Transactional
    public UserProfileDTO addEducation(UUID userId, AddEducationDTO educationDTO) throws JsonProcessingException, AccessDeniedException {
        boolean isCurrentUser = securityService.assertCurrentUserMatches(userId);
        if (!isCurrentUser) {
            log.warn("Current user is not authorized to add education for user {}", userId);
            throw new AccessDeniedException("You are not authorized to add education for this user");
        };
        
        User user = userRepository.getUserByIdOrThrow(userId);
        Education entity = new Education();
        entity.setStartDate(educationDTO.startDate());
        entity.setEndDate(educationDTO.endDate());
        entity.setUniversity(educationDTO.university());
        entity.setUser(user);
        entity.setDegree(educationDTO.degree());
        
        user.getEducations().add(entity);
        UserProfileDTO profile = UserMapper.toUserProfileDTO(userRepository.save(user), true, false);
        cacheService.writeHash(userId, profile);
        return profile;
    }

    @Transactional
    public UserProfileDTO deleteEducation(UUID userId, UUID educationId) throws JsonProcessingException, AccessDeniedException {
        
        boolean isCurrentUser = securityService.assertCurrentUserMatches(userId);
        if (!isCurrentUser) {
            log.warn("Current user is not authorized to delete education for user {}", userId);
            throw new AccessDeniedException("You are not authorized to delete education for this user");
        };

        User user = userRepository.getUserByIdOrThrow(userId);
        List<Education> educations = user.getEducations();
        educations.removeIf((education) -> {
            return education.getId().equals(educationId);
        });
        UserProfileDTO profile = UserMapper.toUserProfileDTO(userRepository.save(user), true, false);
        cacheService.writeHash(userId, profile);
        return profile;
    }

    /**
     * Skill section
     */

    public UserProfileDTO addSkill(UUID userId, String skill) throws DuplicateSkillException, JsonProcessingException, AccessDeniedException {
        boolean isCurrentUser = securityService.assertCurrentUserMatches(userId);
        if (!isCurrentUser) {
            log.warn("Current user is not authorized to add skill for user {}", userId);
            throw new AccessDeniedException("You are not authorized to add skill for this user");
        };

        User user = userRepository.getUserByIdOrThrow(userId);
        List<String> skills = user.getSkills();
        if (skills.contains(skill)) {
            log.warn("Add skill fail: skill {} already exists", skill);
            throw new DuplicateSkillException("Skill: " + skill + " already exists");
        }
        skills.add(skill);
        UserProfileDTO profile = UserMapper.toUserProfileDTO(userRepository.save(user), true, false);
        cacheService.writeHash(userId, profile);
        return profile;
    }

    public UserProfileDTO deleteSkill(UUID userId, String skill) throws JsonProcessingException, AccessDeniedException {
        boolean isCurrentUser = securityService.assertCurrentUserMatches(userId);
        if (!isCurrentUser) {
            log.warn("Current user is not authorized to delete skill for user {}", userId);
            throw new AccessDeniedException("You are not authorized to delete skill for this user");
        };

        User user = userRepository.getUserByIdOrThrow(userId);
        user.getSkills().remove(skill);
        UserProfileDTO profile = UserMapper.toUserProfileDTO(userRepository.save(user), true, false);
        cacheService.writeHash(userId, profile);
        return profile;
    }
    
    /**
     * Follow section
     */
    @Transactional
    public StatusResponseDTO follow(FollowRequestDTO dto) throws IllegalArgumentException {
        User user = securityService.getCurrentUser();
        IFollowingMechanism mechanism = followingStrategyFactory.getStrategy(dto.targetType());
        return mechanism.follow(user, dto.targetId());
    }
}
