package shoe.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import shoe.store.entity.ProductSize;

@Repository
public interface ProductSizeRepository extends BaseRepository<ProductSize, Long> {

	List<ProductSize> findByProductId(Long id);

	@Query("select ps.size from ProductSize ps group by ps.size order by ps.size asc")
	List<Integer> getAllSizes();

}
