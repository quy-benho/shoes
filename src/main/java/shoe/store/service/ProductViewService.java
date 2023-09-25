package shoe.store.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import shoe.store.entity.Product;
import shoe.store.entity.ProductView;
import shoe.store.entity.User;

public interface ProductViewService extends BaseService<ProductView, Long> {

	Page<Product> findProductWatchedByUser(User user, Pageable pageable);

}
