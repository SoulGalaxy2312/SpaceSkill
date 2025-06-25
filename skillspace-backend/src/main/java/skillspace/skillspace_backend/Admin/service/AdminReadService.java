package skillspace.skillspace_backend.Admin.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import skillspace.skillspace_backend.Admin.response.AdminBaseUserBrief;

public interface AdminReadService {
    List<AdminBaseUserBrief> getBaseUsers(Pageable pageable);
}
