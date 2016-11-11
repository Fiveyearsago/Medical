package com.jy.medical.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jy.medical.R;
import com.jy.medical.greendao.entities.CityData;

import java.util.List;

/**
 * Created by 19459 on 2016/9/21.
 */

public class CityAdapter extends BaseHeadFootAdapter {

    private Context context;
    private List<CityData>list;

    public CityAdapter(Context context, List<CityData> list) {
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
        final CityViewHolder viewHolder= (CityViewHolder) holder;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                view.setBackgroundColor(Color.parseColor("#F5F5F5"));
//                viewHolder.toolText.setTextColor(Color.parseColor("#999999"));
//                Toast.makeText(context,"big text "+position+"was clicked",Toast.LENGTH_SHORT).show();
//                view.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                context.startActivity(new Intent(context, LawDetailActivity.class));
            }
        });

        viewHolder.name.setText(list.get(position).getName());
    }

    @Override
    public CityViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_city,null);
        return new CityViewHolder(view);
    }
}
