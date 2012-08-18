package kr.kakaruto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity{

	Button bt_write;
	ListView memoList;

	MemoDBHelper dbHelper;
	SQLiteDatabase db;
	Cursor cursor;
	
	SimpleCursorAdapter adapter;


	final static int ACT_WRITE = 0;	// instant�� ���� requestCode

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setTitle("�޸���");

		bt_write =  (Button)findViewById(R.id.bt_direct_write);
		memoList =  (ListView)findViewById(R.id.memoList);

		loadToMemoList();

		bt_write.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Log.d("kakaruto", "�۾��� ȭ������ �̵�");
				Intent intent = new Intent(MainActivity.this, WriteActivity.class);
				startActivityForResult(intent , ACT_WRITE);
			}
		});

		memoList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int pos,long id) {
				Cursor selectedItem = (Cursor)adapter.getItem(pos);
				final int delId = selectedItem.getInt(0);
				
				new AlertDialog.Builder(MainActivity.this)
				.setTitle("�޸����")
				.setMessage("�� �޸� �����Ͻðڽ��ϱ�?")
				.setNegativeButton("�޸����", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						deleteMemo(delId);
						loadToMemoList();
					}
				})
				.show();
			}
		});
	}

/**
 * DB���� �޸��� �ҷ����� 
 */
	public void loadToMemoList() {

		dbHelper = new MemoDBHelper(this);
		db = dbHelper.getReadableDatabase();
		cursor = db.rawQuery("select * from memo order by _id desc ", null);;
		cursor.moveToFirst();

		adapter = new SimpleCursorAdapter(
				this, 
				android.R.layout.simple_list_item_2, 
				cursor,
				new String[]{"content" ,"rdate"},
				new int[] {android.R.id.text1 , android.R.id.text2 }
		);

		memoList.setAdapter(adapter);

		//		dbHelper.close();
		//		db.close();
		//		cursor.close();
	}
	
	
/**
 * DB �޸� ����  	
 */
	public void deleteMemo(int delId){
		dbHelper = new MemoDBHelper(this);
		db = dbHelper.getWritableDatabase();
		db.execSQL("delete from memo where _id="+delId);
	}

	
/**
 * ����Ʈ �ݹ� �޼���
 */
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("kakaruto", "requestCode : "+requestCode+", resultCode : "+resultCode);
		switch (requestCode) {
		case ACT_WRITE:	// �۾��� 
			if(resultCode == RESULT_OK){ // ������ DB���� �ٽ� �� �о���� 
				loadToMemoList();
			}
			break;

		default:
			break;
		}
	}

	
/**
 * ���� ����� db ���ҽ� ����
 */
	public void onDestroy() {
		db.close();
		dbHelper.close();
		cursor.close();
		super.onDestroy();
	}
}