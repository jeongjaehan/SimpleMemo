package kr.kakaruto.activity;

import kr.kakaruto.domain.Memo;
import android.os.Bundle;
import android.util.Log;

/**
 * ���ã�� ȭ��
 * @author kakaruto
 *
 */
public class FavorityActivity extends MainActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d("life","onCreate");
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		loadToMemoList();
	}
	
	/**
	 * ���ã�� �޸� ��ϸ� �ε�
	 */
	public void loadToMemoList() {
		
		String content = tx_search.getText().toString();
		
		Memo pMemo = new Memo();
		pMemo.setContext(this);
		pMemo.setContent(content);
		pMemo.setIsFav("Y");
		
		memoList = memoService.getMemoList(pMemo);
		MemoAdapter memoAdapter = new MemoAdapter(this, R.layout.memo_row, memoList);
		lv_memoList.setAdapter(memoAdapter);
	}
}
