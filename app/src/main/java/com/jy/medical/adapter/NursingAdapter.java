package com.jy.medical.adapter;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jy.medical.R;
import com.jy.medical.activities.SelectCategoryActivity;
import com.jy.medical.adapter.viewholder.ContactViewHolder;
import com.jy.medical.adapter.viewholder.NursingViewHolder;
import com.jy.medical.greendao.entities.ContactData;
import com.jy.medical.greendao.entities.NursingData;

import java.util.List;

/**
 * Created by 19459 on 2016/9/21.
 */

public class NursingAdapter extends BaseHeadFootAdapter {

    private Context context;
    private List<NursingData> list;

    public NursingAdapter(Context context, List<NursingData> list) {
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
        NursingData nursingData=new NursingData("","","","","","","");
        list.add(list.size(),nursingData);
        notifyItemInserted(list.size());
    }

    @Override
    protected void onBindView(RecyclerView.ViewHolder holder, final int position) {
        final NursingViewHolder viewHolder = (NursingViewHolder) holder;
        final NursingData nursingData=list.get(position);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        viewHolder.title.setText("护理人"+(position+1));
        viewHolder.nursingId.setText(nursingData.getIdValue());
        viewHolder.name.setText(nursingData.getName());
        viewHolder.days.setText(nursingData.getDays());
        viewHolder.deleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除联系人
            }
        });
        viewHolder.nursingId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选择联系人信息
                Intent intent = new Intent(context,SelectCategoryActivity.class);
                ((AppCompatActivity) context).startActivityForResult(intent, position);
            }
        });
    }


    @Override
    public NursingViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_nursing, null);
        return new NursingViewHolder(view);
    }
    private boolean checkData(){

        return true;
    }
}
