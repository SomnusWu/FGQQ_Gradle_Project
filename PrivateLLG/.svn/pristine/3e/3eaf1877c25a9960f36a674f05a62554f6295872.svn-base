package com.llg.privateproject.fragment;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.privateproject.actvity.MyFabuActivity;
import com.llg.privateproject.adapters.FabuFabuAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FabuFragmentFabu extends Fragment {
	Context context;
	private View view;
	@ViewInject(R.id.fabu_fabu_lv)
	private ListView listview;
	@ViewInject(R.id.btn_myFabu)
	private ListView btnMyFabu;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		context = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fabu_fragment_fabu, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		FabuFabuAdapter adapter = new FabuFabuAdapter(getActivity());
		listview.setAdapter(adapter);
	}

	@OnClick(R.id.btn_myFabu)
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_myFabu:
			Intent intent = new Intent(getActivity(), MyFabuActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
}
