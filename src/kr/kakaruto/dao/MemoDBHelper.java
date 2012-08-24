package kr.kakaruto.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MemoDBHelper extends SQLiteOpenHelper {
	public MemoDBHelper(Context context) {
		super(context, "Memo.db", null, 2);
	}

	public void onCreate(SQLiteDatabase db) {
		StringBuffer createDDL = new StringBuffer();
		createDDL.append("CREATE TABLE memo ( _id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT, rdate TEXT, isFav TEXT)");
		db.execSQL(createDDL.toString());
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS memo");
		onCreate(db);
	}
}