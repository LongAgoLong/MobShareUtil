package com.leo.mobsharelib.authorize;

import android.support.annotation.NonNull;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

public final class MobAuthorizeUtil {

    public static void authorize(@NonNull Platform plat, @NonNull PlatformActionListener listener) {
        // 关闭SSO授权(true)
        plat.SSOSetting(false);
        plat.setPlatformActionListener(listener);
        plat.showUser(null);
    }
}
