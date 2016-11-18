package com.jy.medical.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jy.medical.MedicalApplication;
import com.jy.medical.R;
import com.jy.medical.adapter.SelectedDiagnoseAdapter;
import com.jy.medical.adapter.SelectedHospitalAdapter;
import com.jy.medical.greendao.entities.SelectedDepartment;
import com.jy.medical.greendao.entities.SelectedDiagnose;
import com.jy.medical.greendao.entities.SelectedHospital;
import com.jy.medical.greendao.manager.SelectedDepartmentManager;
import com.jy.medical.greendao.manager.SelectedDiagnoseManager;
import com.jy.medical.greendao.manager.SelectedHospitalManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.inter.OnItemClickListener;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MedicalVisitsActivity extends BaseActivity {
    private SwipeMenuRecyclerView hospitalRecyclerView;
    private SwipeMenuRecyclerView diagnoseRecyclerView;
    private SelectedHospitalAdapter selectedHospitalAdapter;
    private SelectedDiagnoseAdapter selectedDiagnoseAdapter;
    private List<SelectedHospital> selectedHospitals;
    private List<SelectedDepartment> selectedDepartments;
    private List<SelectedDiagnose>selectedDiagnoseList;
    private Context mContext;
    private String taskNo;
    private SelectedHospitalManager selectedHospitalManager= DaoUtils.getSelectedHospitaInstance();
    private SelectedDiagnoseManager selectedDiagnoseManager= DaoUtils.getSelectedDiagnoseInstance();
    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_medical_visits;
    }

    @Override
    public void initParms(Bundle parms) {
        taskNo=parms.getString("taskNo");
    }

    @Override
    public void initView() {
        mContext=this;
        setStatusBarTint();
        MedicalApplication.getInstance().addActivity(this);
        setTitleState(findViewById(R.id.title_head), true, "编辑", true, "保存");
        selectedHospitals=new ArrayList<>();
        selectedDiagnoseList=new ArrayList<>();
        findViewById(R.id.add_hospital).setOnClickListener(this);
        findViewById(R.id.add_nurse).setOnClickListener(this);
        findViewById(R.id.add_diagnose).setOnClickListener(this);
        hospitalRecyclerView= (SwipeMenuRecyclerView) findViewById(R.id.hospital_recycler_view);
        diagnoseRecyclerView= (SwipeMenuRecyclerView) findViewById(R.id.diagnose_recycler_view);

        hospitalRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        hospitalRecyclerView.setHasFixedSize(true);
        hospitalRecyclerView.setItemAnimator(new DefaultItemAnimator());
        hospitalRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        hospitalRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);
        selectedHospitalAdapter=new SelectedHospitalAdapter(selectedHospitals,this);
        selectedHospitalAdapter.setOnItemClickListener(onItemClickListener);
        hospitalRecyclerView.setAdapter(selectedHospitalAdapter);

        diagnoseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        diagnoseRecyclerView.setHasFixedSize(true);
        diagnoseRecyclerView.setItemAnimator(new DefaultItemAnimator());
        diagnoseRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        diagnoseRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener1);
        selectedDiagnoseAdapter=new SelectedDiagnoseAdapter(selectedDiagnoseList,this);
        selectedDiagnoseAdapter.setOnItemClickListener(onItemClickListener1);
        diagnoseRecyclerView.setAdapter(selectedDiagnoseAdapter);


    }

    public  void initHospitalData(){
        selectedHospitals=selectedHospitalManager.getDataList(taskNo);
        selectedHospitalAdapter=new SelectedHospitalAdapter(selectedHospitals,this);
        selectedHospitalAdapter.setOnItemClickListener(onItemClickListener);
        hospitalRecyclerView.setAdapter(selectedHospitalAdapter);
    }
    public  void initDiagnoseData(){
        selectedDiagnoseList=selectedDiagnoseManager.getDataList(taskNo);
        selectedDiagnoseAdapter=new SelectedDiagnoseAdapter(selectedDiagnoseList,this);
        selectedDiagnoseAdapter.setOnItemClickListener(onItemClickListener1);
        diagnoseRecyclerView.setAdapter(selectedDiagnoseAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initHospitalData();
        initDiagnoseData();
    }

    @Override
    public void widgetClick(View v) {
        Bundle bundle=new Bundle();
        bundle.putString("taskNo",taskNo);
        switch (v.getId()) {
            case R.id.page_head_image:
                finish();
                break;
            case R.id.page_head_button:
                break;
            case R.id.add_hospital:
                //选择医院
                bundle.putString("taskNo",taskNo);
                startActivity(SelectHospitalActivity.class,bundle);
                break;
            case R.id.add_nurse:
                //添加护理人
                startActivity(AddNursingActivity.class,bundle);
                break;
            case R.id.add_diagnose:
                //添加护理人
                startActivity(AddDiagnoseActivity.class,bundle);
                break;
        }
    }
    private SwipeMenuCreator swipeMenuCreator=new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.creator_width);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
//            SwipeMenuItem editItem = new SwipeMenuItem(mContext)
//                    .setBackgroundDrawable(R.drawable.creator_edit_selector)
//                    .setImage(R.mipmap.creator_edit)
//                    .setWidth(width)
//                    .setHeight(height);
            SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
                    .setBackgroundDrawable(R.drawable.creator_delete_selector)
                    .setImage(R.mipmap.creator_delete)
                    .setWidth(width)
                    .setHeight(height);
//            swipeRightMenu.addMenuItem(editItem);
            swipeRightMenu.addMenuItem(deleteItem);
        }
    };
    /**
     * 菜单点击监听。
     */
    private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                selectedHospitalManager.deleteSingleData(selectedHospitals.get(adapterPosition));
                selectedHospitals.remove(adapterPosition);
                selectedHospitalAdapter.notifyDataSetChanged();
                Toast.makeText(mContext, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                Toast.makeText(mContext, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }
        }
    };
    private OnSwipeMenuItemClickListener menuItemClickListener1 = new OnSwipeMenuItemClickListener() {
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                selectedDiagnoseManager.deleteSingleData(selectedDiagnoseList.get(adapterPosition));
                selectedDiagnoseList.remove(adapterPosition);
                selectedDiagnoseAdapter.notifyDataSetChanged();
                Toast.makeText(mContext, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                Toast.makeText(mContext, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }
        }
    };
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            Toast.makeText(mContext, "我是第" + position + "条。", Toast.LENGTH_SHORT).show();
        }
    };
    private OnItemClickListener onItemClickListener1 = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            Toast.makeText(mContext, "我是第" + position + "条。", Toast.LENGTH_SHORT).show();
        }
    };

}
