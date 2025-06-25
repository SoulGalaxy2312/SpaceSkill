package skillspace.skillspace_backend.Admin.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import skillspace.skillspace_backend.Admin.mapper.AdminBaseUserBriefMapper;
import skillspace.skillspace_backend.Admin.response.AdminBaseUserBrief;
import skillspace.skillspace_backend.BaseUser.repository.BaseUserRepository;

@Service
public class AdminReadServiceImpl implements AdminReadService {

    private final BaseUserRepository baseUserRepository;

    public AdminReadServiceImpl(BaseUserRepository baseUserRepository) {
        this.baseUserRepository = baseUserRepository;
    }

    @Override
    public List<AdminBaseUserBrief> getBaseUsers(Pageable pageable) {
        return baseUserRepository.findAll(pageable)
            .stream()
            .map(AdminBaseUserBriefMapper::toAdminBaseUserBrief)
            .toList();
    }
    
}
