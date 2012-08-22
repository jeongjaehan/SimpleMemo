package kr.kakaruto.dao;

import java.util.ArrayList;

import kr.kakaruto.activity.MemoDBHelper;
import kr.kakaruto.domain.Memo;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MemoDao {
	
	/**
	 * 메모 목록 조회 Dao
	 * @param pMemo
	 * @return
	 */
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
	
	
	/**
	 * 메모 상세보기 Dao
	 * @param pMemo
	 * @return
	 */
	public Memo getOneMemo(Memo pMemo){
		Memo rMemo = null;
		
		String query ="select _id,content,rdate from memo where _id="+pMemo.get_id();
		
		MemoDBHelper dbHelper = new MemoDBHelper(pMemo.getContext());
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
		cursor.moveToFirst();

		rMemo = new Memo();
		rMemo.set_id(cursor.getInt(0));
		rMemo.setContent(cursor.getString(1));
		rMemo.setRdate(cursor.getString(2));

		db.close();
		cursor.close();
		
		return rMemo;
		
	}
	
	
	/**
	 * 메모 삭제 Dao
	 * @param pMemo
	 */
	public void deleteMemo(Memo pMemo){
		MemoDBHelper dbHelper = new MemoDBHelper(pMemo.getContext());
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		db.execSQL("delete from memo where _id="+pMemo.get_id());

		dbHelper.close();
		db.close();
	}
	
	/**
	 * 메모 수정 Dao
	 * @param pMemo
	 */
	public void updateMemo(Memo pMemo){
		MemoDBHelper dbHelper = new MemoDBHelper(pMemo.getContext());
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.execSQL("update memo set content='"+pMemo.getContent()+"' , rdate='"+pMemo.getRdate()+"' where _id="+pMemo.get_id());
		
		dbHelper.close();
		db.close();
	}
	
	/**
	 * 메모 등록 Dao
	 * @param pMemo
	 */
	public void insertMemo(Memo pMemo){
		MemoDBHelper dbHelper = new MemoDBHelper(pMemo.getContext());
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		// insert 메서드로 삽입
		ContentValues row = new ContentValues();
		row.put("content", pMemo.getContent());
		row.put("rdate", pMemo.getRdate());
		
		db.insert("memo", null, row);
		
		dbHelper.close();
		db.close();
	}
}
