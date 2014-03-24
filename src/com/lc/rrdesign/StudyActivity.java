package com.lc.rrdesign;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.widget.TextView;

public class StudyActivity extends Activity {

	private static int time = 25;
	private static int snoozeTimes;
	private Context mContext, context;
	private MediaPlayer mediaPlayer;
	private static String urlMusic;
	private Uri url;
	StudyCount counter, counterSnooze;
	AlertDialog.Builder alert;
	boolean check;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_study);
		if (url == null && time ==25) {
			counter = new StudyCount(25 * 1000 * 60, 1000);
			mediaPlayer = MediaPlayer.create(this, R.raw.soblue);
			;
			mediaPlayer.start();
		} else {
				counter = new StudyCount(time * 1000 * 60, 1000);
				url = Uri.parse(urlMusic);
				mediaPlayer = MediaPlayer.create(this, url);
				mediaPlayer.start();
			}
		
		

		counter.start();
		DialogSnooze();

	}

	private void DialogSnooze() {
		// / Make a dialog snooze
		alert = new AlertDialog.Builder(this);
		alert.setTitle(R.string.snooze).setMessage(R.string.dialogMessage);
		// Creat a button on dialog
		alert.setPositiveButton("Snooze",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Your action here

						mediaPlayer.start();
						counterSnooze = new StudyCount(snoozeTimes * 1000 * 60,
								1000);
						counterSnooze.start();

					}
				});
		alert.setNegativeButton("Relax ",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						Intent okSetting = new Intent(StudyActivity.this,
								RelaxActivity.class);
						startActivity(okSetting);
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.study, menu);
		return true;
	}

	public class StudyCount extends CountDownTimer {
		StudyCount(long millisInFuture, long countDownInterval) {
			// TODO Auto-generated constructor stub
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			((TextView) findViewById(R.id.time)).setText("Finish");
			Uri notification = RingtoneManager
					.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			Ringtone r = RingtoneManager.getRingtone(getApplicationContext(),
					notification);
			r.play();
			mediaPlayer.pause();
			alert.show();

			// Intent startRelax = new Intent(mContext, RelaxActivity.class);
			// mContext.startActivity(startRelax);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			int m, s;
			m = (int) millisUntilFinished / 60000;
			s = (int) (millisUntilFinished - m * 60000) / 1000;
			((TextView) findViewById(R.id.time)).setText("" + m + " : " + s);
			if (!mediaPlayer.isPlaying()) {
				mediaPlayer.start();
			}
		}
	}

	public static void settingStudy(int time2, int snoozetime, String musicUrl) {
		// TODO Auto-generated method stub
		time = time2;
		snoozeTimes = snoozetime;
		urlMusic = musicUrl;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mediaPlayer.stop();
		counter.cancel();
		finish();
	}

}
