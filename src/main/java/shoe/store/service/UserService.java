package shoe.store.service;

import java.sql.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;

import shoe.store.entity.User;


public interface UserService extends BaseService<User, Long> {

	Optional<User> loginByEmail(String email);
	

}
