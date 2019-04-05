package com.leo.mobsharelib.share;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * Created by LEO
 * on 2018/2/6.
 */

public class MobShareListener implements PlatformActionListener {
    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
    }

    @Override
    public void onCancel(Platform platform, int i) {
    }
}
