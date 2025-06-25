package skillspace.skillspace_backend.BaseUser.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import skillspace.skillspace_backend.BaseUser.model.BaseUser;

@Repository
public interface BaseUserRepository extends JpaRepository<BaseUser, UUID> {
    Optional<BaseUser> findByEmail(String email);
    Optional<BaseUser> findById(UUID id);

    default BaseUser findByIdOrThrow(UUID id) {
        return findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    default BaseUser findByEmailOrThrow(String email) {
        return findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
}