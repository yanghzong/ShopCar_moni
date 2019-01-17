package com.example.shopcar_moni.view;

import com.example.shopcar_moni.bean.ShopCarBean;

public interface ShopCarView {
    void  getShopCar(ShopCarBean shopCarBean);
    void  onfailed(Exception e);
}
