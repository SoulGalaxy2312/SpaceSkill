package skillspace.skillspace_backend.User.service;

import java.util.List;
import java.util.UUID;

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
    public UserProfileDTO addExperience(AddExperienceDTO dto) throws JsonProcessingException {
        User user = securityService.getCurrentUser();
        Experience entity = new Experience();
        entity.setStartDate(dto.startDate());
        entity.setEndDate(dto.endDate());
        entity.setCompany(dto.company());
        entity.setTitle(dto.title());
        entity.setUser(user);

        user.getExperiences().add(entity);
        UserProfileDTO profile = UserMapper.toUserProfileDTO(userRepository.save(user), true, false);
        cacheService.writeHash(user.getId(), profile);
        return profile;
    }

    @Transactional
    public UserProfileDTO deleteExperience(UUID experienceId) throws JsonProcessingException {
        User user = securityService.getCurrentUser();
        List<Experience> experiences = user.getExperiences();
        experiences.removeIf((experience) -> {
            return experience.getId().equals(experienceId);
        });
        UserProfileDTO profile = UserMapper.toUserProfileDTO(userRepository.save(user), true, false);
        cacheService.writeHash(user.getId(), profile);
        return profile;
    }

    /**
     * Education section
     */
    @Transactional
    public UserProfileDTO addEducation(AddEducationDTO educationDTO) throws JsonProcessingException {
        User user = securityService.getCurrentUser();
        Education entity = new Education();
        entity.setStartDate(educationDTO.startDate());
        entity.setEndDate(educationDTO.endDate());
        entity.setUniversity(educationDTO.university());
        entity.setUser(user);
        entity.setDegree(educationDTO.degree());
        
        user.getEducations().add(entity);
        UserProfileDTO profile = UserMapper.toUserProfileDTO(userRepository.save(user), true, false);
        cacheService.writeHash(user.getId(), profile);
        return profile;
    }

    @Transactional
    public UserProfileDTO deleteEducation(UUID educationId) throws JsonProcessingException {
        User user = securityService.getCurrentUser();
        List<Education> educations = user.getEducations();
        educations.removeIf((education) -> {
            return education.getId().equals(educationId);
        });
        UserProfileDTO profile = UserMapper.toUserProfileDTO(userRepository.save(user), true, false);
        cacheService.writeHash(user.getId(), profile);
        return profile;
    }

    /**
     * Skill section
     */
    public UserProfileDTO addSkill(String skill) throws DuplicateSkillException, JsonProcessingException {
        User user = securityService.getCurrentUser();
        List<String> skills = user.getSkills();
        if (skills.contains(skill)) {
            log.warn("Add skill fail: skill {} already exists", skill);
            throw new DuplicateSkillException("Skill: " + skill + " already exists");
        }
        skills.add(skill);
        UserProfileDTO profile = UserMapper.toUserProfileDTO(userRepository.save(user), true, false);
        cacheService.writeHash(user.getId(), profile);
        return profile;
    }

    public UserProfileDTO deleteSkill(String skill) throws JsonProcessingException {
        User user = securityService.getCurrentUser();
        user.getSkills().remove(skill);
        UserProfileDTO profile = UserMapper.toUserProfileDTO(userRepository.save(user), true, false);
        cacheService.writeHash(user.getId(), profile);
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
