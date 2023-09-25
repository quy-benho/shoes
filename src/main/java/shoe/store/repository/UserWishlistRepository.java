package shoe.store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import shoe.store.entity.Product;
import shoe.store.entity.User;
import shoe.store.entity.UserWishlist;

@Repository
public interface UserWishlistRepository extends BaseRepository<UserWishlist, Long> {

    Optional<UserWishlist> findByUserAndProduct(User user, Product product);

    @Query("select uw.product from UserWishlist uw where uw.user = :user")
    List<Product> getListWishlistProductByUser(@Param("user") User user);

}
