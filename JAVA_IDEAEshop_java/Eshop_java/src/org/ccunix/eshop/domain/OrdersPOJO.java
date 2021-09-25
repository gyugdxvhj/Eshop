package org.ccunix.eshop.domain;

public class OrdersPOJO {
    private Integer id;
    private Integer member;
    private Integer cart;
    private String orderNO;
    private String orderDate;
    private Integer orderStatus;

    public OrdersPOJO() {
    }

    public OrdersPOJO(Integer id, Integer member, Integer cart, String orderNO, String orderDate, Integer orderStatus) {
        this.id = id;
        this.member = member;
        this.cart = cart;
        this.orderNO = orderNO;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
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

    public Integer getCart() {
        return cart;
    }

    public void setCart(Integer cart) {
        this.cart = cart;
    }

    public String getOrderNO() {
        return orderNO;
    }

    public void setOrderNO(String orderNO) {
        this.orderNO = orderNO;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
}
