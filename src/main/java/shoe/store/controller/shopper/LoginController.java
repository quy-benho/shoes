package shoe.store.controller.shopper;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shoe.store.entity.User;
import shoe.store.security.Role;
import shoe.store.service.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
    private AuthenticationManager authenticationManager;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/login")
	public String showLoginRedirect() {

		return "redirect:/customer/login";
	}

	@GetMapping("/logout")
	public String showLogoutRedirect() {

		return "redirect:/customer/logout";
	}

	@GetMapping("customer/login")
	public String showLogin() {

		return "shopper/login";
	}

	@GetMapping("signup")
	public String showRegister() {

		return "shopper/register";
	}

	@PostMapping("register-user")
	public String registerUser(@RequestParam Map<String, String> registerUser) {

		String name = registerUser.get("name");
		String email = registerUser.get("email");
		String phone = registerUser.get("phone");
		String password = registerUser.get("password");

		try {
			User user = new User(name, email, phone);
			user.setPassword(bCryptPasswordEncoder.encode(password));
			user.setRole(Role.CUSTOMER);
			userService.save(user);

			UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
			authenticationManager.authenticate(authentication);

			if(authentication.isAuthenticated()) {
				SecurityContextHolder.getContext().setAuthentication(authentication);
				return "redirect:/index";
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		return "redirect:/signup";
	}
}
