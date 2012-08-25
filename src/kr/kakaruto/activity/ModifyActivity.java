package kr.kakaruto.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import kr.kakaruto.domain.Memo;
import kr.kakaruto.service.MemoService;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Selection;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ModifyActivity extends Activity {

	EditText tx_modify ;

	MemoService memoService;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify);
		setTitle("글수정");

		tx_modify = (EditText)findViewById(R.id.tx_modify);

		tx_modify.setSelection(tx_modify.getText().length());
		
		memoService = new MemoService();

		Button bt_modify = (Button)findViewById(R.id.bt_modify);
		Button bt_list = (Button)findViewById(R.id.bt_list2);

		Bundle bundle = getIntent().getExtras();
		Memo memo = bundle.getParcelable("memo");
		final int _id = memo.get_id();

		bt_modify.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				String content = tx_modify.getText().toString();		// 글내용
				String rdate = getDateString("yyyy.MM.dd HH:mm:ss", Locale.KOREA );	// 글쓴시간

				Memo pMemo = new Memo();
				pMemo.set_id(_id);
				pMemo.setContent(content);
				pMemo.setRdate(rdate);
				pMemo.setContext(ModifyActivity.this);

				memoService.updateMemo(pMemo);
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

		loadMemo(memo.get_id());
	}

	/**
	 * 메모 조회하기 
	 */
	public void loadMemo(int id){
		Memo pMemo = new Memo();
		pMemo.set_id(id);
		pMemo.setContext(this);
		Memo memo = memoService.getOneMemo(pMemo);
		tx_modify.setText(memo.getContent());
	}

	/**
	 * 현재 날짜 문자형태로 리턴
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


