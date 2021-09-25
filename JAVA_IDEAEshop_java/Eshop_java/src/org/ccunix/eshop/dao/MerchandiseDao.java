package org.ccunix.eshop.dao;

import org.ccunix.eshop.domain.VO.CategoryVO;
import org.ccunix.eshop.domain.VO.MerchandiseVO;
import org.ccunix.eshop.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MerchandiseDao {
    //是否特价查询商品列表
    public List<MerchandiseVO> selectMerchListSpecial(int special,String query) {
        List<MerchandiseVO> merchandiseList = new ArrayList<>();

        Connection connection = DataSource.getConnection();
        try {
            String sql = "";
            if ("all".equals(query)){
                sql = "SELECT id,category,merName,price,sprice,merModel,picture,merDesc,manufacturer,leaveFactoryDate,special " +
                        "from merchandise where special = ?";
            }else {
                sql = "SELECT id,category,merName,price,sprice,merModel,picture,merDesc,manufacturer,leaveFactoryDate,special " +
                        "from merchandise where special = ? LIMIT 3";

            }
            PreparedStatement ps = connection.prepareStatement(sql);
            //对问号传值
            ps.setInt(1,special);

            ResultSet set = ps.executeQuery();
            while(set.next()){
                MerchandiseVO merchandise = new MerchandiseVO(
                        set.getInt("id"),
                        set.getInt("category"),
                        set.getString("merName"),
                        set.getBigDecimal("price"),
                        set.getBigDecimal("sprice"),
                        set.getString("merModel"),
                        set.getString("picture"),
                        set.getString("merDesc"),
                        set.getString("manufacturer"),
                        set.getString("leaveFactoryDate"),
                        set.getInt("special")
                        );

                merchandiseList.add(merchandise);
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
        return merchandiseList;
    }

    public MerchandiseVO getMerchantById(int id) {

        MerchandiseVO merchandise = null;

        Connection connection = DataSource.getConnection();
        try {
            String sql = "SELECT id,category,merName,price,sprice,merModel,picture,merDesc,manufacturer,DATE_FORMAT(leaveFactoryDate,'%Y-%m-%d') leaveFactoryDate,special " +
                    "from merchandise where id = ? ";
            PreparedStatement ps = connection.prepareStatement(sql);
            //对问号传值
            ps.setInt(1,id);
            ResultSet set = ps.executeQuery();
            if (set.next()){
                 merchandise = new MerchandiseVO(
                        set.getInt("id"),
                        set.getInt("category"),
                        set.getString("merName"),
                        set.getBigDecimal("price"),
                        set.getBigDecimal("sprice"),
                        set.getString("merModel"),
                        set.getString("picture"),
                        set.getString("merDesc"),
                        set.getString("manufacturer"),
                        set.getString("leaveFactoryDate"),
                        set.getInt("special")
                );
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
        return merchandise;
    }
}
