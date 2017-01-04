package com.jy.medical.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jy.medical.R;
import com.jy.medical.adapter.BaseHeadFootAdapter;
import com.jy.medical.greendao.entities.AddressData;

import java.util.List;

/**
 * Created by 19459 on 2016/9/21.
 */

public class AddressAdapter1 extends BaseAdapter {

    private Context context;
    private List<AddressData> list;
    private ACallBack callBack;

    public AddressAdapter1(Context context, List<AddressData> list, ACallBack callBack) {
        this.context = context;
        this.list = list;
        this.callBack = callBack;
    }


    public void setData(List<AddressData> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_address, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView
                    .findViewById(R.id.item_address_title);
            viewHolder.text = (TextView) convertView
                    .findViewById(R.id.item_address_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.changeSearch(position);
            }
        });

        viewHolder.title.setText(list.get(position).getTitle());
        viewHolder.text.setText(list.get(position).getText());
        return convertView;
    }

    public interface ACallBack {
        void changeSearch(int index);
    }

    public static class ViewHolder {
        public TextView title;
        public TextView text;
    }
}
