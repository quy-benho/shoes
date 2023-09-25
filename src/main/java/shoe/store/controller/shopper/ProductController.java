package shoe.store.controller.shopper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import shoe.store.entity.Product;
import shoe.store.entity.ProductView;
import shoe.store.entity.User;
import shoe.store.entity.UserWishlist;
import shoe.store.security.CurrentUser;
import shoe.store.security.UserPrincipal;
import shoe.store.service.BrandService;
import shoe.store.service.CategoryService;
import shoe.store.service.ColorService;
import shoe.store.service.ProductService;
import shoe.store.service.ProductSizeService;
import shoe.store.service.ProductViewService;
import shoe.store.service.UserWishlistService;
import shoe.store.util.Validator;

@Controller
public class ProductController {

	@GetMapping("/product-listing")
	public String showProductListing(ModelMap model, @RequestParam(value = "sortSelect", required = false) String sortSelect,
													 @RequestParam(value = "categoryId", required = false) String categoryId,
													 @RequestParam(value = "brandId", required = false) String brandId,
													 @RequestParam(value = "priceMin", required = false) String priceMin,
													 @RequestParam(value = "priceMax", required = false) String priceMax,
													 @RequestParam(value = "size", required = false) String size,
													 @RequestParam(value = "colorId", required = false) String colorId,
													 @RequestParam(value = "pageRequest", required = false) String pageRequest,
													 @CurrentUser UserPrincipal userPrincipal,
													 HttpServletResponse res
													 ) {

		String field = "createdAt";
		boolean isDescending = true;
		boolean isSignedIn = userPrincipal != null;
		List<Product> products = new ArrayList<Product>();
		List<Product> productWatched = new ArrayList<Product>();
		int page = pageRequest == null ? 0 : Integer.valueOf(pageRequest);

		categoryId = Validator.checkFilterId(categoryId);
		brandId = Validator.checkFilterId(brandId);
		size = Validator.checkFilterId(size);
		colorId = Validator.checkFilterId(colorId);
		long priceMin_ = Validator.checkFilterPrice(priceMin, true);
		long priceMax_ = Validator.checkFilterPrice(priceMax, false);

		if (Validator.checkId(sortSelect)) {
			switch (Integer.valueOf(sortSelect)) {
			case 1:
				field = "price";
				isDescending = false;
				break;

			case 2:
				field = "price";
				break;

			default:
				break;
			}
		}

		try {
			if (isDescending) {
				products = productService.listing(categoryId, brandId, priceMin_, priceMax_, size, colorId, PageRequest.of(page, 12, Sort.by(field).descending())).getContent();
			} else {
				products = productService.listing(categoryId, brandId, priceMin_, priceMax_, size, colorId, PageRequest.of(page, 12, Sort.by(field).ascending())).getContent();
			}

			if (isSignedIn) {
				User user = userPrincipal.getCurrentUser();
				List<Product> productWishlists = userWishlistService.getListWishlistProductByUser(user);
				products.forEach(product -> {
					if (productWishlists.contains(product)) product.setIsWishList(true);
				});
				productWatched = productViewService.findProductWatchedByUser(user, PageRequest.of(0, 3)).getContent();
			}

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

		model.addAttribute("categories", categoryService.findAll());
		model.addAttribute("brands", brandService.findAll());
		model.addAttribute("sizes", productSizeService.getAllSizes());
		model.addAttribute("colors", colorService.findAll());
		model.addAttribute("productWatched", productWatched);
		model.addAttribute("isSignedIn", isSignedIn);
		model.addAttribute("products", products);

		if (page == 0) return "shopper/product-listing";
		else {
			if (products.size() == 0) {
				try {
					PrintWriter out = res.getWriter();
			        out.println("0");
			        out.close();
				} catch (IOException e) {
					System.out.println(e);
				}
			}
			return "shopper/product-listing :: product-listing-item";
		}
	}

	@GetMapping("/search")
	public String searchProduct(ModelMap model, @RequestParam(value = "q", required = false) String text, @CurrentUser UserPrincipal userPrincipal) {

		boolean isSignedIn = userPrincipal != null;
		List<Product> products = productService.search(text, PageRequest.of(0, 20)).getContent();
		if (isSignedIn) {
			User user = userPrincipal.getCurrentUser();
			List<Product> productWishlists = userWishlistService.getListWishlistProductByUser(user);
			products.forEach(product -> {
				if (productWishlists.contains(product)) product.setIsWishList(true);
			});
		}

		model.addAttribute("products", products);
		model.addAttribute("isSignedIn", isSignedIn);
		model.addAttribute("text", text);
		return "shopper/search";
	}

	@GetMapping("/product/{id}")
	public String showProductDetail(ModelMap model, @PathVariable String id, @CurrentUser UserPrincipal userPrincipal) {

		boolean isSignedIn = userPrincipal != null;
		if(Validator.checkId(id)) {
			Product product = productService.findById(Long.valueOf(id)).orElse(null);
			List<Product> relatedProducts = productService.getRelatedProductByBrand(product.getBrand(), product, PageRequest.of(0, 5)).getContent();

			if (product != null) {
				if (isSignedIn) {
					User user = userPrincipal.getCurrentUser();
					List<Product> productWishlists = userWishlistService.getListWishlistProductByUser(user);
					if (productWishlists.contains(product)) product.setIsWishList(true);

					relatedProducts.forEach(relatedProduct -> {
						if (productWishlists.contains(relatedProduct)) relatedProduct.setIsWishList(true);
					});
				}

				model.addAttribute("product", product);
				model.addAttribute("isSignedIn", isSignedIn);
				model.addAttribute("relatedProducts", relatedProducts);
				return "shopper/product";
			}
		}
		return "redirect:/index";
	}

	@PostMapping("/product-quick-view/{id}")
	public String showProductQuickView(ModelMap model, @PathVariable String id) {

		if(Validator.checkId(id)) {
			Product product = productService.findById(Long.valueOf(id)).orElse(null);

			if (product != null) {
				model.addAttribute("productView", product);
				return "shopper/fragments/modal :: quickView";
			}
		}
		return "shopper/fragments/modal :: quickView";
	}

	@ResponseBody
	@PostMapping("/product-wishlist/{id}")
	public String setProductWishList(@PathVariable String id, @CurrentUser UserPrincipal userPrincipal) {

		if (userPrincipal == null) return "not-signed-in";

		if(Validator.checkId(id)) {
			Product product = productService.findById(Long.valueOf(id)).orElse(null);
			User user = userPrincipal.getCurrentUser();
			UserWishlist userWishlist = userWishlistService.findByUserAndProduct(user, product).orElse(null);

			if (userWishlist == null) {
				userWishlistService.save(new UserWishlist(user, product));
				return "added";
			} else {
				userWishlistService.delete(userWishlist);
				return "removed";
			}

		} else return "error";
	}

	@ResponseBody
	@PostMapping("/product-view/{id}")
	public void setProductView(@PathVariable String id, @CurrentUser UserPrincipal userPrincipal) {

		if(Validator.checkId(id)) {
			Product product = productService.findById(Long.valueOf(id)).orElse(null);
			User user = userPrincipal == null ? null : userPrincipal.getCurrentUser();
			productViewService.save(new ProductView(product, user));
		}
	}

	@PostMapping("/product/more")
	public String getMoreProduct() {

		
		
		
		return "";
	}

	@Autowired
	ProductService productService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	BrandService brandService;

	@Autowired
	ProductSizeService productSizeService;

	@Autowired
	ColorService colorService;

	@Autowired
	UserWishlistService userWishlistService;

	@Autowired
	ProductViewService productViewService;

}
