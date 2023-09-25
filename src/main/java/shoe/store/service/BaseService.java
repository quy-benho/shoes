package shoe.store.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import shoe.store.entity.BaseEntity;

public interface BaseService<T extends BaseEntity, ID extends Serializable> {

	long count();

	void delete(T entity);

	void deleteAll();

	void deleteAll(Iterable<T> entities);

	void deleteById(ID id);

	boolean existsById(ID id);

	List<T> findAll();

	Page<T> findAll(Pageable pageable);

	Optional<T> findById(ID id);

	T save(T entity);

	List<T> saveAll(Iterable<T> entities);

	Page<T> search(String text, Pageable pageable);

	Page<T> searchAll(Pageable pageable);

}
