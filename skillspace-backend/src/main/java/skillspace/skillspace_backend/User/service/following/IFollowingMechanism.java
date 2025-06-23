package skillspace.skillspace_backend.User.service.following;

import java.util.UUID;

import skillspace.skillspace_backend.User.model.User;
import skillspace.skillspace_backend.shared.enums.UserRole;
import skillspace.skillspace_backend.shared.response.StatusResponseDTO;

public interface IFollowingMechanism {
    /**
     * Follow a user.
     *
     * @param userId the ID of the user to follow
     * @param dto    the follow request data transfer object
     * @return a status response indicating success or failure
     */
    StatusResponseDTO follow(User follower, UUID targetId);

    /**
     * Unfollow a user.
     *
     * @param userId the ID of the user to unfollow
     * @param dto    the follow request data transfer object
     * @return a status response indicating success or failure
     */
    StatusResponseDTO unfollow(User follower, UUID targetId);

    UserRole getTargetRole();
}