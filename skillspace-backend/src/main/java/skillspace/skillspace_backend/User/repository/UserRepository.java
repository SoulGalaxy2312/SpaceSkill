package skillspace.skillspace_backend.User.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
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
}
