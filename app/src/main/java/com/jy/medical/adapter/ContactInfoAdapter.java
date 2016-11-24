package com.jy.medical.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.jy.medical.R;
import com.jy.medical.activities.FollowDetailActivity;
import com.jy.medical.adapter.viewholder.ContactEditViewHolder;
import com.jy.medical.greendao.entities.ContactData;
import com.jy.medical.greendao.manager.ContactManager;
import com.jy.medical.greendao.util.DaoUtils;

import java.util.List;

/**
 * Created by 19459 on 2016/9/21.
 */

public class ContactInfoAdapter extends BaseHeadFootAdapter {

    private Context context;
    private List<ContactData> list;

    public ContactInfoAdapter(Context context, List<ContactData> list) {
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
        final ContactEditViewHolder viewHolder = (ContactEditViewHolder) holder;
        final ContactData contactData=list.get(position);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        viewHolder.name.setText(contactData.getName());
        viewHolder.phone.setText(contactData.getPhoneNum());
        viewHolder.deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除联系人
                AlertView mAlertView = new AlertView(contactData.getName(), contactData.getPhoneNum(), "取消", new String[]{"呼叫"}, null, context, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position1) {
                        if (position1==0){
                            //拨打电话
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contactData.getPhoneNum().trim()));
                            context.startActivity(intent);
                        }
                    }
                }).setCancelable(true).setOnDismissListener(null);
                mAlertView.show();
            }
        });
    }


    @Override
    public ContactEditViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_info_contact, null);
        return new ContactEditViewHolder(view);
    }
}
