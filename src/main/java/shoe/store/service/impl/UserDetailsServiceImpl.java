package shoe.store.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import shoe.store.entity.User;
import shoe.store.repository.UserRepository;
import shoe.store.security.UserPrincipal;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userRepository.loginByEmail(email).orElse(null);

		if (user == null) {
            throw new UsernameNotFoundException("Email " + email + " was not found in the database");
        }

		String roleID = user.getRole();

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if (roleID != null) {
			 GrantedAuthority authority = new SimpleGrantedAuthority(roleID);
			 authorities.add(authority);
		}

		return new UserPrincipal(user, authorities);
	}
}
