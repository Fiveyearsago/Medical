package com.jy.medical.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jy.medical.R;
import com.jy.medical.adapter.viewholder.CategoryViewHolder;
import com.jy.medical.adapter.viewholder.DepartmentViewHolder;
import com.jy.medical.greendao.entities.CategoryData;
import com.jy.medical.greendao.entities.MedicalDepartment;

import java.util.List;

/**
 * Created by 19459 on 2016/9/21.
 */

public class CategoryAdapter extends BaseHeadFootAdapter {
    private Context context;
    private List<CategoryData> list;
    private NotifyCallBack notifyCallBack;

    public CategoryAdapter(Context context, List<CategoryData> list) {
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

    @Override
    protected void onBindView(RecyclerView.ViewHolder holder, final int position) {
        final CategoryViewHolder viewHolder = (CategoryViewHolder) holder;
        viewHolder.setIsRecyclable(false);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,"click",Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.text.setText(list.get(position).getValue());
        viewHolder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,"click",Toast.LENGTH_SHORT).show();
//                notifyCallBack.changeHeadInfo(list.get(position));
                Intent intent=new Intent();
                intent.putExtra("key",list.get(position).getKey());
                intent.putExtra("value",list.get(position).getValue());
                ((AppCompatActivity)context).setResult(Activity.RESULT_OK,intent);
                ((AppCompatActivity) context).finish();
            }
        });
    }

    @Override
    public CategoryViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, null);
        return new CategoryViewHolder(view);
    }
    public interface NotifyCallBack{
        void changeHeadInfo(CategoryData categoryData);
    }
}
