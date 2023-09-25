package shoe.store.controller.shopper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shoe.store.entity.OrderWeb;
import shoe.store.entity.User;
import shoe.store.repository.DeliveryLogRepository;
import shoe.store.security.CurrentUser;
import shoe.store.security.IsAuthenticated;
import shoe.store.security.UserPrincipal;
import shoe.store.service.OrderWebService;
import shoe.store.service.UserService;
import shoe.store.service.UserWishlistService;
import shoe.store.util.DeliveryStatus;
import shoe.store.util.PaymentStatus;
import shoe.store.util.Validator;

@Controller
@IsAuthenticated
@RequestMapping("/my-account")
public class MyAccountController {

	@Autowired
	OrderWebService orderWebService;

	@Autowired
	UserWishlistService userWishlistService;

	@Autowired
	UserService userService;

	@Autowired
	DeliveryLogRepository deliveryLogRepository;

	@GetMapping("/")
	public String redirectMyAccount() {
		
		return "redirect:/my-account";
	}

	@GetMapping("/order")
	public String showMyOrder() {

		return "redirect:/my-account";
	}

	@GetMapping("")
	public String showMyAccount(ModelMap model, @CurrentUser UserPrincipal userPrincipal) {
		User user = userPrincipal.getCurrentUser();
		List<OrderWeb> orderWebs = orderWebService.findByUser(user);

        model.addAttribute("user", user);
        model.addAttribute("orderWebs", orderWebs);
		return "shopper/account";
	}

	@GetMapping("/order/{id}")
	public String showMyOrderDetail(@PathVariable String id, ModelMap model, @CurrentUser UserPrincipal userPrincipal) {

		if(Validator.checkId(id)) {
			OrderWeb orderWeb = orderWebService.findById(Long.valueOf(id)).get();
			if (orderWeb.getUser().getId().equals(userPrincipal.getCurrentUser().getId())) {
				String paymentStatus = orderWeb.getPaymentStatus();
				String deliveryStatus = orderWeb.getDeliveryStatus();
				boolean btnCancel = true;
				String psColor = "paid";

				switch (paymentStatus) {
				case PaymentStatus.UNPAID:
					psColor = "unpaid";
					break;
				case PaymentStatus.REFUNDED:
					psColor = "refunded";
					break;
				case PaymentStatus.CANCELLED:
					psColor = "unpaid";
					break;
				default:
					break;
				}

				if (paymentStatus.equalsIgnoreCase(PaymentStatus.CANCELLED) 
						|| deliveryStatus.equalsIgnoreCase(DeliveryStatus.CANCELLED)
						|| deliveryStatus.equalsIgnoreCase(DeliveryStatus.SUCCESSFUL)) {
					btnCancel = false;
				}

		        model.addAttribute("psColor", psColor);
		        model.addAttribute("orderWeb", orderWeb);
		        model.addAttribute("dlog", deliveryLogRepository.findByOrderWeb(orderWeb));
		        model.addAttribute("btnCancel", btnCancel);
				return "shopper/order-detail";
			}
		}
		return "redirect:/my-account";
	}

	@GetMapping("/edit")
	public String editMyAccount(ModelMap model, @CurrentUser UserPrincipal userPrincipal) {

        model.addAttribute("user", userService.findById(userPrincipal.getCurrentUser().getId()).get());
		return "shopper/account_address_fields";
	}

	@PostMapping("/edit")
	public String saveMyAccount(ModelMap model, @RequestParam String fullname,
												@RequestParam String gender,
												@RequestParam String dateOfBirth,
												@RequestParam String phone,
												@RequestParam String address, 
												@CurrentUser UserPrincipal userPrincipal) {

		User user = userService.findById(userPrincipal.getCurrentUser().getId()).get();
		user.setName(fullname);
		user.setGender(gender.equals("1"));
		try {
			user.setDateOfBirth(new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth).getTime()));
		} catch (ParseException e) {
			System.out.println(e);
		}
		user.setPhone(phone);
		user.setAddress(address);
		userService.save(user);

        model.addAttribute("user", user);
		return "shopper/account_address_fields";
	}

	@GetMapping("/wishlist")
	public String showMyWishlist(ModelMap model, @CurrentUser UserPrincipal userPrincipal) {

		User user = userPrincipal.getCurrentUser();

		model.addAttribute("products", userWishlistService.getListWishlistProductByUser(user));
		return "shopper/wishlist";
	}
}
