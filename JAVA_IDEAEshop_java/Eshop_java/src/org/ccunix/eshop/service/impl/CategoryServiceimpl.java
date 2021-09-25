package org.ccunix.eshop.service.impl;

import org.ccunix.eshop.dao.CategoryDao;
import org.ccunix.eshop.domain.VO.CategoryVO;
import org.ccunix.eshop.service.ICategoryService;

import java.util.List;


public class CategoryServiceimpl implements ICategoryService {
    CategoryDao categoryDao = new CategoryDao();

    @Override
    public List<CategoryVO> getCategoryList() {
        return categoryDao.selectCategoryList();
    }
}
