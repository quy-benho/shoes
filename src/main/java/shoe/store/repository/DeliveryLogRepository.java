package shoe.store.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import shoe.store.entity.DeliveryLog;
import shoe.store.entity.OrderWeb;

@Repository
public interface DeliveryLogRepository extends BaseRepository<DeliveryLog, Long> {

	List<DeliveryLog> findByOrderWeb(OrderWeb orderWeb);

}
