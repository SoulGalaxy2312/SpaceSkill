package skillspace.skillspace_backend.Application.service.retrievingApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import skillspace.skillspace_backend.shared.enums.UserRole;

@Service
public class RetrievingStrategyFactory {

    private final Map<UserRole, RetrievingStrategy> strategies = new HashMap<>();
    
    public RetrievingStrategyFactory(List<RetrievingStrategy> strategies) {
        for (RetrievingStrategy strategy : strategies) {
            this.strategies.put(strategy.getTargetUserRole(), strategy);
        }
    }

    public RetrievingStrategy getTargetRetrievingStrategy(UserRole role) {
        return this.strategies.get(role);
    }
}
