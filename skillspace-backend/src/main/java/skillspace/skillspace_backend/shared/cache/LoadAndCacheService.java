package skillspace.skillspace_backend.shared.cache;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class LoadAndCacheService<T> extends CustomHashMapping<T>{
    public LoadAndCacheService(RedisTemplate<String, String> redisTemplate) {
        super(redisTemplate);
    }
    
    protected abstract Class<T> getTClass();
    protected abstract TypeReference<List<T>> getDedicatedTypeReference();

    protected T loadAndCacheValueByKey(String key, Supplier<T> value, Duration duration) throws JsonProcessingException {
        if (duration == null) duration = Duration.ofMinutes(30);
        log.info("Load and cache value for key {}", key);
        T hash = this.loadHash(key, getTClass());

        if (Objects.isNull(hash)) {
            T data = value.get();
            this.writeHash(key, data, duration);
            return data;
        }
        return hash;
    }

    protected List<T> loadAndCacheListByKey(String key, Supplier<List<T>> value, Duration duration) throws JsonProcessingException {
        if (duration == null) duration = Duration.ofMinutes(30);
        log.info("Load and cache value for key {}", key);
        List<T> hash = this.loadHash(key, getDedicatedTypeReference());
        
        if (CollectionUtils.isEmpty(hash)) {
            List<T> data = value.get();
            this.writeHash(key, data, duration);
            return data;
        }
        return hash;
    }
}
