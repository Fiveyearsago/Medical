package com.jy.medical.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jy.medical.R;
import com.jy.medical.greendao.entities.CityData;

import java.util.List;

/**
 * Created by songran on 17/1/9.
 */

public class ProvinceLAdapter extends BaseAdapter {
    private Context context;
    private List<CityData> list;
    private ProvinceLAdapter.LCallBack lCallBack;

    public ProvinceLAdapter(Context context, List<CityData> list, LCallBack lCallBack) {
        this.context = context;
        this.list = list;
        this.lCallBack = lCallBack;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
//        if (convertView == null) {
        convertView = LayoutInflater.from(context).inflate(
                R.layout.item_province, null);
        viewHolder = new ViewHolder();
        viewHolder.name = (TextView) convertView
                .findViewById(R.id.item_province);
        convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
        viewHolder.name.setText(list.get(position).getName().toString());
        return convertView;
    }

    class ViewHolder {
        TextView name;
    }

    public interface LCallBack {
        void changeCityData(int position);
    }
}
