package shoe.store.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import shoe.store.entity.User;

public class UserPrincipal extends org.springframework.security.core.userdetails.User {

    private static final long serialVersionUID = 769250655889495809L;

    private User user;

    public UserPrincipal(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getEmail(), user.getPassword(), authorities);
        this.user = user;
    }

    public User getCurrentUser() {
        return user;
    }

    public String getRole() {
    	return user != null ? user.getRole() : null;
    }
}
