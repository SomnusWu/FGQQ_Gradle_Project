package com.llg.privateproject.adapters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.llg.privateproject.AppContext;
import com.bjg.lcc.jsonparser.ParseJson;
import com.bjg.lcc.privateproject.R;
import com.llg.privateproject.actvity.AddAddressActivity;
import com.llg.privateproject.adapters.AddressListViewAdapter.PostAddress;
import com.llg.privateproject.entities.Address;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.fragment.BaseActivity.Refresh;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.llg.privateproject.view.CustomProgressSmall;
import com.llg.privateproject.view.Gerenziliao_Dialog;
import com.llg.privateproject.view.Gerenziliao_Dialog.OnDeleteListener;
import com.smartandroid.sa.view.AutoLoading;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddressListViewAdapter extends BaseAdapter {
	private Context context;
	private List<Address> list;
	private PostAddress postAddress;
	private CustomProgressSmall customProgressSmall;
	private int selectPositon=-1;

	public AddressListViewAdapter(Context context, List<Address> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return (list == null) ? 0 : list.size();
	}

	@Override
	public Address getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder vh = null;
		if (v == null) {
			vh = new ViewHolder();
			v = View.inflate(context, R.layout.order_address_listview_item,
					null);
			if (position == 0) {
				v.findViewById(R.id.xian_dian).setVisibility(View.GONE);
			}
			vh.name = (TextView) v.findViewById(R.id.name);
			vh.telphone = (TextView) v.findViewById(R.id.telphone);
			vh.area = (TextView) v.findViewById(R.id.area);
			vh.street = (TextView) v.findViewById(R.id.street);
			vh.tvDefault = (TextView) v.findViewById(R.id.tv_default);
			vh.iv = (ImageView) v.findViewById(R.id.iv);
			vh.rl_edit = (RelativeLayout) v.findViewById(R.id.rl_edit);
			vh.rl_delete = (RelativeLayout) v.findViewById(R.id.rl_delete);

			v.setTag(vh);
		} else {
			vh = (ViewHolder) v.getTag();
		}
		if (list.get(position) != null
				&& list.get(position).getIsDefault() != null) {
			if (list.get(position).getIsDefault().equals("N")) {
				vh.tvDefault.setVisibility(View.GONE);
			} else {
				// vh.rb.setChecked(true);
				vh.tvDefault.setVisibility(View.VISIBLE);
				if (selectPositon==-1) {
				vh.iv.setVisibility(View.VISIBLE);
				}
			}
		}
		if (selectPositon==position) {
			vh.iv.setVisibility(View.VISIBLE);
		}else {
			if (selectPositon==-1) {
				vh.iv.setVisibility(View.GONE);
			}
		}
		
		vh.name.setText(list.get(position).getName());
		vh.telphone.setText(list.get(position).getPhone());
		vh.area.setText(list.get(position).getZoneName());
		vh.street.setText(list.get(position).getAddress());
		vh.rl_edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, AddAddressActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("title", "修改收货地址");
				bundle.putString("name", list.get(position).getName());
				bundle.putString("phone", list.get(position).getPhone());
				bundle.putString("area", list.get(position).getZoneName());
				bundle.putString("zone", list.get(position).getZone());
				bundle.putString("street", list.get(position).getAddress());
				bundle.putString("id", list.get(position).getId());
				bundle.putBoolean("isEdit", true);
				if (list.get(position).getIsDefault().equals("N")) {
					bundle.putBoolean("isChecked", false);
				} else {
					bundle.putBoolean("isChecked", true);
				}
				intent.putExtras(bundle);
				((Activity) context).startActivityForResult(intent, 2);
				intent = null;
				bundle = null;
				Toast.makeText(context, "编辑", Toast.LENGTH_SHORT).show();
			}
		});
		vh.rl_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Gerenziliao_Dialog dialog = new Gerenziliao_Dialog(
						context, 5);
				dialog.show();
				WindowManager.LayoutParams lp = dialog.getWindow()
						.getAttributes();
				lp.width = AppContext.getScreenWidth() * 4 / 5;
				lp.height = AppContext.getScreenWidth() / 2;
				dialog.getWindow().setAttributes(lp);
				dialog.setOnDeleteListener(new OnDeleteListener() {

					@Override
					public void deleteAddress() {
						// TODO Auto-generated method stub
						dialog.dismiss();
						customProgressSmall = CustomProgressSmall.initDialog(
								context, "正在删除中", true, new OnCancelListener() {

									@Override
									public void onCancel(DialogInterface arg0) {
										// TODO Auto-generated method stub
										customProgressSmall.dismiss();
									}
								});
						customProgressSmall.show();
						requestDeleteAddress(UserInformation
								.getAccess_token(), position);
					}
				});
			}
		});
		return v;
	}

	/** 传递收货 */
	public interface PostAddress {
		void setAddress(String name, String phone, String address,
						String id);
	}

	public void setPostAddress(PostAddress postAaddress) {
		postAddress = postAaddress;
	}

	class ViewHolder {
		ImageView iv;
		TextView tvDefault;
		View xiandian;
		TextView name;
		TextView telphone;
		TextView area;
		TextView street;
		TextView mail;
		RelativeLayout rl_edit;
		RelativeLayout rl_delete;
	}

	public void setList(List<Address> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	private void requestDeleteAddress(String access_token, final int position) {
		RequestParams params = new RequestParams();
		Log.i("tag", access_token + "-----requestHttp---access_token------");
		Log.i("tag",
				((BaseActivity) context).getSharePrefence().getString(
						"access_token", "")
						+ "-----requestAddressList---access_token--sharefrence----");
		params.addQueryStringParameter("access_token", access_token);
		params.addQueryStringParameter("id", list.get(position).getId());
		params.addHeader("X-Requested-With", "XMLHttpRequest");
		AppContext.getHtmlUitls().xUtilsm(context, HttpMethod.POST,
				"m/revAddr/deleteAddress", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						Toast.makeText(context, "删除失败", Toast.LENGTH_LONG)
								.show();
						Log.i("tag", msg + "--------进来了--msg------");
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						ParseJson parseJson = ParseJson.getParseJson();
						Map<String, Object> map = ParseJson.getParseJson()
								.parseIsSuccess(json);
						Log.i("tag", map.get("isSuccess")
								+ "--------------isSuccess-----" + json);
						// customProgressSmall.dismiss();
						if ((Boolean) map.get("isSuccess")) {
							list.remove(position);
							notifyDataSetChanged();
							customProgressSmall.dismiss();
							Toast.makeText(context, "删除成功", Toast.LENGTH_LONG)
									.show();
						} else if (map.get("errorCode").equals("NOT_LOGIN")) {
							// Log.i("tag", json.toString()
							// + "------监听上一句--------");
							((BaseActivity) context)
									.setRefreshListtener(new Refresh() {
										@Override
										public void refreshRequst(
												String access_token) {
											// TODO Auto-generated method stub
											Log.i("tag", "------监听里-------");
											requestDeleteAddress(access_token,
													position);
										}
									});
							((BaseActivity) context).RefeshToken();
						}
					}
				});
	}

	public void setPosition(int selectPosition) {
		this.selectPositon = selectPosition;
	}

}