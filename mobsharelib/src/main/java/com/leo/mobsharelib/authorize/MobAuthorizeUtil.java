package com.leo.mobsharelib.authorize;


import android.util.Log;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

public final class MobAuthorizeUtil {
    private static final String TAG = MobAuthorizeUtil.class.getSimpleName();

    public static void authorize(Platform plat, PlatformActionListener listener) {
        // 关闭SSO授权(true)
        if (null != plat && null != listener) {
            plat.SSOSetting(false);
            plat.setPlatformActionListener(listener);
            plat.showUser(null);
        } else {
            Log.e(TAG, "Platform or PlatformActionListener is null");
        }
    }
}
