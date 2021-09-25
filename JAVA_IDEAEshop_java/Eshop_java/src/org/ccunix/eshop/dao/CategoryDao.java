package org.ccunix.eshop.dao;

import org.ccunix.eshop.domain.VO.CategoryVO;
import org.ccunix.eshop.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {

    public List<CategoryVO> selectCategoryList() {
        List<CategoryVO> list = new ArrayList<>();
        Connection connection = DataSource.getConnection();
        try {
            String sql = "select id,cateName,cateDesc from category";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet set = ps.executeQuery();
            while(set.next()){
                CategoryVO category = new CategoryVO();
                category.setId(set.getInt("id"));
                category.setCateName(set.getString("cateName"));
                category.setCateDesc(set.getString("cateDesc"));

                list.add(category);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        for (CategoryVO categoryVO : list) {
            System.out.println(categoryVO);
        }
        return list;
    }
}
