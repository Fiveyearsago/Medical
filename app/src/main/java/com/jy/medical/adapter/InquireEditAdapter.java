package com.jy.medical.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jy.medical.R;
import com.jy.medical.adapter.viewholder.ContactEditViewHolder;
import com.jy.medical.adapter.viewholder.InquireEditViewHolder;
import com.jy.medical.greendao.entities.ContactData;
import com.jy.medical.greendao.entities.Inquire;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Created by 19459 on 2016/9/21.
 */

public class InquireEditAdapter extends SwipeMenuAdapter<InquireEditViewHolder> {

    private Context context;
    private List<Inquire> list;

    public InquireEditAdapter(Context context, List<Inquire> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_edit_inquire, null, false);
        return view;
    }

    @Override
    public InquireEditViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new InquireEditViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(InquireEditViewHolder holder, int position) {
        final InquireEditViewHolder viewHolder = (InquireEditViewHolder) holder;
        final Inquire inquire = list.get(position);
        viewHolder.name.setText(inquire.getName());
        viewHolder.phone.setText(inquire.getPhoneNum());
        viewHolder.peopleId.setText("(" + inquire.getPeopleIdentityValue() + ")");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
