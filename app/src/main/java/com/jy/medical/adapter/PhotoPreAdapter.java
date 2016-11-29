package com.jy.medical.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.jy.medical.R;
import com.jy.medical.activities.LocalAlbumActivity;
import com.jy.medical.util.ImageUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by songran on 16/10/10.
 */

public class PhotoPreAdapter extends PagerAdapter {
    private Context context;
    private List<Bitmap> list;
    private List<ImageView> imageViews = new ArrayList<>();

    public PhotoPreAdapter(Context context, List<Bitmap> list) {
        this.context = context;
        this.list = list;

    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(R.layout.item_picture_pre, null);
        imageView.setImageBitmap(list.get(position));
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
//        container.removeViewAt(position);
        ((ViewPager) container).removeView((View)object);
    }
    @Override
    public int getItemPosition(Object object) {//TODO 这是重点继续研究
        //return super.getItemPosition(object);//默认是不改变
        return POSITION_NONE;//可以即时刷新看源码
    }
}
