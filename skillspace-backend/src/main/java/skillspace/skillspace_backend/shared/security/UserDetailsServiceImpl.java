package skillspace.skillspace_backend.shared.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import skillspace.skillspace_backend.Company.repository.CompanyRepository;
import skillspace.skillspace_backend.User.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public UserDetailsServiceImpl(UserRepository userRepository, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(UserPrincipal::new)
                .or(() -> companyRepository.findByEmail(email)
                                            .map(UserPrincipal::new))
                .orElseThrow(() -> new UsernameNotFoundException("No user or company found with email: " + email));
    }
}
