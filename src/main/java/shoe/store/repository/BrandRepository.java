package shoe.store.repository;

import org.springframework.stereotype.Repository;

import shoe.store.entity.Brand;

@Repository
public interface BrandRepository extends BaseRepository<Brand, Long> {

}
