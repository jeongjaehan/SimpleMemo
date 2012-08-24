package kr.kakaruto.dao;

import java.util.ArrayList;

import kr.kakaruto.domain.Memo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MemoDao {

	/**
	 * 메모 목록 조회 Dao
	 * @param pMemo
	 * @return
	 */
	public ArrayList<Memo> getMemoList(Memo pMemo){

		ArrayList<Memo> returnMemoList = new ArrayList<Memo>();

		StringBuffer query =new StringBuffer();
		query.append("select _id, content, rdate, isFav from memo ");
		
		boolean isFirst = true;	// and 조건이 처음인지 ?

		if(pMemo.getContent() != null && !pMemo.getContent().equals("")){
			query.append("where content like '%"+pMemo.getContent()+"%' ");
			isFirst = false;
		}
		if(pMemo.getRdate() != null && !pMemo.getRdate().equals("")){
			if(isFirst) query.append("where ");	// 조건이 처음일 경우 where 
			else		query.append("and ");	// 조건이 처음이 아닐경우 and
			
			query.append(" rdate='"+pMemo.getRdate()+"' ");
			isFirst = false;
		}
		if(pMemo.getIsFav() != null && !pMemo.getIsFav().equals("")){
			if(isFirst) query.append("where "); 
			else		query.append("and ");
			query.append(" isFav='"+pMemo.getIsFav()+"' ");
			isFirst = false;
		}
		
		query.append("order by _id desc ");

		MemoDBHelper dbHelper = new MemoDBHelper(pMemo.getContext());
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		Log.d("sql", query.toString());
		Cursor cursor = db.rawQuery(query.toString(), null);

		while(cursor.moveToNext()){
			Memo memo = new Memo();
			memo.set_id(cursor.getInt(0));
			memo.setContent(cursor.getString(1));
			memo.setRdate(cursor.getString(2));
			memo.setIsFav(cursor.getString(3));

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
		StringBuffer query = new StringBuffer();
		query.append("select _id,content,rdate,isFav from memo where _id="+pMemo.get_id());

		MemoDBHelper dbHelper = new MemoDBHelper(pMemo.getContext());
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		Log.d("sql", query.toString());
		Cursor cursor = db.rawQuery(query.toString(), null);

		cursor.moveToFirst();

		rMemo = new Memo();
		rMemo.set_id(cursor.getInt(0));
		rMemo.setContent(cursor.getString(1));
		rMemo.setRdate(cursor.getString(2));
		rMemo.setIsFav(cursor.getString(3));

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

		StringBuffer query = new StringBuffer();
		query.append("delete from memo where _id="+pMemo.get_id());

		Log.d("sql", query.toString());
		db.execSQL(query.toString());

		dbHelper.close();
		db.close();
	}

	/**
	 * 메모 수정 Dao
	 * @param pMemo
	 */
	public void updateMemo(Memo pMemo){
		if(pMemo == null || pMemo.get_id()==-1 ){
			return;
		}

		MemoDBHelper dbHelper = new MemoDBHelper(pMemo.getContext());
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		//"update memo set content='"+pMemo.getContent()+"' , rdate='"+pMemo.getRdate()+"' where _id="+pMemo.get_id();
		StringBuffer query = new StringBuffer();

		query.append("update memo set ");


		boolean isFirst = true;	// and 조건이 처음인지 ?

		if(pMemo.getContent() != null && !pMemo.getContent().equals("")){
			query.append("content='"+pMemo.getContent()+"'");
			isFirst = false;
		}
		if(pMemo.getRdate() != null && !pMemo.getRdate().equals("")){
			if(!isFirst) query.append(", ");	// 처음 조건이 아닌경우 콤마삽입
			query.append("rdate='"+pMemo.getRdate()+"'");
			isFirst = false;
		}
		if(pMemo.getIsFav() != null && !pMemo.getIsFav().equals("")){
			if(!isFirst) query.append(", ");
			query.append("isFav='"+pMemo.getIsFav()+"'");
			isFirst = false;
		}

		query.append(" where _id="+pMemo.get_id());

		Log.d("sql", query.toString());

		db.execSQL(query.toString());

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
		StringBuffer query = new StringBuffer();

		query.append("insert into memo (content, rdate,isFav) values('"+pMemo.getContent()+"','"+pMemo.getRdate()+"','N')");

		Log.d("sql", query.toString());

		db.execSQL(query.toString());

		//		 insert 메서드로 삽입
		/*		ContentValues row = new ContentValues();
		row.put("content", pMemo.getContent());
		row.put("rdate", pMemo.getRdate());

		db.insert("memo", null, row);*/

		dbHelper.close();
		db.close();
	}
}
