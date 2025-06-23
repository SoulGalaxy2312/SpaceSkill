package skillspace.skillspace_backend.User.service.following;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.User.repository.UserRepository;
import skillspace.skillspace_backend.User.service.UserHelper;
import skillspace.skillspace_backend.shared.enums.UserRole;
import skillspace.skillspace_backend.shared.response.StatusResponseDTO;

@Service
@Slf4j
public class UserFollowingMechanism implements IFollowingMechanism {

    private final UserHelper userHelper;
    private final UserRepository userRepository;

    public UserFollowingMechanism(UserHelper userHelper, UserRepository userRepository) {
        this.userHelper = userHelper;
        this.userRepository = userRepository;
    }

    @Override
    public StatusResponseDTO follow(User follower, UUID targetId) {
        log.info("User {} is trying to follow user {}", follower.getId(), targetId);

        User target = userHelper.getUserById(targetId);
        if (follower.getId().equals(target.getId())) {
            throw new IllegalArgumentException("You cannot follow yourself");
        }

        List<User> connections = follower.getConnections();
        if (connections.contains(target)) {
            throw new IllegalArgumentException("You have already followed this user");
        }

        connections.add(target);
        target.getConnections().add(follower);
        userRepository.save(follower);
        return new StatusResponseDTO(true, "Followed successfully");
    }

    @Override
    public StatusResponseDTO unfollow(User follower, UUID targetId) {
        User target = userHelper.getUserById(targetId);
        if (follower.getId().equals(target.getId())) {
            throw new IllegalArgumentException("You cannot unfollow yourself");
        }
        List<User> connections = follower.getConnections();
        if (!connections.contains(target)) {
            throw new IllegalArgumentException("You have not followed this user");
        }
        connections.remove(target);
        target.getConnections().remove(follower);
        userRepository.save(follower);
        userRepository.save(target);
        return new StatusResponseDTO(true, "Unfollowed successfully");
    }

    @Override
    public UserRole getTargetRole() {
        return UserRole.USER;
    }
}
