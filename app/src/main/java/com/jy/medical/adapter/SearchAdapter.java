package com.jy.medical.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jy.medical.R;
import com.jy.medical.adapter.viewholder.AddressViewHolder;
import com.jy.medical.adapter.viewholder.SearchViewHolder;
import com.jy.medical.greendao.entities.AddressData;
import com.jy.medical.greendao.entities.SearchData;

import java.util.List;

/**
 * Created by 19459 on 2016/9/21.
 */

public class SearchAdapter extends BaseHeadFootAdapter {

    private Context context;
    private List<SearchData> list;

    public SearchAdapter(Context context, List<SearchData> list) {
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
        final SearchViewHolder viewHolder = (SearchViewHolder) holder;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        viewHolder.text.setText(list.get(position).getSearchText());
    }

    @Override
    public SearchViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search, null);
        return new SearchViewHolder(view);
    }
}
