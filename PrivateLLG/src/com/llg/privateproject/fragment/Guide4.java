package com.llg.privateproject.fragment;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.BitmapUtils;
import com.llg.help.MyFormat;
import com.llg.privateproject.actvity.MainActivity;
import com.llg.privateproject.actvity.NewHomeActivity;
import com.llg.privateproject.actvity.WelcomeActivity;
import com.llg.privateproject.actvity.WelcomeActivity.IsetPic;
import com.llg.privateproject.utils.CommonUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Guide4 extends Fragment implements IsetPic {
	String url;
	LinearLayout llLayout;
	View v;
Context context;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.welcome, null);
		llLayout = (LinearLayout) view.findViewById(R.id.welcome);
		v = view;
		llLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Log.d("my", "view.setOnClickListener"+getActivity());
				Intent intent = new Intent(getActivity(), NewHomeActivity.class);
				startActivity(intent);

				getActivity().finish();
			}
		});
		return view;
	}
@Override
public void onAttach(Activity activity) {
	// TODO Auto-generated method stub
	super.onAttach(activity);
	context=activity;
}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		v.setBackgroundResource(R.drawable.index_4);

	}

	@Override
	public void setPicId(String url) {
		// TODO Auto-generated method stub
		this.url = url;
		 new BitmapUtils(getActivity(),
		 CommonUtils.createSDCardDir()).configDefaultLoadFailedImage(R.drawable.index_4).display(v,
		 url);
	}

}