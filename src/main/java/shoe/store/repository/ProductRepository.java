package shoe.store.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import shoe.store.entity.Brand;
import shoe.store.entity.Product;

@Repository
public interface ProductRepository extends BaseRepository<Product, Long> {

	@Override
	@Query("from Product p where p.isDelete = 0 and (p.id = :id or p.name like %:text% or p.brand.name like %:text% or p.versionName like %:text%)")
	Page<Product> search(@Param("id") long id, @Param("text") String name, Pageable pageable);

	@Query("select p from Product p "
						+ "inner join ProductColor pc on p.id = pc.product.id "
						+ "inner join ProductSize ps on p.id = ps.product.id "
						+ "where p.isDelete = 0"
						+ "and p.category.id like :categoryId || '' "
						+ "and p.brand.id like :brandId || '' "
						+ "and (p.price >= :priceMin and p.price <= :priceMax) "
						+ "and ps.size like :size || '' "
						+ "and pc.color.id like :colorId || '' "
						+ "group by p")
	Page<Product> listing(@Param("categoryId") String categoryId,
						  @Param("brandId") String brandId,
						  @Param("priceMin") long priceMin,
						  @Param("priceMax") long priceMax,
						  @Param("size") String size,
						  @Param("colorId") String colorId,
						  Pageable pageable);

	@Query("from Product p where p.brand = :brand and p != :currentProduct")
	Page<Product> getRelatedProductByBrand(@Param("brand") Brand brand, @Param("currentProduct") Product product, Pageable pageable);

}
