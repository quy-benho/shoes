package shoe.store.service.impl;

import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import shoe.store.entity.User;
import shoe.store.repository.UserRepository;
import shoe.store.service.UserService;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public Optional<User> loginByEmail(String email) {
		return userRepository.loginByEmail(email);
	}



}
