package com.jy.medical.greendao.manager;

/**
 * Created by songran on 16/11/3.
 */

import android.content.Context;

import com.jy.medical.greendao.entities.ClaimBeanData;
import com.jy.medical.greendao.entities.ContactData;
import com.jy.medical.greendao.entities.ContactData;
import com.jy.medical.greendao.gen.BaseDao;
import com.jy.medical.greendao.gen.ClaimBeanDataDao;
import com.jy.medical.greendao.gen.ContactDataDao;
import com.jy.medical.util.ToastUtil;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import static com.jy.medical.greendao.util.DaoUtils.context;


/**
 * Created by songran on 16/11/3.
 */
public class ContactManager extends BaseDao<ContactData> {
    public ContactManager(Context context) {
        super(context);
    }

    /***************************数据库查询*************************/

    /**
     * 通过ID查询对象
     *
     * @param id
     * @return
     */
    private ContactData loadById(long id) {
        return daoSession.getContactDataDao().load(id);
    }

    public boolean isExist(ContactData contactData) {
        ContactDataDao contactDataDao = daoSession.getContactDataDao();
        QueryBuilder<ContactData> qb = contactDataDao.queryBuilder();
        qb.where(qb.and(ContactDataDao.Properties.TaskNo.eq(contactData.getTaskNo()), ContactDataDao.Properties.Name.eq(contactData.getName()), ContactDataDao.Properties.PhoneNum.eq(contactData.getPhoneNum())));
        qb.list();
        return qb.list().size() > 0 ? true : false;
    }

    public List<ContactData> selectAllContact(String taskNo) {
        ContactDataDao contactDataDao = daoSession.getContactDataDao();
        QueryBuilder<ContactData> qb = contactDataDao.queryBuilder();
        qb.where(ContactDataDao.Properties.TaskNo.eq(taskNo));
        return qb.list();

    }

    public void insertData(@NotNull List<ContactData> contactDataList) {
        ContactDataDao contactDataDao = daoSession.getContactDataDao();
        for (int i = 0; i < contactDataList.size(); i++) {
            if (!isExist(contactDataList.get(i))) {
                contactDataDao.insert(contactDataList.get(i));
            }
        }

    }

    public void insertSingleData(@NotNull ContactData contactData) {
        ContactDataDao contactDataDao = daoSession.getContactDataDao();
        if (!isExist(contactData)) {
            contactDataDao.insert(contactData);
        }
    }

    public void updateData(@NotNull ContactData contactData) {
        ContactDataDao contactDataDao = daoSession.getContactDataDao();
        contactDataDao.update(contactData);
    }


    /**
     * 获取某个对象的主键ID
     *
     * @param contactData
     * @return
     */
    public long getID(ContactData contactData) {

        return daoSession.getContactDataDao().getKey(contactData);
    }


    /***************************数据库删除*************************/

    /**
     * 根据ID进行数据库的删除操作
     *
     * @param id
     */
    public void deleteById(long id) {

        daoSession.getContactDataDao().deleteByKey(id);
    }


    /**
     * 根据ID同步删除数据库操作
     *
     * @param ids
     */
    private void deleteByIds(List<Long> ids) {

        daoSession.getContactDataDao().deleteByKeyInTx(ids);
    }

    /***********************************
     * 在次添加一些ContactData特有的数据库操作语句
     ************************************/
    public void deleteSingleData(ContactData contactData) {
        ContactDataDao contactDataDao = daoSession.getContactDataDao();
        if (isExist(contactData)) {
            contactDataDao.delete(contactData);
        }
    }
}
