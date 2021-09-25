package org.ccunix.eshop.service.impl;

import org.ccunix.eshop.dao.ShopCarDao;
import org.ccunix.eshop.domain.VO.CartSelectedmerVO;
import org.ccunix.eshop.domain.VO.CartVO;
import org.ccunix.eshop.domain.VO.MerchandiseVO;
import org.ccunix.eshop.domain.VO.OrdersVO;
import org.ccunix.eshop.service.IMerchandiseService;
import org.ccunix.eshop.service.IOrdersService;
import org.ccunix.eshop.service.IShopCarService;
import org.ccunix.eshop.utils.DataFormatUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ShopCarServiceimpl implements IShopCarService {
    IMerchandiseService merchandiseService = new MerchandiseServiceimpl();
    IOrdersService ordersService = new OrdersServiceimpl();
    ShopCarDao shopCarDao = new ShopCarDao();
    boolean b = false;

//    IShopCarService shopCarService = new　 ShopCarServiceimpl();
    @Override
    public CartVO getShopCart(Integer member) {
        //先从数据库中查找是否保存完毕
        CartVO cartVO = shopCarDao.selectCarByMember(member);
        if (cartVO == null){
            //创建一个购物车
            shopCarDao.insert(member);
            cartVO = shopCarDao.selectCarByMember(member);
        }
        return cartVO;
    }

    @Override
    public List<CartSelectedmerVO> getCarSelectedMerListByCar(Integer id) {
        return shopCarDao.selectCarSelectedMerListByCar(id);
    }

    @Override
    public Boolean addShopCar(Integer cart, String merId,Integer favourable) {
        //获得商品信息
        MerchandiseVO merchandVO = merchandiseService.getMerchantById(merId);
        //去实现
        //该商品在购物车项目中存在  cart merchandise 1  更新
        CartSelectedmerVO cartSelectedmerVO = getCarSelectedMerByCarAndMid(cart,merId);
        //商品不存在 那么就进行 插入
        if (cartSelectedmerVO == null){
            //insert
            //商品是否是特价  特价和会员价 比较 哪个小按哪个来
            BigDecimal calcPrice = null;
            //打折后的价格
            BigDecimal favourablePrice = merchandVO.getPrice().multiply(new BigDecimal(String.valueOf(favourable)));
            favourablePrice = favourablePrice.multiply(new BigDecimal("0.01"));
            if (merchandVO.getSpecial() == 1){
                //特价
                BigDecimal special = merchandVO.getSprice();
                //比较特价和折扣价大小
                if (special.compareTo(favourablePrice) == 1){
                    calcPrice = favourablePrice;
                }else {
                    calcPrice = special;
                }
            }else {
                //商品时新品 按会员价来

                calcPrice = favourablePrice;
            }
            //插入数据库
             b = addCarSelectMer(cart,merchandVO.getId(),1,calcPrice);

            //商品是新品  那么按照会员价格来
        }else {
            //update
             b = updateCartSelectMerNumber(cartSelectedmerVO.getId(),cart);//更新购物项  更新购物车的总金额
        }

        return b;
    }

    @Override
    public boolean updateCartSelectMerNumber(Integer id,Integer cart) {
        //更新商品项和购物车总金额
        return shopCarDao.updateCartSelectMerNumber(id,cart);
    }

    @Override
    public CartSelectedmerVO getCarSelectedMerByCarAndMid(Integer cart, String merId) {
        //查询
        int merchandise = Integer.parseInt(merId);
        return shopCarDao.selectCarSelectedMerByCarAndMid(cart,merchandise);
    }

    @Override
    public boolean addCarSelectMer(Integer cart, Integer merchandise, int i, BigDecimal calcPrice) {
        return shopCarDao.addCarSelectMer(cart,merchandise,i,calcPrice);
    }

    //删除购物车商品项
    @Override
    public Boolean deleteCartSelectedMerById(String id,int cart) {
        Integer int_id = Integer.parseInt(id);
        return shopCarDao.deleteCartSelectedMerById(int_id,cart);
    }
    //清空购物车
    @Override
    public Boolean clearCartSelectedMerByCart(Integer cart) {
        return shopCarDao.clearCartSelectedMerByCart(cart);
    }
    //修改商品信息的数量
    @Override
    public Boolean updateCartSelectedMerNumber(String id,Integer cart,Integer num) {
        Integer int_id = Integer.parseInt(id);
        return shopCarDao.updateCartSelectedMerNumber(int_id,cart,num);
    }
    //提交购物车订单
    @Override
    public OrdersVO submitShopCart(Integer cart, Integer member) {
        String orderDate = DataFormatUtil.formatDateString(new Date(),"yyyy-MM-dd HH:mm:ss");
        String orderNO = DataFormatUtil.getOrderNoByUUIDHashCode("");
        //提交购物车
        boolean b = shopCarDao.submitShopCart(cart,member,orderNO,orderDate);
        if (b){
            //查询一下刚才生成的订单
            return ordersService.getOrdersByOrderNO(orderNO);
        }

        return null;
    }
}
