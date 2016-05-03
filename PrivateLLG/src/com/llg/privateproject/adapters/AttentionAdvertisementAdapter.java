//package com.llg.privateproject.adapters;
//
//import java.util.List;
//import java.util.Map;
//
//import org.json.JSONObject;
//
//import com.bjg.lcc.jsonparser.ParseJson;
//import com.bjg.lcc.privateproject.R;
//import com.lidroid.xutils.http.RequestParams;
//import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
//import com.llg.help.MyFormat;
//import com.llg.privateproject.AppContext;
//import com.llg.privateproject.adapters.GuangGaoJiFenAdapter.ViewHolder;
//import com.llg.privateproject.entities.Advertisement;
//import com.llg.privateproject.entities.AttentionAdvertisement;
//import com.llg.privateproject.entities.UserInformation;
//import com.llg.privateproject.fragment.BaseActivity;
//import com.llg.privateproject.fragment.BaseActivity.Refresh;
//import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
//import com.llg.privateproject.view.CustomProgressSmall;
//
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.DialogInterface.OnCancelListener;
//import android.graphics.Paint;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewTreeObserver;
//import android.view.View.OnClickListener;
//import android.view.ViewTreeObserver.OnGlobalLayoutListener;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.Toast;
//import android.widget.LinearLayout.LayoutParams;
//import android.widget.TextView;
//
//public class AttentionAdvertisementAdapter extends BaseAdapter {
//	private Context context;
//	private List<AttentionAdvertisement> list;
//	private LayoutInflater inflater;
//	private CustomProgressSmall customProgressSmall;
//	public AttentionAdvertisementAdapter(Context context,
//			List<AttentionAdvertisement> list) {
//		this.context = context;
//		this.list = list;
//		inflater = LayoutInflater.from(context);
//	}
//
//	@Override
//	public int getCount() {
//		// TODO Auto-generated method stub
//		return 	(list == null) ? 0 : list.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//		// TODO Auto-generated method stub
//		return list.get(position);
//	}
//
//	@Override
//	public long getItemId(int position) {
//		// TODO Auto-generated method stub
//		return position;
//	}
//
//	@Override
//	public View getView(final int position, View v, ViewGroup parent) {
//		// TODO Auto-generated method stub
//		final ViewHolder holder;
//		if (v == null) {
//			holder = new ViewHolder();
//			v = inflater.inflate(R.layout.listitem_attention_advertisement,
//					null);
//			holder.iv = (ImageView) v.findViewById(R.id.iv);
//			holder.tvLocation = (TextView) v.findViewById(R.id.tv_location);
//			holder.tvSend = (TextView) v.findViewById(R.id.tv_send);
//			holder.tvIsCanDraw = (TextView) v.findViewById(R.id.tv_isCanDraw);
//			v.setTag(holder);
//		} else {
//			holder = (ViewHolder) v.getTag();
//		}
//		holder.tvSend.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); // 下划线
//		ViewTreeObserver vto = holder.iv.getViewTreeObserver();
//		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
//			@Override
//			public void onGlobalLayout() {
//				if (position == list.size()) {
//					holder.iv.getViewTreeObserver()
//							.removeGlobalOnLayoutListener(this);
//				}
//				int width = holder.iv.getWidth();
//				holder.iv.setLayoutParams(new LayoutParams(width,
//						(int) (width * 0.5)));
//			}
//		});
//		MyFormat.setBitmap(context, holder.iv, list.get(position)
//				.getCOVER_IMAGE_URL(), 0, 0);
//		String name = list.get(position).getLOCATION_NAME();
//		holder.tvLocation.setText(name);
//		holder.tvSend.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				customProgressSmall = CustomProgressSmall.initDialog(
//						context, "正在转发中", true, new OnCancelListener() {
//                          
//							@Override
//							public void onCancel(DialogInterface arg0) {
//								// TODO Auto-generated
//								// method stub
//								customProgressSmall.dismiss();
//							}
//						});
//				customProgressSmall.show();
//				requestHttp(UserInformation.getAccess_token(), position);
//			}
//		});
//		int co = list.get(position).getHAS_CO();
//		int money = list.get(position).getHAS_MONEY();
//		if (co == 1 || money == 1) {
//			holder.tvIsCanDraw.setText("用户可领取红包");
//		} else {
//			holder.tvIsCanDraw.setText("红包已领取完");
//		}
//		return v;
//	}
//	/**
//	 * 转发广告
//	 */
//	private void requestHttp(String access_token,final int position) {
//		RequestParams params = new RequestParams();
//		Log.i("tag", access_token + "-----requestHttp---access_token------");
//		params.addQueryStringParameter("access_token", access_token);
//		params.addQueryStringParameter("adInfoId", list.get(position).getAD_INFO_ID());
//		params.addQueryStringParameter("parentId", list.get(position).getAD_FORWARD_ID());
//		params.addHeader("X-Requested-With", "XMLHttpRequest");
//		AppContext.getHtmlUitls().xUtilsm(context, HttpMethod.POST,
//				"m/ad/doForwardAd", params, new HttpCallback() {
//
//					@Override
//					public void onError(String msg) {
//						// TODO Auto-generated method stub
//						customProgressSmall.dismiss();
//						((BaseActivity)context).toast("转发失败");
//						Log.i("tag", msg + "--------进来了--msg------");
//					}
//
//					@Override
//					public void onBack(JSONObject json) {
//						// TODO Auto-generated method stub
//						ParseJson parseJson = ParseJson.getParseJson();
//						Map<String, Object> map = ParseJson.getParseJson()
//								.parseIsSuccess(json);
//						Log.i("tag", (Boolean) map.get("isSuccess")
//								+ "--------------isSuccess-----" + json);
//						// customProgressSmall.dismiss();
//						if ((Boolean) map.get("isSuccess")) {
//							customProgressSmall.dismiss();		
//							((BaseActivity)context).toast("转发成功");
//						} else if (map.get("errorCode").equals("NOT_LOGIN")) {
//							// Log.i("tag", json.toString()
//							// + "------监听上一句--------");
//							((BaseActivity)context).setRefreshListtener(new Refresh() {
//								@Override
//								public void refreshRequst(String access_token) {
//									// TODO Auto-generated method stub
//									Log.i("tag", "------监听里-------");
//									requestHttp(access_token,position);
//								}
//							});
//							((BaseActivity)context).RefeshToken();
//						}
//					}
//				});
//	}
//
//	class ViewHolder {
//		private ImageView iv;
//		private TextView tvLocation;
//		private TextView tvSend;
//		private TextView tvIsCanDraw;
//	}
//
//	public void setList(List<AttentionAdvertisement> list) {
//		this.list = list;
//		notifyDataSetChanged();
//	}
//}
