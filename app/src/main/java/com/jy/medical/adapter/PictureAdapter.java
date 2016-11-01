package com.jy.medical.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jy.medical.R;
import com.jy.medical.activities.LawDetailActivity;
import com.jy.medical.entities.ToolData;

import java.util.List;

/**
 * Created by songran on 16/10/10.
 */

public class PictureAdapter extends BaseHeadFootAdapter {
    private Context context;
    private List<Bitmap> list;

    public PictureAdapter(Context context, List<Bitmap> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    protected void onBindHeaderView(View headerView) {

    }

    @Override
    protected void onBindFooterView(View footerView) {

    }

    @Override
    protected int getItemNum() {
        return list.size();
    }

    @Override
    protected void onBindView(RecyclerView.ViewHolder holder, int position) {
        final PictureHolder viewHolder= (PictureHolder) holder;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        viewHolder.imageView.setImageBitmap(list.get(position));
    }

    @Override
    protected PictureHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_picture,null);
        return new PictureHolder(view);
    }
}
