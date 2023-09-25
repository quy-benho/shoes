package shoe.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import shoe.store.entity.Brand;
import shoe.store.entity.Product;
import shoe.store.repository.ProductRepository;
import shoe.store.service.ProductService;

@Service
public class ProductServiceImpl extends BaseServiceImpl<Product, Long> implements ProductService {

	@Autowired
	ProductRepository productRepository;

	@Override
	public Page<Product> listing(String categoryId, String brandId, long priceMin, long priceMax, String size, String colorId, Pageable pageable) {

		return productRepository.listing(categoryId, brandId, priceMin, priceMax, size, colorId, pageable);
	}

	@Override
	public Page<Product> getRelatedProductByBrand(Brand brand, Product product, Pageable pageable) {

		return productRepository.getRelatedProductByBrand(brand, product, pageable);
	}

}
