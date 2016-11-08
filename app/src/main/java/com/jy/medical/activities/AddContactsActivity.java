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

import com.jy.medical.R;
import com.jy.medical.adapter.ContactAdapter;
import com.jy.medical.greendao.entities.ContactData;

import java.util.ArrayList;
import java.util.List;

public class AddContactsActivity extends BaseActivity {

    private RecyclerView contactRecycler;
    private List<ContactData>list;
    private ContactAdapter adapter;
    private static final String[] PHONES_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.PHOTO_ID, ContactsContract.CommonDataKinds.Phone.CONTACT_ID};

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_contacts;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView() {
        setStatusBarTint();
        setTitleState(findViewById(R.id.title_head), true, "添加联系人", true, "保存");
        findViewById(R.id.add_contact_layout).setOnClickListener(this);
        findViewById(R.id.add_contact_button).setOnClickListener(this);
        contactRecycler = (RecyclerView) findViewById(R.id.contact_recycler);
        contactRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        contactRecycler.setLayoutManager(layoutManager);
        list=new ArrayList<>();
        adapter=new ContactAdapter(this,list);
        contactRecycler.setAdapter(adapter);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.page_head_image:
                finish();
                break;
            case R.id.page_head_button:
                break;
            case R.id.add_contact_button:
                //添加一天联系人空白记录
                addItem();
                break;
            case R.id.add_contact_layout:
                //添加一天联系人空白记录
                addItem();
//                Toast.makeText(AddContactsActivity.this,"dd",Toast.LENGTH_SHORT).show();
                break;
        }
    }
    public void addItem(){
        adapter.addItem();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri contactData = data.getData();
            ContentResolver resolver = this.getContentResolver();
            // 获取手机联系人
            Cursor phoneCursor2 = resolver.query(contactData,
                    PHONES_PROJECTION, null, null, null);
            String phoneNumber=null,contactName = null;
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
            adapter.notifyDataSetChanged();
        }

    }
}
