package kr.kakaruto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class WriteActivity extends Activity {

	MemoDBHelper dbHelper;
	SQLiteDatabase db;
	ContentValues row;
	
	EditText tx_write ;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.write);
		setTitle("글쓰기");

		tx_write = (EditText)findViewById(R.id.tx_write);

		tx_write.requestFocus();
		tx_write.setSelection(0);

		Button bt_write = (Button)findViewById(R.id.bt_write);
		Button bt_list = (Button)findViewById(R.id.bt_list);
		
		dbHelper = new MemoDBHelper(this);

		bt_write.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Log.d("kakaruto", "글쓰기 Action");
				
				String content = tx_write.getText().toString();		// 글내용
				String rdate = getDateString("yyyy.MM.dd HH:mm:ss", Locale.KOREA );	// 글쓴시간
				
				Log.d("kakaruto", content);
				Log.d("kakaruto", rdate);
				
				
				db = dbHelper.getWritableDatabase();
				
				// insert 메서드로 삽입
				row = new ContentValues();
				row.put("content", content);
				row.put("rdate", rdate);
				
				db.insert("memo", null, row);
				
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
	}
	
	public String getDateString(String format , Locale locale){
		SimpleDateFormat formatter = new SimpleDateFormat ( format, locale);
		Date currentTime = new Date ( );
		String dTime = formatter.format ( currentTime );
		
		return dTime;
	}
}


