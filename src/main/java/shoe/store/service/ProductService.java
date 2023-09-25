package shoe.store.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import shoe.store.entity.Brand;
import shoe.store.entity.Product;

public interface ProductService extends BaseService<Product, Long> {

	Page<Product> listing(String categoryId, String brandId, long priceMin, long priceMax, String size, String colorId, Pageable pageable);

	Page<Product> getRelatedProductByBrand(Brand brand, Product product, Pageable pageable);

}
