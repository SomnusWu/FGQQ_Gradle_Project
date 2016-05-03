package com.llg.privateproject.fragment;

import java.util.ArrayList;
import java.util.List;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.adapters.FabuKindAdapter;
import com.llg.privateproject.adapters.FabuKindSecendAdapter;
import com.llg.privateproject.entities.Fabu;
import com.llg.privateproject.view.MyGridView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

public class FabuFragmentFenlei extends Fragment {
	Context context;
	/** 右侧广告图片 */
	@ViewInject(R.id.iv_banner)
	private ImageView iv_banner;
	/** 左侧分类ListView */
	@ViewInject(R.id.cate_listview)
	private ListView mCateListView;
	/** 右侧产品 MyGridView */
	@ViewInject(R.id.secondary_gridView_id)
	private MyGridView myGridView;
	/** 二级级分类界面界面 */
	@ViewInject(R.id.feilei2_lv)
	private ListView catelistView2;
	private List<List<Fabu>> list2;
	private List<String> catelist1;
	private FabuKindAdapter fabuKindAdapter;
	private FabuKindSecendAdapter kindSecendAdapter;

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
		View view = inflater.inflate(R.layout.fabu_fragment_fenlei, container,
				false);
		ViewUtils.inject(this, view);
		ViewUtils.inject(view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		// catelistView2.addHeaderView(iv_banner);
		init();
		mCateListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				fabuKindAdapter.setSelectedPos(position);
				kindSecendAdapter.setSelectedPos(position);
			}
		});
	}

	private void init() {
		list2 = new ArrayList<List<Fabu>>();
		List<Fabu> flist = new ArrayList<Fabu>();
		Fabu fabu = new Fabu();
		List<String> list = new ArrayList<String>();
		list.add("电话销售");
		list.add("导购");
		list.add("销售");
		list.add("司机");
		fabu.setTitle("最热职位");
		fabu.setList(list);
		Fabu fabu1 = new Fabu();
		List<String> list3 = new ArrayList<String>();
		list3.add("司机");
		list3.add("导购");
		list3.add("销售");
		list3.add("司机");
		fabu1.setTitle("商务");
		fabu1.setList(list3);
		flist.add(fabu1);
		flist.add(fabu);

		List<Fabu> flist2 = new ArrayList<Fabu>();
		Fabu fabu3 = new Fabu();
		List<String> slist = new ArrayList<String>();
		slist.add("电话销售");
		slist.add("导购");
		slist.add("销售代表");
		slist.add("司机");
		fabu3.setTitle("技工");
		fabu3.setList(slist);
		Fabu fabu4 = new Fabu();
		List<String> list4 = new ArrayList<String>();
		list4.add("司机");
		list4.add("导购");
		list4.add("销售");
		list4.add("司机");
		fabu4.setTitle("商务/营销");
		fabu4.setList(list4);
		flist2.add(fabu3);
		flist2.add(fabu4);
		list2.add(flist2);
		list2.add(flist);

		catelist1 = new ArrayList<String>();
		catelist1.add("物流");
		catelist1.add("汽车");
		fabuKindAdapter = new FabuKindAdapter(getActivity(), catelist1);
		mCateListView.setAdapter(fabuKindAdapter);
		kindSecendAdapter = new FabuKindSecendAdapter(context, list2);
		catelistView2.setAdapter(kindSecendAdapter);
		LayoutParams params = (LayoutParams) iv_banner.getLayoutParams();
		params.width = AppContext.getScreenWidth() * 3 / 4;
		params.height = AppContext.getScreenWidth() * 3 / 8;
		iv_banner.setLayoutParams(params);
	}

}
