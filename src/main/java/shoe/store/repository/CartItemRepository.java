package shoe.store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import shoe.store.entity.CartItem;
import shoe.store.entity.User;

@Repository
public interface CartItemRepository extends BaseRepository<CartItem, Long> {

    List<CartItem> findByUser(User user);

    Optional<CartItem> findByUserAndProductSizeId(User user, Long id);

    @Transactional
    void deleteByUser(User user);

}
