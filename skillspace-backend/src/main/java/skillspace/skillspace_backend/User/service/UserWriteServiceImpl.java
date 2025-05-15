package skillspace.skillspace_backend.User.service;

import java.util.List;
import java.util.Set;
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
import skillspace.skillspace_backend.User.response.UserProfileDTO;

@Service
@Slf4j
public class UserWriteServiceImpl implements UserWriteService {

    private final UserRepository userRepository;
    private final UserHelper userHelper;
    private final UserProfileDTOLoadAndCacheService cacheService;

    public UserWriteServiceImpl(
        UserRepository userRepository,
        UserHelper userHelper,
        UserProfileDTOLoadAndCacheService cacheService) {

        this.cacheService = cacheService;
        this.userRepository = userRepository;
        this.userHelper = userHelper;
    }

    /**
     * Experience section
     */
    @Transactional
    public UserProfileDTO addExperience(UUID userId, AddExperienceDTO dto) throws JsonProcessingException {
        User user = userHelper.getUserById(userId);
        
        Experience entity = new Experience();
        entity.setStartDate(dto.startDate());
        entity.setEndDate(dto.endDate());
        entity.setCompany(dto.company());
        entity.setTitle(dto.title());
        entity.setUser(user);

        user.getExperiences().add(entity);

        UserProfileDTO profile = UserMapper.toUserProfileDTO(userRepository.save(user));
        cacheService.writeHash(userId, profile);
        return profile;
    }

    @Transactional
    public UserProfileDTO deleteExperience(UUID userId, UUID experienceId) throws JsonProcessingException {
        User user = userHelper.getUserById(userId);
        List<Experience> experiences = user.getExperiences();
        experiences.removeIf((experience) -> {
            return experience.getId().equals(experienceId);
        });
        UserProfileDTO profile = UserMapper.toUserProfileDTO(userRepository.save(user));
        cacheService.writeHash(userId, profile);
        return profile;
    }

    /**
     * Education section
     */
    @Transactional
    public UserProfileDTO addEducation(UUID userId, AddEducationDTO educationDTO) throws JsonProcessingException {
        User user = userHelper.getUserById(userId);
        Education entity = new Education();
        entity.setStartDate(educationDTO.startDate());
        entity.setEndDate(educationDTO.endDate());
        entity.setUniversity(educationDTO.university());
        entity.setUser(user);
        entity.setDegree(educationDTO.degree());
        
        user.getEducations().add(entity);
        UserProfileDTO profile = UserMapper.toUserProfileDTO(userRepository.save(user));
        cacheService.writeHash(userId, profile);
        return profile;
    }

    @Transactional
    public UserProfileDTO deleteEducation(UUID userId, UUID educationId) throws JsonProcessingException {
        User user = userHelper.getUserById(userId);
        List<Education> educations = user.getEducations();
        educations.removeIf((education) -> {
            return education.getId().equals(educationId);
        });
        UserProfileDTO profile = UserMapper.toUserProfileDTO(userRepository.save(user));
        cacheService.writeHash(userId, profile);
        return profile;
    }

    /**
     * Skill section
     */

    public UserProfileDTO addSkill(UUID userId, String skill) throws DuplicateSkillException, JsonProcessingException {
        User user = userHelper.getUserById(userId);
        Set<String> skills = user.getSkills();
        if (skills.contains(skill)) {
            log.warn("Add skill fail: skill {} already exists", skill);
            throw new DuplicateSkillException("Skill: " + skill + " already exists");
        }
        skills.add(skill);
        UserProfileDTO profile = UserMapper.toUserProfileDTO(userRepository.save(user));
        cacheService.writeHash(userId, profile);
        return profile;
    }

    public UserProfileDTO deleteSkill(UUID userId, String skill) throws JsonProcessingException {
        User user = userHelper.getUserById(userId);
        user.getSkills().remove(skill);
        UserProfileDTO profile = UserMapper.toUserProfileDTO(userRepository.save(user));
        cacheService.writeHash(userId, profile);
        return profile;
    }
}
