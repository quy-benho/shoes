package shoe.store.repository;

import org.springframework.stereotype.Repository;

import shoe.store.entity.Color;

@Repository
public interface ColorRepository extends BaseRepository<Color, Long> {

}
