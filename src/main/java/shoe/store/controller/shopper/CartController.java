package shoe.store.controller.shopper;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import shoe.store.entity.CartItem;
import shoe.store.entity.ProductSize;
import shoe.store.entity.User;
import shoe.store.security.CurrentUser;
import shoe.store.security.IsAuthenticated;
import shoe.store.security.UserPrincipal;
import shoe.store.service.CartItemService;
import shoe.store.service.ProductService;
import shoe.store.service.ProductSizeService;
import shoe.store.util.Validator;

@Controller
@IsAuthenticated
public class CartController {

	@Autowired
	CartItemService cartItemService;

	@Autowired
	ProductService productService;

	@Autowired
	ProductSizeService productSizeService;

	Validator validator = new Validator();

	@GetMapping("/cart")
	public String showCart(ModelMap model, @CurrentUser UserPrincipal userPrincipal) {

		User user = userPrincipal.getCurrentUser();
		List<CartItem> cartItems = cartItemService.findByUser(user);
		if (cartItems.size() == 0) return "shopper/empty-cart";

		Map<String, Object> cartInfo = cartItemService.getCartInfo(user, false);
		model.addAttribute("customer", user);
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("totalAmount", cartInfo.get("totalAmount"));

		return "shopper/cart";
	}

	@PostMapping("/cart-header")
	public String getCartHeader(ModelMap model, @CurrentUser UserPrincipal userPrincipal) {

		User user = userPrincipal.getCurrentUser();
		List<CartItem> cartItems = cartItemService.findByUser(user);

		Map<String, Object> cartInfo = cartItemService.getCartInfo(user, false);
		model.addAttribute("cartItemHeaders", cartItems);
		model.addAttribute("totalQuantity", cartInfo.get("totalQuantity"));
		model.addAttribute("totalAmount", cartInfo.get("totalAmount"));

		return "shopper/fragments/common :: cart-header";
	}

	@ResponseBody
	@PostMapping("/remove-cart-header/{id}")
	public Map<String, Object> removeCartHeader(@PathVariable String id, @CurrentUser UserPrincipal userPrincipal) {

		User user = userPrincipal.getCurrentUser();
		if (Validator.checkId(id)) {
			cartItemService.deleteById(Long.valueOf(id));
		}
		return cartItemService.getCartInfo(user, false);
	}

	@ResponseBody
	@PostMapping("/add-to-cart-header/{id}")
	public String addToCart(@PathVariable String id, @CurrentUser UserPrincipal userPrincipal) {

		String res = "Invalid";
		User user = userPrincipal.getCurrentUser();
		if (Validator.checkId(id)) {
			ProductSize productSize = productSizeService.findById(Long.valueOf(id)).get();
			int quantityLeft = productSize.getQuantity();

			CartItem cartItem;
			cartItem = cartItemService.findByUserAndProductSizeId(user, productSize.getId()).orElse(null);
			if (cartItem == null) {
				cartItem = new CartItem(user, productSize, 1);

			} else {
				cartItem.setQuantity(cartItem.getQuantity() + 1);
			}

			if (cartItem.getQuantity() > 5) {
				res = "Giới hạn đặt 5 sản phẩm cùng loại";
			} else if (cartItem.getQuantity() > quantityLeft) {
				res = "Đã vượt quá sỗ lượng sản phẩm còn lại trong kho";
			} else {
				res = "";
				cartItemService.save(cartItem);
			}
		}
		return res;
	}

	@GetMapping("/remove-cart/{id}")
	public String removeCart(@PathVariable String id) {

		cartItemService.deleteById(Long.valueOf(id));
		return "redirect:/cart";
	}

	@GetMapping("/change-quantity")
	public String changeQuantity(@RequestParam("id") Long id, @RequestParam("change") String change) {

		CartItem cartItem = cartItemService.findById(id).get();
		int quantity = cartItem.getQuantity();

		if (change.equals("plus")) {
			cartItem.setQuantity(quantity + 1);
		} else {
			if (quantity > 1) {
				cartItem.setQuantity(quantity - 1);
			}
		}

		cartItemService.save(cartItem);
		return "redirect:/cart";
	}

}
