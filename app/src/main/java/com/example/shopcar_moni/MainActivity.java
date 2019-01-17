package com.example.shopcar_moni;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopcar_moni.adapter.MyAdapter;
import com.example.shopcar_moni.bean.ShopCarBean;
import com.example.shopcar_moni.inter.Apis;
import com.example.shopcar_moni.presenter.ShopCarPresenter;
import com.example.shopcar_moni.view.ShopCarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity  implements ShopCarView{
    @BindView(R.id.el_shop)
    ExpandableListView  elShop;
    @BindView(R.id.cb_all)
    CheckBox  cbAll;
    @BindView(R.id.tv_allprice)
    TextView  tvAllPrice;
    @BindView(R.id.tv_allnum)
    TextView  tvAllNum;
    private List<ShopCarBean.DataBean> sellerData;
    private MyAdapter myAdapter;
    private ShopCarPresenter shopCarPresenter;
    private List<ShopCarBean.DataBean.ListBean> childlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this,this);
        //初始化p层
        initPresenter();
        //初始化adapter
        initAdapter();



    }

    private void initAdapter() {
        sellerData=new ArrayList<>();
        childlist = new ArrayList<>();
        myAdapter = new MyAdapter(MainActivity.this, sellerData);
        myAdapter.setOnCartListChangeListener(new MyAdapter.OnCartListChangeListener() {

            @Override
            public void SellerSelectedChange(int groupPosition) {
                //先得到 checkbox的状态
                boolean b = myAdapter.isCurrentSellerAllProductSelected(groupPosition);
                myAdapter.changeCurrentSellerAllProductSelected(groupPosition,!b);
                myAdapter.notifyDataSetChanged();
                refreshAllSelectedAndTotalPriceAndTotalNumber();

            }

            @Override
            public void changeCurrentProductSelected(int groupPosition, int childPosition) {
                myAdapter.changeCurrentProductSelected(groupPosition,childPosition);
                myAdapter.notifyDataSetChanged();
                refreshAllSelectedAndTotalPriceAndTotalNumber();
            }

            @Override
            public void ProductNumberChange(int groupPosition, int childPosition, int number) {
                myAdapter.changeCurrentProductNumber(groupPosition,childPosition,number);
                myAdapter.notifyDataSetChanged();
                refreshAllSelectedAndTotalPriceAndTotalNumber();
            }
        });
        elShop.setAdapter(myAdapter);
//
        for (int i = 0; i < sellerData.size() ; i++) {
            elShop.expandGroup(i);

        }
    }

    private void initPresenter() {
        shopCarPresenter = new ShopCarPresenter();
        shopCarPresenter.attach(this);
        shopCarPresenter.getShopCarP(Apis.ShopCarUrl);
    }

    private void  refreshAllSelectedAndTotalPriceAndTotalNumber(){

        boolean allProductsSelected = myAdapter.isAllProductsSelected();
        cbAll.setChecked(allProductsSelected);
        Double totalPrice = myAdapter.calculateTotalPrice();
        tvAllPrice.setText("总价：￥"+totalPrice);
        int totalNumber = myAdapter.calculateTotalNumber();
        tvAllNum.setText("去结算("+totalNumber+")");
    }

    @OnClick(R.id.cb_all)
    public  void cbAdd(){
        boolean allProductsSelected = myAdapter.isAllProductsSelected();
        myAdapter.changeAllProductsSelected(!allProductsSelected);
        myAdapter.notifyDataSetChanged();
        //刷新底部的方法
        refreshAllSelectedAndTotalPriceAndTotalNumber();
    }

    @Override
    public void getShopCar(ShopCarBean shopCarBean) {
        List<ShopCarBean.DataBean> data = shopCarBean.getData();
        sellerData.clear();
        sellerData.addAll(data);
        myAdapter.notifyDataSetChanged();
        List<ShopCarBean.DataBean.ListBean> list = shopCarBean.getData().get(0).getList();

    }

    @Override
    public void onfailed(Exception e) {
        Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
    }
}
