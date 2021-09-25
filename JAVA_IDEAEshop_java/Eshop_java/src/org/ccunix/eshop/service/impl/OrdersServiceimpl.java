package org.ccunix.eshop.service.impl;

import org.ccunix.eshop.dao.OrdersDao;
import org.ccunix.eshop.domain.VO.OrdersVO;
import org.ccunix.eshop.service.IOrdersService;

import java.util.List;

public class OrdersServiceimpl implements IOrdersService {
    OrdersDao ordersDao = new OrdersDao();

    @Override
    public OrdersVO getOrdersByOrderNO(String orderNO) {
        return ordersDao.selectOrdersByOrderNO(orderNO);
    }

    @Override
    public List<OrdersVO> getOrdersByMid(Integer id) {
        return ordersDao.selectOrdersByMid(id);
    }

    @Override
    public Boolean deleteOrdersById(String id) {
        Integer int_id = Integer.parseInt(id);
        return ordersDao.deleteOrdersById(int_id);
    }

    @Override
    public OrdersVO getOrdersById(String id) {
        Integer int_id = Integer.parseInt(id);
        return ordersDao.getOrdersById(int_id);
    }
}
