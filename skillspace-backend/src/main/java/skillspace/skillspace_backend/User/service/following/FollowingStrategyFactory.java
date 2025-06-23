package skillspace.skillspace_backend.User.service.following;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.shared.enums.UserRole;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FollowingStrategyFactory {
    
    private final Map<UserRole, IFollowingMechanism> strategies;

    public FollowingStrategyFactory(List<IFollowingMechanism> strategies) {
        this.strategies = strategies.stream()
            .collect(Collectors.toMap(
                IFollowingMechanism::getTargetRole, 
                strategy -> strategy));
    }

    public IFollowingMechanism getStrategy(UserRole targetRole) {
        IFollowingMechanism strategy = strategies.get(targetRole);
        log.info("Following strategy for role {}", targetRole);
        if (strategy == null) {
            throw new IllegalArgumentException("No following mechanism found for role: " + targetRole);
        }
        return strategy;
    }
}
