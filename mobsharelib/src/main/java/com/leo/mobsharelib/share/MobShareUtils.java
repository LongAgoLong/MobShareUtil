package com.leo.mobsharelib.share;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Create by LEO
 * on 2018/8/2
 * at 11:23
 * in MoeLove Company
 * 分享工具类
 */
public final class MobShareUtils {
    private static String shareSiteUrl = "";
    private static String shareSite = "";
    private static String shareDefaultImgUrl = "";
    private static String shareWxMinProgram = "";

    public static void setShareSiteUrl(String shareSiteUrl) {
        MobShareUtils.shareSiteUrl = shareSiteUrl;
    }

    public static void setShareSite(String shareSite) {
        MobShareUtils.shareSite = shareSite;
    }

    public static void setShareDefaultImgUrl(String shareDefaultImgUrl) {
        MobShareUtils.shareDefaultImgUrl = shareDefaultImgUrl;
    }

    public static void setShareWxMinProgram(String shareWxMinProgram) {
        MobShareUtils.shareWxMinProgram = shareWxMinProgram;
    }

    /**
     * 自定义UI分享的实现
     * @param context
     * @param title
     * @param content
     * @param image
     * @param shareUrl
     * @return OnekeyShare调用setPlatform设置分享到指定平台，然后调用show；如果需要分享微信小程序调用setShareWXMiniProgram方法
     */
    public static OnekeyShare share(Context context, @NonNull String title, @NonNull String content, String image, @NonNull String shareUrl) {
        if (title.length() > 20) {
            title = title.substring(0, 20) + "...";
        }
        if (content.length() > 20) {
            content = content.substring(0, 20) + "...";
        }
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();// 关闭sso授权
        oks.setTitle(title);// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitleUrl(shareUrl);
        oks.setText(content);// text是分享文本，所有平台都需要这个字段
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        if (TextUtils.isEmpty(image) || isGif(image)) {
            if (!TextUtils.isEmpty(shareDefaultImgUrl)) {
                oks.setImageUrl(shareDefaultImgUrl);
            }
        } else {
            oks.setImageUrl(image);
        }
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(shareUrl);
        oks.setSite(shareSite);// site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSiteUrl(shareSiteUrl);// siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setCallback(new MobShareListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                super.onComplete(platform, i, hashMap);
                Toast.makeText(context, "分享成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                super.onError(platform, i, throwable);
                Toast.makeText(context, "分享失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                super.onCancel(platform, i);
                Toast.makeText(context, "取消分享", Toast.LENGTH_SHORT).show();
            }
        });
        return oks;
    }

    private static boolean isGif(String imgPath) {
        if (TextUtils.isEmpty(imgPath)) {
            return false;
        }
        try {
            int lastIndexOf = imgPath.lastIndexOf(".");
            int endIndexOf = lastIndexOf + 4;
            if (-1 != lastIndexOf && imgPath.length() >= endIndexOf) {
                String extension = imgPath.substring(lastIndexOf, endIndexOf).toLowerCase();
                return ".gif".equals(extension);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param oks    OnekeyShare对象
     * @param wxPath 小程序中的页面的路径
     */
    private static void setShareWXMiniProgram(@NonNull OnekeyShare oks, @NonNull String wxPath) {
        oks.setShareContentCustomizeCallback((platform, paramsToShare) -> {
            if (platform.getName().equals("Wechat")) {
                paramsToShare.setShareType(Platform.SHARE_WXMINIPROGRAM);
                paramsToShare.setWxUserName(shareWxMinProgram);
                paramsToShare.setWxPath(wxPath);
            }
        });
    }
}
