package com.zxxapp.mall.maintenance.helper.TencentIM;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zxxapp.mall.maintenance.app.BaseApplication;

import java.io.File;

/**
 * Created by gabriel on 2017/3/5.
 */

public class SQLiteUtil {
    public static final String FRIEND_DB_TABLE_NAME = "friendList";
    public static final String FRIEND_DB_IDENTIFIER = "identifier";
    public static final String FRIEND_DB_NAME = "name";
    public static final String FRIEND_DB_REMARK = "remark";
    public static final String FRIEND_DB_TEAM = "team";
    public static SQLiteDatabase getFriendListDb() {
        String path = BaseApplication.getContext().getFilesDir() + File.separator + "firendList.db";
        int FriendListVer = 1;
        SQLiteOpenHelper sqLiteOpenHelper = new SQLiteOpenHelper(BaseApplication.getContext(), path, null, FriendListVer) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL("create table friendList(identifier varchar(40) PRIMARY KEY, name varchar(40), remark varchar(40), team varchar(40))");
//                        new Object[]{FRIEND_DB_TABLE_NAME, FRIEND_DB_IDENTIFIER, FRIEND_DB_NAME, FRIEND_DB_REMARK, FRIEND_DB_TEAM});
//                db.execSQL("create table ?(? Integer)",new Object[]{"kkk", FRIEND_DB_IDENTIFIER });
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };
        return sqLiteOpenHelper.getWritableDatabase();
    }
}
