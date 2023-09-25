package shoe.store.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import shoe.store.entity.Product;
import shoe.store.entity.ProductView;
import shoe.store.entity.User;

@Repository
public interface ProductViewRepository extends BaseRepository<ProductView, Long> {

	@Query("select pv.product from ProductView pv where pv.user = :user group by pv.product order by pv.createdAt desc")
	Page<Product> findProductWatchedByUser(@Param("user") User user, Pageable pageable);

}
