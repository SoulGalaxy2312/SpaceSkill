package skillspace.skillspace_backend.shared.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import skillspace.skillspace_backend.BaseUser.repository.BaseUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    private final BaseUserRepository baseUserRepository;

    public UserDetailsServiceImpl(BaseUserRepository baseUserRepository) {
        this.baseUserRepository = baseUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return baseUserRepository.findByEmail(email)
                .map(UserPrincipal::new)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with email: " + email));
    }
}
