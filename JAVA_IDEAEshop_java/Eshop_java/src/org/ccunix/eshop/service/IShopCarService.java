package org.ccunix.eshop.service;

import org.ccunix.eshop.domain.VO.CartSelectedmerVO;
import org.ccunix.eshop.domain.VO.CartVO;
import org.ccunix.eshop.domain.VO.OrdersVO;

import java.math.BigDecimal;
import java.util.List;

public interface IShopCarService {
    /**
     * 获得购物车
     */
    public CartVO getShopCart(Integer member);

    /**
     * 获得商品详情列表
     * @param id
     * @return
     */
    List<CartSelectedmerVO> getCarSelectedMerListByCar(Integer id);

    /**
     * 添加购物车
     * @param id
     * @param merId
     * @return
     */
    Boolean addShopCar(Integer id, String merId,Integer favourable);

    /**
     * 更新商品项和购物车
     * @param id
     * @return
     */
    boolean updateCartSelectMerNumber(Integer id,Integer cart);

    /**
     * 按照购物车id和商品项查看购物车详情信息
     * @param cart
     * @param merId
     * @return
     */
    CartSelectedmerVO getCarSelectedMerByCarAndMid(Integer cart, String merId);

    /**
     * 添加购物项商品的个数是 1
     * @param cart
     * @param id
     * @param i
     * @param calcPrice
     * @return
     */
    boolean addCarSelectMer(Integer cart, Integer id, int i, BigDecimal calcPrice);

    /**
     * 删除商品项
     * @param id
     * @return
     */
    Boolean deleteCartSelectedMerById(String id,int cart);

    /**
     * 清空购物车
     * @param id
     * @return
     */
    Boolean clearCartSelectedMerByCart(Integer id);

    /**
     * 修改商品信息的数量
     * @param id
     * @return
     */
    Boolean updateCartSelectedMerNumber(String id,Integer cart,Integer num);

    /**
     * 提交购物车订单
     * @param cart
     * @param member
     * @return
     */
    OrdersVO submitShopCart(Integer cart, Integer member);
}
