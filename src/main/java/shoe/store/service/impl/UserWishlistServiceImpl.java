package shoe.store.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shoe.store.entity.Product;
import shoe.store.entity.User;
import shoe.store.entity.UserWishlist;
import shoe.store.repository.UserWishlistRepository;
import shoe.store.service.UserWishlistService;

@Service
public class UserWishlistServiceImpl extends BaseServiceImpl<UserWishlist, Long> implements UserWishlistService {

	@Autowired
	UserWishlistRepository userWishlistRepository;

	@Override
	public Optional<UserWishlist> findByUserAndProduct(User user, Product product) {
		return userWishlistRepository.findByUserAndProduct(user, product);
	}

	@Override
	public List<Product> getListWishlistProductByUser(User user) {
		return userWishlistRepository.getListWishlistProductByUser(user);
	}

}
