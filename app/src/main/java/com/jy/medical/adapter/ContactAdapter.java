package com.jy.medical.adapter;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jy.medical.R;
import com.jy.medical.adapter.viewholder.ContactViewHolder;
import com.jy.medical.greendao.entities.ContactData;

import java.util.List;

/**
 * Created by 19459 on 2016/9/21.
 */

public class ContactAdapter extends BaseHeadFootAdapter {

    private Context context;
    private List<ContactData> list;

    public ContactAdapter(Context context, List<ContactData> list) {
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

    public void addItem(){
        ContactData contactData=new ContactData("联系人"+(list.size()+1),"","");
        list.add(list.size(),contactData);
        notifyItemInserted(list.size());
    }

    @Override
    protected void onBindView(RecyclerView.ViewHolder holder, final int position) {
        final ContactViewHolder viewHolder = (ContactViewHolder) holder;
        final ContactData contactData=list.get(position);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        viewHolder.title.setText(contactData.getTitle());
        viewHolder.name.setText(contactData.getName());
        viewHolder.phone.setText(contactData.getPhoneNum());
        viewHolder.name.clearFocus();
        viewHolder.phone.clearFocus();

        viewHolder.deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除联系人
            }
        });
        viewHolder.deleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除联系人
            }
        });
        viewHolder.addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选择联系人信息
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                ((AppCompatActivity) context).startActivityForResult(intent, position);
            }
        });
        viewHolder.addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选择联系人信息
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                ((AppCompatActivity) context).startActivityForResult(intent, position);
                viewHolder.phone.clearFocus();
            }
        });
    }


    @Override
    public ContactViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_contact, null);
        return new ContactViewHolder(view);
    }
}
