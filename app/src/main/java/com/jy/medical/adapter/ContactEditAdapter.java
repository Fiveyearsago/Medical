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
    private ContactManager contactManager;
    public com.jy.medical.inter.OnItemClickListener mOnItemClickListener;
    public ContactEditAdapter(Context context, List<ContactData> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(com.jy.medical.inter.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
//    @Override
//    protected void onBindHeaderView(View headerView) {
//        headerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        });
//    }

//    @Override
//    protected void onBindFooterView(View footerView) {
//    }


//    @Override
//    protected int getItemNum() {
//        return list.size();
//    }


//    @Override
//    protected void onBindView(RecyclerView.ViewHolder holder, final int position) {
//        final ContactEditViewHolder viewHolder = (ContactEditViewHolder) holder;
//        final ContactData contactData=list.get(position);
//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        });
//        viewHolder.name.setText(contactData.getName());
//        viewHolder.phone.setText(contactData.getPhoneNum());
//        viewHolder.deleteImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //删除联系人
//                AlertView mAlertView = new AlertView("提示", "是否删除联系人？", "否", new String[]{"是"}, null, context, AlertView.Style.Alert, new OnItemClickListener() {
//                    @Override
//                    public void onItemClick(Object o, int position1) {
//                        if (position1==0){
//                            //删除联系人
//                            contactManager = DaoUtils.getContactInstance();
//                            Long id=contactManager.getID(list.get(position));
//                            contactManager.deleteById(id);
//                            list.remove(position);
//                            notifyDataSetChanged();
//
//                        }
//                    }
//                }).setCancelable(true).setOnDismissListener(null);
//                mAlertView.show();
//            }
//        });
//    }


//    @Override
//    public ContactEditViewHolder onCreateHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_edit_contact, null);
//        return new ContactEditViewHolder(view);
//    }

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
