package com.jy.medical.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jy.medical.R;
import com.jy.medical.activities.SelectAddressActivity;
import com.jy.medical.activities.SelectCategoryActivity;
import com.jy.medical.adapter.viewholder.NursingViewHolder;
import com.jy.medical.adapter.viewholder.SupporterViewHolder;
import com.jy.medical.greendao.entities.NursingData;
import com.jy.medical.greendao.entities.SupporterPerson;
import com.jy.medical.util.MultiSelectUtil;
import com.jy.medical.widget.CleanableEditText;

import java.util.List;

/**
 * Created by 19459 on 2016/9/21.
 */

public class SupporterAdapter extends BaseHeadFootAdapter {

    private Context context;
    private List<SupporterPerson> list;

    public SupporterAdapter(Context context, List<SupporterPerson> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    protected void onBindHeaderView(View headerView) {
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    protected void onBindFooterView(View footerView) {
    }


    @Override
    protected int getItemNum() {
        return list.size();
    }

    public void addItem(){
        SupporterPerson supporterPerson=new SupporterPerson("","","","","","","","","","","");
        list.add(list.size(),supporterPerson);
        notifyItemInserted(list.size());
    }

    @Override
    protected void onBindView(RecyclerView.ViewHolder holder, final int position) {
        final SupporterViewHolder viewHolder = (SupporterViewHolder) holder;
        final SupporterPerson supporterPerson=list.get(position);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        viewHolder.title.setText("被扶养人"+(position+1));
        viewHolder.name.setText(supporterPerson.getName());
        viewHolder.bornTime.setText(supporterPerson.getBornTime());
        viewHolder.houseKind.setText(supporterPerson.getHouseKindValue());
        viewHolder.relation.setText(supporterPerson.getRelationValue());
        viewHolder.age.setText(supporterPerson.getAge());
        viewHolder.years.setText(supporterPerson.getYears());
        viewHolder.num.setText(supporterPerson.getNum());
        viewHolder.address.setText(supporterPerson.getAddress());
        viewHolder.deleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除
            }
        });
        viewHolder.bornTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选择时间
                MultiSelectUtil.initTimePicker(context, viewHolder.bornTime, viewHolder.bornTime.getText().toString(), "选择出生日期");

            }
        });
        viewHolder.relation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选择关系
                Intent intent = new Intent(context,SelectCategoryActivity.class);
                intent.putExtra("kindCode","D116");
                intent.putExtra("requestKind","01");
                intent.putExtra("title", "与受害人关系");
                ((AppCompatActivity) context).startActivityForResult(intent, position);
            }
        });
        viewHolder.houseKind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选择户口性质
                Intent intent = new Intent(context,SelectCategoryActivity.class);
                intent.putExtra("kindCode","D109");
                intent.putExtra("requestKind","02");
                intent.putExtra("title", "户口性质");
                ((AppCompatActivity) context).startActivityForResult(intent, position);
            }
        });
        viewHolder.location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SelectAddressActivity.class);
                ((AppCompatActivity) context).startActivityForResult(intent, 0x13);
            }
        });
    }


    @Override
    public SupporterViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_supporter, null);
        return new SupporterViewHolder(view);
    }
    private boolean checkData(){

        return true;
    }
}
