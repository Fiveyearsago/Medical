package com.jy.medical.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jy.medical.R;
import com.jy.medical.activities.LawDetailActivity;
import com.jy.medical.entities.AddressData;
import com.jy.medical.entities.ToolData;

import java.util.List;

/**
 * Created by 19459 on 2016/9/21.
 */

public class AddressAdapter extends BaseHeadFootAdapter {

    private Context context;
    private List<AddressData>list;

    public AddressAdapter(Context context, List<AddressData> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    protected void onBindHeaderView(View headerView) {
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,"head was clicked",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onBindFooterView(View footerView) {
//        footerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context,"foot was clicked",Toast.LENGTH_SHORT).show();
//            }
//        });
    }



    @Override
    protected int getItemNum() {
        return list.size();
    }



    @Override
    protected void onBindView(RecyclerView.ViewHolder holder, final int position) {
        final AddressViewHolder viewHolder= (AddressViewHolder) holder;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        viewHolder.title.setText(list.get(position).getTitle());
        viewHolder.text.setText(list.get(position).getText());
    }

    @Override
    public AddressViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_address,null);
        return new AddressViewHolder(view);
    }
}
