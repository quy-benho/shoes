package shoe.store.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import shoe.store.entity.OrderWeb;
import shoe.store.entity.User;

public interface OrderWebService extends BaseService<OrderWeb, Long> {

	List<OrderWeb> findByUser(User user);

	Page<OrderWeb> searchFilter(String text, String paymentMethod, String paymentStatus, String deliveryStatus, Pageable pageable);

}
