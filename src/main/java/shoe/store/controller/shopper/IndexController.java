package shoe.store.controller.shopper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import shoe.store.entity.Brand;
import shoe.store.entity.Product;
import shoe.store.entity.User;
import shoe.store.security.CurrentUser;
import shoe.store.security.UserPrincipal;
import shoe.store.service.BrandService;
import shoe.store.service.ProductService;
import shoe.store.service.UserWishlistService;

@Controller
public class IndexController {

	@Autowired
	ProductService productService;

	@Autowired
	BrandService brandService;

	@Autowired
	UserWishlistService userWishlistService;

	@GetMapping("/*")
	public String redirect() {

		return "redirect:/index";
	}

	@GetMapping("/index")
	public String showIndex(ModelMap model, @RequestParam(value = "page", required = false) String pageRequest, @CurrentUser UserPrincipal userPrincipal) {

		boolean isSignedIn = userPrincipal != null;
		List<Product> products = productService.searchAll(PageRequest.of(0, 8)).getContent();
		if (isSignedIn) {
			User user = userPrincipal.getCurrentUser();
			List<Product> productWishlists = userWishlistService.getListWishlistProductByUser(user);
			products.forEach(product -> {
				if (productWishlists.contains(product)) product.setIsWishList(true);
			});
		}

		model.addAttribute("products", products);
		model.addAttribute("isSignedIn", isSignedIn);
//		model.addAttribute("productView", productService.findById(1L).get());
		model.addAttribute("newProducts", productService.searchAll(PageRequest.of(3, 3)));
		model.addAttribute("specialProducts", productService.searchAll(PageRequest.of(4, 3)));
		model.addAttribute("featuredProducts", productService.searchAll(PageRequest.of(5, 3)));
		return "shopper/index";
	}

	@GetMapping("about")
	public String showAbout() {

		return "shopper/about";
	}

	@GetMapping("contact")
	public String showContact() {

		return "shopper/contact";
	}

	@ResponseBody
	@PostMapping("/signed-in")
	public String isSignIn(@CurrentUser UserPrincipal userPrincipal) {
		return userPrincipal != null ? userPrincipal.getCurrentUser().getName() : "";
	}

	@ResponseBody
	@GetMapping("/brands")
	public List<Brand> getBrands() {
		return brandService.findAll();
	}

}
