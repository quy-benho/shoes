package shoe.store.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import shoe.store.entity.OrderWeb;
import shoe.store.entity.User;

@Repository
public interface OrderWebRepository extends BaseRepository<OrderWeb, Long> {

    List<OrderWeb> findByUserOrderByIdDesc(User user);

	@Override
	@Query("FROM OrderWeb o where o.id = :id or o.consignee like %:text% "
										  + "or o.consigneePhone like %:text% "
										  + "or o.deliveryAddress like %:text% "
										  + "or o.paymentStatus like %:text% "
										  + "or o.deliveryStatus like %:text% "
										  + "or o.paymentMethod like %:text% ")
	Page<OrderWeb> search(@Param("id") long id, @Param("text") String name, Pageable pageable);

	@Query(value = "SELECT * FROM order_web o WHERE o.payment_method LIKE %:paymentMethod "
									+ "AND o.payment_status LIKE %:paymentStatus "
									+ "AND o.delivery_status LIKE %:deliveryStatus "
									+ "AND o.id LIKE %:id% "
									+ "AND (o.consignee LIKE %:text% "
									+ "OR o.consignee_phone LIKE %:text% "
									+ "OR o.delivery_address LIKE %:text%)"
									, nativeQuery = true)

	Page<OrderWeb> searchFilter(@Param("text") String text,
								@Param("id") String id,
								@Param("paymentMethod") String paymentMethod, 
								@Param("paymentStatus") String paymentStatus, 
								@Param("deliveryStatus") String deliveryStatus,
								Pageable pageable);

}
