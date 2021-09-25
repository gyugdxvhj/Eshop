package org.ccunix.eshop.dao;

import org.ccunix.eshop.domain.VO.CartVO;
import org.ccunix.eshop.domain.VO.OrdersVO;
import org.ccunix.eshop.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrdersDao {
    /**
     * 根据订单编号查询订单信息
     * @param orderNO
     * @return
     */
    public OrdersVO selectOrdersByOrderNO(String orderNO) {

        Connection connection = DataSource.getConnection();
        try {
            String sql = "select orders.id,orders.member,cart,orderNO,DATE_FORMAT(orderDate,'%Y-%m-%d %H:%i:%S') orderDate ,orderStatus, money " +
                    "from orders,cart where orders.cart=cart.id and orders.orderNO=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,orderNO);
            ResultSet set = ps.executeQuery();
            if (set.next()){
               return new OrdersVO(set.getInt("id"),
                       set.getInt("member"),
                       set.getInt("cart"),
                       set.getString("orderNO"),
                       set.getString("orderDate"),
                       set.getInt("orderStatus"),
                       set.getBigDecimal("money"));
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

        return null;
    }

    /**
     * 根据会员id查询订单列表  按照时间倒序
     * @param id
     * @return
     */
    public List<OrdersVO> selectOrdersByMid(Integer id) {
        List<OrdersVO> ordersList = new ArrayList<>();

        Connection connection = DataSource.getConnection();
        try {
            String sql = "select orders.id,orders.member,cart,orderNO,DATE_FORMAT(orderDate,'%Y-%m-%d') orderDate ,orderStatus, money " +
                    "from orders,cart where orders.cart=cart.id and orders.member=? order by orderDate desc ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet set = ps.executeQuery();
            while (set.next()){
                OrdersVO ordersVO = new OrdersVO(set.getInt("id"),
                        set.getInt("member"),
                        set.getInt("cart"),
                        set.getString("orderNO"),
                        set.getString("orderDate"),
                        set.getInt("orderStatus"),
                        set.getBigDecimal("money"));

                ordersList.add(ordersVO);
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

        return ordersList;
    }

    /**
     * 根据id删除订单
     * @param int_id
     * @return
     */
    public Boolean deleteOrdersById(Integer int_id) {
        Connection connection = DataSource.getConnection();
        try {
            String sql = "delete from orders where id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,int_id);
            //执行
            int row = ps.executeUpdate();
            if (row>0){
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

    /**
     * 根据订单id查询订单信息
     * @param id
     * @return
     */
    public OrdersVO getOrdersById(Integer id) {
        Connection connection = DataSource.getConnection();
        try {
            String sql = "select orders.id,orders.member,cart,orderNO,DATE_FORMAT(orderDate,'%Y-%m-%d %H:%i:%S') orderDate ,orderStatus, money " +
                    "from orders,cart where orders.cart=cart.id and orders.id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet set = ps.executeQuery();
            if (set.next()){
                return new OrdersVO(set.getInt("id"),
                        set.getInt("member"),
                        set.getInt("cart"),
                        set.getString("orderNO"),
                        set.getString("orderDate"),
                        set.getInt("orderStatus"),
                        set.getBigDecimal("money"));
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

        return null;
    }
}
