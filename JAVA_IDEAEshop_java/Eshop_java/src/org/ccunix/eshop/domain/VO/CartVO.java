package org.ccunix.eshop.domain.VO;

import org.ccunix.eshop.domain.CartPOJO;

import java.math.BigDecimal;

public class CartVO extends CartPOJO {
    public CartVO() {
    }

    public CartVO(Integer id, Integer member, BigDecimal money, Integer carStatus) {
        super(id, member, money, carStatus);
    }
}
