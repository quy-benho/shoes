package shoe.store.repository;

import java.util.List;
import java.util.Optional;



import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import shoe.store.entity.User;

@Repository


public interface UserRepository extends BaseRepository<User, Long> {

	@Query("FROM User u where u.isDelete = 0 and u.email = ?1")
	Optional<User> loginByEmail(String email);

	@Query("FROM User u where u.role = 'ROLE_APP_MANAGER' or u.role = 'ROLE_APP_STAFF'")
	List<User> findAppUser();

	@Query("FROM User u where u.role = 'ROLE_WEB_MANAGER' or u.role = 'ROLE_WEB_STAFF'")
	List<User> findWebUser();

	List<User> findUserByRole(String role);
	 
	  



	
}
