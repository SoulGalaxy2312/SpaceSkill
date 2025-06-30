package skillspace.skillspace_backend.User.service;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import skillspace.skillspace_backend.User.response.UserProfileDTO;
import skillspace.skillspace_backend.shared.cache.LoadAndCacheService;

@Service
public class UserProfileDTOLoadAndCacheService extends LoadAndCacheService<UserProfileDTO> {
    private static final String USER_PROFILE_CACHE_KEY_PREFIX = "skillspace:user:profile";

    public UserProfileDTOLoadAndCacheService(RedisTemplate<String, String> redisTemplate) {
        super(redisTemplate);
    }

    protected Class<UserProfileDTO> getTClass() {
        return UserProfileDTO.class;
    }

    protected TypeReference<List<UserProfileDTO>> getDedicatedTypeReference() {
        return new TypeReference<List<UserProfileDTO>>() {};
    }

    public UserProfileDTO loadAndCacheUserProfileDTO(UUID userId, Supplier<UserProfileDTO> dataSupplier)
        throws JsonProcessingException {

        final String key = String.format("%s:%s", USER_PROFILE_CACHE_KEY_PREFIX, userId.toString());
        return this.loadAndCacheValueByKey(key, dataSupplier, Duration.ofMinutes(2));
    }

    public void writeHash(UUID userId, UserProfileDTO value) throws JsonProcessingException {
        final String key = String.format("%s:%s", USER_PROFILE_CACHE_KEY_PREFIX, userId.toString());
        this.writeHash(key, value, Duration.ofMinutes(2));
    }

    public void flushAll(UUID userId) throws JsonProcessingException {
        final String key = String.format("%s:%s", USER_PROFILE_CACHE_KEY_PREFIX, userId.toString());
        this.deleteCacheByPattern(key);
    }
}
