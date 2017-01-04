package com.jy.medical.adapter;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jy.medical.R;
import com.jy.medical.activities.SelectCategoryActivity;
import com.jy.medical.adapter.viewholder.InquireViewHolder;
import com.jy.medical.greendao.entities.Inquire;

import java.util.List;

/**
 * Created by 19459 on 2016/9/21.
 */

public class InquireAdapter extends BaseHeadFootAdapter {

    private Context context;
    private List<Inquire> list;

    public InquireAdapter(Context context, List<Inquire> list) {
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

    public void addItem(String taskNo) {
        Inquire inquire = new Inquire("", "", taskNo, "", "");
        list.add(list.size(), inquire);
        notifyItemInserted(list.size());
    }

    @Override
    protected int getItemNum() {
        return list.size();
    }

    @Override
    protected void onBindView(RecyclerView.ViewHolder holder, final int position) {
        final InquireViewHolder viewHolder = (InquireViewHolder) holder;
        final Inquire contactData = list.get(position);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        viewHolder.peopleId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选择行业
                Intent intent = new Intent(context, SelectCategoryActivity.class);
                intent.putExtra("kindCode", "D117");
                intent.putExtra("title", "被扶养人身份");
                ((AppCompatActivity) context).startActivityForResult(intent, 0x12);
            }
        });
        viewHolder.name.setText(contactData.getName());
        viewHolder.phone.setText(contactData.getPhoneNum());
        viewHolder.peopleId.setText(contactData.getPeopleIdentityValue());
        viewHolder.name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                list.get(position).setName(s + "");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        viewHolder.phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                list.get(position).setPhoneNum(s + "");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        viewHolder.name.clearFocus();
        viewHolder.phone.clearFocus();

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
    public InquireViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_inquire, null);
        return new InquireViewHolder(view);
    }
}
