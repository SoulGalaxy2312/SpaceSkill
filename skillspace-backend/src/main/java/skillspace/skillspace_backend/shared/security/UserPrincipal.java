package skillspace.skillspace_backend.shared.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import skillspace.skillspace_backend.BaseUser.model.BaseUser;

public class UserPrincipal implements UserDetails {
    private final BaseUser user;

    public UserPrincipal(BaseUser user) {
        this.user = user;
    }

    public String getUsername() {
        return user.getEmail();
    }

    public String getPassword() {
        return user.getPassword();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }
}
