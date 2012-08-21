package kr.kakaruto.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ModifyActivity extends Activity {

	MemoDBHelper dbHelper;
	SQLiteDatabase db;
	ContentValues row;
	
	EditText tx_modify ;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify);
		setTitle("�ۼ���");

		tx_modify = (EditText)findViewById(R.id.tx_modify);

		tx_modify.requestFocus();
		tx_modify.setSelection(0);

		Button bt_modify = (Button)findViewById(R.id.bt_modify);
		Button bt_list = (Button)findViewById(R.id.bt_list2);

		Intent intent = getIntent();
		final String _id = intent.getStringExtra("_id");
		
		dbHelper = new MemoDBHelper(this);

		bt_modify.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				String content = tx_modify.getText().toString();		// �۳���
				String rdate = getDateString("yyyy.MM.dd HH:mm:ss", Locale.KOREA );	// �۾��ð�
				
				db = dbHelper.getWritableDatabase();
				db.execSQL("update memo set content='"+content+"' , rdate='"+rdate+"' where _id="+_id);
				
				dbHelper.close();	
				
				setResult(RESULT_OK);
				finish();
			}
		});
		
		bt_list.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
				finish();
			}
		});
		
		this.loadMemo(_id);
	}
	
	/**
	 * �޸� ��ȸ�ϱ� 
	 */
	public void loadMemo(String id){
		String query ="select content from memo where _id="+id;
		db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);;
		cursor.moveToFirst();
		
		String content = cursor.getString(0);
		
		tx_modify.setText(content);
		
		db.close();
		cursor.close();
	}
	
	/**
	 * ���� ��¥ �������·� ����
	 * @param format
	 * @param locale
	 * @return
	 */
	public String getDateString(String format , Locale locale){
		SimpleDateFormat formatter = new SimpleDateFormat ( format, locale);
		Date currentTime = new Date ( );
		String dTime = formatter.format ( currentTime );
		
		return dTime;
	}
}


