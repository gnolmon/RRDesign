package com.lc.rrdesign;

import java.net.URISyntaxException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends Activity {

	/*
	 * TODO: Xu ly setting chua tot, nen thay the = SharedPreference
	 */

	private static int time = 45, snooze = 3;
	private static String urlMusic = " ";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		SeekBar seekBar1 = (SeekBar) findViewById(R.id.seekBar1);

		// Them min max va step cho seekBar
		seekBar1.setProgress(15);
		seekBar1.incrementProgressBy(1);
		seekBar1.setMax(60);
		final TextView seekBar1Value = (TextView) findViewById(R.id.seekbar1value);
		seekBar1Value.setText(String.valueOf(15));
		seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {

				time = progress;

				seekBar1Value.setText(String.valueOf(progress));
				StudyActivity.settingStudy(time, snooze, urlMusic);
				RelaxActivity.settingRelax(time / 5, snooze);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});

		SeekBar seekBar2 = (SeekBar) findViewById(R.id.seekBar2);

		// Them min max va step cho seekBar
		seekBar2.setProgress(1);
		seekBar2.incrementProgressBy(1);
		seekBar2.setMax(3);
		final TextView seekBar2Value = (TextView) findViewById(R.id.seekbar2value);
		seekBar2Value.setText(String.valueOf(1));
		seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {

				seekBar2Value.setText(String.valueOf(progress));
				StudyActivity.settingStudy(time, snooze, urlMusic);
				RelaxActivity.settingRelax(time / 5, snooze);

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});

		final Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showFileChooser();

			}
		});

	}

	private static final int FILE_SELECT_CODE = 0;

	private void showFileChooser() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

		// Set data type so the picker should display audio file only
		intent.setType("audio/*");
		intent.addCategory(Intent.CATEGORY_OPENABLE);

		try {
			startActivityForResult(
					Intent.createChooser(intent, "Select a File to Upload"),
					FILE_SELECT_CODE);
		} catch (android.content.ActivityNotFoundException ex) {
			// Potentially direct the user to the Market with a Dialog
			Toast.makeText(this, "Please install a File Manager.",
					Toast.LENGTH_SHORT).show();
			StudyActivity.settingStudy(time, snooze, urlMusic);
			RelaxActivity.settingRelax(time / 5, snooze);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case FILE_SELECT_CODE:
			if (resultCode == RESULT_OK) {
				// Get the Uri of the selected file
				Uri uri = data.getData();
				// Get the path
				try {
					urlMusic = SettingActivity.getPath(this, uri);
				} catch (URISyntaxException e) {

					e.printStackTrace();
				}

				// Get the file instance
				// File file = new File(path);
				// Initiate the upload
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
		StudyActivity.settingStudy(time, snooze, urlMusic);
		RelaxActivity.settingRelax(time / 5, snooze);
	}

	public static String getPath(Context context, Uri uri)
			throws URISyntaxException {
		if ("content".equalsIgnoreCase(uri.getScheme())) {
			String[] projection = { "_data" };
			Cursor cursor = null;

			try {
				cursor = context.getContentResolver().query(uri, projection,
						null, null, null);
				int column_index = cursor.getColumnIndexOrThrow("_data");
				if (cursor.moveToFirst()) {
					return cursor.getString(column_index);
				}
			} catch (Exception e) {
				// Eat it
			}
		} else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}
		return null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setting, menu);
		return true;
	}

}
