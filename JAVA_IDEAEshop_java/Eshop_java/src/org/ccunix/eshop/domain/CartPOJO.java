package org.ccunix.eshop.domain;

import java.math.BigDecimal;

public class CartPOJO {
    private Integer id;
    private Integer member;
    private BigDecimal money;
    private Integer carStatus;

    public CartPOJO() {
    }

    public CartPOJO(Integer id, Integer member, BigDecimal money, Integer carStatus) {
        this.id = id;
        this.member = member;
        this.money = money;
        this.carStatus = carStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMember() {
        return member;
    }

    public void setMember(Integer member) {
        this.member = member;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(Integer carStatus) {
        this.carStatus = carStatus;
    }
}
