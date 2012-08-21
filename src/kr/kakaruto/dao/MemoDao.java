package kr.kakaruto.dao;

import java.util.ArrayList;

import kr.kakaruto.activity.MemoDBHelper;
import kr.kakaruto.domain.Memo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MemoDao {
	
	
	public ArrayList<Memo> getMemoList(Memo pMemo){
		
		ArrayList<Memo> returnMemoList = new ArrayList<Memo>();
		
		String query ="";
		String content = pMemo.getContent();
		
		if(content.equals(""))
			query = "select _id, content, rdate from memo order by _id desc ";
		else
			query = "select _id, content, rdate from memo where content like '%"+content+"%'  order by _id desc ";

		MemoDBHelper dbHelper = new MemoDBHelper(pMemo.getContext());
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
		while(cursor.moveToNext()){
			Memo memo = new Memo();
			memo.set_id(cursor.getInt(0));
			memo.setContent(cursor.getString(1));
			memo.setRdate(cursor.getString(2));

			returnMemoList.add(memo);
		}
		
		dbHelper.close();
		db.close();
		cursor.close();
		
		return returnMemoList;
	}
}
