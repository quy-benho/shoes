package shoe.store.controller.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import shoe.store.entity.Product;
import shoe.store.payload.Meta;
import shoe.store.payload.response.ListProductsResponse;
import shoe.store.service.ProductService;

@Controller
@RequestMapping("/admin/products")
public class ProductAdminController {

	@Autowired
	ProductService productService;

	@PostMapping()
	public String showProducts() {

		return "admin/products";
	}

	@ResponseBody
	@PostMapping(path = "/data")
	public ListProductsResponse getDataProducts(@RequestParam Map<String, String> request) {

		Meta meta = new Meta(request);
		PageRequest pageRequest = PageRequest.of(meta.getPage() - 1, meta.getPerpage());
		Page<Product> productsPage = productService.search(meta.getQuerySearch(), pageRequest);

		meta.setPages(productsPage.getTotalPages());
		meta.setTotal(productsPage.getTotalElements());
		ListProductsResponse productsResponse = new ListProductsResponse(meta, productsPage.getContent());

		return productsResponse;
	}
}
