package com.jy.medical.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jy.medical.R;

/**
 * Created by songran on 16/10/10.
 */

public class LawViewHolder  extends RecyclerView.ViewHolder {
    public TextView lawText;
    public TextView lawOrganization;
    public TextView lawTime;
    public LawViewHolder(View itemView) {
        super(itemView);
        lawText = (TextView) itemView.findViewById(R.id.law_text);
        lawOrganization = (TextView) itemView.findViewById(R.id.law_detail_kind_text);
        lawTime = (TextView) itemView.findViewById(R.id.law_detail_time);
    }
}
