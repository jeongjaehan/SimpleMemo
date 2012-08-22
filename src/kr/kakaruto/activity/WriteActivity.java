package kr.kakaruto.activity;

import java.util.Locale;

import kr.kakaruto.domain.Memo;
import kr.kakaruto.service.MemoService;
import kr.kakaruto.util.DateUtils;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class WriteActivity extends Activity {
	EditText tx_write ;
	
	MemoService memoService;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.write);
		setTitle("글쓰기");

		tx_write = (EditText)findViewById(R.id.tx_write);

		tx_write.requestFocus();
		tx_write.setSelection(0);
		
		memoService = new MemoService();

		Button bt_write = (Button)findViewById(R.id.bt_write);
		Button bt_list = (Button)findViewById(R.id.bt_list);
		
		bt_write.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				String content = tx_write.getText().toString();		// 글내용
				String rdate = DateUtils.getDateString("yyyy.MM.dd HH:mm:ss", Locale.KOREA );	// 글쓴시간
				
				Memo pMemo = new Memo();
				pMemo.setContent(content);
				pMemo.setRdate(rdate);
				pMemo.setContext(WriteActivity.this);
				
				memoService.insertMemo(pMemo);
				
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
}


