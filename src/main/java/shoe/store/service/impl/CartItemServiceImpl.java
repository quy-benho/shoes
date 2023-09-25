package shoe.store.service.impl;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shoe.store.entity.CartItem;
import shoe.store.entity.User;
import shoe.store.repository.CartItemRepository;
import shoe.store.service.CartItemService;

@Service
public class CartItemServiceImpl extends BaseServiceImpl<CartItem, Long> implements CartItemService {

	@Autowired
	CartItemRepository cartItemRepository;

	@Override
	public List<CartItem> findByUser(User user) {
		return cartItemRepository.findByUser(user);
	}

	@Override
	public Optional<CartItem> findByUserAndProductSizeId(User user, Long id) {
		return cartItemRepository.findByUserAndProductSizeId(user, id);
	}

	@Override
	public void deleteByUser(User user) {
		cartItemRepository.deleteByUser(user);
	}

	@Override
	public Map<String, Object> getCartInfo(User user, boolean haslist) {

		long totalAmount = 0;
		int totalQuantity = 0;
		Map<String, Object> map = new HashMap<String, Object>();

		List<CartItem> cartItems = cartItemRepository.findByUser(user);
		for (CartItem cartItem : cartItems) {
			totalAmount += cartItem.getProductSize().getProduct().getPrice() * cartItem.getQuantity();
			totalQuantity += cartItem.getQuantity();
		}
		map.put("totalAmount", new DecimalFormat("#,###").format(totalAmount).replace(",", ".") + " ₫");
		map.put("totalQuantity", totalQuantity);
		if (haslist) map.put("cartItems", cartItems);

		return map;
	}

}
