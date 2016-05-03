package com.llg.privateproject.actvity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.android.util.StringUtils;
import com.bjg.lcc.privateproject.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.llg.help.MyFormat;
import com.llg.help.SaveImg;
import com.llg.privateproject.AppContext;
import com.llg.privateproject.adapters.GetredcommentAdapter;
import com.llg.privateproject.entities.MORE_IMAGEBean;
import com.llg.privateproject.entities.UserInformation;
import com.llg.privateproject.entities.WXShareBean;
import com.llg.privateproject.fragment.BaseActivity;
import com.llg.privateproject.html.AndroidCallBack.HttpCallback;
import com.llg.privateproject.utils.CommonUtils;
import com.llg.privateproject.view.FGQQSelectShareDialog;
import com.llg.privateproject.view.MyListView;
import com.llg.privateproject.view.ProductDetailEllipsisPoPuWindow;
import com.llg.privateproject.view.ProductDetailEllipsisPoPuWindow.SelecetListener;
import com.smartandroid.sa.eventbus.EventBus;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

//implements android.view.View.OnTouchListener.onTouch

/** 获取红包界面 */
public class GetRedPackage extends BaseActivity implements SelecetListener,
		OnCheckedChangeListener {
	/** 标题 */
	@ViewInject(R.id.head_tilte)
	private TextView head_tilte;
	/** 分享 */
	@ViewInject(R.id.more)
	private ImageView head_tilte2;
	/** 问题 */
	@ViewInject(R.id.question)
	public TextView question;
	/** 答案 */
	@ViewInject(R.id.answer)
	public TextView answer;
	/** 验证码 */
	@ViewInject(R.id.eanswer)
	public EditText eanswer;
	/** 评论内容 */
	@ViewInject(R.id.et_comment)
	public EditText et_comment;
	/** 提交评论内容 */
	@ViewInject(R.id.tv_comment)
	public TextView tv_comment;

	@ViewInject(R.id.rg)
	private RadioGroup rg;
	@ViewInject(R.id.tb1)
	private RadioButton rb1;
	@ViewInject(R.id.tb2)
	private RadioButton rb2;
	@ViewInject(R.id.tb3)
	private RadioButton rb3;
	@ViewInject(R.id.tb4)
	private RadioButton rb4;
	/** 问题 */
	@ViewInject(R.id.tv_question)
	public TextView tv_question;
	/** 领取红包金额提示 */
	@ViewInject(R.id.red_package_hint)
	private TextView red_package_hint;
	/** 红包入账提示金额提示 */
	@ViewInject(R.id.fgqqg)
	private TextView fgqqg;
	/** 离开 */
	@ViewInject(R.id.turn)
	private ImageView turn;
	/** 问题布局 */
	@ViewInject(R.id.ll_q)
	private LinearLayout ll_q;
	/** 问题布局 */
	@ViewInject(R.id.ll_up)
	private LinearLayout ll_up;
	/** 评论布局 */
	@ViewInject(R.id.ll_comment)
	private MyListView ll_comment;
	/** 抢到红包提示布局 */
	@ViewInject(R.id.ll_red_hint)
	private LinearLayout ll_red_hint;
	/** 获取提示布局 */
	@ViewInject(R.id.lll)
	private LinearLayout lll;
	/** 评价布局 */
	@ViewInject(R.id.ll_pingjia)
	private LinearLayout ll_pingjia;
	/** 查看更多 */
	@ViewInject(R.id.to_see1)
	private TextView to_see;
	/** 布局背景广告图片 */
	@ViewInject(R.id.ll)
	private ImageView ll;

	@ViewInject(R.id.ll_layout_image_details)
	private LinearLayout ll_layout_image_details;

	private String adInfoId;
	private String adRedEnvelopId;
	private ProductDetailEllipsisPoPuWindow ellipticalsisWindow;
	private List<Map<String, Object>> list;
	// @ViewInject(R.id.v1)
	// private View v1;
	// @ViewInject(R.id.v2)
	// private View v2;
	private String objectId = "";
	private String img = "";
	private String objectType = "";
	private String id;
	private List<MORE_IMAGEBean> mMoreImageBean;
	/** 发广告者的cusid */
	private String CUS_ID = "";
	String co = "";
	String money = "";
	String mesg = "";
	AppContext appContext;
	String questionStr = "";
	String adForwardId = "";
	private IWXAPI api;
	private String QUESTION = "";// 问题

	private String CORRECT_ANSWER = "";// 答案
	private String select_answer = "";// 选择的答案
	private String TITLE = "";
	private SoundPool soudPool;
	private int play;
	/** 0不显示,1显示 */
	private int lll_isshow = 0;
	private Random random = new Random();
	// 数组大小
	private static final int SIZE = 4;
	// 要重排序的数组
	private String[] positions = new String[SIZE];
	GetredcommentAdapter adapter;
	GestureDetector gestureDetector;

	/** 是否抢过红包,Y已抢过,N没抢过 */
	private String IS_GETTED_RED = "";
	/** 为空 无money */
	private String HAS_MONEY = "";
	/** 为空 无co */
	private String HAS_CO = "";

	/** 是否需要分享才能抢红包 Y：需要 N:不需要 **/
	private String NEED_SHARE = "";
	private long time1 = 0;
	FGQQSelectShareDialog dialog;
	private boolean isShareClick = false;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:// 失败

				break;

			case 1:// 领取红包成功
					// soundID a soundID returned by the load() function
					// leftVolume left volume value (range = 0.0 to 1.0)
					// rightVolume right volume value (range = 0.0 to 1.0)
					// priority stream priority (0 = lowest priority)
					// loop loop mode (0 = no loop, -1 = loop forever)
					// rate playback rate (1.0 = normal playback, range 0.5 to
					// 2.0)

				if (co.length() > 0 && money.length() > 0) {
					ll_red_hint.setVisibility(View.VISIBLE);
					soudPool.play(play, 1, 1, 0, 0, 1);
					red_package_hint.setText("恭喜你获取红包:"
							+ MyFormat.getPriceFormat(money) + "元,获取酷币:" + co
							+ "个!" + "将在1分钟到账");
				} else if (co.length() > 0 && money.length() < 1) {
					ll_red_hint.setVisibility(View.VISIBLE);
					soudPool.play(play, 1, 1, 0, 0, 1);
					red_package_hint
							.setText("恭喜你获取酷币:" + co + "个!" + "将在1分钟到账");

				} else if (co.length() < 1 && money.length() > 0) {
					ll_red_hint.setVisibility(View.VISIBLE);
					soudPool.play(play, 1, 1, 0, 0, 1);
					red_package_hint
							.setText("恭喜你获取红包:"
									+ MyFormat.getPriceFormat(money) + "元!"
									+ "将在1分钟到账");

				} else if (mesg != null && mesg.length() > 0) {
					fgqqg.setTextColor(getResources().getColor(R.color.black2));
					fgqqg.setText(mesg);
				}
				if (getIntent().getBooleanExtra("isDialog", false)) {
					new CountDownTimer(5 * 1000, 1000) {
						@Override
						public void onTick(long millisUntilFinished) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onFinish() {
							// TODO Auto-generated method stub
							finish();
						}
					}.start();
				}
				eanswer.setText("");
				break;
			case 2:// 获取问题

				break;
			case 3:// 清空 评论类容
				et_comment.setText("");
				break;
			case 4:// 获取广告详情

				if (objectId != null && objectId.length() > 5
						&& !objectType.equals("0")) {
					to_see.setVisibility(View.VISIBLE);
					if (objectType.equals("4")) {
						to_see.setText("外网详情");
					} else {
						to_see.setText("查看更多");
					}
				} else {
					to_see.setVisibility(View.GONE);
				}
				if (!StringUtils.isEmpty(QUESTION)) {
					rg.setVisibility(View.VISIBLE);
				} else {
					rg.setVisibility(View.GONE);
				}
				ll_q.setVisibility(View.VISIBLE);
				// --------------------------------------------------------------------------------------------
				ll_layout_image_details.removeAllViews();
				for (int i = 0; i < mMoreImageBean.size(); i++) {
					ImageView image = new ImageView(GetRedPackage.this);
					image.setLayoutParams(new LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT));
					image.setScaleType(ScaleType.FIT_XY);
					ll_layout_image_details.addView(image);

					MyFormat.setBitmap(GetRedPackage.this, image,
							mMoreImageBean.get(i).getIMAGE_URL(),
							AppContext.getScreenWidth(),
							AppContext.getScreenWidth());
				}
				if (mMoreImageBean.size() == 0) {
					MyFormat.setBitmap(GetRedPackage.this, ll, img,
							AppContext.getScreenWidth(),
							AppContext.getScreenWidth());
				}
				head_tilte.setText(TITLE);
				tv_question.setText(QUESTION);
				// HAS_CO;
				// HAS_MONEY

				if (IS_GETTED_RED.equals("Y")) {
					lll.setVisibility(View.GONE);
					rg.setVisibility(View.GONE);
				} else {
					rg.setVisibility(View.VISIBLE);
					lll.setVisibility(View.VISIBLE);
				}
				if ((HAS_CO.equals("1")) || (HAS_MONEY.equals("1"))) {
				} else {
					lll.setVisibility(View.GONE);
					rg.setVisibility(View.GONE);
				}

				String positions0 = positions[0];
				String positions1 = positions[1];
				String positions2 = positions[2];
				String positions3 = positions[3];

				if (getIsEmpty(positions0) && getIsEmpty(positions1)
						&& getIsEmpty(positions2) && getIsEmpty(positions3)) {
					rg.setVisibility(View.GONE);
				}
				changePosition();
				break;
			case 5:// 不可以评论,隐藏品论框
				break;
			case 10:// 分享
				bitmap = (Bitmap) msg.obj;
				// if(isShareClick){
				// dialog = new FGQQSelectShareDialog(GetRedPackage.this,
				// mClickListener);
				// dialog.show();
				// }
				shareUrl(type);
				break;
			case 11:// 评论返回
				if (pageNo >= totalPages) {
					ll_up.setVisibility(View.GONE);
					return;
				} else {
					ll_up.setVisibility(View.VISIBLE);
				}
				break;
			}
		}
	};

//	private OnClickListener mClickListener = new OnClickListener() {
//
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			switch (v.getId()) {
//			case R.id.tv_share_friend:
//				// getRed();
//				type = 1;
//
//				break;
//			case R.id.tv_share_friend_circle:
//				// getRed();
//				type = 2;
//				break;
//
//			default:
//				break;
//			}
//			shareUrl(type);
//		}
//	};

	private void getRed() {
		saveAttentionAd();
		// isShareClick = true;
		// dialog.dismiss();
		/**
		 * 先分享 等分享回调成功 就可以领取红包 handelr send message 10
		 * 
		 * Modify: 判断一下红包最小值是否≥5毛钱。 如果是才要求转发到朋友圈。 如果不是则可以不用转发到朋友圈
		 */
		// if (saveImg == null) {
		if (!StringUtils.isEmpty(NEED_SHARE) && NEED_SHARE.equals("Y")) {
			saveImg = new SaveImg(img, handler, bitmap, this);
		} else {
			redpackageMethod();
		}

		// }
		// getRedPackage();
	}

	// 重排序
	public void changePosition() {

		for (int index = SIZE - 1; index >= 0; index--) {
			// 从0到index处之间随机取一个值，跟index处的元素交换
			exchange(random.nextInt(index + 1), index);
		}
		rb1.setText(positions[0]);
		rb2.setText(positions[1]);
		rb3.setText(positions[2]);
		rb4.setText(positions[3]);

	}

	// 交换位置
	private void exchange(int p1, int p2) {
		String temp = positions[p1];
		positions[p1] = positions[p2];
		positions[p2] = temp; // 更好位置

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.getredpackage);
		ViewUtils.inject(this);
		turn.setImageResource(R.drawable.threestagepage_return_yuan);
		if (getIntent().getExtras() != null) {
			adInfoId = getIntent().getStringExtra("adInfoId");
			time1 = System.currentTimeMillis();
			soudPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 5);
			play = soudPool.load(this, R.raw.getmoney, 1);

		}
		comehere();
		init();
		getRedAndComment();
		valiIsComment();
		// 分享 得到回调 状态
		EventBus.getDefault().register(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		ms();
	}

	/** 在该页面停留时间 */
	private void ms() {
		long time = System.currentTimeMillis() - time1;
		if (time < 3000) {
			return;
		}
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("adInfoId", adInfoId);
		params.addQueryStringParameter("cusId", AppContext.userid);
		params.addQueryStringParameter("ms", "" + time);
		params.addQueryStringParameter("type", "" + 1);
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"common/ad/adStay", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub

					}
				});
	}

	private void comehere() {
		XGPushClickedResult click = XGPushManager.onActivityStarted(this);
		if (click != null) {
			lll_isshow = 1;
			JSONObject obj;
			try {
				obj = new JSONObject(click.getCustomContent());
				if (obj.has("objectId") && obj.get("objectId") != null) {

					adInfoId = obj.get("objectId").toString();
				} else {
					finish();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private void init() {

		mMoreImageBean = new ArrayList<MORE_IMAGEBean>();
		if (getIntent().getIntExtra("type", 0) == 0
				|| (getIntent().getStringExtra("isEmpty") != null && getIntent()
						.getStringExtra("isEmpty").equals("Y"))) {// 等于零的时候没有红包,影藏部分布局
			rg.setVisibility(View.GONE);
			ll_pingjia.setVisibility(View.VISIBLE);
			lll.setVisibility(View.GONE);

		}
		if (lll_isshow == 1) {
			lll.setVisibility(View.VISIBLE);
		}
		ll_up.setVisibility(View.GONE);
		list = new ArrayList<Map<String, Object>>();
		adapter = new GetredcommentAdapter(this, list);
		ll_comment.setAdapter(adapter);
		ll_red_hint.setVisibility(View.GONE);
		question.setText("关闭收藏");

		ll_q.setVisibility(View.GONE);
		rg.setOnCheckedChangeListener(this);
		getAdInfo();
		saveHistory();
		api = WXAPIFactory.createWXAPI(this,
				com.llg.privateproject.utils.Constants.APP_ID, true);
		api.registerApp(com.llg.privateproject.utils.Constants.APP_ID);
		head_tilte2.setImageResource(R.drawable.threestagepage_ellipsis_share);
		head_tilte2.setVisibility(View.VISIBLE);

		head_tilte.setText("");
		appContext = getAppContext();
		to_see.setVisibility(View.GONE);
		objectId = getIntent().getStringExtra("objectId");
		img = getIntent().getStringExtra("img");
		objectType = getIntent().getStringExtra("objectType");
		adForwardId = getIntent().getStringExtra("adForwardId");
		id = getIntent().getStringExtra("id");
		LayoutParams params = new LayoutParams(AppContext.getScreenWidth(),
				LayoutParams.WRAP_CONTENT);
		ll.setLayoutParams(params);
		ll.setMaxWidth(AppContext.getScreenWidth());
		ll.setMaxHeight(AppContext.getScreenWidth() * 30);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(img, options);
		// ----------------------------------------------------------------------------------------
		// MyFormat.setBitmap(this, ll, img, AppContext.getScreenWidth(),
		// AppContext.getScreenWidth());

		if (objectId != null && objectId.length() > 5
				&& !objectType.equals("0")) {
			to_see.setVisibility(View.VISIBLE);
		}
		red_package_hint.setText("");
		red_package_hint.setTextColor(getMyColor(R.color.meihong));
		fgqqg.setText("");
		fgqqg.setTextColor(getMyColor(R.color.black3));
		fgqqg.setTextSize(getDimen(R.dimen.dimen_12sp) / 2);
		if (objectId == null || objectId.equals("null")) {
			to_see.setVisibility(View.GONE);
		}
		if (getIntent().getBooleanExtra("isDialog", false)) {
			new CountDownTimer(1000 * 5, 1000) {

				@Override
				public void onTick(long times) {
					// TODO Auto-generated method stub
					fgqqg.setTextColor(getResources().getColor(R.color.meihong));
					fgqqg.setText(times / 1000 + "s后关闭");

				}

				@Override
				public void onFinish() {
					// TODO Auto-generated method stub
					fgqqg.setText("");
					finish();
					// getRedPackage();
				}
			}.start();
		}

	}

	private int pageNo = 0;
	private int totalPages = 1;

	/** 获取评论列表 */
	private void getRedAndComment() {
		if (pageNo >= totalPages) {
			toast("暂无更多评论");
			ll_up.setVisibility(View.GONE);
			return;
		} else {
			ll_up.setVisibility(View.VISIBLE);
		}
		if (!appContext.isNetworkConnected()) {
			return;
		}
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("adInfoId", adInfoId);
		params.addQueryStringParameter("pageNo", String.valueOf(++pageNo));
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"common/ad/getRedAndComment", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub

						handler.sendEmptyMessage(11);
						try {
							if (json.getBoolean("success")) {
								JSONObject obj = json.getJSONObject("obj");
								totalPages = obj.getInt("totalPages");
								JSONArray result = obj.getJSONArray("result");
								if (result.length() > 0) {
									Map<String, Object> map;
									for (int i = 0; i < result.length(); i++) {
										map = new HashMap<String, Object>();
										JSONObject o = result.getJSONObject(i);
										if (!o.get("MONEY").equals(null)
												&& o.get("MONEY") != "null") {
											map.put("MONEY",
													o.getDouble("MONEY"));
										} else {
											map.put("MONEY", 0);
										}
										if (!o.get("CO").equals(null)
												&& o.get("CO") != "null") {
											map.put("CO", o.getInt("CO"));
										} else {
											map.put("CO", 0);
										}
										map.put("img",
												o.getString("PICTURE_URL"));
										map.put("COMMON_TEXT",
												o.getString("COMMON_TEXT"));
										map.put("APPELLATION",
												o.getString("APPELLATION"));
										map.put("COMMENT_ID",
												o.getString("COMMENT_ID"));
										map.put("REPLY_TEXT",
												o.getString("REPLY_TEXT"));
										if (!AppContext.userid.equals(null)
												&& CUS_ID == AppContext.userid) {
											map.put("reply", "Y");
										} else {
											map.put("reply", "N");
										}
										map.put("REPLY_TEXT",
												o.getString("REPLY_TEXT"));
										list.add(map);
										map = null;
									}

									adapter.notifyDataSetChanged();
								}
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

	}

	@OnClick({ R.id.turn, R.id.to_see1, R.id.more, R.id.answer,
			R.id.tv_comment, R.id.question, R.id.ll, R.id.ll_up })
	public void myClick(View v) {
		switch (v.getId()) {
		case R.id.question:// 隐藏页面
			saveAttentionAd();
			finish();
			break;
		case R.id.ll://
			if (appContext.isNetworkConnected()) {
				MyFormat.toSomeWhere(this, objectType, objectId, getIntent()
						.getStringExtra("forwardId"), getIntent()
						.getStringExtra("chain"), adInfoId);
				finish();
			} else {
				toast(R.string.t_Connection);
			}
			break;
		case R.id.turn:
//			startActivity(new Intent(GetRedPackage.this, NewHomeActivity.class));
			finish();
			break;
		case R.id.ll_up:
			getRedAndComment();
			break;
		case R.id.to_see1:
			if (appContext.isNetworkConnected()) {
				MyFormat.toSomeWhere(this, objectType, objectId, getIntent()
						.getStringExtra("forwardId"), getIntent()
						.getStringExtra("chain"), adInfoId);
				finish();
			} else {
				toast(R.string.t_Connection);
			}
			break;

		case R.id.more:// 分享
			if (appContext.isNetworkConnected()) {
				if (ellipticalsisWindow == null) {

					// 判断当前的广告是不是 自己发的广告
					if (!StringUtils.isEmpty(AppContext.userid)
							&& CUS_ID.equals(AppContext.userid)) {
						ellipticalsisWindow = new ProductDetailEllipsisPoPuWindow(
								this, this, 9, head_tilte2);
					} else {
						ellipticalsisWindow = new ProductDetailEllipsisPoPuWindow(
								this, this, 5, head_tilte2);
					}

				}
				head_tilte2.setVisibility(View.VISIBLE);
				ellipticalsisWindow.showAsDropDown(head_tilte2);
			} else {
				toast(R.string.t_Connection);
			}
			break;
		/**
		 * 分享并领取红包
		 */
		case R.id.answer:// 获取红包,保存红包
			if (soudPool == null) {
				soudPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 5);
			}

			//
			// soudPool.play(
			// soudPool.load(GetRedPackage.this, R.raw.beep, 1),
			// 1, 1, 1, 1, 1);
			if (appContext.isNetworkConnected()) {

				getRedPackage();

				// saveAttentionAd();
				// isShareClick = true;
				//
				// getRedPackage();

			} else {
				toast(R.string.t_Connection);
			}
			// answer.setClickable(false);
			// new CountDownTimer(1000 * 5, 1000) {
			//
			// @Override
			// public void onTick(long times) {
			// // TODO Auto-generated method stub
			//
			// }
			//
			// @Override
			// public void onFinish() {
			// if (answer != null) {
			// answer.setClickable(true);
			// }
			// }
			// }.start();
			break;
		case R.id.tv_comment:// 提交评论
			if (appContext.isNetworkConnected()) {
				if (et_comment.length() < 1) {
					toast("亲,还没有评论内容哦!");
					return;
				} else {
					saveAdComment();
				}
			} else {
				toast(R.string.t_Connection);
			}
			break;

		}

	}

//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		// TODO Auto-generated method stub
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
////			startActivity(new Intent(GetRedPackage.this, NewHomeActivity.class));
//			finish();
//			return true;
//		}
//		return super.onKeyDown(keyCode, event);
//
//	}

	/** 保存浏览广告记录 */
	private void saveHistory() {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("access_token", UserInformation.getAccess_token());
		params.addBodyParameter("adInfoId", adInfoId);
		params.addHeader(MyFormat.HEADER_KEY, MyFormat.HEADER_VALUE);
		params.addQueryStringParameter("time", "" + System.currentTimeMillis());
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"m/ad/saveHistory", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onBack(JSONObject json) {

					}
				});
	}

	/** 获取广告详情页面 */
	private void getAdInfo() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("adInfoId", adInfoId);
		params.addBodyParameter("cusId", AppContext.userid);
		params.addQueryStringParameter("time", "" + System.currentTimeMillis());
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"common/ad/getAdInfo", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						Log.d("my", "广告信息" + json.toString());
						try {
							if (json.getBoolean("success")) {
								JSONObject obj = json.getJSONObject("obj");
								positions[0] = obj.getString("ERROR_ANSWER1");
								positions[1] = obj.getString("ERROR_ANSWER2");
								positions[2] = obj.getString("ERROR_ANSWER3");
								positions[3] = obj.getString("CORRECT_ANSWER");
								CORRECT_ANSWER = obj
										.getString("CORRECT_ANSWER");
								QUESTION = obj.getString("QUESTION");
								CUS_ID = obj.getString("CUS_ID");
								TITLE = obj.getString("TITLE");
								img = obj.getString("IMAGE_URL");
								if (!obj.get("HAS_CO").equals(null)) {
									HAS_CO = obj.getString("HAS_CO");
								}
								NEED_SHARE = obj.getString("NEED_SHARE");

								if (!obj.get("HAS_MONEY").equals(null)) {
									HAS_MONEY = obj.getString("HAS_MONEY");
								}
								if (obj.has("IS_GETTED_RED")) {
									IS_GETTED_RED = obj
											.getString("IS_GETTED_RED");
								}
								if (obj.get("OBJECT_ID") != null) {
									objectId = obj.getString("OBJECT_ID");
								}
								if (obj.get("OBJECT_TYPE") != null) {
									objectType = obj.getString("OBJECT_TYPE");
								}

								Gson gson = new Gson();
								List<MORE_IMAGEBean> ps = gson.fromJson(obj
										.get("MORE_IMAGES").toString(),
										new TypeToken<List<MORE_IMAGEBean>>() {
										}.getType());
								mMoreImageBean.addAll(ps);
								handler.sendEmptyMessage(4);

								adRedEnvelopId = obj
										.getString("AD_RED_ENVELOP_ID");

							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	private boolean getIsEmpty(String msg) {
		return StringUtils.isEmpty(msg);
	}

	/** 收藏广告 */
	private void saveAttentionAd() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("adInfoId", adInfoId);
		params.addBodyParameter("adForwardId",
				getIntent().getStringExtra("adForwardId"));
		params.addQueryStringParameter("access_token", UserInformation.getAccess_token());
		params.addQueryStringParameter("time", "" + System.currentTimeMillis());
		params.addHeader("X-Requested-With", "XMLHttpRequest");
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"m/ad/saveAttentionAd", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						// myLogShow("my", "json:"+json);
					}
				});
	}

	/** 提交广告评论 */
	private void saveAdComment() {
		// GetProgressBar.getProgressBar(this);
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("text", et_comment.getText().toString());// 评论内容

		params.addQueryStringParameter("adInfoId", adInfoId);// 广告id
		params.addQueryStringParameter("access_token", UserInformation.getAccess_token());
		params.addQueryStringParameter("time", "" + System.currentTimeMillis());
		params.addHeader(MyFormat.HEADER_KEY, MyFormat.HEADER_VALUE);
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.GET,
				"m/ad/saveAdComment", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						try {
							if (json.getBoolean("success")) {
								toast("评论已提交");
								handler.sendEmptyMessage(3);
							} else {
								if (json.getString("errorCode").equals(
										MyFormat.NOT_LOGIN)) {
									toast(json.getString("msg"));
									setRefreshListtener(new Refresh() {
										@Override
										public void refreshRequst(
												String access_token) {
											// TODO Auto-generated method stub
											saveAdComment();
										}
									});
									RefeshToken();// "msg":"你已经评论过该广告"

								} else if (json.get("msg") != null
										&& json.getString("msg").equals(
												"你已经评论过该广告")) {
									toast("你已经评论过了");
								}
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	private boolean isCanComment = true;

	/** 判断是否可以评论 */
	private void valiIsComment() {
		// GetProgressBar.getProgressBar(this);
		RequestParams params = new RequestParams();

		params.addQueryStringParameter("adInfoId", adInfoId);// 广告id
		params.addQueryStringParameter("access_token", UserInformation.getAccess_token());
		params.addQueryStringParameter("time", "" + System.currentTimeMillis());
		params.addHeader(MyFormat.HEADER_KEY, MyFormat.HEADER_VALUE);
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.GET,
				"m/ad/valiIsComment", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						try {
							if (json.getBoolean("success")) {
								// 可以平路
								isCanComment = true;

							} else {
								isCanComment = false;

								handler.sendEmptyMessage(5);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	/** 转发广告 */
	private void doForwardAd() {
		// GetProgressBar.getProgressBar(this);
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("parentId",
				getIntent().getStringExtra("adForwardId"));// 转发id
		params.addQueryStringParameter("adInfoId", adInfoId);// 广告id
		params.addQueryStringParameter("time", "" + System.currentTimeMillis());
		params.addQueryStringParameter("access_token",
				UserInformation.getAccess_token());
		params.addHeader(MyFormat.HEADER_KEY, MyFormat.HEADER_VALUE);
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.GET,
				"m/ad/doForwardAd", params, new HttpCallback() {

					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						try {
							if (json.getBoolean("success")) {
								toast("转发成功");
								// handler.sendEmptyMessage(3);
							} else {
								if (json.getString("errorCode").equals(
										MyFormat.NOT_LOGIN)) {
									toast(json.getString("msg"));
									// GetRedPackage.this
									// .startActivity(new Intent(
									// GetRedPackage.this,
									// WebLoginActivity.class));
									setRefreshListtener(new Refresh() {
										@Override
										public void refreshRequst(
												String access_token) {
											// TODO Auto-generated method stub
											doForwardAd();
										}
									});
									RefeshToken();
								} else {
									toast(json.getString("msg"));
								}
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	/** 获取红包 */
	public void getRedPackage() {
		if (select_answer.length() > 0 && select_answer.equals(CORRECT_ANSWER)) {
		} else if (CORRECT_ANSWER.length() == 0) {

		} else {
			toast("答案有误");
			return;
		}
		isShareClick = true;
		getRed();
		this.type = 2;
		// dialog = new FGQQSelectShareDialog(GetRedPackage.this,
		// mClickListener);
		// dialog.show();

		// /**
		// * 先分享 等分享回调成功 就可以领取红包
		// */
		// if (saveImg == null) {
		// saveImg = new SaveImg(img, handler, bitmap, this);
		// }
		// this.type = 1;

		/** ................................................................... */
		// RequestParams params = new RequestParams();
		// params.addQueryStringParameter("time", "" +
		// System.currentTimeMillis());
		// params.addQueryStringParameter("answer", select_answer);
		// params.addQueryStringParameter("access_token",
		// UserInformation.getAccess_token());
		// params.addQueryStringParameter("adredId", adRedEnvelopId);
		// params.addHeader("X-Requested-With", "XMLHttpRequest");
		// AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
		// "m/ad/getRedPackage", params, new HttpCallback() {
		//
		// @Override
		// public void onError(String msg) {
		// // TODO Auto-generated method stub
		// handler.sendEmptyMessage(0);
		// }
		//
		// @Override
		// public void onBack(JSONObject json) {
		// // TODO Auto-generated method stub
		// try {
		// if (json.getBoolean("success")) {
		// if (json.getJSONObject("attributes")
		// .getJSONObject("adRedEnvelopItem") != null) {
		// if (json.getJSONObject("attributes")
		// .getJSONObject("adRedEnvelopItem")
		// .getString("co") != null
		// && json.getJSONObject("attributes")
		// .getJSONObject(
		// "adRedEnvelopItem")
		// .getString("co").length() > 0) {
		//
		// co = json
		// .getJSONObject("attributes")
		// .getJSONObject(
		// "adRedEnvelopItem")
		// .getString("co");
		// }
		// if (json.getJSONObject("attributes")
		// .getJSONObject("adRedEnvelopItem")
		// .getString("money") != null
		// && json.getJSONObject("attributes")
		// .getJSONObject(
		// "adRedEnvelopItem")
		// .getString("money")
		// .length() > 0) {
		//
		// money = json
		// .getJSONObject("attributes")
		// .getJSONObject(
		// "adRedEnvelopItem")
		// .getString("money");
		// }
		//
		// }
		// } else {
		// if (json.getString("errorCode").equals(
		// MyFormat.NOT_LOGIN)) {
		// toast(json.getString("msg"));
		// setRefreshListtener(new Refresh() {
		//
		// @Override
		// public void refreshRequst(
		// String access_token) {
		// // TODO Auto-generated method stub
		// getRedPackage();
		// }
		// });
		// RefeshToken();
		//
		// }
		// toast(json.getString("msg"));
		// // mesg = json.getString("msg");
		//
		// }
		// handler.sendEmptyMessage(1);
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// });

		/** ................................................................... */
	}

	/**
	 * 分享回调
	 * 
	 * @param bean
	 */
	public void onEventMainThread(WXShareBean bean) {
		if (bean.isShare()) {
			toast("分享成功！");
		}
		// 代表分享成功
		if (bean.isShare() && isShareClick) {
			redpackageMethod();
		}
	}

	private SaveImg saveImg;

	/**
	 * url
	 * 
	 * @param type
	 *            :1 微信,2朋友圈
	 */
	private void shareUrl(int type) {
		if (!CommonUtils.isWeixinAvilible(this)) {
			toast("您未安装微信,请安装最新版微信再试!");
			return;
		}
		new downloadImgThread(type).start();
		// new downloadImgThread(networkTask).start();
		// WXWebpageObject webpage = new WXWebpageObject();
		// // webpage.webpageUrl = "http://www.fgqqg.com";
		// webpage.webpageUrl = getResources().getString(R.string.buyserver)
		// + "m/ad?id=" + adInfoId + "&shareCusId=" + AppContext.userid;
		//
		// WXMediaMessage msg = new WXMediaMessage(webpage);
		// msg.title = TITLE;
		// msg.description = TITLE;
		// // Bitmap thumb = BitmapFactory.decodeResource(getResources(),
		// // R.drawable.dengluzhuce_logo);
		// // Bitmap bmp = null;
		// // BitmapUtils bu = new BitmapUtils(this);
		// // String httpImageUrl =
		// // getResources().getString(R.string.test_image_server_url);
		// // try {
		// // bmp = BitmapFactory.decodeStream(new
		// // URL(httpImageUrl+img).openStream());
		// // } catch (MalformedURLException e) {
		// // // TODO Auto-generated catch block
		// // e.printStackTrace();
		// // } catch (IOException e) {
		// // // TODO Auto-generated catch block
		// // e.printStackTrace();
		// // }
		// // Bitmap thumb = compressionBitmap(
		// // BitmapGlobalConfig.getInstance(this,
		// //
		// CommonUtils.createSDCardDir()).getBitmapCache().getBitmapFromDiskCache(img,new
		// // BitmapDisplayConfig ()));
		//
		// try {
		// String imgStart = getResources().getString(
		// R.string.test_image_server_url);
		// String imgUrl = imgStart + mMoreImageBean.get(0).getIMAGE_URL();
		//
		// // Bitmap bmp = BitmapFactory.decodeFile(imgUrl);
		// InputStream is = CommonUtils.getImageStream(imgUrl);
		// Bitmap mBitmap = BitmapFactory.decodeStream(is);
		//
		// // Bitmap bmp = BitmapFactory.decodeStream(new
		// // URL(imgUrl).openStream());
		// // Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
		// // bmp.recycle();
		//
		// Bitmap thumbBmp = Bitmap
		// .createScaledBitmap(mBitmap, 150, 150, true);
		// mBitmap.recycle();
		// msg.thumbData = com.llg.help.Util.bmpToByteArray(
		// compressionBitmap(thumbBmp), true);
		// } catch (Exception e) {
		// // TODO: handle exception
		// e.printStackTrace();
		// }
		//
		// SendMessageToWX.Req req = new SendMessageToWX.Req();
		// req.transaction = buildTransaction("webpage");
		// req.message = msg;
		// if (type == 1) {
		// req.scene = SendMessageToWX.Req.WXSceneSession;
		// } else if (type == 2) {
		// req.scene = SendMessageToWX.Req.WXSceneTimeline;
		// }
		// // req.scene = isTimelineCb.isChecked() ?
		// // SendMessageToWX.Req.WXSceneTimeline :
		// // SendMessageToWX.Req.WXSceneSession;
		// boolean reqd = api.sendReq(req);
		// if (reqd) {
		// // redpackageMethod();
		// } else {
		// toast("分享失败！");
		// }
		// finish();
	}

	/**
	 * 网络操作相关的子线程
	 */
	Runnable networkTask = new Runnable() {

		@Override
		public void run() {
			// TODO
			// 在这里进行 http request.网络请求相关操作
			Message msg = new Message();
			msg.what = 111;
			Bundle data = new Bundle();
			data.putString("value", "请求结果");
			msg.setData(data);
			handler.sendMessage(msg);
		}
	};

	/**
	 * 下载图片文件
	 */
	private class downloadImgThread extends Thread {
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */

		private int type = 0;

		public downloadImgThread(int type) {
			// TODO Auto-generated constructor stub
			this.type = type;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			WXWebpageObject webpage = new WXWebpageObject();
			// webpage.webpageUrl = "http://www.fgqqg.com";
			webpage.webpageUrl = getResources().getString(R.string.buyserver)
					+ "m/ad?id=" + adInfoId + "&shareCusId="
					+ AppContext.userid;

			WXMediaMessage msg = new WXMediaMessage(webpage);
			msg.title = TITLE;
			msg.description = TITLE;
			// Bitmap thumb = BitmapFactory.decodeResource(getResources(),
			// R.drawable.dengluzhuce_logo);
			// Bitmap bmp = null;
			// BitmapUtils bu = new BitmapUtils(this);
			// String httpImageUrl =
			// getResources().getString(R.string.test_image_server_url);
			// try {
			// bmp = BitmapFactory.decodeStream(new
			// URL(httpImageUrl+img).openStream());
			// } catch (MalformedURLException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// Bitmap thumb = compressionBitmap(
			// BitmapGlobalConfig.getInstance(this,
			// CommonUtils.createSDCardDir()).getBitmapCache().getBitmapFromDiskCache(img,new
			// BitmapDisplayConfig ()));

			try {
				String imgStart = getResources().getString(
						R.string.test_image_server_url);
				String imgUrl = imgStart + mMoreImageBean.get(0).getIMAGE_URL();

				InputStream is = CommonUtils.getImageStream(imgUrl);
				Bitmap mBitmap = BitmapFactory.decodeStream(is);

				Bitmap thumbBmp = Bitmap.createScaledBitmap(mBitmap, 150, 150,
						true);
				mBitmap.recycle();
				msg.thumbData = com.llg.help.Util.bmpToByteArray(
						compressionBitmap(thumbBmp), true);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			SendMessageToWX.Req req = new SendMessageToWX.Req();
			req.transaction = buildTransaction("webpage");
			req.message = msg;
			if (type == 1) {
				req.scene = SendMessageToWX.Req.WXSceneSession;
			} else if (type == 2) {
				req.scene = SendMessageToWX.Req.WXSceneTimeline;
			}
			boolean reqd = api.sendReq(req);
			if (reqd) {
				// redpackageMethod();
			} else {
				toast("分享失败！");
			}

		}
	}

	private void redpackageMethod() {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("time", "" + System.currentTimeMillis());
		params.addQueryStringParameter("answer", select_answer);
		params.addQueryStringParameter("access_token",
				UserInformation.getAccess_token());
		params.addQueryStringParameter("adredId", adRedEnvelopId);
		params.addHeader("X-Requested-With", "XMLHttpRequest");
		AppContext.getHtmlUitls().xUtilsm(this, HttpMethod.POST,
				"m/ad/getRedPackage", params, new HttpCallback() {
					@Override
					public void onError(String msg) {
						// TODO Auto-generated method stub
						handler.sendEmptyMessage(0);
					}

					@Override
					public void onBack(JSONObject json) {
						// TODO Auto-generated method stub
						try {
							if (json.getBoolean("success")) {
								if (json.getJSONObject("attributes")
										.getJSONObject("adRedEnvelopItem") != null) {
									if (json.getJSONObject("attributes")
											.getJSONObject("adRedEnvelopItem")
											.getString("co") != null
											&& json.getJSONObject("attributes")
													.getJSONObject(
															"adRedEnvelopItem")
													.getString("co").length() > 0) {

										co = json
												.getJSONObject("attributes")
												.getJSONObject(
														"adRedEnvelopItem")
												.getString("co");
									}
									if (json.getJSONObject("attributes")
											.getJSONObject("adRedEnvelopItem")
											.getString("money") != null
											&& json.getJSONObject("attributes")
													.getJSONObject(
															"adRedEnvelopItem")
													.getString("money")
													.length() > 0) {

										money = json
												.getJSONObject("attributes")
												.getJSONObject(
														"adRedEnvelopItem")
												.getString("money");
									}

								}
							} else {
								if (json.getString("errorCode").equals(
										MyFormat.NOT_LOGIN)) {
									toast(json.getString("msg"));
									setRefreshListtener(new Refresh() {

										@Override
										public void refreshRequst(
												String access_token) {
											// TODO Auto-generated method
											// stub
											getRedPackage();
										}
									});
									RefeshToken();

								}
								toast(json.getString("msg"));
								// mesg = json.getString("msg");

							}
							// 分享成功
							handler.sendEmptyMessage(1);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	/** 剪裁图片 */
	private Bitmap compressionBitmap(Bitmap bitmap) {
		int wid = 1;
		int hei = 1;
		int newWid = 100;
		int newHei = 100;
		if (bitmap != null) {
			wid = bitmap.getWidth();
			hei = bitmap.getHeight();

		}
		// 设置缩放比
		float scaleW = (float) newWid / wid;
		float scaleH = (float) newHei / hei;
		// 取得要想缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleW, scaleH);
		Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, wid, hei, matrix,
				true);

		return newBitmap;
	}

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}

	private Bitmap bitmap;
	private int type;

	/**
	 * 
	 */
	@Override
	public void setMessage(String message, int type) {
		// TODO Auto-generated method stub
		if (message.equals("分享到朋友圈")) {
			toast(message);
			// if (saveImg == null) {
			saveImg = new SaveImg(img, handler, bitmap, this);
			// }
			this.type = type = 2;
			// shareUrl(2);
		} else if (message.equals("分享给微信好友")) {
			// if (saveImg == null) {
			saveImg = new SaveImg(img, handler, bitmap, this);
			// }
			this.type = type = 1;
			toast(message);
			// shareUrl(1);
		} else if (message.equals("转发为我的广告")) {
			doForwardAd();
		}

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch (checkedId) {
		case R.id.tb1:
			select_answer = rb1.getText().toString();
			break;
		case R.id.tb2:
			select_answer = rb2.getText().toString();

			break;
		case R.id.tb3:
			select_answer = rb3.getText().toString();

			break;
		case R.id.tb4:
			select_answer = rb4.getText().toString();

			break;

		default:
			break;
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
