package com.lc.rrdesign;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RequestActivity extends Activity {
	Button  ok;
	TextView userName, userEmail, contentRequest;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request);
		ok = (Button) findViewById(R.id.requestOk);
		userName = (TextView) findViewById(R.id.nameUser);
		userEmail = (TextView) findViewById(R.id.emailUser);
		contentRequest = (TextView) findViewById(R.id.requestContent);
		ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast toast = Toast.makeText(RequestActivity.this, "Sending....." , Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.BOTTOM, 0, 0);
				toast.show();
				
				userName.setText("");
				userEmail.setText("");
				contentRequest.setText("");
				
				
				Toast finishRequest = Toast.makeText(RequestActivity.this, "Your request are sent. Congratulations!!!" , Toast.LENGTH_SHORT);
				finishRequest.setGravity(Gravity.BOTTOM, 0, 0);
				finishRequest.show();
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.request, menu);
		return true;
	}

}
