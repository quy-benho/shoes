package shoe.store.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class DashboardAdminController {

	@GetMapping("")
	public String redirectAdmin() {

		return "redirect:/admin/products";
	}

	@GetMapping("/*")
	public String redirectAdminGet() {

		return "admin/layout";
	}

	@PostMapping("/*")
	public String redirectAdminPost() {

		return "error/error-1b :: content";
	}

	@PostMapping("/dashboard")
	public String showDashboard(ModelMap model) {

		return "admin/dashboard";
	}

}
