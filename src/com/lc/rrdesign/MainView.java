package com.lc.rrdesign;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainView extends View {
	private Bitmap buttonStudy, buttonRelax, buttonSetting, buttonRequest,
			background, buttonUpdate;
	private int screenW;
	private int screenH;

	boolean buttonStudyPress, buttonRelaxPress, buttonSettingPress,
			buttonRequestPress, buttonUpdatePress;
	private Context mContext;

	public MainView(Context context) {
		super(context);
		mContext = context;
		// Load bitmap study from resource
		buttonStudy = BitmapFactory.decodeResource(getResources(),
				R.drawable.study);
		// Load bitmap relax from resource
		buttonRelax = BitmapFactory.decodeResource(getResources(),
				R.drawable.relax);
		// Load bitmap study from resource
		buttonSetting = BitmapFactory.decodeResource(getResources(),
				R.drawable.setting);
		// Load bitmap study from resource
		buttonRequest = BitmapFactory.decodeResource(getResources(),
				R.drawable.request);
		// Load background
		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.display);
		buttonUpdate = BitmapFactory.decodeResource(getResources(),
				R.drawable.update);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		background = Bitmap.createScaledBitmap(background, screenW, screenH,
				false);
		canvas.drawBitmap(background, (screenW - background.getWidth()) / 2, 0,
				null);
		// Draw study button
		buttonStudy = Bitmap.createScaledBitmap(buttonStudy, 680, 195, false);
		canvas.drawBitmap(buttonStudy, (screenW - buttonStudy.getWidth()) / 2,
				50, null);
		// Draw study button
		buttonRelax = Bitmap.createScaledBitmap(buttonRelax, 680, 195, false);
		canvas.drawBitmap(buttonRelax, (screenW - buttonRelax.getWidth()) / 2,
				296, null);
		// Draw study button
		buttonSetting = Bitmap.createScaledBitmap(buttonSetting, 680, 195,
				false);
		canvas.drawBitmap(buttonSetting,
				(screenW - buttonSetting.getWidth()) / 2, 541, null);
		// Draw study button
		buttonRequest = Bitmap.createScaledBitmap(buttonRequest, 680, 195,
				false);
		canvas.drawBitmap(buttonRequest,
				(screenW - buttonRequest.getWidth()) / 2, 787, null);
		// Draw study button
		buttonUpdate = Bitmap.createScaledBitmap(buttonUpdate, 680, 195, false);
		canvas.drawBitmap(buttonUpdate,
				(screenW - buttonUpdate.getWidth()) / 2, 1032, null);

	}

	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		screenW = w;
		screenH = h;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub

		int eventaction = event.getAction();
		int X = (int) event.getX();
		int Y = (int) event.getY();
		switch (eventaction) {

		case MotionEvent.ACTION_DOWN:
			if (studyPress(X, Y)) {
				buttonStudyPress = true;
			}
			if (relaxPress(X, Y)) {
				buttonRelaxPress = true;
			}
			if (settingPress(X, Y)) {
				buttonSettingPress = true;
			}
			if (requestPress(X, Y)) {
				buttonRequestPress = true;
			}
			if (updatePress(X, Y)){
				buttonUpdatePress = true;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			break;

		case MotionEvent.ACTION_UP:
			if (buttonStudyPress) {
				Intent startStudy = new Intent(mContext, StudyActivity.class);
				mContext.startActivity(startStudy);
			}
			buttonStudyPress = false;
			if (buttonRelaxPress) {
				Intent startRelax = new Intent(mContext, RelaxActivity.class);
				mContext.startActivity(startRelax);
			}
			buttonRelaxPress = false;
			if (buttonSettingPress) {
				Intent startSetting = new Intent(mContext,
						SettingActivity.class);
				mContext.startActivity(startSetting);
			}
			buttonSettingPress = false;
			if (buttonRequestPress) {
				Intent startRequest = new Intent(mContext,
						RequestActivity.class);
				mContext.startActivity(startRequest);
			}
			buttonRequestPress = false;
			if (buttonUpdatePress){
				Toast toast = Toast.makeText(mContext, "You have just updated the newest database and version", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.BOTTOM, 0, 0);
				toast.show();
			}
			buttonUpdatePress = false;
			break;

		}

		invalidate();
		return true;
	}

	boolean studyPress(int X, int Y) {
		return X > (screenW - buttonStudy.getWidth()) / 2
				&& X < (screenW - buttonStudy.getWidth() / 2)
						+ buttonStudy.getWidth() && Y > 50
				&& Y < 50 + buttonStudy.getHeight();
	}

	boolean relaxPress(int X, int Y) {
		return X > (screenW - buttonRelax.getWidth()) / 2
				&& X < (screenW - buttonRelax.getWidth() / 2)
						+ buttonRelax.getWidth() && Y > 296
				&& Y < 296 + buttonRelax.getHeight();
	}

	boolean settingPress(int X, int Y) {
		return X > (screenW - buttonSetting.getWidth()) / 2
				&& X < (screenW - buttonSetting.getWidth() / 2)
						+ buttonSetting.getWidth() && Y > 541
				&& Y < 541 + buttonSetting.getHeight();
	}

	boolean requestPress(int X, int Y) {
		return X > (screenW - buttonRequest.getWidth()) / 2
				&& X < (screenW - buttonRequest.getWidth() / 2)
						+ buttonRequest.getWidth() && Y > 787
				&& Y < 787 + buttonRequest.getHeight();
	}

	boolean updatePress(int X, int Y) {
		return X > (screenW - buttonUpdate.getWidth()) / 2
				&& X < (screenW - buttonUpdate.getWidth() / 2)
						+ buttonUpdate.getWidth() && Y > 1032
				&& Y < 1032 + buttonUpdate.getHeight();
	}
}
