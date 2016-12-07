package com.jy.medical.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.jy.medical.R;
import com.jy.medical.adapter.SortAdapter;
import com.jy.medical.util.GetLocation;
import com.jy.medical.util.SPUtils;
import com.jy.medical.widget.selectcityview.CharacterParser;
import com.jy.medical.widget.selectcityview.CitySortModel;
import com.jy.medical.widget.selectcityview.PinyinComparator;
import com.jy.medical.widget.selectcityview.SideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelectCityActivity extends BaseActivity implements GetLocation.LocationCallBack {
    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter adapter;
    private CharacterParser characterParser;
    private List<CitySortModel> SourceDateList;
    private TextView tv_catagory, tv_city_name;

    @Override
    public void initData() {
        GetLocation.getLoc(this, this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_city;
    }

    @Override
    public void initParams(Bundle parms) {

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_head_image:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void initView() {
        setStatusBarTint();
        setTitleState(findViewById(R.id.title_head), true, "地区选择", false, "");
        initSelectView();
    }

    private void initSelectView() {
        characterParser = CharacterParser.getInstance();

        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(dialog);
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position + 1);
                }

            }
        });


        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position >= 1) {
                    String province = ((CitySortModel) adapter.getItem(position - 1)).getName();
//                    Toast.makeText(getApplication(),
//                            province,
//                            Toast.LENGTH_SHORT).show();
//                    SPUtils.put(SelectCityActivity.this,"province",province);
                    Intent intent = new Intent();
                    intent.putExtra("province", province);
                    setResult(RESULT_OK, intent);
                    SelectCityActivity.this.finish();
                }
            }
        });

        SourceDateList = filledData(getResources().getStringArray(R.array.provinces));
        Collections.sort(SourceDateList, new PinyinComparator());
        adapter = new SortAdapter(this, SourceDateList);
        sortListView.addHeaderView(initHeadView());
        sortListView.setAdapter(adapter);
    }

    private View initHeadView() {
        View headView = getLayoutInflater().inflate(R.layout.item_city_head, null);
        tv_catagory = (TextView) headView.findViewById(R.id.tv_catagory);
        tv_city_name = (TextView) headView.findViewById(R.id.tv_city_name);
        tv_catagory.setText("当前位置");
        tv_city_name.setText("北京市");
//        Drawable drawable= getResources().getDrawable(R.mipmap.city_sure);
//        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//        tv_city_name.setCompoundDrawablesWithIntrinsicBounds(null, null,drawable , null);
        tv_city_name.setCompoundDrawablePadding(100);
        return headView;
    }

    private List<CitySortModel> filledData(String[] date) {
        List<CitySortModel> mSortList = new ArrayList<>();
        ArrayList<String> indexString = new ArrayList<>();

        for (int i = 0; i < date.length; i++) {
            CitySortModel sortModel = new CitySortModel();
            sortModel.setName(date[i]);
            String pinyin = characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();
            if (sortString.matches("[A-Z]")) {

                //对重庆多音字做特殊处理
                if (pinyin.startsWith("zhongqing")) {
                    sortString = "C";
                    sortModel.setSortLetters("C");
                } else {
                    sortModel.setSortLetters(sortString.toUpperCase());
                }

                if (!indexString.contains(sortString)) {
                    indexString.add(sortString);
                }
            }

            mSortList.add(sortModel);
        }
        Collections.sort(indexString);
        sideBar.setIndexText(indexString);
        return mSortList;

    }

    /**
     * 定位回调
     *
     * @param address
     * @param province
     * @param city
     */
    @Override
    public void getLocationSuccess(BDLocation bdLocation, String address, String province, String city) {
        tv_city_name.setText(province);
    }

    @Override
    public void getLocationFailed() {

    }
}
