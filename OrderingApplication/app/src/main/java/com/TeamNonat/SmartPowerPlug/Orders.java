package com.TeamNonat.SmartPowerPlug;

public class Orders {
    String productname,price,ordertype,orderStatus;

    public Orders() {

    }

    public Orders(String name, String price)
    {
        this.productname = name;
        this.price = price;

    }

    public void setProductName(String name) {
        this.productname = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public void setOrderType(String ordertype) {
        this.ordertype = ordertype;
    }
    public void setOrderStatus(String orderstatus) {
        this.orderStatus = orderstatus;
    }


    public String getProductName() {
        return productname;
    }

    public String getPrice() {
        return price;
    }
    public String getOrderType() {
        return ordertype;
    }
    public String getOrderStatus() {
        return orderStatus;
    }

}
