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
 * 메인화면
 * @author kakaruto
 *
 */
public class MainActivity extends Activity{

	// activity 내 widget 변수 
	Button bt_write;
	Button bt_search;
	ListView lv_memoList;
	EditText tx_search;
	
	// 메모 서비스 
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

//		 TODO 키보드 숨기는 로직인데 왜 안될까?
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInputFromWindow(tx_search.getWindowToken(), 0, 1);

		loadToMemoList(); // 메모목록 로딩

//		글쓰기 이벤트
		bt_write.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, WriteActivity.class);
				startActivityForResult(intent , ACT_WRITE);
			}
		});
		

//		메모 클릭 이벤트
		lv_memoList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int pos,long id) {
				final Memo memo = (Memo)memoList.get(pos);

				new AlertDialog.Builder(MainActivity.this)
				.setTitle("이 메모를 ")
				.setItems(new String[] {"수정", "삭제"}, 
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
				.setNegativeButton("취소", null)
				.show();
			}
		});
		

//		검색 이벤트
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
	 * DB에서 메모목록 불러오기 
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
	 * 인텐트 콜백 메서드
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("kakaruto", "requestCode : "+requestCode+", resultCode : "+resultCode);
		switch (requestCode) {
		case ACT_WRITE:	// 글쓰기 
			if(resultCode == RESULT_OK){ // 성공시 DB에서 다시 값 읽어오기 
				loadToMemoList();
			}
			break;
		case ACT_MODIFY:	// 글수정 
			if(resultCode == RESULT_OK){  
				loadToMemoList();
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 메모 커스텀 아답터 
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
				
				//즐겨찾기 표시 
				if(iv_fav != null){
					if(memo.getIsFav().equals("Y"))
						iv_fav.setImageResource(R.drawable.fav_on);
					else
						iv_fav.setImageResource(R.drawable.fav_off);
				}
				
//				즐겨찾기 이벤트
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