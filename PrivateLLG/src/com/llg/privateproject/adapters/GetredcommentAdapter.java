package com.llg.privateproject.adapters;

import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.bjg.lcc.privateproject.R;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.llg.help.MyFormat;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.smartandroid.sa.aysnc.Log;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("CutPasteId")
public class GetredcommentAdapter extends BaseAdapter {
	Context context;
	List<Map<String, Object>> list;

	/**
	 * 订单状态列表适配器
	 * 
	 * @param dialog12
	 */
	public GetredcommentAdapter(Context context, List<Map<String, Object>> list) {
		super();
		this.context = context;
		this.list = list;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		final Map<String, Object> map = list.get(position);
		Holder holder = null;
		if (v == null) {
			// map.put("MONEY", o.getDouble("MONEY"));
			// map.put("CO", o.getInt("CO"));
			// map.put("img", o.getString("PICTURE_URL"));
			// map.put("COMMON_TEXT", o.getString("COMMON_TEXT"));
			// map.put("COMMENT_ID", o.getString("COMMENT_ID"));
			// map.put("REPLY_TEXT", o.getString("REPLY_TEXT"));
			v = View.inflate(context, R.layout.getred_comment_item, null);
			holder = new Holder();
			holder.img = v.findViewById(R.id.head);
			holder.money = (TextView) v.findViewById(R.id.money);
			holder.view = v.findViewById(R.id.view);
			holder.content = (TextView) v.findViewById(R.id.content);
			holder.replay = (TextView) v.findViewById(R.id.replay);
			holder.replay_bt = (TextView) v.findViewById(R.id.replay_bt);
			holder.ll_reply = (LinearLayout) v.findViewById(R.id.ll_reply);
			// holder.et_reply=(EditText) v.findViewById(R.id.et_reply);
			holder.tv_publish = (TextView) v.findViewById(R.id.et_reply);
			
			holder.tv_name = (TextView) v.findViewById(R.id.tv_name);

			v.setTag(holder);
		} else {
			holder = (Holder) v.getTag();
		}

		// map.put("reply", "Y");
		if (map.get("reply").toString().equals("Y")
				&& (map.get("REPLY_TEXT").equals(null) || map.get("REPLY_TEXT")
						.toString() == "null")) {
			holder.replay_bt.setVisibility(View.GONE);
			holder.ll_reply.setVisibility(View.VISIBLE);
			holder.view.setVisibility(View.VISIBLE);
			final EditText et = (EditText) v.findViewById(R.id.et_reply);
			holder.ll_reply.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.d("my", "onclick:" + map.get("reply").toString());
					if (et.getText().toString().length() > 0) {
						reply(et.getText().toString(), map.get("COMMENT_ID")
								.toString(), et);
					} else {
						Toast.makeText(context, "请输入回复内容", Toast.LENGTH_SHORT)
								.show();
					}
				}
			});
		} else {
			if (!map.get("REPLY_TEXT").equals(null)
					&& map.get("REPLY_TEXT") != "null") {
				holder.replay.setVisibility(View.VISIBLE);
				holder.replay.setText(map.get("REPLY_TEXT").toString());
			} else {
				holder.replay.setVisibility(View.GONE);
			}
		}
		final EditText et = (EditText) v.findViewById(R.id.et_reply);

		MyFormat.setBitmap(context, holder.img, map.get("img").toString(), 0, 0);
		if (map.get("MONEY") != null && map.get("MONEY") != "null"
				&& Double.parseDouble(map.get("MONEY").toString()) > 0
				&& map.get("CO") != null && map.get("CO") != "null"
				&& Integer.parseInt(map.get("CO").toString()) > 0) {

			holder.money.setText("+" + "现金:" + map.get("MONEY").toString()
					+ "," + "酷币:" + map.get("CO").toString());
		} else if (map.get("MONEY") != null && map.get("MONEY") != "null"
				&& Double.parseDouble(map.get("MONEY").toString()) > 0) {
			holder.money.setText("+" + "现金:" + map.get("MONEY").toString());
		} else if (map.get("CO") != null && map.get("CO") != "null"
				&& Integer.parseInt(map.get("CO").toString()) > 0) {
			holder.money.setText("+" + "酷币:" + map.get("CO").toString());
		}
		if (map.get("COMMON_TEXT") != null) {
			holder.content.setText(map.get("COMMON_TEXT").toString());
		}
		if (map.get("APPELLATION") != null) {
			holder.tv_name.setText(map.get("APPELLATION").toString());
		}

		return v;
	}

	/***/
	private void reply(String text, String adCommentId, EditText et) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("text", text);
		params.addQueryStringParameter("adCommentId", adCommentId);
		params.addQueryStringParameter("access_token",
				UserInformation.getAccess_token());
		if (et != null) {
			et.setText("");
		}
		AppContext.getHtmlUitls().xUtilsm(context, HttpMethod.POST,
				"m/ad/saveAdReply", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						Log.d("my", "json:" + json);
						try {
							if (json.getBoolean("success")) {
								Toast.makeText(context, "操作成功",
										Toast.LENGTH_SHORT).show();
							} else {
								if (!json.get("msg").equals(null)) {
									Toast.makeText(context,
											json.getString("msg"),
											Toast.LENGTH_SHORT).show();

								}
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	private class Holder {
		View img;
		TextView money;
		TextView content;
		TextView replay_bt;
		TextView replay;
		LinearLayout ll_reply;
		EditText et_reply;
		TextView tv_publish;
		View view;
		TextView tv_name;
	}
}
