package shoe.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import shoe.store.entity.OrderWeb;
import shoe.store.entity.User;
import shoe.store.repository.OrderWebRepository;
import shoe.store.service.OrderWebService;

@Service
public class OrderWebServiceImpl extends BaseServiceImpl<OrderWeb, Long> implements OrderWebService {

	@Autowired
	OrderWebRepository orderWebRepository;

	@Override
	public List<OrderWeb> findByUser(User user) {
		return orderWebRepository.findByUserOrderByIdDesc(user);
	}

	@Override
	public Page<OrderWeb> searchFilter(String text, String paymentMethod, String paymentStatus, String deliveryStatus, Pageable pageable) {

		String id = "";
		if (text == null) {
			text = "";

		} else if (text.matches("[0-9]+")) {
			id = String.valueOf(Integer.valueOf(text));
			text = "";
		}

		return orderWebRepository.searchFilter(text, id, paymentMethod, paymentStatus, deliveryStatus, pageable);
	}

}
