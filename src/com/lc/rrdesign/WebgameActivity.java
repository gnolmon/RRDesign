package com.lc.rrdesign;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class WebgameActivity extends Activity {
	Button changeGame;
	WebView webGame;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webgame);
		
		changeGame = (Button) findViewById(R.id.buttonWebGame);
		webGame = (WebView) findViewById(R.id.webGame);
		//To display web game and make activity for button
		webGame.loadUrl("file:///android_asset/2048/2048.htm");
		changeGame.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent relax = new Intent(WebgameActivity.this, StudyActivity.class);
				startActivity(relax);
			}
		});
	
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.webgame, menu);
		return true;
	}

}
