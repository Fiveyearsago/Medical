package com.jy.medical.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.jy.medical.R;
import com.jy.medical.activities.LocalAlbumActivity;
import com.jy.medical.util.ImageUtils;
import com.jy.medical.util.PublicString;

import java.util.List;

/**
 * Created by songran on 16/10/10.
 */

public class PictureAdapter extends BaseHeadFootAdapter {
    private Context context;
    private List<Bitmap> list;

    public PictureAdapter(Context context, List<Bitmap> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    protected void onBindHeaderView(View headerView) {

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
        final PictureHolder viewHolder= (PictureHolder) holder;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        viewHolder.imageView.setImageBitmap(list.get(position));
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position==list.size()-1){
                    //添加照片
                    new AlertView("添加照片", null, "取消", null,
                            new String[]{"拍照", "从相册中选择"},
                            context, AlertView.Style.ActionSheet, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position1) {
                            if (position1==0){
                                //拍照


                            }else if (position1==1){
                                //从相册选取
//                                Intent intent = new Intent(
//                                        "com.jy.recycle.action.CHOSE_PHOTOS");
//                                // 指定图片最大选择数
//                                intent.putExtra("maxNum", 10);
                                Intent intent = new Intent(context, LocalAlbumActivity.class);
                                ((AppCompatActivity)context).startActivityForResult(intent, ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);
                            }
                        }
                    }).show();
                }else {
                    //预览照片
                }
            }
        });
    }

    @Override
    protected PictureHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_picture,null);
        return new PictureHolder(view);
    }
}
