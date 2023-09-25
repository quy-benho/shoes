package shoe.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import shoe.store.entity.Product;
import shoe.store.entity.ProductView;
import shoe.store.entity.User;
import shoe.store.repository.ProductViewRepository;
import shoe.store.service.ProductViewService;

@Service
public class ProductViewServiceImpl extends BaseServiceImpl<ProductView, Long> implements ProductViewService {

	@Autowired
	ProductViewRepository productViewRepository;

	@Override
	public Page<Product> findProductWatchedByUser(User user, Pageable pageable) {
		// TODO Auto-generated method stub
		return productViewRepository.findProductWatchedByUser(user, pageable);
	}

}
