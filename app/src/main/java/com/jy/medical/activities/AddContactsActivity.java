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
import android.widget.Toast;

import com.jy.medical.R;
import com.jy.medical.adapter.ContactAdapter;
import com.jy.medical.greendao.entities.ContactData;
import com.jy.medical.greendao.manager.ContactManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.util.ToastUtil;
import com.jy.medical.widget.ClearEditText;
import com.jy.medical.widget.SwipeBackLayout;

import java.util.ArrayList;
import java.util.List;

public class AddContactsActivity extends BaseActivity {

    private static final String[] PHONES_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.PHOTO_ID, ContactsContract.CommonDataKinds.Phone.CONTACT_ID};
    private ContactManager contactManager;
    private String taskNo;
    private String name;
    private String phone;
    private Long id;
    private ClearEditText nameEdit, phoneEdit;

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_contacts;
    }

    @Override
    public void initParams(Bundle parms) {
        taskNo = parms.getString("taskNo");
        name = parms.getString("name") == null ? "" : parms.getString("name");
        phone = parms.getString("phone") == null ? "" : parms.getString("phone");
        id = parms.getLong("Id") > 0 ? parms.getLong("Id") : 0;
    }

    @Override
    public void initView() {
        setStatusBarTint();
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        setTitleState(findViewById(R.id.title_head), true, "添加联系人", true, "保存");
        findViewById(R.id.add_image).setOnClickListener(this);
        nameEdit = (ClearEditText) findViewById(R.id.name);
        phoneEdit = (ClearEditText) findViewById(R.id.phone);
        contactManager = DaoUtils.getContactInstance();
        if (!name.equals("")) {
            nameEdit.setText(name);
        }
        if (!phone.equals("")) {
            phoneEdit.setText(phone);
        }
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_head_image:
                finish();
                break;
            case R.id.page_head_button:
                //保存联系人
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
        ContactData contactData = new ContactData(taskNo, nameEdit.getText().toString(), phoneEdit.getText().toString());

        if (name.equals(""))
            if (!contactManager.isExist(contactData)) {
                contactManager.insertSingleData(contactData);
            } else {
                ToastUtil.showToast(this, "此联系人已存在");
                return;
            }
        else {
            contactData.setId(id);
            contactManager.updateData(contactData);
        }
        finish();


    }

    private boolean checkContact() {
        //检查联系人填写
        if (nameEdit.getText().toString().equals("") || phoneEdit.getText().toString().equals("")) {
            Toast.makeText(this, "请完善联系人信息", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 0x10) {
            Uri contactData = data.getData();
            ContentResolver resolver = this.getContentResolver();
            // 获取手机联系人
            Cursor phoneCursor2 = resolver.query(contactData,
                    PHONES_PROJECTION, null, null, null);
            String phoneNumber = "", contactName = "";
            while (phoneCursor2.moveToNext()) {
                phoneNumber = phoneCursor2
                        .getString(1).replace(" ", "");
                contactName = phoneCursor2
                        .getString(0).replace(" ", "");
                phoneCursor2.close();
                nameEdit.setText(contactName);
                phoneEdit.setText(phoneNumber);
                nameEdit.setSelection(nameEdit.getText().length());
                phoneEdit.setSelection(phoneEdit.getText().length());
                break;
            }

        }

    }
}
