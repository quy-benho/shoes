package shoe.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shoe.store.entity.ProductSize;
import shoe.store.repository.ProductSizeRepository;
import shoe.store.service.ProductSizeService;

@Service
public class ProductSizeServiceImpl extends BaseServiceImpl<ProductSize, Long> implements ProductSizeService {

    @Autowired
    ProductSizeRepository productSizeRepository;

    @Override
    public List<ProductSize> findByProductId(Long id) {

        return productSizeRepository.findByProductId(id);
    }

	@Override
	public List<Integer> getAllSizes() {

		return productSizeRepository.getAllSizes();
	}

}
