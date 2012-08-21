package kr.kakaruto;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class TestTabActivity extends TabActivity {
	TabHost tabHost;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        TabHost tabHost = getTabHost();

        tabHost.addTab(tabHost.newTabSpec("tag")
                .setIndicator("목록")
                .setContent(new Intent(this, MainActivity.class)));

        tabHost.addTab(tabHost.newTabSpec("tab2")
                .setIndicator("즐겨찾기")
                .setContent(new Intent(this, FavorityActivity.class)));
        
        tabHost.addTab(tabHost.newTabSpec("tab3")
                .setIndicator("설정")
                .setContent(new Intent(this, ConfActivity.class)));
	}
}
