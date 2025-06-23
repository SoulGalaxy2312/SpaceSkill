package skillspace.skillspace_backend.User.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import skillspace.skillspace_backend.User.exception.UserNotFoundException;
import skillspace.skillspace_backend.User.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> { 
    default User getUserByIdOrThrow(UUID userId) {
        return findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }
    default User getUserByEmailOrThrow(String email) {
        return findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }
    
    Optional<User> findByEmail(String email);

    @Query(
        value = """
                SELECT EXISTS (
                    SELECT 1 
                    FROM user_connections 
                    WHERE connected_user_id = :followed 
                    AND follower_user_id = :follower
                )
                """,
        nativeQuery = true
    )
    boolean isUserFollowedByCurrentBaseUser(UUID followed, UUID follower);

    @Query(
        value = """
                SELECT EXISTS (
                    SELECT 1 
                    FROM user_following_companies 
                    WHERE company_id = :company
                    AND user_id = :follower
                )
                """,
        nativeQuery = true
    )
    boolean isCompanyFollowedByCurrentBaseUser(UUID company, UUID follower);
}
