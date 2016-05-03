package com.llg.privateproject.actvity;

import java.util.Timer;
import java.util.TimerTask;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.bjg.lcc.privateproject.R;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 摇一摇界面 yh 2015.08.22
 * 
 * */
public class ShakeScreen extends Activity implements SensorEventListener {
	/** 传感器 */
	SensorManager ssm;
	Vibrator vibrator;
	/** 标题 摇一摇 */
	@ViewInject(R.id.head_tilte)
	private TextView head_tilte;
	/** 动画图片 */
	@ViewInject(R.id.iv_anim)
	private ImageView iv_anim;
	SoundPool soudPool;
	/** 摇一摇是加载 */
	int middle;
	/** 获取数据后加载 */
	int end;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shakescreen);
		ViewUtils.inject(this);
		init();
	}

	private void init() {
		head_tilte.setText("摇一摇");
		ssm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		soudPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 5);
		middle = soudPool.load(this, R.raw.shake_sound_male, 1);
		end = soudPool.load(this, R.raw.shake_match, 1);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// 注册监听
		ssm.registerListener(this,
				ssm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		ssm.unregisterListener(this);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		float[] xyz = event.values;
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			if (Math.abs(xyz[0]) > 16 || Math.abs(xyz[1]) > 16
					|| Math.abs(xyz[2]) > 16) {
				vibrator.vibrate(500);
				Animation animation = AnimationUtils.loadAnimation(this,
						R.anim.rotate);
				// animation.setFillAfter(true);//设置动画结束保持状态
				iv_anim.startAnimation(animation);
				soudPool.play(middle, 1, 1, 1, 1, 1);
				new Timer().schedule(new TimerTask() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						soudPool.play(end, 1, 1, 1, 1, 1);
					}
				}, 3000);

			}
		}

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@OnClick({ R.id.turn })
	public void myClick(View v) {
		switch (v.getId()) {
		case R.id.turn:// 返回
			finish();

			break;

		default:
			break;
		}
	}
}
