package org.ccunix.eshop.domain.VO;

import org.ccunix.eshop.domain.OrdersPOJO;

import java.math.BigDecimal;

public class OrdersVO extends OrdersPOJO {
    //扩展字段
    private BigDecimal money;//总金额

    public OrdersVO(Integer id, Integer member, Integer cart, String orderNO, String orderDate, Integer orderStatus) {
        super(id, member, cart, orderNO, orderDate, orderStatus);
    }

    public OrdersVO(BigDecimal money) {
        this.money = money;
    }

    public OrdersVO(Integer id, Integer member, Integer cart, String orderNO, String orderDate, Integer orderStatus, BigDecimal money) {
        super(id, member, cart, orderNO, orderDate, orderStatus);
        this.money = money;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
