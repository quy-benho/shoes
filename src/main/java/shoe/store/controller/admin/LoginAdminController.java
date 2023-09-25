package shoe.store.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginAdminController {

	@GetMapping("admin/login")
	public String showLogin() {

		return "admin/login";
	}

}
