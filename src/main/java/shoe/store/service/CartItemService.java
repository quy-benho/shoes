package shoe.store.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import shoe.store.entity.CartItem;
import shoe.store.entity.User;

public interface CartItemService extends BaseService<CartItem, Long> {

    List<CartItem> findByUser(User user);

    Optional<CartItem> findByUserAndProductSizeId(User user, Long id);

    void deleteByUser(User user);

    Map<String, Object> getCartInfo(User user, boolean haslist);

}
