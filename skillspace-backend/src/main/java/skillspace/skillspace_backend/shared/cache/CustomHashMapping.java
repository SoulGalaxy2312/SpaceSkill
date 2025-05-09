package skillspace.skillspace_backend.shared.cache;

import java.time.Duration;
import java.util.List;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.KeyScanOptions;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CustomHashMapping<T> {
    
    private final RedisTemplate<String, String> redisTemplate;
    private ObjectMapper mapper = new ObjectMapper();

    public CustomHashMapping(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    protected void writeHash(String key, T value, Duration duration) throws JsonProcessingException {
        log.info("Write cache for key {}", key);
        String serialized = mapper.writeValueAsString(value);
        redisTemplate.opsForValue().set(key, serialized);
    }

    protected void writeHash(String key, List<T> value, Duration duration) throws JsonProcessingException {
        log.info("Write cache for key {}", key);
        String serialized = mapper.writeValueAsString(value);
        redisTemplate.opsForValue().set(key, serialized);
    }

    protected T loadHash(String key, Class<T> clazz) throws JsonProcessingException {
        log.info("Load cache for key {}", key);
        String hash = redisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(hash)) return null;
        return mapper.readValue(hash, clazz);
    }

    protected List<T> loadHash(String key, TypeReference<List<T>> typeReference) throws JsonProcessingException {
        log.info("Load cache for key {}", key);        
        String hash = redisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(hash)) return null;
        return mapper.readValue(hash, typeReference);
    }

    protected void deleteCacheByPattern(String pattern) {
        // redisTemplate.keys(pattern).forEach(_key -> {
        //     log.info("Delete cache for key {}", _key);
        //     redisTemplate.delete(_key);
        // });

        redisTemplate.execute((RedisCallback<Void>) connection -> {
            try (Cursor<byte[]> cursor = connection.scan(KeyScanOptions.scanOptions()
                                                            .match(pattern)
                                                            .count(10)
                                                            .build())) {
                
                while (cursor.hasNext()) {  
                    String key = new String(cursor.next());
                    log.info("Deleting key {}", key);
                    redisTemplate.delete(key);
                }

            } // cursor.close() is automatically called here

            return null;
        });
    }
}
