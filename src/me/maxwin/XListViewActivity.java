package me.maxwin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ihome.R.string;
import com.ihome.XListView.IXListViewListener;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

public class News extends Activity implements IXListViewListener{
	private ImageView refresh;
	private ImageView write;
	private XListView mListView;
	private SimpleAdapter simpleAdapter;
	ArrayList<HashMap<String, Object>> simpleItems = new ArrayList<HashMap<String,Object>>();
	private Handler mHandler;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//隐藏标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.news);
		getItem();
		
		refresh = (ImageView) findViewById(R.id.refresh);
		write = (ImageView) findViewById(R.id.write);
		mListView = (XListView) findViewById(R.id.xListView);
		mListView.setPullLoadEnable(true);

		mListView.setXListViewListener(this);
		mHandler = new Handler();
		
		MyClick click = new MyClick();
		refresh.setOnClickListener(click);
		write.setOnClickListener(click);

	     simpleAdapter = new SimpleAdapter(this, simpleItems, R.layout.list_item, new String[] {
                 "image","name","number"
         }, new int[] {
                 R.id.imageView,R.id.number,R.id.name
         });
         mListView.setAdapter(simpleAdapter);
	}
	
private void getItem(){
	String[] name = new String[15];
	for(int i = 0; i < 15; i++)
	{name[i] = "name" + i;}
	String[] detal = new String[15];
	for(int i = 0; i < 15; i++)
	{detal[i] = "detal item" + i;}
	
	 
     for (int i = 0; i != 20; ++i) {
         HashMap<String, Object> item = new HashMap<String, Object>();
         item.put("image", R.drawable.ic_launcher);
         item.put("name", name[i]);
         item.put("number", detal[i]);
         simpleItems.add(item);
     }
}
     
private void onLoad() {
	mListView.stopRefresh();
	mListView.stopLoadMore();
	mListView.setRefreshTime("刚刚");
}

public void onRefresh() {
	mHandler.postDelayed(new Runnable() {
		public void run() {
			//refresh items
			simpleItems.clear();
			getItem();
	        mListView.setAdapter(simpleAdapter);
			onLoad();
		}
	}, 2000);
}

public void onLoadMore() {
	mHandler.postDelayed(new Runnable() {
		public void run() {
			getItem();
			simpleAdapter.notifyDataSetChanged();
			onLoad();
		}
	}, 2000);
}


//定义一个点击事件的内部类，处理整个页面的点击事件
class MyClick implements View.OnClickListener{
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.refresh:onRefresh();
			break;
		case R.id.write:onLoadMore();
			break;
		}
		
	}
} 
}