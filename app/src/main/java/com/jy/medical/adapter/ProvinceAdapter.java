package com.jy.medical.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jy.medical.R;
import com.jy.medical.adapter.viewholder.ProvinceViewHolder;
import com.jy.medical.greendao.entities.CityData;

import java.util.List;

/**
 * Created by 19459 on 2016/9/21.
 */

public class ProvinceAdapter extends BaseHeadFootAdapter {

    private Context context;
    private List<CityData>list;
    private LCallBack lCallBack;

    public ProvinceAdapter(Context context, List<CityData> list,LCallBack lCallBack) {
        this.context = context;
        this.list = list;
        this.lCallBack=lCallBack;
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
        final ProvinceViewHolder viewHolder= (ProvinceViewHolder) holder;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lCallBack.changeCityData(position);
            }
        });

        viewHolder.name.setText(list.get(position).getName());
    }

    @Override
    public ProvinceViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_province,null);
        return new ProvinceViewHolder(view);
    }
    public interface LCallBack{
        public void changeCityData(int position);
    }
}
