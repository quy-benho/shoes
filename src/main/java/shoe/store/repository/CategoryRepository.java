package shoe.store.repository;

import org.springframework.stereotype.Repository;

import shoe.store.entity.Category;

@Repository
public interface CategoryRepository extends BaseRepository<Category, Long> {

}
