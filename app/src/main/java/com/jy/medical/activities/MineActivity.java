package com.jy.medical.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jy.medical.R;

public class MineActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        setStatusBarTint();
        View view= LayoutInflater.from(this).inflate(R.layout.page_head,null);
        View navView=view.findViewById(R.id.title_head);
//        View navView=this.findViewById(R.id.mine_head);
        setTitleState(navView,false,"我",false,"");
    }

    @Override
    public void initParms(Bundle parms) {

    }


    @Override
    public void widgetClick(View v) {

    }


}
