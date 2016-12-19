package com.jy.medical.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jy.medical.R;
import com.jy.medical.greendao.entities.SupporterPerson;
import com.jy.medical.inter.OnItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Created by songran on 16/11/16.
 */

public class SelectedSupporterAdapter extends SwipeMenuAdapter<SelectedSupporterAdapter.SelectedSupporterViewHolder> {
    public List<SupporterPerson> selectedNursings;
    public Context context;
    public OnItemClickListener mOnItemClickListener;
    public SelectedSupporterAdapter(Context context,List<SupporterPerson> selectedNursings ) {
        this.selectedNursings = selectedNursings;
        this.context = context;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(context).inflate(R.layout.item_selected_supporter, null,false);
    }

    @Override
    public SelectedSupporterViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new SelectedSupporterViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(SelectedSupporterViewHolder holder, int position) {
        holder.setOnItemClickListener(mOnItemClickListener);
        holder.supporterName.setText(selectedNursings.get(position).getName());
        holder.supporterId.setText("("+selectedNursings.get(position).getRelationValue()+")");
        holder.supporterHousehold.setText("￥"+selectedNursings.get(position).getHouseKindValue());
        holder.supporterYears.setText(selectedNursings.get(position).getYears());
    }


    @Override
    public int getItemCount() {
        return selectedNursings.size();
    }


    static class SelectedSupporterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView supporterName;
        public TextView supporterId;
        public TextView supporterHousehold;
        public TextView supporterYears;
        OnItemClickListener mOnItemClickListener;
        public SelectedSupporterViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            supporterName = (TextView) itemView.findViewById(R.id.supporter_name);
            supporterId = (TextView) itemView.findViewById(R.id.supporter_relation);
            supporterHousehold = (TextView) itemView.findViewById(R.id.supporter_households);
            supporterYears = (TextView) itemView.findViewById(R.id.supporter_years);
        }
        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
