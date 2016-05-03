package com.smartandroid.sa.verifi;

import android.content.Context;

public class NotEmptyVerifior extends AbstractVerifior {

	private int mErrorMessage;

	public NotEmptyVerifior(Context c) {
		super(c);
		mErrorMessage = c.getResources().getIdentifier("validator_empty",
				"string", c.getPackageName());
	}

	public NotEmptyVerifior(Context c, int errorMessage) {
		super(c);
		mErrorMessage = errorMessage;
	}

	@Override
	public boolean isValid(String value) {
		if (value != null) {
			return value.length() > 0;
		} else {
			return false;
		}
	}

	@Override
	public String getMessage() {
		return mContext.getString(mErrorMessage);
	}

}
