package kr.kakaruto.domain;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

public class Memo implements Parcelable{
	private int _id = -1;
	private String content;
	private String rdate;
	private String isFav;
	private Context context;

	public Memo() {

	}

	public Memo(Parcel in){
		readFromParcel(in);
	}

	public Memo(int _id, String content, String rdate, String isFav,Context context) {
		this._id = _id;
		this.content = content;
		this.rdate = rdate;
		this.isFav = isFav;
		this.context = context;
	}

	private void readFromParcel(Parcel in){
		_id = in.readInt();
		content = in.readString();
		rdate = in.readString();
		isFav = in.readString();
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(_id);
		dest.writeString(content);
		dest.writeString(rdate);
		dest.writeString(isFav);
	}

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


	public static final Parcelable.Creator<Memo> CREATOR = new Parcelable.Creator<Memo>() {

		public Memo createFromParcel(Parcel source) {
			return new Memo(source);
		}

		public Memo[] newArray(int size) {
			return new Memo[size];
		}		
	};
}
