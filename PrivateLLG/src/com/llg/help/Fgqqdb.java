package com.llg.help;

import java.io.File;

import com.llg.privateproject.utils.CommonUtils;
import com.smartandroid.sa.aysnc.Log;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Fgqqdb {
private  String path;
private SQLiteDatabase db;
private String tableName="fg";
public Fgqqdb(){
	
	openDb();
}
/**打开fgqq表*/
private void openDb(){
	String path1=CommonUtils.createSDCardDir()+"db/";
	File path2=new File(path1);
	if(!path2.exists()){
		path2.mkdirs();
	}
	db=SQLiteDatabase.openOrCreateDatabase(path2+"fgqq", null);
//	String createTable="create table if not exists fgqq (_id INTEGER PRIMARY KEY AUTOINCREMENT,homepage)";
	String createTable="create table if not exists "+tableName+" (_id ,a,b,c,d)";
	db.execSQL(createTable);
	
}
public SQLiteDatabase getdb(){
	return db;
}
/**向表中插入数据*/
public void insert(){
	//insert into [User] (UserId,Name,LoginName,Pwd)values(5,123,31321,1);
	//a 商城,b分类,c推荐
	String insertsql="INSERT INTO "+tableName+"(_id, a, b ,c, d)"+" VALUES (?,?,?,?,?)";
	db.execSQL(insertsql,new String[]{"1","1","1","1","1"});
}
/**更新数据*/
public void update(String newdata,String abcd){
	if(selectData(abcd).length()<=0){
	
	insert();
	}
		ContentValues cv=new ContentValues();
		cv.put(abcd, newdata);
		//update(String table, ContentValues values, String whereClause, String[] whereArgs)
		db.update(tableName, cv, " _id = ? ", new String[]{"1"});
//		String sql=" update "+tableName+" set "+abcd +" = "+newdata + " where _id=?";
//		db.execSQL(sql,new String[]{"1"});
	
}
/**查询数据*/
public String selectData(String a){
	Cursor c=db.rawQuery("SELECT * FROM "+tableName+"  WHERE _id = ? ", new String[]{"1"});
	
//	Log.d("my","c.getCount()"+ c.getCount());
	while(c.moveToNext()){
//	Log.d("my", "c.getstrring:"+c.getString(c.getColumnIndex(a)) );
		return c.getString(c.getColumnIndex(a));
	}
	
	c.close();
	
	return "";
}
}
