package shoe.store.service;

import java.util.List;
import java.util.Optional;

import shoe.store.entity.Product;
import shoe.store.entity.User;
import shoe.store.entity.UserWishlist;

public interface UserWishlistService extends BaseService<UserWishlist, Long> {

	Optional<UserWishlist> findByUserAndProduct(User user, Product product);

	List<Product> getListWishlistProductByUser (User user);

}
