package com.example.shopcar_moni.selfview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopcar_moni.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyAddJian  extends LinearLayout {
   @BindView(R.id.tv_add)
   TextView  tvAdd;
   @BindView(R.id.tv_jian)
   TextView  tvJian;
   @BindView(R.id.tv_num)
   TextView  tvNum;
    private int number = 1;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        tvNum.setText(number+"");
    }

    public MyAddJian(Context context) {
        this(context,null);
    }

    public MyAddJian(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View inflate = View.inflate(context, R.layout.addjian_item, null);
        ButterKnife.bind(this,inflate);


    }
    @OnClick({R.id.tv_add,R.id.tv_jian})
    public  void  onAddJian(View v){
        switch (v.getId()){
            case R.id.tv_add:
                if (number>1){
                    --number;
                    tvNum.setText(number+"");
                    if (onNumberChangeListener != null){
                        onNumberChangeListener.OnNumberChange(number);
                    }
                }else {
                    Toast.makeText(getContext(), "不买滚犊子!", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.tv_jian:
                ++number;
                tvNum.setText(number+"");
                if (onNumberChangeListener!=null){
                    onNumberChangeListener.OnNumberChange(number);
                }
                break;
        }
    }





    public interface OnNumberChangeListener {

        void OnNumberChange(int number);


    }
    OnNumberChangeListener onNumberChangeListener;
    public void setOnNumberChangeListener(OnNumberChangeListener onNumberChangeListener) {
        this.onNumberChangeListener = onNumberChangeListener;
    }
}
