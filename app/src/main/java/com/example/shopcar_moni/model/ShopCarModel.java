package com.example.shopcar_moni.model;

import com.example.shopcar_moni.inter.ICallBack;
import com.example.shopcar_moni.utils.HttpUtils;

import java.lang.reflect.Type;

public class ShopCarModel {
    public  void  getShopCarm(String url, ICallBack callBack, Type type){
        HttpUtils.getInstance().get(url,callBack,type);
    }
}
