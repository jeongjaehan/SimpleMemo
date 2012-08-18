package kr.kakaruto;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class MemoDBHelper extends SQLiteOpenHelper {
	public MemoDBHelper(Context context) {
		super(context, "Memo.db", null, 1);
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE memo ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
		"content TEXT, rdate TEXT);");
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS memo");
		onCreate(db);
	}
}