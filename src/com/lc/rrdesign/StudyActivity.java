package com.lc.rrdesign;

import android.app.Activity;
import android.app.AlertDialog;
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

	/*
	 * TODO: Xử lý setting chưa tốt, nên thay thế = SharedPreference
	 */

	/*
	 * TODO: Thiết lập hẳn 1 service để quản lý MediaPlayer và các Counter Dùng
	 * thêm notification để biết người dùng đang học cũng giúp cho process ko bị
	 * kill bởi android khi còn ít resource
	 */

	private static int time = 25;
	private static int snoozeTimes;
	private MediaPlayer mediaPlayer;
	private static String urlMusic = " ";
	private Uri url;
	StudyCount counter, counterSnooze;
	AlertDialog.Builder alert;
	boolean check;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_study);

		// Process music
		if (urlMusic == " ") {
			mediaPlayer = MediaPlayer.create(this, R.raw.soblue);
			// mediaPlayer.start();
			//
		} else {

			url = Uri.parse(urlMusic);
			mediaPlayer = MediaPlayer.create(this, url);
			// mediaPlayer.start();
		}

		counter = new StudyCount(time * 1000 * 60, 1000);
		mediaPlayer.start();
		counter.start();
		DialogSnooze();

	}

	private void DialogSnooze() {
		// / Make a dialog snooze
		alert = new AlertDialog.Builder(this);
		alert.setTitle(R.string.snooze).setMessage(R.string.dialogMessage);
		// Creat a button on dialog
		alert.setPositiveButton("Keep learning",
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
						finish();
					}
				});

		// Set this alert cannot cancel so user must select option, can't
		// dismiss this
		alert.setCancelable(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.study, menu);
		return true;
	}

	public class StudyCount extends CountDownTimer {
		StudyCount(long millisInFuture, long countDownInterval) {

			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {

			((TextView) findViewById(R.id.time)).setText("Finish");
			Uri notification = RingtoneManager
					.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			Ringtone r = RingtoneManager.getRingtone(getApplicationContext(),
					notification);
			mediaPlayer.pause();
			r.play();
			alert.show();

			// Intent startRelax = new Intent(mContext, RelaxActivity.class);
			// mContext.startActivity(startRelax);
		}

		@Override
		public void onTick(long millisUntilFinished) {

			int m, s;
			m = (int) millisUntilFinished / 60000;
			s = (int) (millisUntilFinished - m * 60000) / 1000;
			((TextView) findViewById(R.id.time)).setText("" + m + " : " + s);
			// if (!mediaPlayer.isPlaying()) {
			// mediaPlayer.start();
			// }
		}
	}

	public static void settingStudy(int time2, int snoozetime, String musicUrl) {

		time = time2;
		snoozeTimes = snoozetime;
		urlMusic = musicUrl;
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
		mediaPlayer.stop();
		mediaPlayer.release();
		counter.cancel();
	}

}
