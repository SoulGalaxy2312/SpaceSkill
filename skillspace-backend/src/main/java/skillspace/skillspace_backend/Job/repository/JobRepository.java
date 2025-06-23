package skillspace.skillspace_backend.Job.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import skillspace.skillspace_backend.Job.exception.JobNotFoundException;
import skillspace.skillspace_backend.Job.model.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, UUID>, JpaSpecificationExecutor<Job> { 
    default Job getJobByIdOrThrow(UUID id) {
        return findById(id)
            .orElseThrow(() -> new JobNotFoundException(id));
    }
}