package org.ccunix.eshop.dao;

import org.ccunix.eshop.domain.VO.CartSelectedmerVO;
import org.ccunix.eshop.domain.VO.CartVO;
import org.ccunix.eshop.domain.VO.CategoryVO;
import org.ccunix.eshop.domain.VO.OrdersVO;
import org.ccunix.eshop.utils.DataSource;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShopCarDao {
    /**
     * 按照用户id查询没有提交的购物车
     * @param member
     * @return
     */
    public CartVO selectCarByMember(Integer member) {
        CartVO cartVO =null;
        Connection connection = DataSource.getConnection();
        try {
            String sql = "select id,member,money,cartStatus from cart where member=? and cartStatus=0";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,member);
            ResultSet set = ps.executeQuery();
            if (set.next()){
                cartVO = new CartVO(set.getInt("id"), set.getInt("member"),
                        set.getBigDecimal("money"),set.getInt("cartStatus"));
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

        return cartVO;
    }

    public Boolean insert(Integer member) {
        Connection connection = DataSource.getConnection();
        try {
            String sql = "insert into cart(member,money,cartStatus) values (?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,member);
            ps.setBigDecimal(2,new BigDecimal("0.00"));
            ps.setInt(3,0);

            int row = ps.executeUpdate();
            if (row > 0){
                return true;
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
        return false;
    }

    public List<CartSelectedmerVO> selectCarSelectedMerListByCar(Integer cart) {

        List<CartSelectedmerVO> cartSelectedmerVOList = new ArrayList<>();
        Connection connection = DataSource.getConnection();
        try {
            String sql = "SELECT c.id,c.cart,c.merchandise,c.number,c.price,c.money,m.merName merName,m.price merPrice " +
                    "FROM cartselectedmer c , merchandise m WHERE cart = ? AND c.merchandise = m.id";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,cart);
            ResultSet set = ps.executeQuery();
            while (set.next()){
                CartSelectedmerVO vo = new CartSelectedmerVO(
                        set.getInt("id"),
                        set.getInt("cart"),
                        set.getInt("merchandise"),
                        set.getInt("number"),
                        set.getBigDecimal("price"),
                        set.getBigDecimal("money"),
                        set.getString("merName"),
                        set.getBigDecimal("merPrice")
                );
                cartSelectedmerVOList.add(vo);
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

        return cartSelectedmerVOList;
    }
    //同时修改俩张表的数据时需要启动事务
    public boolean updateCartSelectMerNumber(Integer id,Integer cart) {
        Connection connection = DataSource.getConnection();
        try {
            // 将提交方式改为手动提交
            connection.setAutoCommit(false);
            String sql = "update cartselectedmer set number=number+1,money=money+price where id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,id);
           //执行sql语句
            int row1 = ps.executeUpdate();
            //更新购物车
            String sql2 = "update cart set money=(select sum(money) from cartselectedmer where cart=? group by cart) where id=?";
            ps = connection.prepareStatement(sql2);
            ps.setInt(1,cart);
            ps.setInt(2,cart);
            int row2 = ps.executeUpdate();
            if (row1>0 && row2>0){
                //手动提交
                connection.commit();
                return true;
            }

        }catch (Exception e){
            e.printStackTrace();
            //回滚
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            try {
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public CartSelectedmerVO selectCarSelectedMerByCarAndMid(Integer cart, int merchandise) {
        CartSelectedmerVO vo = null;
        Connection connection = DataSource.getConnection();
        try {
            String sql = "SELECT c.id,c.cart,c.merchandise,c.number,c.price,c.money " +
                    "FROM cartselectedmer c  WHERE cart = ? AND c.merchandise = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,cart);
            ps.setInt(2,merchandise);
            ResultSet set = ps.executeQuery();
            while (set.next()){
                 vo = new CartSelectedmerVO(
                        set.getInt("id"),
                        set.getInt("cart"),
                        set.getInt("merchandise"),
                        set.getInt("number"),
                        set.getBigDecimal("price"),
                        set.getBigDecimal("money")
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
        return vo;
    }

    public boolean addCarSelectMer(Integer cart, Integer merchandise, int i, BigDecimal calcPrice) {

        Connection connection = DataSource.getConnection();
        try {
            // 将提交方式改为手动提交
            connection.setAutoCommit(false);
            String sql = "insert into cartselectedmer(cart,merchandise,number,price,money) values (?,?,1,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,cart);
            ps.setInt(2,merchandise);
            ps.setBigDecimal(3,calcPrice);
            ps.setBigDecimal(4,calcPrice);
            //执行sql语句
            int row1 = ps.executeUpdate();
            //更新购物车
            String sql2 = "update cart set money=(select sum(money) from cartselectedmer where cart=? group by cart) where id=?";
            ps = connection.prepareStatement(sql2);
            ps.setInt(1,cart);
            ps.setInt(2,cart);
            int row2 = ps.executeUpdate();
            if (row1>0 && row2>0){
                //手动提交
                connection.commit();
            }

        }catch (Exception e){
            e.printStackTrace();
            //回滚
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            try {
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }
    //删除购物车商品项
    public Boolean deleteCartSelectedMerById(Integer int_id,int cart) {

        Connection connection = DataSource.getConnection();
        try {
            // 将提交方式改为手动提交
            connection.setAutoCommit(false);
            String sql = "delete from cartselectedmer where id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,int_id);
            //执行sql语句
            int row1 = ps.executeUpdate();
            //更新购物车
            String sql2 = "update cart set money=(select sum(money) from cartselectedmer where cart=? group by cart) where id=?";
            ps = connection.prepareStatement(sql2);
            ps.setInt(1,cart);
            ps.setInt(2,cart);
            int row2 = ps.executeUpdate();
            if (row1>0 && row2>0){
                //手动提交
                connection.commit();
            }

        }catch (Exception e){
            e.printStackTrace();
            //回滚
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            try {
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return false;
    }
    //请空购物车
    public Boolean clearCartSelectedMerByCart(Integer cart) {

        Connection connection = DataSource.getConnection();
        try {
            // 将提交方式改为手动提交
            connection.setAutoCommit(false);
            String sql = "delete from cartselectedmer where cart=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,cart);
            //执行sql语句
            int row1 = ps.executeUpdate();
            //更新购物车
            String sql2 = "update cart set money=(select sum(money) from cartselectedmer where cart=? group by cart) where id=?";
            ps = connection.prepareStatement(sql2);
            ps.setInt(1,cart);
            ps.setInt(2,cart);
            int row2 = ps.executeUpdate();
            if (row1>0 && row2>0){
                //手动提交
                connection.commit();
            }

        }catch (Exception e){
            e.printStackTrace();
            //回滚
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            try {
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return false;
    }

    public Boolean updateCartSelectedMerNumber(Integer int_id, Integer cart, Integer num) {
        Connection connection = DataSource.getConnection();
        try {
            // 将提交方式改为手动提交
            connection.setAutoCommit(false);
            String sql = "update cartselectedmer set number=?,money=price*? where id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,num);
            ps.setInt(2,num);
            ps.setInt(3,int_id);
            //执行sql语句
            int row1 = ps.executeUpdate();
            //更新购物车
            String sql2 = "update cart set money=(select sum(money) from cartselectedmer where cart=? group by cart) where id=?";
            ps = connection.prepareStatement(sql2);
            ps.setInt(1,cart);
            ps.setInt(2,cart);
            int row2 = ps.executeUpdate();
            if (row1>0 && row2>0){
                //手动提交
                connection.commit();
            }

        }catch (Exception e){
            e.printStackTrace();
            //回滚
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            try {
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return false;
    }
    //提交购物车订单
    public Boolean submitShopCart(Integer cart, Integer member,String orderNO,String orderDate) {
        Connection connection = DataSource.getConnection();
        try {
            // 将提交方式改为手动提交
            connection.setAutoCommit(false);

            //1.生成订单信息
            String sql1 = "insert into orders(member,cart,orderNO,orderDate,orderStatus) values(?,?,?,?,1) ";
            PreparedStatement ps = connection.prepareStatement(sql1);
            ps.setInt(1,member);
            ps.setInt(2,cart);
            ps.setString(3,orderNO);
            ps.setString(4,orderDate);
            int row2 = ps.executeUpdate();

            //2.把购物车的状态改为 1 (提交状态)
            String sql2 = "update cart set cartStatus=1 where id=?";
            ps = connection.prepareStatement(sql2);
            ps.setInt(1,cart);
            int row1 = ps.executeUpdate();

            //3.生成一个新的购物车  查询创建一个新的购物车
            String sql3 = "insert into cart(member,money,cartStatus) values (?,?,?)";
            ps = connection.prepareStatement(sql3);
            ps.setInt(1,member);
            ps.setBigDecimal(2,new BigDecimal("0.00"));
            ps.setInt(3,0);
            int row3 = ps.executeUpdate();
            if (row1>0 && row2>0 && row3>0){
                connection.commit();//提交数据
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            //回滚
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            try {
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return false;
    }
}
