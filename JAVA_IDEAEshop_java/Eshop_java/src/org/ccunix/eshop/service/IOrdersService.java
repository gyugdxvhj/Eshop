package org.ccunix.eshop.service;

import org.ccunix.eshop.domain.VO.OrdersVO;

import java.util.List;

public interface IOrdersService {
    /**
     * 根据订单编号查询订单信息
     * @param orderNO
     * @return
     */
    OrdersVO getOrdersByOrderNO(String orderNO);

    /**
     * 根据会员id查询订单列表
     * @param id
     * @return
     */
    List<OrdersVO> getOrdersByMid(Integer id);

    /***
     * 按照id删除订单
     * @param id
     * @return
     */
    Boolean deleteOrdersById(String id);

    /**
     * 根据订单id查询订单
     * @param id
     * @return
     */
    OrdersVO getOrdersById(String id);
}
