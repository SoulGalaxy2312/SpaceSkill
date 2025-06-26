package skillspace.skillspace_backend.Admin.service;

import org.springframework.data.domain.Pageable;

import skillspace.skillspace_backend.Admin.response.AdminBaseUserBrief;
import skillspace.skillspace_backend.shared.response.PagingDTO;

public interface AdminReadService {
    PagingDTO<AdminBaseUserBrief> getBaseUsers(Pageable pageable);
}
