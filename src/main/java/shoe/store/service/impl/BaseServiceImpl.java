package shoe.store.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import shoe.store.entity.BaseEntity;
import shoe.store.repository.BaseRepository;
import shoe.store.service.BaseService;

public class BaseServiceImpl<T extends BaseEntity, ID extends Serializable> implements BaseService<T, ID> {

	@Autowired
	BaseRepository<T, ID> baseRepository;

	@Override
	public long count() {
		return baseRepository.count();
	}

	@Override
	public void delete(T entity) {
		baseRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		baseRepository.deleteAll();
	}

	@Override
	public void deleteAll(Iterable<T> entities) {
		baseRepository.deleteAll(entities);
	}

	@Override
	public void deleteById(ID id) {
		baseRepository.deleteById(id);
	}

	@Override
	public boolean existsById(ID id) {
		return baseRepository.existsById(id);
	}

	@Override
	public List<T> findAll() {
		return baseRepository.findAll();
	}

	@Override
	public Page<T> findAll(Pageable pageable) {
		return baseRepository.findAll(pageable);
	}

	@Override
	public Optional<T> findById(ID id) {
		return baseRepository.findById(id);
	}

	@Override
	public T save(T entity) {
		return baseRepository.save(entity);
	}

	@Override
	public List<T> saveAll(Iterable<T> entities) {
		return baseRepository.saveAll(entities);
	}

	@Override
	public Page<T> search(String text, Pageable pageable) {

		long id = -1;
		if (text == null) {
			text = "";

		} else if (text.matches("[0-9]+")) {
			id = Integer.valueOf(text);
		}

		return search(id, text, pageable);
	}

	@Override
	public Page<T> searchAll(Pageable pageable) {
		return search(-1, "", pageable);
	}

	private Page<T> search(long id, String text, Pageable pageable) {
		try {
			return baseRepository.search(id, text, pageable);

		} catch (InvalidDataAccessResourceUsageException ie) {
			System.out.println("method search() not overridden in repository, used findAll() instead.");
			return baseRepository.findAll(pageable);

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return null;
	}
}
