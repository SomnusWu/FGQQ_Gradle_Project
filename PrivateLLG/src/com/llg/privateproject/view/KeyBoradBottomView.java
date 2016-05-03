package com.llg.privateproject.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjg.lcc.privateproject.R;

public class KeyBoradBottomView extends LinearLayout implements OnClickListener {
	private Context mContext;

	private TextView tv_phone_call;
	private ImageView btn_keyborad_switch, img_keyborad_delete;

	public KeyBoradBottomView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context);

	}

	public KeyBoradBottomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);

	}

	public KeyBoradBottomView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);

	}

	private void init(Context context) {
		mContext = context;
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.view_keyborad_bottom_layout, this);
		initView(view);
	}

	private void initView(View v) {
		btn_keyborad_switch = (ImageView) v
				.findViewById(R.id.btn_keyborad_switch);
		tv_phone_call = (TextView) v.findViewById(R.id.tv_phone_call);
		img_keyborad_delete = (ImageView) v
				.findViewById(R.id.img_keyborad_delete);

		btn_keyborad_switch.setOnClickListener(this);
		tv_phone_call.setOnClickListener(this);
		img_keyborad_delete.setOnClickListener(this);
	}

	public interface iKeyBoradBottomInterFace {
		void boradBottomItem(KeyBoradBottom item);
	}

	private iKeyBoradBottomInterFace miKeyBoradBottomInterFace;

	public enum KeyBoradBottom {
		KeyBoradSwitch, KeyBoradCall, KeyBoradDelete
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		KeyBoradBottom keyBItem = KeyBoradBottom.KeyBoradSwitch;
		switch (v.getId()) {
		case R.id.btn_keyborad_switch:
			keyBItem = KeyBoradBottom.KeyBoradSwitch;
			break;
		case R.id.tv_phone_call:
			keyBItem = KeyBoradBottom.KeyBoradCall;
			break;
		case R.id.img_keyborad_delete:
			keyBItem = KeyBoradBottom.KeyBoradDelete;
			break;

		default:
			break;
		}
		if (miKeyBoradBottomInterFace != null) {
			miKeyBoradBottomInterFace.boradBottomItem(keyBItem);
		}
	}

	public iKeyBoradBottomInterFace getMiKeyBoradBottomInterFace() {
		return miKeyBoradBottomInterFace;
	}

	public void setMiKeyBoradBottomInterFace(
			iKeyBoradBottomInterFace miKeyBoradBottomInterFace) {
		this.miKeyBoradBottomInterFace = miKeyBoradBottomInterFace;
	}

}
