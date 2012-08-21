package kr.kakaruto.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TabHost;

public class MainTabActivity extends TabActivity {
	TabHost tabHost;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maintab);
		setTitle("간단한메모장");
		
//        TabHost tabHost = getTabHost();
        TabHost tabHost =  (TabHost)findViewById(android.R.id.tabhost);  

        tabHost.addTab(tabHost.newTabSpec("tag")
                .setIndicator("목록")
                .setContent(new Intent(this, MainActivity.class)));

        tabHost.addTab(tabHost.newTabSpec("tab2")
                .setIndicator("즐겨찾기")
                .setContent(new Intent(this, FavorityActivity.class)));
        
        tabHost.addTab(tabHost.newTabSpec("tab3")
                .setIndicator("설정")
                .setContent(new Intent(this, ConfActivity.class)));
        
        tabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#534512"));
        tabHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.parseColor("#4E4E9C"));
        tabHost.getTabWidget().getChildAt(2).setBackgroundColor(Color.parseColor("#6E8E1C"));
        tabHost.getTabWidget().setCurrentTab(0);  
	}
}
