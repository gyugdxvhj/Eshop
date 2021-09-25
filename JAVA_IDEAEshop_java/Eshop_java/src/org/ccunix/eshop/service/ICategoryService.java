package org.ccunix.eshop.service;

import org.ccunix.eshop.domain.VO.CategoryVO;

import java.util.List;

public interface ICategoryService {

    List<CategoryVO> getCategoryList();
}
