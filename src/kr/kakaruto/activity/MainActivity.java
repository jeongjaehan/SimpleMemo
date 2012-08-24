package kr.kakaruto.activity;

import java.util.ArrayList;

import kr.kakaruto.domain.Memo;
import kr.kakaruto.service.MemoService;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


/**
 * ����ȭ��
 * @author kakaruto
 *
 */
public class MainActivity extends Activity{

	// activity �� widget ���� 
	Button bt_write;
	Button bt_search;
	ListView lv_memoList;
	EditText tx_search;
	
	// �޸� ���� 
	MemoService memoService;
	
	ArrayList<Memo> memoList;

	//defined request code
	final static int ACT_WRITE = 0;	
	final static int ACT_MODIFY = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		bt_write =  (Button)findViewById(R.id.bt_direct_write);
		bt_search =  (Button)findViewById(R.id.bt_search);
		tx_search =  (EditText)findViewById(R.id.tx_search);
		lv_memoList =  (ListView)findViewById(R.id.memoList);
		
		memoService = new MemoService();

//		 TODO Ű���� ����� �����ε� �� �ȵɱ�?
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInputFromWindow(tx_search.getWindowToken(), 0, 1);

		loadToMemoList(); // �޸��� �ε�

//		�۾��� �̺�Ʈ
		bt_write.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, WriteActivity.class);
				startActivityForResult(intent , ACT_WRITE);
			}
		});
		

//		�޸� Ŭ�� �̺�Ʈ
		lv_memoList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int pos,long id) {
				final Memo memo = (Memo)memoList.get(pos);

				new AlertDialog.Builder(MainActivity.this)
				.setTitle("�� �޸� ")
				.setItems(new String[] {"����", "����"}, 
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							Intent intent = new Intent(MainActivity.this, ModifyActivity.class);
							intent.putExtra("memo", memo);
							startActivityForResult(intent , ACT_MODIFY);
							break;

						default:
							memo.setContext(MainActivity.this);
							memoService.deleteMemo(memo);
							loadToMemoList();
							break;
						}
					}
				})
				.setNegativeButton("���", null)
				.show();
			}
		});
		

//		�˻� �̺�Ʈ
		bt_search.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				loadToMemoList();
			}
		});
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		loadToMemoList();
	}

	/**
	 * DB���� �޸��� �ҷ����� 
	 */
	public void loadToMemoList() {
		
		String content = tx_search.getText().toString();
		
		Memo pMemo = new Memo();
		pMemo.setContent(content);
		pMemo.setContext(this);
		
		this.memoList = memoService.getMemoList(pMemo);
		MemoAdapter memoAdapter = new MemoAdapter(this, R.layout.memo_row, memoList);
		lv_memoList.setAdapter(memoAdapter);

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
	 * �޸� Ŀ���� �ƴ��� 
	 *
	 */
	public  class MemoAdapter extends ArrayAdapter<Memo> {
		private ArrayList<Memo> items;

		public MemoAdapter(Context context,  int textViewResourceId, ArrayList<Memo> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.memo_row, null);
			}
			
			final Memo memo = items.get(position);
			
			if (memo != null) {
				TextView tt = (TextView) v.findViewById(R.id.toptext);
				TextView bt = (TextView) v.findViewById(R.id.bottomtext);
				ImageView iv_fav =  (ImageView)v.findViewById(R.id.iv_fav);
				if (tt != null){
					tt.setText(memo.getContent());                            
				}
				if(bt != null){
					bt.setText(memo.getRdate());
				}
				
				//���ã�� ǥ�� 
				if(iv_fav != null){
					if(memo.getIsFav().equals("Y"))
						iv_fav.setImageResource(R.drawable.fav_on);
					else
						iv_fav.setImageResource(R.drawable.fav_off);
				}
				
//				���ã�� �̺�Ʈ
				iv_fav.setOnClickListener(new Button.OnClickListener() {
					public void onClick(View v) {
						Memo pMemo = new Memo();
						pMemo.set_id(memo.get_id());
						String _isFav = memo.getIsFav().equals("Y")? "N" : "Y"; 
						pMemo.setIsFav(_isFav);
						pMemo.setContext(MainActivity.this);
						
						memoService.updateMemo(pMemo);
						loadToMemoList();
					}
				});
			}
			return v;
		}
	}
	
	
	public void onDestroy() {
		super.onDestroy();
	}
}