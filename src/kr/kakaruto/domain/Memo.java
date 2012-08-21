package kr.kakaruto.domain;

import android.content.Context;

public class Memo {
	private int _id;
	private String content;
	private String rdate;
	private String isFav;
	private Context context;
	
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRdate() {
		return rdate;
	}
	public void setRdate(String rdate) {
		this.rdate = rdate;
	}
	public String getIsFav() {
		return isFav;
	}
	public void setIsFav(String isFav) {
		this.isFav = isFav;
	}
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	
}
