package skillspace.skillspace_backend.Admin.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import skillspace.skillspace_backend.Admin.mapper.AdminBaseUserBriefMapper;
import skillspace.skillspace_backend.Admin.response.AdminBaseUserBrief;
import skillspace.skillspace_backend.BaseUser.model.BaseUser;
import skillspace.skillspace_backend.BaseUser.repository.BaseUserRepository;
import skillspace.skillspace_backend.shared.response.PagingDTO;

@Service
public class AdminReadServiceImpl implements AdminReadService {

    private final BaseUserRepository baseUserRepository;

    public AdminReadServiceImpl(BaseUserRepository baseUserRepository) {
        this.baseUserRepository = baseUserRepository;
    }

    @Override
    public PagingDTO<AdminBaseUserBrief> getBaseUsers(Pageable pageable) {  
        Page<BaseUser> page = baseUserRepository.findAll(pageable);
        return new PagingDTO<>(
            page.stream()
                .map(AdminBaseUserBriefMapper::toAdminBaseUserBrief)
                .toList(),
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages()
        );
    }
    
}
