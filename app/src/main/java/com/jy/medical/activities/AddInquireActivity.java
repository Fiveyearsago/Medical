package com.jy.medical.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.jy.medical.R;
import com.jy.medical.adapter.InquireAdapter;
import com.jy.medical.greendao.entities.Inquire;
import com.jy.medical.greendao.manager.ContactManager;
import com.jy.medical.greendao.manager.InquireManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.widget.SwipeBackLayout;

import java.util.ArrayList;
import java.util.List;

public class AddInquireActivity extends BaseActivity {

    private RecyclerView contactRecycler;
    private List<Inquire> list;
    private InquireAdapter adapter;
    private static final String[] PHONES_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.PHOTO_ID, ContactsContract.CommonDataKinds.Phone.CONTACT_ID};
    private InquireManager inquireManager;
    private String taskNo;

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_inquire;
    }

    @Override
    public void initParams(Bundle parms) {
        taskNo = parms.getString("taskNo");
    }

    @Override
    public void initView() {
        setStatusBarTint();
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        setTitleState(findViewById(R.id.title_head), true, "添加被询问人", true, "保存");
        contactRecycler = (RecyclerView) findViewById(R.id.contact_recycler);
        contactRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        contactRecycler.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        adapter = new InquireAdapter(this, list);
        contactRecycler.setAdapter(adapter);
        inquireManager = DaoUtils.getInquireManagerInstance();
        adapter.addItem(taskNo);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_head_image:
                finish();
                break;
            case R.id.page_head_button:
                //保存被询问人
                saveContact();
                break;
        }
    }

    private void saveContact() {
        if (!checkContact()) return;
        inquireManager.insertData(list);
        finish();

    }

    private boolean checkContact() {
        //检查联系人填写
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals("") || list.get(i).getPhoneNum().equals("") || list.get(i).getPeopleIdentityValue().equals("")) {
                Toast.makeText(this, "请完善被询问人信息", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0x12) {
                list.get(0).setPeopleIdentityValue(data.getStringExtra("value"));
                list.get(0).setPeopleIdentity(data.getStringExtra("key"));
                adapter.notifyDataSetChanged();
            } else {
                Uri inquire = data.getData();
                ContentResolver resolver = this.getContentResolver();
                // 获取手机联系人
                Cursor phoneCursor2 = resolver.query(inquire,
                        PHONES_PROJECTION, null, null, null);
                String phoneNumber = null, contactName = null;
                while (phoneCursor2.moveToNext()) {
                    phoneNumber = phoneCursor2
                            .getString(1);
                    contactName = phoneCursor2
                            .getString(0);
                    phoneCursor2.close();
                    break;
                }
                list.get(requestCode).setName(contactName);
                list.get(requestCode).setPhoneNum(phoneNumber);
                list.get(requestCode).setTaskNo(taskNo);

                adapter.notifyDataSetChanged();
            }
        }

    }
}
