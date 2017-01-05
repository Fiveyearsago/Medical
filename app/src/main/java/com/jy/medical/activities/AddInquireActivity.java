package com.jy.medical.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jy.medical.R;
import com.jy.medical.adapter.InquireAdapter;
import com.jy.medical.greendao.entities.Inquire;
import com.jy.medical.greendao.manager.ContactManager;
import com.jy.medical.greendao.manager.InquireManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.util.ToastUtil;
import com.jy.medical.widget.ClearEditText;
import com.jy.medical.widget.SwipeBackLayout;

import java.util.ArrayList;
import java.util.List;

public class AddInquireActivity extends BaseActivity {

    private static final String[] PHONES_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.PHOTO_ID, ContactsContract.CommonDataKinds.Phone.CONTACT_ID};
    private InquireManager inquireManager;
    private String taskNo;
    private String name;
    private String phone;
    private String peopleId = "";
    private String peopleIdValue = "";
    private Long id;
    private ClearEditText nameEdit, phoneEdit;
    private TextView textId;

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
        name = parms.getString("name") == null ? "" : parms.getString("name");
        phone = parms.getString("phone") == null ? "" : parms.getString("phone");
        peopleId = parms.getString("peopleId") == null ? "" : parms.getString("peopleId");
        peopleIdValue = parms.getString("peopleIdValue") == null ? "" : parms.getString("peopleIdValue");
        id = parms.getLong("Id") > 0 ? parms.getLong("Id") : 0;
    }

    @Override
    public void initView() {
        setStatusBarTint();
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        setTitleState(findViewById(R.id.title_head), true, "添加被询问人", true, "保存");
        inquireManager = DaoUtils.getInquireManagerInstance();
        findViewById(R.id.add_image).setOnClickListener(this);
        nameEdit = (ClearEditText) findViewById(R.id.edit_name);
        phoneEdit = (ClearEditText) findViewById(R.id.edit_phone);
        textId = (TextView) findViewById(R.id.people_id);
        textId.setOnClickListener(this);
        inquireManager = DaoUtils.getInquireManagerInstance();
        if (!name.equals("")) {
            nameEdit.setText(name);
        }
        if (!phone.equals("")) {
            phoneEdit.setText(phone);
        }
        if (!peopleIdValue.equals("")) {
            textId.setText(peopleIdValue);
        }
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_head_image:
                finish();
                break;
            case R.id.people_id:
                Intent intent1 = new Intent(this, SelectCategoryActivity.class);
                intent1.putExtra("kindCode", "D117");
                intent1.putExtra("title", "被扶养人身份");
                startActivityForResult(intent1, 0x12);
                break;
            case R.id.page_head_button:
                //保存被询问人
                saveContact();
                break;
            case R.id.add_image:
                //选择联系人
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(intent, 0x10);
                break;
        }
    }

    private void saveContact() {
        if (!checkContact()) return;
        Inquire inquire = new Inquire(nameEdit.getText().toString(), phoneEdit.getText().toString(), taskNo, peopleId, peopleIdValue);
        if (name.equals(""))
            if (!inquireManager.isExist(inquire)) {
                inquireManager.insertSingleData(inquire);
            } else {
                ToastUtil.showToast(this, "此被询问人已存在");
                return;
            }
        else {
            inquire.setId(id);
            inquireManager.updateData(inquire);
        }
        finish();

    }

    private boolean checkContact() {
        //检查联系人填写
        if (nameEdit.getText().toString().equals("") || phoneEdit.getText().toString().equals("") || textId.getText().toString().equals("")) {
            Toast.makeText(this, "请完善被询问人信息", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0x12:
                    peopleIdValue = data.getStringExtra("value");
                    peopleId = data.getStringExtra("key");
                    textId.setText(data.getStringExtra("value"));
                    break;
                case 0x10:
                    Uri inquire = data.getData();
                    ContentResolver resolver = this.getContentResolver();
                    // 获取手机联系人
                    Cursor phoneCursor2 = resolver.query(inquire,
                            PHONES_PROJECTION, null, null, null);
                    String phoneNumber = null, contactName = null;
                    while (phoneCursor2.moveToNext()) {
                        phoneNumber = phoneCursor2
                                .getString(1).replaceAll(" ", "");
                        contactName = phoneCursor2
                                .getString(0).replaceAll(" ", "");
                        phoneCursor2.close();
                        break;
                    }
                    nameEdit.setText(contactName);
                    phoneEdit.setText(phoneNumber);
                    nameEdit.setSelection(nameEdit.getText().length());
                    phoneEdit.setSelection(phoneEdit.getText().length());
                    break;
            }
        }

    }
}
