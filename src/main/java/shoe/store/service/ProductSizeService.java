package shoe.store.service;

import java.util.List;

import shoe.store.entity.ProductSize;

public interface ProductSizeService extends BaseService<ProductSize, Long> {

    List<ProductSize> findByProductId(Long id);

    List<Integer> getAllSizes();

}
