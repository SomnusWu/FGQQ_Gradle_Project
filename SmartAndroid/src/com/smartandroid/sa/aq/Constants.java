/*
 * Copyright 2011 - AndroidQuery.com (tinyeeliu@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.smartandroid.sa.aq;

public interface Constants {


	String VERSION = "0.23.16";
	
	int LAYER_TYPE_SOFTWARE = 1;
	int LAYER_TYPE_HARDWARE = 2;
	int FLAG_HARDWARE_ACCELERATED = 0x01000000;
	int FLAG_ACTIVITY_NO_ANIMATION = 0x00010000;
	int OVER_SCROLL_ALWAYS = 0;
	int OVER_SCROLL_IF_CONTENT_SCROLLS = 1;
	int OVER_SCROLL_NEVER = 2;
	int INVISIBLE = -1;
	int GONE = -2;
	int PRESET = -3;
	int FADE_IN = -1;
	int FADE_IN_NETWORK = -2;
	int FADE_IN_FILE = -3;
	
	int CACHE_DEFAULT = 0;
	int CACHE_PERSISTENT = 1;
	
	int METHOD_GET = 0;
	int METHOD_POST = 1;
	int METHOD_DELETE = 2;
	int METHOD_PUT = 3;
	int METHOD_DETECT = 4;
	
	int TAG_URL = 0x40FF0001;
	int TAG_SCROLL_LISTENER = 0x40FF0002;
	int TAG_LAYOUT = 0x40FF0003;
	int TAG_NUM = 0x40FF0004;
	
	float RATIO_PRESERVE = Float.MAX_VALUE;
	float ANCHOR_DYNAMIC = Float.MAX_VALUE;
	
	String ACTIVE_ACCOUNT = "aq.account";
	
	String AUTH_READER = "g.reader";
	String AUTH_PICASA = "g.lh2";
	String AUTH_SPREADSHEETS = "g.wise";
	String AUTH_DOC_LIST = "g.writely";
	String AUTH_YOUTUBE = "g.youtube";
	String AUTH_ANALYTICS = "g.analytics";
	String AUTH_BLOGGER = "g.blogger";
	String AUTH_CALENDAR = "g.cl";
	//public static final String AUTH_BUZZ = "g.buzz";
	String AUTH_CONTACTS = "g.cp";
	//public static final String AUTH_FINANCE = "g.finance";
	String AUTH_MAPS = "g.local";

	String POST_ENTITY = "%entity";
	
	int SDK_INT = android.os.Build.VERSION.SDK_INT;
}
