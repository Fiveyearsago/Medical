package com.jy.medical.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.jy.medical.R;
import com.jy.medical.adapter.viewholder.ContactEditViewHolder;
import com.jy.medical.greendao.entities.ContactData;
import com.jy.medical.greendao.manager.ContactManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Created by 19459 on 2016/9/21.
 */

public class ContactEditAdapter extends SwipeMenuAdapter<ContactEditViewHolder> {

    private Context context;
    private List<ContactData> list;
    public ContactEditAdapter(Context context, List<ContactData> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_edit_contact, null);
        return view;
    }

    @Override
    public ContactEditViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new ContactEditViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(ContactEditViewHolder holder, int position) {
        final ContactEditViewHolder viewHolder = (ContactEditViewHolder) holder;
        final ContactData contactData=list.get(position);
        viewHolder.name.setText(contactData.getName());
        viewHolder.phone.setText(contactData.getPhoneNum());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
