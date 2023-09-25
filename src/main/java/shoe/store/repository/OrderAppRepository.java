package shoe.store.repository;

import org.springframework.stereotype.Repository;

import shoe.store.entity.OrderApp;

@Repository
public interface OrderAppRepository extends BaseRepository<OrderApp, Long> {

}
