package com.example.shopcar_moni.presenter;

import com.example.shopcar_moni.bean.ShopCarBean;
import com.example.shopcar_moni.inter.ICallBack;
import com.example.shopcar_moni.model.ShopCarModel;
import com.example.shopcar_moni.view.ShopCarView;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class ShopCarPresenter {

    private ShopCarModel shopCarModel;
    private ShopCarView shopCarView;


    public  void attach(ShopCarView shopCarView){
        this.shopCarView=shopCarView;
        shopCarModel=new ShopCarModel();
    }

    public  void  getShopCarP(String url){
        Type type=new TypeToken<ShopCarBean>(){}.getType();
        shopCarModel.getShopCarm(url,new ICallBack() {
            @Override
            public void onSuccess(Object obj) {
                ShopCarBean shopCarBean= (ShopCarBean) obj;
                shopCarView.getShopCar(shopCarBean);
            }

            @Override
            public void onFailed(Exception e) {
                shopCarView.onfailed(e);
            }
        },type);
    }
}
