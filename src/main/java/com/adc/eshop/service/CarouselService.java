
package com.adc.eshop.service;

import java.util.List;

import com.adc.eshop.controller.vo.IndexCarouselVO;
import com.adc.eshop.entity.Carousel;
import com.adc.eshop.util.PageQueryUtil;
import com.adc.eshop.util.PageResult;

public interface CarouselService {
    
    PageResult getCarouselPage(PageQueryUtil pageUtil);

    String saveCarousel(Carousel carousel);

    String updateCarousel(Carousel carousel);

    Carousel getCarouselById(Integer id);

    Boolean deleteBatch(Integer[] ids);

    
    List<IndexCarouselVO> getCarouselsForIndex(int number);
}
