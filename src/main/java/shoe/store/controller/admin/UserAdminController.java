package shoe.store.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import shoe.store.service.UserService;

@Controller
@RequestMapping("/admin/staff")
public class UserAdminController {
	@Autowired
	UserService userService;

	@PostMapping()
	public String showUsers() {
		return "admin/staff";
	}
}
