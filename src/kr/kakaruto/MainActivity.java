package kr.kakaruto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends Activity{

	Button bt_write;
	Button bt_search;
	ListView memoList;
	EditText searchText;

	MemoDBHelper dbHelper;
	SQLiteDatabase db;
	Cursor cursor;

	SimpleCursorAdapter adapter;


	final static int ACT_WRITE = 0;	// instant�� ���� requestCode
	final static int ACT_MODIFY = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setTitle("�޸���");

		bt_write =  (Button)findViewById(R.id.bt_direct_write);
		bt_search =  (Button)findViewById(R.id.bt_search);
		searchText =  (EditText)findViewById(R.id.tx_search);
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
				final int _id = selectedItem.getInt(0);

				new AlertDialog.Builder(MainActivity.this)
				.setTitle("�� �޸� ")
				.setItems(new String[] {"����", "����"}, 
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							Intent intent = new Intent(MainActivity.this, ModifyActivity.class);
							intent.putExtra("_id", String.valueOf(_id));
							startActivityForResult(intent , ACT_MODIFY);
							break;

						default:
							deleteMemo(_id);
							loadToMemoList();
							break;
						}
					}
				})
				.setNegativeButton("���", null)
				.show();
			}
		});


		bt_search.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				loadToMemoList();
			}
		});
	}

	/**
	 * DB���� �޸��� �ҷ����� 
	 */
	public void loadToMemoList() {

		String keyword = searchText.getText().toString();
		String query = "";

		if(keyword.equals(""))
			query = "select * from memo order by _id desc ";
		else
			query = "select * from memo where content like '%"+keyword+"%'  order by _id desc ";

		dbHelper = new MemoDBHelper(this);
		db = dbHelper.getReadableDatabase();
		cursor = db.rawQuery(query, null);;
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
		case ACT_MODIFY:	// �ۼ��� 
			if(resultCode == RESULT_OK){  
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